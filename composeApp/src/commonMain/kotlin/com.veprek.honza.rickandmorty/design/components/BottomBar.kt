package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.veprek.honza.rickandmorty.navigation.system.Screen
import com.veprek.honza.rickandmorty.navigation.system.routeToScreen
import kotlinx.coroutines.flow.map
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rickandmorty.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navigator: Navigator,
) {
    val currentScreen by navigator.currentEntry.map { it?.route?.route.routeToScreen() }
        .collectAsState(initial = null)
    if (currentScreen?.isRoot == true) {
        BottomAppBar(modifier) {
            BottomBarItem(
                isSelected = currentScreen == Screen.List,
                textRes = Res.string.bottom_bar_characters,
                iconPainter = painterResource(Res.drawable.ic_all),
                onClick = {
                    if (currentScreen != Screen.List) {
                        navigator.navigate(
                            Screen.List.route,
                            NavOptions(popUpTo = PopUpTo.First()),
                        )
                    }
                },
            )
            BottomBarItem(
                isSelected = currentScreen == Screen.Favourite,
                textRes = Res.string.bottom_bar_favourites,
                iconPainter = rememberVectorPainter(Icons.Default.Favorite),
                onClick = {
                    if (currentScreen != Screen.Favourite) {
                        navigator.navigate(
                            Screen.Favourite.route,
                            NavOptions(popUpTo = PopUpTo.First()),
                        )
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RowScope.BottomBarItem(
    isSelected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = Color.Gray,
    textRes: StringResource,
    iconPainter: Painter,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        label = {
            Text(
                text = stringResource(textRes),
                color =
                    if (isSelected) {
                        selectedColor
                    } else {
                        unSelectedColor
                    },
            )
        },
        onClick = onClick,
        selected = isSelected,
        icon = {
            Icon(
                painter = iconPainter,
                tint =
                    if (isSelected) {
                        selectedColor
                    } else {
                        unSelectedColor
                    },
                contentDescription = stringResource(textRes),
            )
        },
    )
}
