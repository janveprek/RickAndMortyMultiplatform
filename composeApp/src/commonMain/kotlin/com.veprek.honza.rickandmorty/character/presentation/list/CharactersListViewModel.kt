package com.veprek.honza.rickandmorty.character.presentation.list

import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.GetAllCharactersUseCase
import com.veprek.honza.rickandmorty.character.domain.GetCharactersByNameUseCase
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCase
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.State
import com.veprek.honza.rickandmorty.character.presentation.list.state.CharacterListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CharactersListViewModel(
    private val getAllCharacters: GetAllCharactersUseCase,
    private val getCharactersByName: GetCharactersByNameUseCase,
    private val addCharacterToFavourites: AddCharacterToFavouritesUseCase,
    private val removeCharacterFromFavourites: RemoveCharacterFromFavouritesUseCase,
) : ViewModel() {
    private val _charactersState = MutableStateFlow(CharacterListState(emptyList()))
    val charactersState = _charactersState.asStateFlow()

    init {
        updateCharacters()
    }

    internal fun updateCharacters() {
        _charactersState.update { it.copy(state = State.Loading) }
        viewModelScope.launch {
            val result = getAllCharacters()
            when (result) {
                is ResultWrapper.Success -> {
                    val characters = result.value
                    _charactersState.update {
                        it.copy(
                            state = State.Success,
                            characters = characters,
                        )
                    }
                }
                is ResultWrapper.Error -> {
                    _charactersState.update {
                        it.copy(
                            state = State.Error,
                        )
                    }
                }
            }
        }
    }

    internal fun toggleFavourite(character: CharacterModel) {
        val updatedCharacter =
            _charactersState.value.characters.find { it.id == character.id }
                ?.copy(isFavourite = !character.isFavourite)
        updatedCharacter?.let {
            if (updatedCharacter.isFavourite) {
                viewModelScope.launch {
                    addCharacterToFavourites(character)
                }
            } else {
                viewModelScope.launch {
                    removeCharacterFromFavourites(character)
                }
            }
            val updatedCharacters =
                _charactersState.value.characters.toMutableList()
                    .apply { this[this.indexOf(character)] = updatedCharacter }
            _charactersState.update {
                it.copy(characters = updatedCharacters)
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            val result = getCharactersByName(name)
            when (result) {
                is ResultWrapper.Success -> {
                    val characters = result.value
                    _charactersState.update {
                        it.copy(
                            state = State.Success,
                            characters = characters,
                        )
                    }
                }
                is ResultWrapper.Error -> {
                    _charactersState.update {
                        it.copy(
                            state = State.Error,
                        )
                    }
                }
            }
        }
    }
}
