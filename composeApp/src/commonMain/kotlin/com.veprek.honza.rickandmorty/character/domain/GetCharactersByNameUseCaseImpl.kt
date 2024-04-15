package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.StatusFilter

class GetCharactersByNameUseCaseImpl(
    private val repository: CharacterRepository,
) : GetCharactersByNameUseCase {
    override suspend fun invoke(name: String, filter: StatusFilter): ResultWrapper<List<CharacterModel>> {
        return repository.getCharactersByName(name, filter)
    }
}
