package com.gan.breakingbadcharacters.data.repository.characters

import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.data.repository.Result
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharactersList(): Flow<Result<List<BreakingBadCharacter>>>
}
