package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAllCharactersUseCaseImplTest {
    private val repository = mockk<CharacterRepository>()

    @Test
    fun `should call repository getAllCharacters`() = runTest {
        coEvery { repository.getAllCharacters(any()) } returns ResultWrapper.Success(mockk(relaxed = true))

        val useCase = GetAllCharactersUseCaseImpl(repository)
        useCase(1)
        coVerify(exactly = 1) { repository.getAllCharacters(any()) }
    }
}