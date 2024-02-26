package com.veprek.honza.rickandmorty.character.presentation.list.state

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.State

data class CharacterListState(
    val characters: List<CharacterModel> = emptyList(),
    val query: String = "",
    val state: State = State.Loading,
)
