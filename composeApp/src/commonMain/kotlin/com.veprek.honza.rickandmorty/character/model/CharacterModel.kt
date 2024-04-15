package com.veprek.honza.rickandmorty.character.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val iconUrl: String,
    val isFavourite: Boolean = false,
)
