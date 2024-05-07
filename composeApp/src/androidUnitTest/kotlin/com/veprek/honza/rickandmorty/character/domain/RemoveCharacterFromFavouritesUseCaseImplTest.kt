package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RemoveCharacterFromFavouritesUseCaseImplTest {
    private val repository = mockk<CharacterRepository>()

    @Test
    fun `should call repository removeCharacterFromFavourites`() = runTest {
        coEvery { repository.removeCharacterFromFavourites(any()) } just runs

        val useCase = RemoveCharacterFromFavouritesUseCaseImpl(repository)
        useCase(CharacterModel(1, "name", "status", "url"))
        coVerify(exactly = 1) { repository.removeCharacterFromFavourites(any()) }
    }
}