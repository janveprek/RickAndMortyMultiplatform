package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

interface GetAllCharactersUseCase {
    suspend operator fun invoke(page: Long = 0): ResultWrapper<List<CharacterModel>>
}
