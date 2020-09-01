package com.gan.breakingbadcharacters.domain

import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import kotlinx.coroutines.flow.Flow
import com.gan.breakingbadcharacters.data.repository.Result

interface CharactersUseCase {
    suspend fun getCharacters(): Flow<Result<List<BreakingBadCharacter>>>
    suspend fun getCharacter(characterId: Int): Flow<Result<BreakingBadCharacter?>>
}