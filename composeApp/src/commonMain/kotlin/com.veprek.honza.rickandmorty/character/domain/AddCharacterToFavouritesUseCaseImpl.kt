package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel

class AddCharacterToFavouritesUseCaseImpl(
    private val repository: CharacterRepository,
) : AddCharacterToFavouritesUseCase {
    override suspend fun invoke(character: CharacterModel) {
        repository.addCharacterToFavourites(character)
    }
}
