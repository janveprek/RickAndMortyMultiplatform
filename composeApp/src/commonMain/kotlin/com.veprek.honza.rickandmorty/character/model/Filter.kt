package com.veprek.honza.rickandmorty.character.model

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import rickandmorty.composeapp.generated.resources.Res

sealed interface Filter

@OptIn(ExperimentalResourceApi::class)
sealed class StatusFilter(
    val nameResource: StringResource,
    val apiName: String,
): Filter {
    data object All : StatusFilter(Res.string.status_all, "")
    data object Alive : StatusFilter(Res.string.status_alive, "alive")
    data object Dead : StatusFilter(Res.string.status_dead, "dead")
    data object Unknown : StatusFilter(Res.string.status_unknown, "unknown")
}
