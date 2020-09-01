package com.gan.breakingbadcharacters

import android.app.Application
import com.gan.breakingbadcharacters.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BreakingBadCharactersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjectionFramework()
    }

    private fun initDependencyInjectionFramework() {
        startKoin {
            androidLogger()
            androidContext(this@BreakingBadCharactersApplication)
            modules(
                listOf(
                    applicationModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}