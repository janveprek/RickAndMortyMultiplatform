package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

interface GetCharactersByNameUseCase {
    suspend operator fun invoke(name: String): ResultWrapper<List<CharacterModel>>
}
