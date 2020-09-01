package com.gan.breakingbadcharacters.ui.characterslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.gan.breakingbadcharacters.R
import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.ui.base.BaseViewModelFragment
import com.gan.breakingbadcharacters.ui.characterdetails.CharacterDetailsFragment
import com.gan.breakingbadcharacters.ui.utils.NoNetworkException
import com.gan.breakingbadcharacters.ui.utils.ViewState
import com.gan.breakingbadcharacters.ui.utils.ViewStateType.*
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.fragment_characters_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersListFragment : BaseViewModelFragment() {

    private val viewModel: CharactersListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCharacters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            renderViewState(it)
        })
    }

    private fun initViews() {
        charactersView.apply {
            adapter = CharactersAdapter(
                selectedListener = { character ->
                    navigateTo(action = CharacterDetailsFragment.getNavDirection(character.id))
                },
                imageLoader = imageLoader
            )
            itemAnimator = LandingAnimator()
        }
        nameFilterView.addTextChangedListener {
            viewModel.filterByName(it.toString())
        }
        seasonFilterView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf("All", "1","2","3","4","5"))
        seasonFilterView.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.filterBySeason(position)
            }
        }
        seasonFilterView.setSelection(0)
    }

    private fun renderViewState(viewState: ViewState?) {
        viewState ?: return

        when (viewState.type) {
            LOADING -> {
                showLoading()
            }
            COMPLETED -> {
                hideLoading()
                showCharacters(
                    (viewState.data as? List<BreakingBadCharacter>?) ?: emptyList()
                )
            }
            ERROR -> {
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

    private fun showCharacters(characters: List<BreakingBadCharacter>) {
        (charactersView.adapter as? CharactersAdapter)?.submitList(characters)
        charactersView.smoothScrollToPosition(0)
    }

    private fun hideLoading() {
        loadingView.isVisible = false
    }

    private fun showLoading() {
        loadingView.isVisible = true
    }
}
