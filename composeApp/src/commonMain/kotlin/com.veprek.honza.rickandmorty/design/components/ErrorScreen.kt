package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import com.veprek.honza.rickandmorty.design.theme.paddingMedium
import com.veprek.honza.rickandmorty.design.theme.paddingSmall
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rickandmorty.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    tryAgain: () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize().padding(paddingMedium),
        verticalArrangement =
            Arrangement.spacedBy(
                paddingExtraLarge,
                Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.error_title),
            style = RickAndMortyTheme.typography.titleMedium,
        )

        Icon(
            modifier =
                Modifier
                    .size(iconSizeLarge),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(Res.drawable.ic_error),
            contentDescription = stringResource(Res.string.error_text),
        )

        Text(
            modifier = Modifier.padding(horizontal = paddingSmall),
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.error_text),
            style = RickAndMortyTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(paddingExtraLarge))

        Button(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(text = stringResource(Res.string.try_again))
            },
            onClick = tryAgain,
        )
    }
}
