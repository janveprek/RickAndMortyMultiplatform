package com.veprek.honza.rickandmorty.character.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResultDto(
    @SerialName("results")
    val result: List<CharacterDto>,
)
