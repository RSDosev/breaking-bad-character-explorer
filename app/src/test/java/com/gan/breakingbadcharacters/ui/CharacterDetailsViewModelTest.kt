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
import com.gan.breakingbadcharacters.ui.characterdetails.CharacterDetailsViewModel

class CharacterDetailsViewModelTest : BaseViewModelTest() {

    private val charactersUseCase = mockk<CharactersUseCase>()

    private val observer = mockk<Observer<ViewState>>(relaxUnitFun = true)

    private val viewModel = CharacterDetailsViewModel(charactersUseCase)
    private val characterId = 1

    @Before
    override fun setup() {
        super.setup()
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `given success, when getting character, then return ViewState Successful`() {

        //given
        coEvery { charactersUseCase.getCharacter(characterId) } returns flowOf(Success(dummyCharacter1))

        //when
        viewModel.getCharacter(characterId)

        //then
        val slot = slot<ViewState>()

        verifyOrder {
            observer.onChanged(capture(slot))
        }

        Assert.assertNotNull(slot.captured.data)

        (slot.captured.data)?.let {
            assertEquals(it, dummyCharacter1)
        }

        coVerify { viewModel.getCharacter(characterId) }

    }

    @Test
    fun `given error, when getting character, then return ViewState Error`() {

        //given
        val errorMsg = "errorMsg"
        coEvery { charactersUseCase.getCharacter(characterId) } returns flowOf(
            Result.Error.BasicError(
                errorMsg = errorMsg
            )
        )

        //when
        viewModel.getCharacter(characterId)

        //then
        verifyOrder {
            observer.onChanged(ViewState.Error(errorMsg))
        }
    }

    @Test
    fun `given network error, when getting character, then return ViewState NetworkError`() {

        //given
        coEvery { charactersUseCase.getCharacter(characterId) } returns flowOf(Result.Error.NetworkError)

        //when
        viewModel.getCharacter(characterId)

        //then
        verifyOrder {
            observer.onChanged(ViewState.NetworkError())
        }
    }
}