package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel

class GetFavouriteCharactersUseCaseImpl(
    private val repository: CharacterRepository,
) : GetFavouriteCharactersUseCase {
    override suspend fun invoke(): List<CharacterModel> {
        return repository.getFavouriteCharacters()
    }
}
