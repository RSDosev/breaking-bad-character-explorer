package com.gan.breakingbadcharacters.ui.characterslist

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

open class CharactersListViewModel constructor(
    private val charactersUseCase: CharactersUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private var allCharacters: List<BreakingBadCharacter>? = null
    private var nameFilter = ""
    private var seasonFilter = 0

    fun getCharacters() {
        _viewState.value = ViewState.Loading()

        viewModelScope.launch {
            charactersUseCase.getCharacters().collect { result ->
                handleResult(result)
            }
        }
    }

    fun filterByName(name: String) {
        allCharacters ?: return
        nameFilter = name
        _viewState.value = ViewState.Loaded(filterCharacters())
    }

    fun filterBySeason(season: Int) {
        allCharacters ?: return
        seasonFilter = season
        _viewState.value = ViewState.Loaded(filterCharacters())
    }

    private fun filterCharacters() =
        allCharacters
            ?.filter { nameFilter.isEmpty() || it.name.contains(nameFilter, ignoreCase = true) }
            ?.filter { seasonFilter == 0 || it.seasonsAppearance.contains(seasonFilter) }

    private fun handleResult(result: Result<List<BreakingBadCharacter>>) {
        _viewState.value = when (result) {
            is Result.Success -> {
                allCharacters = result.data
                ViewState.Loaded(filterCharacters())
            }
            is Result.Error.BasicError -> ViewState.Error(result.errorMsg)
            Result.Error.NetworkError -> ViewState.NetworkError()
            is Result.Error.ApiCallError -> ViewState.Error(result.errorMsg)
        }
    }
}
