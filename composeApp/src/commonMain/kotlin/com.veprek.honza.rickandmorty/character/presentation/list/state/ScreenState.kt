package com.veprek.honza.rickandmorty.character.presentation.list.state

sealed class ScreenState {
    data object Loading : ScreenState()

    data object Error : ScreenState()

    data object Empty : ScreenState()

    data object Success : ScreenState()
}
