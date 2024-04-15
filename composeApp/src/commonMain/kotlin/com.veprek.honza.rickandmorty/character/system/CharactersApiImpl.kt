package com.veprek.honza.rickandmorty.character.system

import com.veprek.honza.rickandmorty.character.data.CharactersApi
import com.veprek.honza.rickandmorty.character.data.entity.CharacterDetailDto
import com.veprek.honza.rickandmorty.character.data.entity.PagedResultDto
import com.veprek.honza.rickandmorty.character.model.StatusFilter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharactersApiImpl(
    private val httpClient: HttpClient,
) : CharactersApi {
    override suspend fun getAllCharacters(page: Long): PagedResultDto {
        return httpClient.get("https://rickandmortyapi.com/api/character/?page=$page").body<PagedResultDto>()
    }

    override suspend fun getCharactersByName(name: String, filter: StatusFilter): PagedResultDto {
        return httpClient.get("https://rickandmortyapi.com/api/character/?name=$name&status=${filter.apiName}")
            .body<PagedResultDto>()
    }

    override suspend fun getCharacterById(id: Long): CharacterDetailDto {
        return httpClient.get("https://rickandmortyapi.com/api/character/$id").body()
    }
}
