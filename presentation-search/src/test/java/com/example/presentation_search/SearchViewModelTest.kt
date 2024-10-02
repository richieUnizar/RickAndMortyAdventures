package com.example.presentation_search

import com.example.common.Either
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.domain.model.Location
import com.example.domain.model.Origin
import com.example.domain.search.GetSearchUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class SearchViewModelTest {

    private val getSearchUseCase: GetSearchUseCase = mockk()

    private lateinit var viewModel: SearchViewModel

    @BeforeEach
    fun setup() {
        viewModel = SearchViewModel(getSearchUseCase)
    }

    @Test
    fun `Given ViewModel initialized When accessing familyMembers Then return the expected family list`() =
        runTest {
            val expectedFamilyMembers =
                listOf("Rick Sanchez", "Morty Smith", "Beth Smith", "Jerry Smith", "Summer Smith")

            val familyMembers = viewModel.familyMembers.first()

            assertEquals(expectedFamilyMembers, familyMembers)
        }


    @Test
    fun `Given a valid name When onSearchClick is called Then characterList is updated with search results`() =
        runTest {
            val name = "Rick"

            coEvery { getSearchUseCase.searchByName(name) } returns Either.Success(
                character
            )

            viewModel.onSearchClick(name)

            val characterList = viewModel.characterList.first()

            assertEquals(character.characterList, characterList)
        }


    @Test
    fun `Given an invalid name When onSearchClick is called Then characterList is empty`() =
        runTest {
            val error = Throwable("Character not found")

            coEvery { getSearchUseCase.searchByName("InvalidName") } returns Either.Error(error)

            viewModel.onSearchClick("InvalidName")

            val characterList = viewModel.characterList.first()

            assert(characterList.isEmpty())
        }


    private val character = Characters(
        info = Info(1, 1),
        characterList = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = Origin(name = "Earth (C-137)", url = "url"),
                location = Location(name = "Earth (Replacement Dimension)", url = "url"),
                image = "image",
                episode = emptyList(),
                url = "https://rickandmortyapi.com/api/character/1",
                created = "2017-11-04T18:48:46.250Z"
            )
        )
    )


}