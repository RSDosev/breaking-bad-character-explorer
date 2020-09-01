package com.gan.breakingbadcharacters.data.repository.characters

import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.data.repository.Result
import com.gan.breakingbadcharacters.data.repository.apiCall
import com.gan.breakingbadcharacters.data.source.local.cache.MemoryCache
import com.gan.breakingbadcharacters.data.source.remote.BreakingBadCharactersAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImpl(
    private val memoryCache: MemoryCache,
    private val apiService: BreakingBadCharactersAPIService
) : CharactersRepository {

    private var cache: List<BreakingBadCharacter>?
        get() = memoryCache.load<List<BreakingBadCharacter>>(CHARACTERS_LIST_CACHE_KEY)
        set(value) {
            if (value != null) memoryCache.save(CHARACTERS_LIST_CACHE_KEY, value)
        }

    override suspend fun getCharactersList(): Flow<Result<List<BreakingBadCharacter>>> = flow {
        cache?.let {
            emit(Result.Success(it))
        }

        val apiResult =
            apiCall { apiService.getCharactersList() }

        if (apiResult is Result.Success) {
            cache = apiResult.data
            emit(
                Result.Success(
                    apiResult.data
                )
            )
        } else if (cache == null) {
            emit(apiResult)
        }
    }

    companion object {
        const val CHARACTERS_LIST_CACHE_KEY = "CHARACTERS_LIST_CACHE"
    }
}
