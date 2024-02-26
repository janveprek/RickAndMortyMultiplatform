package com.veprek.honza.rickandmorty.character.presentation.favorite.state

sealed class ScreenState {
    data object Loading : ScreenState()

    data object Empty : ScreenState()

    data object Success : ScreenState()
}
