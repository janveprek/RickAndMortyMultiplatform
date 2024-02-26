package com.veprek.honza.rickandmorty.character.data

import com.veprek.honza.rickandmorty.character.data.entity.CharacterDetailDto
import com.veprek.honza.rickandmorty.character.data.entity.PagedResultDto

interface CharactersApi {
    suspend fun getAllCharacters(page: Long = 1): PagedResultDto

    suspend fun getCharactersByName(name: String): PagedResultDto

    suspend fun getCharacterById(id: Long): CharacterDetailDto
}
