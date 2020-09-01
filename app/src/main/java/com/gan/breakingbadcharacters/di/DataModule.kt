package com.gan.breakingbadcharacters.di

import com.gan.breakingbadcharacters.data.repository.characters.CharactersRepository
import com.gan.breakingbadcharacters.data.repository.characters.CharactersRepositoryImpl
import com.gan.breakingbadcharacters.data.source.local.cache.DEFAULT_CACHE_SIZE
import com.gan.breakingbadcharacters.data.source.local.cache.MemoryCache
import org.koin.dsl.module

val dataModule = module {
    single { MemoryCache(DEFAULT_CACHE_SIZE) }
    factory<CharactersRepository> { CharactersRepositoryImpl(get(), get()) }
}