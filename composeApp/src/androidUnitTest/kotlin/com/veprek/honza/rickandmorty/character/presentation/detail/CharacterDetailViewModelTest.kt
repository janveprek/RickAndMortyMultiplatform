package com.veprek.honza.rickandmorty.character.presentation.detail

import app.cash.turbine.test
import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.GetCharacterByIdUseCase
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCase
import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.State
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private val getCharacterById = mockk<GetCharacterByIdUseCase>()
    private val addCharacterToFavourites = mockk<AddCharacterToFavouritesUseCase>()
    private val removeCharacterFromFavourites = mockk<RemoveCharacterFromFavouritesUseCase>()

    private fun createViewModel() = CharacterDetailViewModel(
        getCharacterById = getCharacterById,
        addCharacterToFavourites = addCharacterToFavourites,
        removeCharacterFromFavourites = removeCharacterFromFavourites,
        id = 1
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `should get character during init`() = runTest {
        val character = CharacterDetail(
                id = 1,
                name = "Rick",
                status = "alive",
                species = "species",
                type = "type",
                gender = "gender",
                origin = "origin",
                location = "location",
                iconUrl = "icon",
        )
        coEvery { getCharacterById(any()) } returns
                ResultWrapper.Success(character)
        val viewModel = createViewModel()

        viewModel.characterState.test {
            val initialState = awaitItem()
            initialState.state shouldBe State.Loading
            initialState.character shouldBe null
            val screenState = awaitItem()
            screenState.state shouldBe State.Success
            screenState.character shouldBe character
        }
    }

    @Test
    fun `getCharacter should show error screen state`() = runTest {
        coEvery { getCharacterById(any()) } returns ResultWrapper.Error(Error())
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.characterState.test {
            awaitItem().state shouldBe State.Error
        }
    }

    @Test
    fun `toggleFavourite should change screen state character`() = runTest {
        val characterDetail = CharacterDetail(
            id = 1,
            name = "Rick",
            status = "alive",
            species = "species",
            type = "type",
            gender = "gender",
            origin = "origin",
            location = "location",
            iconUrl = "icon",
        )
        coEvery { getCharacterById(any()) } returns
                ResultWrapper.Success(characterDetail)
        coEvery { addCharacterToFavourites(any()) } just runs
        coEvery { removeCharacterFromFavourites(any()) } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.toggleFavourite()

        viewModel.characterState.test {
            awaitItem().character shouldBe characterDetail.copy(isFavourite = true)
        }

        viewModel.toggleFavourite()
        viewModel.characterState.test {
            awaitItem().character shouldBe characterDetail
        }
    }
}