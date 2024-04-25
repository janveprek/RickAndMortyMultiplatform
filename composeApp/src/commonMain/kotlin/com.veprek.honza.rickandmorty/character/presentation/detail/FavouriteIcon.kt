package com.veprek.honza.rickandmorty.character.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import org.jetbrains.compose.resources.DrawableResource
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
                rememberVectorPainter(Icons.Default.Favorite)
            } else {
                rememberVectorPainter(Icons.Default.FavoriteBorder)
            },
        contentDescription = "Add/Remove from favourites",
    )
}
