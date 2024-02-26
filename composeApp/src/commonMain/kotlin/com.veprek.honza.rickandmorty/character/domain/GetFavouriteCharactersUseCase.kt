package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel

interface GetFavouriteCharactersUseCase {
    suspend operator fun invoke(): List<CharacterModel>
}
