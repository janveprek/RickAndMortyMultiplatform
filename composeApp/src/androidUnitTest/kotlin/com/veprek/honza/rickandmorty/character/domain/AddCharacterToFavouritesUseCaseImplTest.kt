package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.CharacterModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AddCharacterToFavouritesUseCaseImplTest {
    private val repository = mockk<CharacterRepository>()

    @Test
    fun `should call repository addCharacterToFavourites`() = runTest {
        coEvery { repository.addCharacterToFavourites(any()) } just runs

        val useCase = AddCharacterToFavouritesUseCaseImpl(repository)
        useCase(CharacterModel(1, "name", "status", "url"))

        coVerify(exactly = 1) { repository.addCharacterToFavourites(any()) }
    }
}