package com.example.presentation_search

import com.example.common.Either
import com.example.common.asSuccess
import com.example.domain.search.GetSearchUseCase
import com.example.domain.search.GetSearchUseCase.Params
import com.example.test_utils_android.InstantExecutorExtension
import com.example.test_utils_android.TestCoroutineExtension
import com.example.test_utils_android.getOrAwaitValue
import com.example.test_utils_android.test_utils.createCharacters
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, TestCoroutineExtension::class)
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

            val familyMembers = viewModel.familyMembers.getOrAwaitValue()

            assertEquals(expectedFamilyMembers, familyMembers)
        }

    @Test
    fun `Given a valid name When onSearchClick is called Then characterList is updated with search results`() =
        runTest {
            val name = "Rick"

            val characters = createCharacters()

            coEvery { getSearchUseCase.run(Params(name)) } returns characters.asSuccess()

            viewModel.onSearchClick(name)

            val characterList = viewModel.characterList.getOrAwaitValue()

            assertEquals(characters.characterList, characterList)
        }


    @Test
    fun `Given an invalid name When onSearchClick is called Then characterList is empty`() =
        runTest {
            val error = Throwable("Character not found")

            coEvery { getSearchUseCase.run(Params("InvalidName")) } returns Either.Error(error)

            viewModel.onSearchClick("InvalidName")

            val characterList = viewModel.characterList.getOrAwaitValue()

            assert(characterList.isEmpty())
        }
}