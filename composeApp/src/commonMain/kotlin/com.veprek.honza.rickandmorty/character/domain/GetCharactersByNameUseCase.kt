package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.StatusFilter

interface GetCharactersByNameUseCase {
    suspend operator fun invoke(name: String, filter: StatusFilter = StatusFilter.All): ResultWrapper<List<CharacterModel>>
}
