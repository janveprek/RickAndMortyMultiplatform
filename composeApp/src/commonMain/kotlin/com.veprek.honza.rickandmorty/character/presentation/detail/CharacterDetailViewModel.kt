package com.veprek.honza.rickandmorty.character.presentation.detail

import com.veprek.honza.rickandmorty.character.domain.GetCharacterByIdUseCase
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.State
import com.veprek.honza.rickandmorty.character.presentation.detail.state.CharacterDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CharacterDetailViewModel(
    private val id: Int,
    private val getCharacterById: GetCharacterByIdUseCase,
) : ViewModel() {
    private val _characterState = MutableStateFlow(CharacterDetailState())
    val characterState = _characterState.asStateFlow()

    init {
        getCharacter()
    }

    private fun getCharacter() {
        viewModelScope.launch {
            val result = getCharacterById(id.toLong())
            when (result) {
                is ResultWrapper.Success -> {
                    val character = result.value
                    _characterState.update {
                        it.copy(
                            state = State.Success,
                            character = character,
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    _characterState.update {
                        it.copy(
                            state = State.Error,
                        )
                    }
                }
            }
        }
    }
}
