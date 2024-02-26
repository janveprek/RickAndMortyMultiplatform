package com.veprek.honza.rickandmorty.character.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import rickandmorty.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FavouriteIcon(
    modifier: Modifier = Modifier,
    isFavourite: Boolean,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier.clickable { onClick() },
        painter =
            if (isFavourite) {
                painterResource(Res.drawable.ic_favourite)
            } else {
                painterResource(Res.drawable.ic_not_favourite)
            },
        contentDescription = "Add/Remove from favourites",
    )
}
