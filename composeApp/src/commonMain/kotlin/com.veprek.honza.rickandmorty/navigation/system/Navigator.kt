package com.veprek.honza.rickandmorty.navigation.system

import androidx.compose.runtime.Composable
import com.veprek.honza.rickandmorty.character.presentation.detail.CharacterDetailScreen
import com.veprek.honza.rickandmorty.character.presentation.favorite.FavoriteCharactersScreen
import com.veprek.honza.rickandmorty.character.presentation.list.CharacterListScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path

@Composable
fun MainNavHost(navigator: Navigator) {
    val navigateToDetail = { id: Int ->
        navigator.navigate(Screen.Detail.createRoute(id))
    }
    NavHost(
        navigator = navigator,
        initialRoute = Screen.List.route,
    ) {
        scene(route = Screen.List.route) {
            CharacterListScreen(
                navigateToDetail = navigateToDetail,
            )
        }
        scene(route = Screen.Favourite.route) {
            FavoriteCharactersScreen(
                navigateToDetail = navigateToDetail,
            )
        }
        scene(route = Screen.Detail.route) { backStackEntry ->
            backStackEntry.path<Int>("id")?.let { id ->
                CharacterDetailScreen(goBack = navigator::goBack, id = id)
            }
        }
    }
}
