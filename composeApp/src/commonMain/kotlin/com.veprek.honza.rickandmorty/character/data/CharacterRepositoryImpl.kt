package com.veprek.honza.rickandmorty.character.data

import com.veprek.honza.rickandmorty.character.data.mapper.toModel
import com.veprek.honza.rickandmorty.character.domain.CharacterRepository
import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.StatusFilter
import com.veprek.honza.rickandmorty.character.presentation.list.CharactersListViewModel
import io.github.aakira.napier.Napier

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

    override suspend fun getCharactersByName(name: String, filter: StatusFilter): ResultWrapper<List<CharacterModel>> {
        return try {
            ResultWrapper.Success(charactersApi.getCharactersByName(name, filter).result.map { it.toModel() })
        } catch (ex: Exception) {
            ResultWrapper.Error(ex)
        }
    }

    override suspend fun getFavouriteCharacters(): List<CharacterModel> {
        return favouriteCharacters
    }

    override suspend fun addCharacterToFavourites(character: CharacterModel) {
        Napier.d("add character, $favouriteCharacters")
        favouriteCharacters += character.copy(isFavourite = true)
    }

    override suspend fun removeCharacterFromFavourites(character: CharacterModel) {
        favouriteCharacters -= character
    }

    override suspend fun getCharacterById(id: Long): ResultWrapper<CharacterDetail> {
        return try {
            val character = charactersApi.getCharacterById(id).toModel()
            if (favouriteCharacters.find { it.id == id.toInt() } != null) {
                ResultWrapper.Success(character.copy(isFavourite = true))
            } else {
                ResultWrapper.Success(character)
            }
        } catch (ex: Exception) {
            ResultWrapper.Error(ex)
        }
    }
}
