package com.veprek.honza.rickandmorty.character.model

sealed class State {
    data object Loading : State()

    data object Error : State()

    data object Success : State()
}
