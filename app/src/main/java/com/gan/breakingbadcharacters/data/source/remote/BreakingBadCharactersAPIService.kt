package com.gan.breakingbadcharacters.data.source.remote

import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import retrofit2.http.GET

const val BASE_SERVICE_PATH = "https://breakingbadapi.com"
const val BASE_API_PATH = "/api"

interface BreakingBadCharactersAPIService {

    @GET("$BASE_API_PATH/characters")
    suspend fun getCharactersList(): List<BreakingBadCharacter>
}
