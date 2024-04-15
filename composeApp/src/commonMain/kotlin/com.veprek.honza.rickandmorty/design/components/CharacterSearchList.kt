package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import com.veprek.honza.rickandmorty.design.theme.cardHeight
import com.veprek.honza.rickandmorty.design.theme.cornerRadiusMedium
import com.veprek.honza.rickandmorty.design.theme.paddingSmall

@Composable
fun CharacterSearchList(
    modifier: Modifier = Modifier,
    characters: List<CharacterModel>,
    onCharacterClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = paddingSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(characters) { character ->
            CharacterItem(
                character = character,
                onCharacterClick = onCharacterClick,
            )
        }
    }
}

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterModel,
    onCharacterClick: (Int) -> Unit,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadiusMedium))
            .clickable { onCharacterClick(character.id) }
            .padding(vertical = paddingSmall),
        text = character.name
    )
}