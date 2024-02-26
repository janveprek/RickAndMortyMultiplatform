package com.veprek.honza.rickandmorty.character.model

sealed interface ResultWrapper<out T> {
    class Success<out T>(val value: T) : ResultWrapper<T>

    class Error(val error: Throwable) : ResultWrapper<Nothing>
}
