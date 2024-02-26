package com.veprek.honza.rickandmorty.design.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.veprek.honza.rickandmorty.design.theme.Gray
import com.veprek.honza.rickandmorty.design.theme.GrayDark
import com.veprek.honza.rickandmorty.design.theme.RickAndMortyTheme
import com.veprek.honza.rickandmorty.design.theme.cardHeight
import com.veprek.honza.rickandmorty.design.theme.cornerRadiusMedium
import com.veprek.honza.rickandmorty.design.theme.iconSizeLarge
import com.veprek.honza.rickandmorty.design.theme.paddingSmall

private const val ITEM_COUNT = 20
private const val PLACEHOLDER_TEXT_LONG_IN_DP = 120
private const val PLACEHOLDER_TEXT_SHORT_IN_DP = 48

@Composable
fun CharacterShimmerList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        userScrollEnabled = false,
    ) {
        items(
            count = ITEM_COUNT,
        ) {
            CharacterShimmerItem(
                modifier =
                    Modifier.padding(
                        horizontal = paddingSmall,
                        vertical = paddingSmall,
                    ),
            )
        }
    }
}

@Composable
fun CharacterShimmerItem(modifier: Modifier = Modifier) {
    Card(
        modifier =
            modifier
                .height(cardHeight)
                .clip(RoundedCornerShape(cornerRadiusMedium)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(cornerRadiusMedium))
                        .size(iconSizeLarge)
                        .shimmerEffect(),
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth().padding(horizontal = paddingSmall),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.padding(vertical = paddingSmall).weight(1f)) {
                    Text(
                        modifier = Modifier.shimmerEffect().width(PLACEHOLDER_TEXT_LONG_IN_DP.dp),
                        text = "",
                        style = RickAndMortyTheme.typography.titleMedium,
                    )
                    Text(
                        modifier = Modifier.shimmerEffect().width(PLACEHOLDER_TEXT_SHORT_IN_DP.dp),
                        text = "",
                        style = RickAndMortyTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier =
    composed {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        val transition = rememberInfiniteTransition()
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec =
                infiniteRepeatable(
                    animation = tween(1000),
                ),
        )

        background(
            brush =
                Brush.linearGradient(
                    colors =
                        listOf(
                            Gray,
                            GrayDark,
                            Gray,
                        ),
                    start = Offset(startOffsetX, 0f),
                    end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
                ),
        )
            .onGloballyPositioned {
                size = it.size
            }
    }
