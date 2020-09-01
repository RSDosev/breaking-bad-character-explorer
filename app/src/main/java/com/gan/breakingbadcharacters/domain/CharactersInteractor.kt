package com.gan.breakingbadcharacters.domain

import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.data.repository.Result
import com.gan.breakingbadcharacters.data.repository.Result.Success
import com.gan.breakingbadcharacters.data.repository.characters.CharactersRepository
import kotlinx.coroutines.flow.*


class CharactersInteractor(
    private val charactersRepository: CharactersRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : CharactersUseCase {

    override suspend fun getCharacters() =
        charactersRepository.getCharactersList().flowOn(coroutineContextProvider.IO)

    override suspend fun getCharacter(characterId: Int): Flow<Result<BreakingBadCharacter?>> =
        getCharacters()
            .map { result ->
                if (result is Success) {
                    Success(result.data.firstOrNull { it.id == characterId })
                } else result
            }
            .map { it as Result<BreakingBadCharacter?> }
            .take(1)

}