package com.veprek.honza.rickandmorty.character.presentation.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.presentation.favorite.state.FavoriteCharactersState
import com.veprek.honza.rickandmorty.character.presentation.favorite.state.ScreenState
import com.veprek.honza.rickandmorty.design.components.AppSearchBar
import com.veprek.honza.rickandmorty.design.components.CharacterCard
import com.veprek.honza.rickandmorty.design.components.CharacterShimmerList
import com.veprek.honza.rickandmorty.design.components.EmptyScreen
import com.veprek.honza.rickandmorty.design.theme.paddingSmall
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun FavoriteCharactersScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: FavoriteCharactersViewModel = koinViewModel(vmClass = FavoriteCharactersViewModel::class)
) {
    val uiState by viewModel.charactersState.collectAsState()
    FavoriteCharactersScreenContent(
        state = uiState,
        navigateToDetail = navigateToDetail,
        toggleFavourite = viewModel::toggleFavourite,
        onSearch = viewModel::search,
    )
}

@Composable
fun FavoriteCharactersScreenContent(
    state: FavoriteCharactersState,
    navigateToDetail: (Int) -> Unit,
    toggleFavourite: (CharacterModel) -> Unit,
    onSearch: (String) -> Unit,
) {
    Scaffold(
        topBar = { AppSearchBar(onSearch = onSearch) },
    ) {
        CharacterList(
            modifier = Modifier.fillMaxSize().padding(it),
            state = state,
            onCharacterClick = navigateToDetail,
            onCharacterLongClick = toggleFavourite,
        )
    }
}

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    state: FavoriteCharactersState,
    onCharacterClick: (Int) -> Unit,
    onCharacterLongClick: (CharacterModel) -> Unit,
) {
    Column(modifier = modifier) {
        when (state.state) {
            is ScreenState.Loading -> CharacterShimmerList()
            is ScreenState.Empty -> EmptyScreen()
            is ScreenState.Success,
            -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(state.characters) { character ->
                            CharacterCard(
                                modifier = Modifier.padding(
                                    horizontal = paddingSmall,
                                    vertical = paddingSmall
                                ),
                                character = character,
                                onCharacterClick = onCharacterClick,
                                onCharacterLongClick = onCharacterLongClick,
                            )
                        }
                    }
                }
            }
        }
    }
}
