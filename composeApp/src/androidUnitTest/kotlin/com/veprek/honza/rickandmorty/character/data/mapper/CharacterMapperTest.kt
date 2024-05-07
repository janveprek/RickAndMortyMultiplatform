package com.veprek.honza.rickandmorty.character.data.mapper

import com.veprek.honza.rickandmorty.character.data.entity.CharacterDetailDto
import com.veprek.honza.rickandmorty.character.data.entity.CharacterDto
import com.veprek.honza.rickandmorty.character.data.entity.LocationDto
import com.veprek.honza.rickandmorty.character.data.entity.OriginDto
import com.veprek.honza.rickandmorty.character.model.CharacterDetail
import com.veprek.honza.rickandmorty.character.model.CharacterModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test


class CharacterMapperTest {

    @Test
    fun `should map characterDto to model correctly`() {
        val id = 2L
        val name = "name"
        val status = "status"
        val iconUrl = "url"

        val character = mockk<CharacterDto> {
            val value = this
            every { value.id } returns id
            every { value.name } returns name
            every { value.status } returns status
            every { value.image } returns iconUrl
        }

        val expected = CharacterModel(
            id = id.toInt(),
            name = name,
            status = status,
            iconUrl = iconUrl
        )
        character.toModel() shouldBe expected
    }

    @Test
    fun `should map character detail to model correctly`() {
        val id = 2
        val name = "name"
        val status = "status"
        val species = "species"
        val type = "type"
        val gender = "gender"
        val origin = OriginDto("origin")
        val location = LocationDto("location")
        val image = "image"

        val character = mockk<CharacterDetailDto> {
            val value = this
            every { value.id } returns id
            every { value.name } returns name
            every { value.status } returns status
            every { value.species } returns species
            every { value.type } returns type
            every { value.gender } returns gender
            every { value.origin } returns origin
            every { value.location } returns location
            every { value.image } returns image
        }

        val expected = CharacterDetail(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin.name,
            location = location.name,
            iconUrl = image
        )
        character.toModel() shouldBe expected
    }
}
