package com.veprek.honza.rickandmorty.navigation.system

sealed class Screen(
    val route: String,
    val isRoot: Boolean = true,
) {
    data object List : Screen(route = "/list")

    data object Favourite : Screen(route = "/favourite")

    data object Detail : Screen(route = "/character/{id}", isRoot = false) {
        fun createRoute(id: Int): String {
            return "/character/$id"
        }
    }

    data object Search : Screen(route = "/search")
}

fun String?.routeToScreen(): Screen? {
    return when (this) {
        Screen.List.route -> Screen.List
        Screen.Favourite.route -> Screen.Favourite
        Screen.Detail.route -> Screen.Detail
        Screen.Search.route -> Screen.Search
        else -> null
    }
}

