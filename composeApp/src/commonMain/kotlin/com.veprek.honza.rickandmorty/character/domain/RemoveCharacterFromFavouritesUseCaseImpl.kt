package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel

class RemoveCharacterFromFavouritesUseCaseImpl(
    private val repository: CharacterRepository,
) : RemoveCharacterFromFavouritesUseCase {
    override suspend fun invoke(character: CharacterModel) {
        repository.removeCharacterFromFavourites(character)
    }
}
