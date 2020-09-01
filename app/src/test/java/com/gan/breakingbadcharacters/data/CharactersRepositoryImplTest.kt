package com.gan.breakingbadcharacters.data

import com.gan.breakingbadcharacters.data.repository.Result
import com.gan.breakingbadcharacters.data.repository.Result.Success
import com.gan.breakingbadcharacters.data.repository.characters.CharactersRepositoryImpl
import com.gan.breakingbadcharacters.data.source.local.cache.MemoryCache
import com.gan.breakingbadcharacters.data.source.remote.BreakingBadCharactersAPIService
import com.gan.breakingbadcharacters.mock.DummyData.Companion.dummyCharacter1
import com.gan.breakingbadcharacters.mock.DummyData.Companion.dummyCharacter2
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

internal class CharactersRepositoryImplTest {

    private val memoryCache = MemoryCache()
    private val service = mockk<BreakingBadCharactersAPIService>()
    private var repository = CharactersRepositoryImpl(memoryCache, service)

    @Test
    fun `given success response, when getting characters list then return Result Success`() {
        runBlocking {

            //given
            val characters = listOf(dummyCharacter1, dummyCharacter2)
            coEvery { service.getCharactersList() } returns characters

            // when
            val result = repository.getCharactersList().toList().first()

            // then
            coVerifyOrder {
                service.getCharactersList()
            }

            assertTrue(result is Success)
            assertEquals(characters, (result as Success).data)
        }
    }

    @Test
    fun `given api call fail, when getting characters list then return Result Error`() {
        runBlocking {
            // given
            coEvery { service.getCharactersList() } throws IOException()

            // when
            val result = repository.getCharactersList().toList().first()

            // then
            coVerifyOrder {
                service.getCharactersList()
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given success response, when getting characters list, then return Result Success, then trying again returns cached`() {
        runBlocking {
            // when
            val firstApiResponse = listOf(dummyCharacter1, dummyCharacter2)

            coEvery { service.getCharactersList() } returns firstApiResponse

            val firstApiCallResult = repository.getCharactersList().toList().first()

            // given
            val secondApiResponse = listOf(dummyCharacter2, dummyCharacter1)

            coEvery { service.getCharactersList() } returns secondApiResponse

            val (cachedResult, secondApiCallResult) = (repository.getCharactersList().toList())

            // then
            coVerify(exactly = 2) {
                service.getCharactersList()
            }

            assertTrue(firstApiCallResult is Success)
            assertTrue((firstApiCallResult as? Success)?.data == firstApiResponse)
            assertTrue(cachedResult is Success)
            assertTrue((cachedResult as? Success)?.data == firstApiResponse)
            assertTrue(secondApiCallResult is Success)
            assertTrue((secondApiCallResult as? Success)?.data != firstApiCallResult)
        }
    }
}