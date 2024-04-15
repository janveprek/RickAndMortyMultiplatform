package com.veprek.honza.rickandmorty.character.presentation.list.state

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.State
import com.veprek.honza.rickandmorty.character.model.StatusFilter

data class CharacterListState(
    val characters: List<CharacterModel> = emptyList(),
    val query: String = "",
    val openBottomSheet: Boolean = false,
    val appliedFilter: StatusFilter = StatusFilter.All,
    val state: ScreenState = ScreenState.Loading,
)