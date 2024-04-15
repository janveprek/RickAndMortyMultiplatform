package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.StatusFilter

interface CharacterRepository {
    suspend fun getAllCharacters(page: Long = 1): ResultWrapper<List<CharacterModel>>

    suspend fun getCharactersByName(name: String, filter: StatusFilter = StatusFilter.All): ResultWrapper<List<CharacterModel>>

    suspend fun getFavouriteCharacters(): List<CharacterModel>

    suspend fun addCharacterToFavourites(character: CharacterModel)

    suspend fun removeCharacterFromFavourites(character: CharacterModel)

    suspend fun getCharacterById(id: Long): ResultWrapper<CharacterDetail>
}
