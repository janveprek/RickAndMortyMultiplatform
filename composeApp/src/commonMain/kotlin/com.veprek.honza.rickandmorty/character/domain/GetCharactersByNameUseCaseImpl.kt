package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

class GetCharactersByNameUseCaseImpl(
    private val repository: CharacterRepository,
) : GetCharactersByNameUseCase {
    override suspend fun invoke(name: String): ResultWrapper<List<CharacterModel>> {
        return repository.getCharactersByName(name)
    }
}
