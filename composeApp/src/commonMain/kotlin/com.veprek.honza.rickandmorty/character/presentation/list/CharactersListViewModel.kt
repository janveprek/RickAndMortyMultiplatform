package com.veprek.honza.rickandmorty.character.presentation.list

import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.GetAllCharactersUseCase
import com.veprek.honza.rickandmorty.character.domain.GetCharactersByNameUseCase
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCase
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.State
import com.veprek.honza.rickandmorty.character.model.StatusFilter
import com.veprek.honza.rickandmorty.character.presentation.list.state.CharacterListState
import com.veprek.honza.rickandmorty.character.presentation.list.state.ScreenState
import io.github.aakira.napier.Napier
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

    override fun onCleared() {
        Napier.d("onCleared", tag = TAG)
        super.onCleared()
    }

    override fun close() {
        Napier.d("close", tag = TAG)
        super.close()
    }

    internal fun updateCharacters() {
        _charactersState.update { it.copy(state = ScreenState.Loading) }
        viewModelScope.launch {
            val result = getAllCharacters()
            when (result) {
                is ResultWrapper.Success -> {
                    val characters = result.value
                    _charactersState.update {
                        it.copy(
                            state = ScreenState.Success,
                            characters = characters,
                        )
                    }
                }
                is ResultWrapper.Error -> {
                    _charactersState.update {
                        it.copy(
                            state = ScreenState.Error,
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

    fun search(query: String) {
        _charactersState.update { it.copy(state = ScreenState.Loading) }
        viewModelScope.launch {
            Napier.d("searching query: $query, filter: ${_charactersState.value.appliedFilter}")
            val result = getCharactersByName(query, _charactersState.value.appliedFilter)
            when (result) {
                is ResultWrapper.Success -> {
                    val characters = result.value
                    if (characters.isNotEmpty()) {
                        _charactersState.update {
                            it.copy(
                                state = ScreenState.Success,
                                query = query,
                                characters = characters,
                            )
                        }
                    } else {
                        _charactersState.update {
                            it.copy(
                                state = ScreenState.Empty,
                                query = query,
                                characters = characters,
                            )
                        }
                    }
                }
                is ResultWrapper.Error -> {
                    _charactersState.update {
                        it.copy(
                            state = ScreenState.Error,
                        )
                    }
                }
            }
        }
    }


    fun applyFilters(filter: StatusFilter) {
        _charactersState.update {
            it.copy(appliedFilter = filter)
        }
        search(_charactersState.value.query)
    }

    fun openBottomSheet() {
        _charactersState.update {
            it.copy(openBottomSheet = true)
        }
    }

    fun closeBottomSheet() {
        _charactersState.update {
            it.copy(openBottomSheet = false)
        }
    }

    companion object {
        private const val TAG = "ListViewModel"
    }
}
