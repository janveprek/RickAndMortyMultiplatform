package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

internal class GetAllCharactersUseCaseImpl(
    private val repository: CharacterRepository,
) : GetAllCharactersUseCase {
    override suspend fun invoke(page: Long): ResultWrapper<List<CharacterModel>> {
        return repository.getAllCharacters(page)
    }
}
