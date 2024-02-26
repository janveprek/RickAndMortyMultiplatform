package com.veprek.honza.rickandmorty

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.veprek.honza.rickandmorty.app.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}
