package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.ResultWrapper

class GetCharacterByIdUseCaseImpl(
    private val repository: CharacterRepository,
) : GetCharacterByIdUseCase {
    override suspend fun invoke(id: Long): ResultWrapper<CharacterDetail> {
        return repository.getCharacterById(id)
    }
}
