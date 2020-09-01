package com.gan.breakingbadcharacters.di

import com.gan.breakingbadcharacters.data.source.remote.BASE_SERVICE_PATH
import com.gan.breakingbadcharacters.data.source.remote.BreakingBadCharactersAPIService
import com.gan.breakingbadcharacters.data.source.remote.client.RetrofitClient
import com.gan.breakingbadcharacters.ui.utils.ImageLoader
import com.gan.breakingbadcharacters.ui.utils.ImageLoaderImpl
import org.koin.dsl.module

val applicationModule = module {
    single<BreakingBadCharactersAPIService> {
        RetrofitClient(BASE_SERVICE_PATH).breakingBadCharactersAPIService
    }

    single<ImageLoader> { ImageLoaderImpl(get()) }
}
