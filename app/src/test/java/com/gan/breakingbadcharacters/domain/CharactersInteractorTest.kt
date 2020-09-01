package com.gan.breakingbadcharacters.domain

import com.gan.breakingbadcharacters.TestCoroutineContextProvider
import com.gan.breakingbadcharacters.data.repository.characters.CharactersRepository
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.gan.breakingbadcharacters.data.repository.Result
import com.gan.breakingbadcharacters.data.repository.Result.Success
import com.gan.breakingbadcharacters.mock.DummyData
import com.gan.breakingbadcharacters.mock.DummyData.Companion.dummyCharacter1
import com.gan.breakingbadcharacters.mock.DummyData.Companion.dummyCharacter2
import kotlinx.coroutines.flow.toList

internal class CharactersInteractorTest {

    private val charactersRepository = mockk<CharactersRepository>()
    private val charactersInteractor =
        CharactersInteractor(charactersRepository, TestCoroutineContextProvider())

    private val ERROR_MSG = "errorMsg"
    private val characterId = 1

    @Test
    fun `given success when getting getting characters list then return Result Success`() {
        runBlocking {
            // when
            val characters = listOf(dummyCharacter1, dummyCharacter2)
            coEvery { charactersRepository.getCharactersList() } returns flowOf(
                Success(characters)
            )

            val result = charactersInteractor.getCharacters().toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Success)
            assertTrue((result as? Success)?.data == characters)
        }
    }

    @Test
    fun `given error when getting getting characters list then return Result Error`() {
        runBlocking {
            // when
            coEvery { charactersRepository.getCharactersList() } returns flowOf(
                Result.Error.BasicError(
                    errorMsg = ERROR_MSG
                )
            )

            val result = charactersInteractor.getCharacters().toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Result.Error)
            assertEquals(ERROR_MSG, (result as? Result.Error.BasicError)?.errorMsg)
        }
    }

    @Test
    fun `given network error when getting characters list then return Result NetworkError`() {
        runBlocking {
            // when
            coEvery { charactersRepository.getCharactersList() } returns flowOf(Result.Error.NetworkError)

            val result = charactersInteractor.getCharacters().toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given other errors when getting characters list then return Result GenericError`() {
        runBlocking {
            // when
            coEvery { charactersRepository.getCharactersList() } returns flowOf(Result.Error.ApiCallError())

            val result = charactersInteractor.getCharacters().toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Result.Error.ApiCallError)
        }
    }

    @Test
    fun `given success when getting getting character then return Result Success`() {
        runBlocking {
            // when
            val characters = listOf(dummyCharacter1, dummyCharacter2)
            coEvery { charactersRepository.getCharactersList() } returns flowOf(
                Success(characters)
            )

            val result = charactersInteractor.getCharacter(characterId).toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Success)
            assertTrue((result as? Success)?.data == characters.first())
        }
    }

    @Test
    fun `given error when getting getting character then return Result Error`() {
        runBlocking {
            // when
            coEvery { charactersRepository.getCharactersList() } returns flowOf(
                Result.Error.BasicError(
                    errorMsg = ERROR_MSG
                )
            )

            val result = charactersInteractor.getCharacter(characterId).toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Result.Error)
            assertEquals(ERROR_MSG, (result as? Result.Error.BasicError)?.errorMsg)
        }
    }

    @Test
    fun `given network error when getting character then return Result NetworkError`() {
        runBlocking {
            // when
            coEvery { charactersRepository.getCharactersList() } returns flowOf(Result.Error.NetworkError)

            val result = charactersInteractor.getCharacter(characterId).toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given other errors when getting character then return Result GenericError`() {
        runBlocking {
            // when
            coEvery { charactersRepository.getCharactersList() } returns flowOf(Result.Error.ApiCallError())

            val result = charactersInteractor.getCharacter(characterId).toList().first()

            // then
            coVerifyOrder {
                charactersRepository.getCharactersList()
            }

            assertTrue(result is Result.Error.ApiCallError)
        }
    }
}