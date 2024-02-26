package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

interface GetCharacterByIdUseCase {
    suspend operator fun invoke(id: Long): ResultWrapper<CharacterDetail>
}
