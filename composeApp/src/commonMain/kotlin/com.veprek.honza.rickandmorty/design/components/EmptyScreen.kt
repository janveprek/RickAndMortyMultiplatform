package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.veprek.honza.rickandmorty.design.theme.RickAndMortyTheme
import com.veprek.honza.rickandmorty.design.theme.iconSizeLarge
import com.veprek.honza.rickandmorty.design.theme.paddingExtraLarge
import com.veprek.honza.rickandmorty.design.theme.paddingSmall
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rickandmorty.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize(),
        verticalArrangement =
            Arrangement.spacedBy(
                paddingExtraLarge,
                Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.empty_title),
            style = RickAndMortyTheme.typography.titleMedium,
        )

        Icon(
            modifier =
                Modifier
                    .size(iconSizeLarge),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(Res.drawable.ic_error),
            contentDescription = stringResource(Res.string.empty_text),
        )

        Text(
            modifier = Modifier.padding(horizontal = paddingSmall),
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.empty_text),
            style = RickAndMortyTheme.typography.bodyMedium,
        )
    }
}
