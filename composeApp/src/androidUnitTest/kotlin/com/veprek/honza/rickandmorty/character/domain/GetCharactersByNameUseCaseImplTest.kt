package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.StatusFilter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCharactersByNameUseCaseImplTest {
    private val repository = mockk<CharacterRepository>()

    @Test
    fun `should call repository getCharactersByName`() = runTest {
        coEvery { repository.getCharactersByName(any(), any()) } returns ResultWrapper.Success(mockk(relaxed = true))

        val useCase = GetCharactersByNameUseCaseImpl(repository)
        useCase("name", StatusFilter.Unknown)
        coVerify(exactly = 1) { repository.getCharactersByName(any(), any()) }
    }
}