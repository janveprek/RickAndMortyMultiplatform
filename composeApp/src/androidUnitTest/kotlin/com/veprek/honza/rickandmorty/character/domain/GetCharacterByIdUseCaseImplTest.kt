package com.veprek.honza.rickandmorty.character.domain

import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
class GetCharacterByIdUseCaseImplTest {
    private val repository = mockk<CharacterRepository>()

    @Test
    fun `should call repository getCharacterById`() = runTest {
        coEvery { repository.getCharacterById(any()) } returns ResultWrapper.Success(mockk(relaxed = true))

        val useCase = GetCharacterByIdUseCaseImpl(repository)
        useCase(1)
        coVerify(exactly = 1) { repository.getCharacterById(any()) }
    }
}