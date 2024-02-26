package com.veprek.honza.rickandmorty.character.data

import com.veprek.honza.rickandmorty.character.data.mapper.toModel
import com.veprek.honza.rickandmorty.character.domain.CharacterRepository
import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

class CharacterRepositoryImpl(
    private val charactersApi: CharactersApi,
) : CharacterRepository {
    private var favouriteCharacters = mutableListOf<CharacterModel>()

    override suspend fun getAllCharacters(page: Long): ResultWrapper<List<CharacterModel>> {
        return try {
            val characters = charactersApi.getAllCharacters(page).result.map { it.toModel() }
            ResultWrapper.Success(characters)
        } catch (ex: Exception) {
            ResultWrapper.Error(ex)
        }
    }

    override suspend fun getCharactersByName(name: String): ResultWrapper<List<CharacterModel>> {
        return try {
            ResultWrapper.Success(charactersApi.getCharactersByName(name).result.map { it.toModel() })
        } catch (ex: Exception) {
            ResultWrapper.Error(ex)
        }
    }

    override suspend fun getFavouriteCharacters(): List<CharacterModel> {
        return favouriteCharacters
    }

    override suspend fun addCharacterToFavourites(character: CharacterModel) {
        favouriteCharacters += character
    }

    override suspend fun removeCharacterFromFavourites(character: CharacterModel) {
        favouriteCharacters -= character
    }

    override suspend fun getCharacterById(id: Long): ResultWrapper<CharacterDetail> {
        return try {
            ResultWrapper.Success(charactersApi.getCharacterById(id).toModel())
        } catch (ex: Exception) {
            ResultWrapper.Error(ex)
        }
    }
}
