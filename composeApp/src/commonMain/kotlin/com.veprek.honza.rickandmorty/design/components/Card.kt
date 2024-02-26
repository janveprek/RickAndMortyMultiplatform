package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.seiko.imageloader.rememberImagePainter
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.character.presentation.detail.FavouriteIcon
import com.veprek.honza.rickandmorty.design.theme.RickAndMortyTheme
import com.veprek.honza.rickandmorty.design.theme.cardHeight
import com.veprek.honza.rickandmorty.design.theme.cornerRadiusMedium
import com.veprek.honza.rickandmorty.design.theme.cornerRadiusSmall
import com.veprek.honza.rickandmorty.design.theme.iconSizeLarge
import com.veprek.honza.rickandmorty.design.theme.paddingSmall

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: CharacterModel,
    onCharacterClick: (Int) -> Unit,
    onCharacterLongClick: (CharacterModel) -> Unit,
) {
    Card(
        modifier =
            modifier
                .height(cardHeight)
                .clip(RoundedCornerShape(cornerRadiusMedium))
                .combinedClickable(
                    onClick = {
                        onCharacterClick(character.id.toInt())
                    },
                    onLongClick = { onCharacterLongClick(character) },
                ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberImagePainter(character.iconUrl),
                contentDescription = "image",
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(cornerRadiusSmall))
                        .size(iconSizeLarge),
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth().padding(horizontal = paddingSmall),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.padding(vertical = paddingSmall).weight(1f)) {
                    Text(
                        text = character.name,
                        style = RickAndMortyTheme.typography.titleSmall,
                    )
                    Text(
                        text = character.status,
                        style = RickAndMortyTheme.typography.bodySmall,
                    )
                }
                FavouriteIcon(
                    isFavourite = character.isFavourite,
                    onClick = { onCharacterLongClick(character) },
                )
            }
        }
    }
}
