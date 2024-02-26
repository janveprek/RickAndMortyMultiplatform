package com.veprek.honza.rickandmorty.character.data.mapper

import com.veprek.honza.rickandmorty.character.data.entity.PagedResultDto
import com.veprek.honza.rickandmorty.character.model.PagedResult

fun PagedResultDto.toModel(): PagedResult {
    return PagedResult(data = result.map { it.toModel() })
}
