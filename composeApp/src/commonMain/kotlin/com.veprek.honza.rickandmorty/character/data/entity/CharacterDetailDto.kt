package com.veprek.honza.rickandmorty.character.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String,
    @SerialName("species")
    val species: String,
    @SerialName("type")
    val type: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("origin")
    val origin: OriginDto,
    @SerialName("location")
    val location: LocationDto,
    @SerialName("image")
    val image: String,
)

@Serializable
data class OriginDto(
    val name: String,
)

@Serializable
data class LocationDto(
    val name: String,
)
