package com.veprek.honza.rickandmorty.character.data

import com.veprek.honza.rickandmorty.character.data.entity.CharacterDetailDto
import com.veprek.honza.rickandmorty.character.data.entity.PagedResultDto
import com.veprek.honza.rickandmorty.character.model.StatusFilter

interface CharactersApi {
    suspend fun getAllCharacters(page: Long = 1): PagedResultDto

    suspend fun getCharactersByName(name: String, filter: StatusFilter): PagedResultDto

    suspend fun getCharacterById(id: Long): CharacterDetailDto
}
