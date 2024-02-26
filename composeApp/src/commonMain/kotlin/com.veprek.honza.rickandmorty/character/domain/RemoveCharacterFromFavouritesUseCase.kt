package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel

interface RemoveCharacterFromFavouritesUseCase {
    suspend operator fun invoke(character: CharacterModel)
}
