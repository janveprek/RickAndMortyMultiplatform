package com.veprek.honza.rickandmorty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
