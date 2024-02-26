package com.veprek.honza.rickandmorty.character.di

import com.veprek.honza.rickandmorty.app.di.KoinHelper
import com.veprek.honza.rickandmorty.character.data.CharacterRepositoryImpl
import com.veprek.honza.rickandmorty.character.data.CharactersApi
import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.AddCharacterToFavouritesUseCaseImpl
import com.veprek.honza.rickandmorty.character.domain.CharacterRepository
import com.veprek.honza.rickandmorty.character.domain.GetAllCharactersUseCase
import com.veprek.honza.rickandmorty.character.domain.GetAllCharactersUseCaseImpl
import com.veprek.honza.rickandmorty.character.domain.GetCharacterByIdUseCase
import com.veprek.honza.rickandmorty.character.domain.GetCharacterByIdUseCaseImpl
import com.veprek.honza.rickandmorty.character.domain.GetCharactersByNameUseCase
import com.veprek.honza.rickandmorty.character.domain.GetCharactersByNameUseCaseImpl
import com.veprek.honza.rickandmorty.character.domain.GetFavouriteCharactersUseCase
import com.veprek.honza.rickandmorty.character.domain.GetFavouriteCharactersUseCaseImpl
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCase
import com.veprek.honza.rickandmorty.character.domain.RemoveCharacterFromFavouritesUseCaseImpl
import com.veprek.honza.rickandmorty.character.presentation.detail.CharacterDetailViewModel
import com.veprek.honza.rickandmorty.character.presentation.favorite.FavoriteCharactersViewModel
import com.veprek.honza.rickandmorty.character.presentation.list.CharactersListViewModel
import com.veprek.honza.rickandmorty.character.system.CharactersApiImpl
import org.koin.dsl.module

val characterModule =
    module {

        single<CharacterRepository> {
            CharacterRepositoryImpl(
                charactersApi = get(),
            )
        }

        single<GetAllCharactersUseCase> {
            GetAllCharactersUseCaseImpl(get())
        }

        single<GetCharacterByIdUseCase> {
            GetCharacterByIdUseCaseImpl(get())
        }

        single<GetCharactersByNameUseCase> {
            GetCharactersByNameUseCaseImpl(get())
        }

        single<GetFavouriteCharactersUseCase> {
            GetFavouriteCharactersUseCaseImpl(get())
        }

        single<AddCharacterToFavouritesUseCase> {
            AddCharacterToFavouritesUseCaseImpl(get())
        }

        single<RemoveCharacterFromFavouritesUseCase> {
            RemoveCharacterFromFavouritesUseCaseImpl(get())
        }

        single {
            FavoriteCharactersViewModel(
                get(),
                get(),
                get(),
            )
        }

        single {
            CharactersListViewModel(
                get(),
                get(),
                get(),
                get(),
            )
        }

        factory { (id: Int) ->
            CharacterDetailViewModel(
                id = id,
                getCharacterById = get(),
            )
        }

        single<CharactersApi> {
            CharactersApiImpl(
                httpClient = KoinHelper.httpClient,
            )
        }
    }
