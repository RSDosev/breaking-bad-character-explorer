package com.gan.breakingbadcharacters.di

import com.gan.breakingbadcharacters.domain.CharactersInteractor
import com.gan.breakingbadcharacters.domain.CharactersUseCase
import com.gan.breakingbadcharacters.domain.CoroutineContextProvider
import org.koin.dsl.module

val domainModule = module {
    single { CoroutineContextProvider() }

    factory<CharactersUseCase> { CharactersInteractor(get(), get()) }
}