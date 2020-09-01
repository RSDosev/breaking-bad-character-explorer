package com.gan.breakingbadcharacters.ui

import androidx.lifecycle.Observer
import com.gan.breakingbadcharacters.domain.CharactersUseCase
import com.gan.breakingbadcharacters.ui.characterslist.CharactersListViewModel
import com.gan.breakingbadcharacters.ui.utils.ViewState
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.gan.breakingbadcharacters.data.repository.Result
import com.gan.breakingbadcharacters.data.repository.Result.Success
import com.gan.breakingbadcharacters.mock.DummyData
import com.gan.breakingbadcharacters.mock.DummyData.Companion.dummyCharacter1
import com.gan.breakingbadcharacters.mock.DummyData.Companion.dummyCharacter2

class CharactersListViewModelTest : BaseViewModelTest() {

    private val charactersUseCase = mockk<CharactersUseCase>()

    private val observer = mockk<Observer<ViewState>>(relaxUnitFun = true)

    private val viewModel = CharactersListViewModel(charactersUseCase)

    @Before
    override fun setup() {
        super.setup()
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `given success, when getting characters list, then return ViewState Successful`() {

        //given
        val characters = listOf(dummyCharacter1, dummyCharacter2)
        coEvery { charactersUseCase.getCharacters() } returns flowOf(Success(characters))

        //when
        viewModel.getCharacters()

        //then
        val slot = slot<ViewState>()

        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(capture(slot))
        }

        Assert.assertNotNull(slot.captured.data)

        (slot.captured.data)?.let {
            assertEquals(characters, it)
        }

        coVerify { viewModel.getCharacters() }
    }

    @Test
    fun `given success, when getting characters list, then return ViewState Successful and filter`() {

        //given
        val characters = listOf(dummyCharacter1, dummyCharacter2)
        coEvery { charactersUseCase.getCharacters() } returns flowOf(Success(characters))

        //when
        viewModel.getCharacters()
        viewModel.filterByName("Char")
        viewModel.filterBySeason(3)

        //then
        val slot = slot<ViewState>()

        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(capture(slot))
        }

        Assert.assertNotNull(slot.captured.data)

        (slot.captured.data)?.let {
            assertEquals(listOf(dummyCharacter2), it)
        }

        coVerify { viewModel.getCharacters() }
    }

    @Test
    fun `given error, when getting characters list, then return ViewState Error`() {

        //given
        val errorMsg = "errorMsg"
        coEvery { charactersUseCase.getCharacters() } returns flowOf(
            Result.Error.BasicError(
                errorMsg = errorMsg
            )
        )

        //when
        viewModel.getCharacters()

        //then
        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(ViewState.Error(errorMsg))
        }
    }

    @Test
    fun `given network error, when getting characters list, then return ViewState NetworkError`() {

        //given
        coEvery { charactersUseCase.getCharacters() } returns flowOf(Result.Error.NetworkError)

        //when
        viewModel.getCharacters()

        //then
        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(ViewState.NetworkError())
        }
    }
}