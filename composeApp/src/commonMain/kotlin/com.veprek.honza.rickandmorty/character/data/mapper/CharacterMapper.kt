package com.veprek.honza.rickandmorty.character.data.mapper

import com.veprek.honza.rickandmorty.character.data.entity.CharacterDetailDto
import com.veprek.honza.rickandmorty.character.data.entity.CharacterDto
import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.CharacterModel

fun CharacterDto.toModel(): CharacterModel {
    return CharacterModel(
        id = id,
        name = name,
        status = status,
        iconUrl = image,
    )
}

fun CharacterDetailDto.toModel(): CharacterDetail {
    return CharacterDetail(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.name,
        location = location.name,
        iconUrl = image,
    )
}
