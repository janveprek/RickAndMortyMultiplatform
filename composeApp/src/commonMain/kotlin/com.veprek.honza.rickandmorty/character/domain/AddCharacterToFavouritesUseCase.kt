package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel

interface AddCharacterToFavouritesUseCase {
    suspend operator fun invoke(character: CharacterModel)
}
