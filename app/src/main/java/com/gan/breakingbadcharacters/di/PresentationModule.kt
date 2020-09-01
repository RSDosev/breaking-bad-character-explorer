package com.gan.breakingbadcharacters.di

import com.gan.breakingbadcharacters.ui.characterdetails.CharacterDetailsViewModel
import com.gan.breakingbadcharacters.ui.characterslist.CharactersListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { CharactersListViewModel(get()) }
    viewModel { CharacterDetailsViewModel(get()) }
}