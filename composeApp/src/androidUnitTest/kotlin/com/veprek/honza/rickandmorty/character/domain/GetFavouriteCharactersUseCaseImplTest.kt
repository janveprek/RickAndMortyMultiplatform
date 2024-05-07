package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetFavouriteCharactersUseCaseImplTest {
    private val repository = mockk<CharacterRepository>()

    @Test
    fun `should call repository getFavouriteCharacters`() = runTest {
        coEvery { repository.getFavouriteCharacters() } returns listOf(CharacterModel(1, "name", "status", "url"))

        val useCase = GetFavouriteCharactersUseCaseImpl(repository)
        useCase()
            coVerify(exactly = 1) { repository.getFavouriteCharacters() }
    }
}