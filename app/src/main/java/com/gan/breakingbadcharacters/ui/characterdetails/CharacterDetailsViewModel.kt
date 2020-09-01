package com.gan.breakingbadcharacters.ui.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.data.repository.Result
import com.gan.breakingbadcharacters.domain.CharactersUseCase
import com.gan.breakingbadcharacters.ui.utils.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class CharacterDetailsViewModel constructor(
    private val charactersUseCase: CharactersUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            charactersUseCase.getCharacter(characterId)
                .collect {
                    handleResult(it)
                }
        }
    }

    private fun handleResult(result: Result<BreakingBadCharacter?>) {
        _viewState.value = when (result) {
            is Result.Success -> ViewState.Loaded(result.data)
            is Result.Error.BasicError -> ViewState.Error(result.errorMsg)
            Result.Error.NetworkError -> ViewState.NetworkError()
            is Result.Error.ApiCallError -> ViewState.Error(result.errorMsg)
        }
    }
}
