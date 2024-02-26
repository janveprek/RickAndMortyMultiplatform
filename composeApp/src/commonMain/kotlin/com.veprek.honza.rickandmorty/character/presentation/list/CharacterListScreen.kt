package com.veprek.honza.rickandmorty.character.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.model.State
import com.veprek.honza.rickandmorty.character.presentation.list.state.CharacterListState
import com.veprek.honza.rickandmorty.design.components.AppSearchBar
import com.veprek.honza.rickandmorty.design.components.CharacterCard
import com.veprek.honza.rickandmorty.design.components.CharacterShimmerList
import com.veprek.honza.rickandmorty.design.components.ErrorScreen
import com.veprek.honza.rickandmorty.design.theme.paddingSmall
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun CharacterListScreen(navigateToDetail: (Int) -> Unit) {
    val viewModel: CharactersListViewModel = koinViewModel(vmClass = CharactersListViewModel::class)

    val uiState by viewModel.charactersState.collectAsState()
    CharacterListScreenContent(
        state = uiState,
        navigateToDetail = navigateToDetail,
        toggleFavourite = viewModel::toggleFavourite,
        onTryAgainClick = viewModel::updateCharacters,
        onSearch = viewModel::search,
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CharacterListScreenContent(
    state: CharacterListState,
    navigateToDetail: (Int) -> Unit,
    toggleFavourite: (CharacterModel) -> Unit,
    onSearch: (String) -> Unit,
    onTryAgainClick: () -> Unit,
) {
    Scaffold(
        topBar = { AppSearchBar(onSearch = onSearch) },
    ) {
        CharacterList(
            modifier = Modifier.fillMaxSize().padding(it),
            state = state,
            onCharacterClick = navigateToDetail,
            onCharacterLongClick = toggleFavourite,
            onTryAgainClick = onTryAgainClick,
        )
    }
}

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    state: CharacterListState,
    onCharacterClick: (Int) -> Unit,
    onCharacterLongClick: (CharacterModel) -> Unit,
    onTryAgainClick: () -> Unit,
) {
    Column(modifier = modifier) {
        when (state.state) {
            is State.Loading -> CharacterShimmerList()
            is State.Error -> ErrorScreen(tryAgain = onTryAgainClick)
            is State.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(state.characters) { character ->
                            CharacterCard(
                                modifier = Modifier.padding(horizontal = paddingSmall, vertical = paddingSmall),
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
