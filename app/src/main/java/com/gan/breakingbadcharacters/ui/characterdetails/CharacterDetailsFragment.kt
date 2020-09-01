package com.gan.breakingbadcharacters.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.NavGraphNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gan.breakingbadcharacters.R
import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.ui.base.BaseViewModelFragment
import com.gan.breakingbadcharacters.ui.characterslist.CharactersAdapter
import com.gan.breakingbadcharacters.ui.characterslist.CharactersListFragmentDirections
import com.gan.breakingbadcharacters.ui.characterslist.CharactersListViewModel
import com.gan.breakingbadcharacters.ui.utils.NoNetworkException
import com.gan.breakingbadcharacters.ui.utils.ViewState
import com.gan.breakingbadcharacters.ui.utils.ViewStateType
import kotlinx.android.synthetic.main.fragment_character_details.*
import org.koin.android.viewmodel.ext.android.viewModel

const val EXTRA_KEY_CHARACTER_ID = "EXTRA_KEY_CHARACTER_ID"

class CharacterDetailsFragment : BaseViewModelFragment() {

    private val viewModel: CharacterDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    private fun getCharacterId() =
        arguments?.let { CharacterDetailsFragmentArgs.fromBundle(it).characterId } ?: 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_KEY_CHARACTER_ID, getCharacterId())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            renderViewState(it)
        })

        viewModel.getCharacter(savedInstanceState?.getInt(EXTRA_KEY_CHARACTER_ID) ?: getCharacterId())
    }

    private fun initViews() {
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun renderViewState(viewState: ViewState?) {
        viewState ?: return

        when (viewState.type) {
            ViewStateType.LOADING -> {
                showLoading()
            }
            ViewStateType.COMPLETED -> {
                hideLoading()
                showCharacter(viewState.data as? BreakingBadCharacter?)
            }
            ViewStateType.ERROR -> {
                hideLoading()
                when (viewState.error) {
                    is NoNetworkException -> showNoConnectionAvailable()
                    else -> showError(viewState.error?.message)
                }
            }
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showNoConnectionAvailable() {
        Toast.makeText(requireContext(), "No network connection!", Toast.LENGTH_LONG).show()
    }

    private fun showCharacter(character: BreakingBadCharacter?) {
        character ?: return
        imageLoader.load(character.avatarUrl, avatarView)
        nameView.text = character.name
        birthdayView.text = character.birthday
        occupationView.text = character.occupation.joinToString(separator = ", ")
        statusView.text = character.status
        nicknameView.text = character.nickname
        seasonsView.text = character.seasonsAppearance.joinToString(separator = ", ")
    }

    private fun hideLoading() {
        loadingView.isVisible = false
    }

    private fun showLoading() {
        loadingView.isVisible = true
    }

    companion object {
        fun getNavDirection(characterId: Int): NavDirections =
            CharactersListFragmentDirections.actionListToDetail().setCharacterId(characterId)
    }
}
