package com.veprek.honza.rickandmorty.character.presentation.favorite

import app.cash.turbine.test
import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.GetFavouriteCharactersUseCase
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCase
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.presentation.favorite.state.ScreenState
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
class FavoriteCharactersViewModelTest {
    private val getFavouriteCharacters = mockk<GetFavouriteCharactersUseCase>()
    private val addCharacterToFavourites = mockk<AddCharacterToFavouritesUseCase>()
    private val removeCharacterFromFavourites = mockk<RemoveCharacterFromFavouritesUseCase>()

    private fun createViewModel() = FavoriteCharactersViewModel(
        getFavouriteCharacters = getFavouriteCharacters,
        addCharacterToFavourites = addCharacterToFavourites,
        removeCharacterFromFavourites = removeCharacterFromFavourites
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
    fun `should get characters during init`() = runTest {
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Rick",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            )
        )
        coEvery { getFavouriteCharacters() } returns characterList
        val viewModel = createViewModel()

        viewModel.charactersState.test {
            val initialState = awaitItem()
            initialState.state shouldBe ScreenState.Loading
            initialState.characters shouldBe emptyList()
            val screenState = awaitItem()
            screenState.state shouldBe ScreenState.Success
            screenState.characters shouldBe characterList
        }
    }

    @Test
    fun `search should change screen state to empty`() = runTest {
        coEvery { getFavouriteCharacters() } returns
                emptyList()
        val query = "query"
        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.search(query)
        viewModel.charactersState.test {
            val screenState = awaitItem()
            screenState.characters shouldBe emptyList()
            screenState.state shouldBe ScreenState.Empty
        }
    }

    @Test
    fun `toggleFavourite should change screen state character`() = runTest {
        val favCharacter = CharacterModel(
            id = 1,
            name = "Rick",
            status = "alive",
            iconUrl = "",
            isFavourite = false
        )
        val characterList = listOf(
            favCharacter,
            CharacterModel(
                id = 2,
                name = "Morty",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            )
        )
        val updatedList = listOf(
            CharacterModel(
                id = 1,
                name = "Rick",
                status = "alive",
                iconUrl = "",
                isFavourite = true
            ),
            CharacterModel(
                id = 2,
                name = "Morty",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            )
        )
        coEvery { getFavouriteCharacters() } returns characterList
        coEvery { addCharacterToFavourites(any()) } just runs
        coEvery { removeCharacterFromFavourites(any()) } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.toggleFavourite(favCharacter)

        viewModel.charactersState.test {
            awaitItem().characters shouldBe updatedList
        }

        viewModel.toggleFavourite(favCharacter.copy(isFavourite = true))
        viewModel.charactersState.test {
            awaitItem().characters shouldBe characterList
        }
    }

    @Test
    fun `search should change state`() = runTest {
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Rick",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            ),
            CharacterModel(
                id = 2,
                name = "Morty",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            )
        )

        coEvery { getFavouriteCharacters() } returns characterList
        val viewModel = createViewModel()
        val query = "rick"
        advanceUntilIdle()
        viewModel.search(query)

        viewModel.charactersState.test {
            awaitItem().state shouldBe ScreenState.Success
        }
    }

    @Test
    fun `search should not change state if query is empty`() = runTest {
        val characterList = listOf(
            CharacterModel(
                id = 1,
                name = "Rick",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            ),
            CharacterModel(
                id = 2,
                name = "Morty",
                status = "alive",
                iconUrl = "",
                isFavourite = false
            )
        )

        coEvery { getFavouriteCharacters() } returns characterList
        val viewModel = createViewModel()
        val emptyQuery = ""
        advanceUntilIdle()
        viewModel.search(emptyQuery)

        viewModel.charactersState.test {
            val screenState = awaitItem()
            screenState.characters shouldBe characterList
        }
    }
}