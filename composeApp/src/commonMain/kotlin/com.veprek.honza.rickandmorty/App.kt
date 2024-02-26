package com.veprek.honza.rickandmorty

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.veprek.honza.rickandmorty.design.components.BottomBar
import com.veprek.honza.rickandmorty.design.theme.RickAndMortyTheme
import com.veprek.honza.rickandmorty.navigation.system.MainNavHost
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun App() {
    Napier.base(DebugAntilog())

    PreComposeApp {
        RickAndMortyTheme {
            val navigator = rememberNavigator()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomBar(
                        navigator = navigator,
                    )
                },
            ) {
                Surface(
                    modifier = Modifier.padding(it),
                ) {
                    MainNavHost(navigator = navigator)
                }
            }
        }
    }
}
