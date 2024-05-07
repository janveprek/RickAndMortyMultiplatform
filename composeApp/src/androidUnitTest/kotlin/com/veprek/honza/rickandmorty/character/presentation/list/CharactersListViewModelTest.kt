package com.veprek.honza.rickandmorty.character.presentation.list

import app.cash.turbine.test
import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.GetAllCharactersUseCase
import com.veprek.honza.rickandmorty.character.domain.GetCharactersByNameUseCase
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCase
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.ResultWrapper
import com.veprek.honza.rickandmorty.character.model.StatusFilter
import com.veprek.honza.rickandmorty.character.presentation.list.state.ScreenState
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
class CharactersListViewModelTest {
    private val getAllCharacters = mockk<GetAllCharactersUseCase>()
    private val getCharactersByName = mockk<GetCharactersByNameUseCase>()
    private val addCharacterToFavourites = mockk<AddCharacterToFavouritesUseCase>()
    private val removeCharacterFromFavourites = mockk<RemoveCharacterFromFavouritesUseCase>()

    private fun createViewModel() = CharactersListViewModel(
        getAllCharacters = getAllCharacters,
        getCharactersByName = getCharactersByName,
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
        coEvery { getAllCharacters() } returns
                ResultWrapper.Success(characterList)
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
    fun `updateCharacters should change screen state`() = runTest {
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

        coEvery { getAllCharacters() } returns ResultWrapper.Success(characterList)
        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.updateCharacters()

        viewModel.charactersState.test {
            awaitItem().state shouldBe ScreenState.Loading
            val screenState = awaitItem()
            screenState.state shouldBe ScreenState.Success
        }
    }

    @Test
    fun `updateCharacters should show error screen state`() = runTest {
        coEvery { getAllCharacters() } returns ResultWrapper.Error(Error())
        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.updateCharacters()

        viewModel.charactersState.test {
            awaitItem().state shouldBe ScreenState.Loading
            val screenState = awaitItem()
            screenState.state shouldBe ScreenState.Error
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
        coEvery { getAllCharacters() } returns
                ResultWrapper.Success(characterList)
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
    fun `search should change screen state to empty`() = runTest {
        coEvery { getAllCharacters() } returns
                ResultWrapper.Success(emptyList())
        coEvery { getCharactersByName(any()) } returns
                ResultWrapper.Success(emptyList())
        val query = "query"
        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.search(query)
        viewModel.charactersState.test {
            awaitItem()
            val screenState = awaitItem()
            screenState.characters shouldBe emptyList<CharacterModel>()
            screenState.state shouldBe ScreenState.Empty
        }
    }

    @Test
    fun `openBottomSheet should change state`() = runTest {
        coEvery { getAllCharacters() } returns ResultWrapper.Success(mockk(relaxed = true))
        val viewModel = createViewModel()
        viewModel.charactersState.test {
            awaitItem().openBottomSheet shouldBe false
            viewModel.openBottomSheet()
            awaitItem().openBottomSheet shouldBe true
        }
    }

    @Test
    fun `closeBottomSheet should change state`() = runTest {
        coEvery { getAllCharacters() } returns ResultWrapper.Success(mockk(relaxed = true))
        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.openBottomSheet()

        viewModel.charactersState.test {
            awaitItem().openBottomSheet shouldBe true
            viewModel.closeBottomSheet()
            awaitItem().openBottomSheet shouldBe false
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

        coEvery { getAllCharacters(any()) } returns ResultWrapper.Success(emptyList())
        coEvery { getCharactersByName(any(), any()) } returns ResultWrapper.Success(characterList)
        val viewModel = createViewModel()
        val query = "query"
        advanceUntilIdle()
        viewModel.search(query)

        viewModel.charactersState.test {
            val oldState = awaitItem()
            oldState.state shouldBe ScreenState.Loading
            oldState.query shouldBe ""
            val screenState = awaitItem()
            screenState.state shouldBe ScreenState.Success
            screenState.query shouldBe query
        }
    }

    @Test
    fun `search should show error screen state`() = runTest {
        coEvery { getAllCharacters(any()) } returns ResultWrapper.Success(emptyList())
        coEvery { getCharactersByName(any()) } returns ResultWrapper.Error(Error())
        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.search("query")

        viewModel.charactersState.test {
            awaitItem().state shouldBe ScreenState.Loading
            val screenState = awaitItem()
            screenState.state shouldBe ScreenState.Error
        }
    }

    @Test
    fun `applyFilters should change state`() = runTest {
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

        coEvery { getAllCharacters(any()) } returns ResultWrapper.Success(emptyList())
        coEvery { getCharactersByName(any(), any()) } returns ResultWrapper.Success(characterList)
        val viewModel = createViewModel()

        viewModel.charactersState.test {
            advanceUntilIdle()
            val oldState = awaitItem()
            oldState.state shouldBe ScreenState.Loading
            oldState.appliedFilter shouldBe StatusFilter.All
            viewModel.applyFilters(StatusFilter.Unknown)
            awaitItem().state shouldBe ScreenState.Success
            awaitItem().appliedFilter shouldBe StatusFilter.Unknown
            val screenState = awaitItem()
            screenState.state shouldBe ScreenState.Loading
            screenState.appliedFilter shouldBe StatusFilter.Unknown
        }
    }

}