package com.gan.breakingbadcharacters

import com.gan.breakingbadcharacters.domain.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val Main: CoroutineContext by lazy { Dispatchers.Unconfined }
    override val IO: CoroutineContext by lazy { Dispatchers.Unconfined }
}