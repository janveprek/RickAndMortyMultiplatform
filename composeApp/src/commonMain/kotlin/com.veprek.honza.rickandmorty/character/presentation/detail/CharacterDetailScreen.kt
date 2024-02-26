package com.veprek.honza.rickandmorty.character.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import com.veprek.honza.rickandmorty.character.model.State
import com.veprek.honza.rickandmorty.character.presentation.detail.state.CharacterDetailState
import com.veprek.honza.rickandmorty.design.components.CharacterShimmerList
import com.veprek.honza.rickandmorty.design.components.ErrorScreen
import com.veprek.honza.rickandmorty.design.components.TopBar
import com.veprek.honza.rickandmorty.design.theme.RickAndMortyTheme
import com.veprek.honza.rickandmorty.design.theme.cornerRadiusSmall
import com.veprek.honza.rickandmorty.design.theme.dividerThickness
import com.veprek.honza.rickandmorty.design.theme.paddingMedium
import com.veprek.honza.rickandmorty.design.theme.paddingSmall
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import rickandmorty.composeapp.generated.resources.Res

private const val AVATAR_SIZE_IN_DP = 120

@Composable
fun CharacterDetailScreen(
    goBack: () -> Unit,
    id: Int,
) {
    val detailViewModel =
        koinViewModel(vmClass = CharacterDetailViewModel::class) { parametersOf(id) }
    val uiState by detailViewModel.characterState.collectAsState()

    CharacterDetailScreenContent(
        state = uiState,
        goBack = goBack,
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CharacterDetailScreenContent(
    modifier: Modifier = Modifier,
    state: CharacterDetailState,
    goBack: () -> Unit,
) {
    state.character?.let { character ->
        Scaffold(
            topBar = { TopBar(title = state.character.name, goBack = goBack) },
        ) {
            Column(modifier = modifier.padding(it)) {
                when (state.state) {
                    is State.Loading -> CharacterShimmerList()
                    is State.Error -> ErrorScreen(tryAgain = {})
                    is State.Success -> {
                        Column {
                            Header(name = character.name, iconUrl = character.iconUrl)
                            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = dividerThickness)
                            InfoPair(title = stringResource(Res.string.status), value = character.status)
                            InfoPair(title = stringResource(Res.string.species), value = character.species)
                            InfoPair(title = stringResource(Res.string.type), value = character.type)
                            InfoPair(title = stringResource(Res.string.gender), value = character.gender)
                            InfoPair(title = stringResource(Res.string.origin), value = character.origin)
                            InfoPair(title = stringResource(Res.string.location), value = character.location)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    name: String,
    iconUrl: String,
) {
    Row(modifier = modifier.fillMaxWidth().padding(paddingMedium)) {
        Image(
            painter = rememberImagePainter(iconUrl),
            contentDescription = "avatar",
            modifier =
                Modifier
                    .clip(RoundedCornerShape(cornerRadiusSmall))
                    .size(AVATAR_SIZE_IN_DP.dp),
        )
        Column(
            modifier =
                modifier.padding(
                    horizontal = paddingMedium,
                ),
        ) {
            Text(
                text = stringResource(Res.string.name),
                style = RickAndMortyTheme.typography.bodyMedium,
            )
            Text(
                text = name,
                style = RickAndMortyTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun InfoPair(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Column(
        modifier =
            modifier.padding(
                horizontal = paddingMedium,
                vertical = paddingSmall,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
        )
        Text(
            text = value,
            style = RickAndMortyTheme.typography.titleSmall,
        )
    }
}
