package com.example.domain.characters

import com.example.common.Either
import com.example.domain.Favourites.get_list.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.characters.GetCharactersUseCase.Params
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.domain.model.Location
import com.example.domain.model.Origin
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class GetCharactersUseCaseTest {

    private val characterRepository: CharactersRepository = mockk()
    private val favouriteCharactersUseCase: GetFavouriteCharactersUseCase = mockk()

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @BeforeEach
    fun setup() {
        getCharactersUseCase = GetCharactersUseCase(characterRepository, favouriteCharactersUseCase)
    }

    @ParameterizedTest(
        name = """
            Given a favourite character {0}
            When GetCharactersUseCase is called
            Then the characters {1}
        """
    )
    @MethodSource("methodSourceTestGetCharactersSuccess")
    fun testGetCharactersSuccess(
        favouriteCharacters: Either.Success<List<FavouriteCharacter>>,
        expectedCharacters: Characters
    ) = runTest {
        val character = createCharacter(false)

        coEvery { characterRepository.getCharacters(PAGE_NUMBER) } returns Either.Success(character)
        coEvery { favouriteCharactersUseCase.run() } returns favouriteCharacters

        val result = getCharactersUseCase.run(Params(PAGE_NUMBER))

        Assertions.assertEquals(expectedCharacters, result.getRightValue())
    }

    @Test
    fun `Given characters repository returns error When getCharacters is called Then the error is returned`() =
        runTest {
            val error = Throwable("Error getting characters")

            coEvery { characterRepository.getCharacters(PAGE_NUMBER) } returns Either.Error(error)
            coEvery { favouriteCharactersUseCase.run() } returns Either.Success(emptyList())

            val result = getCharactersUseCase.run(Params(PAGE_NUMBER))

            Assertions.assertEquals(error, result.getLeftValue())
        }

    @Test
    fun `Given favourite characters use case returns an error and characters repository return characters When getCharacters is called Then characters are returned`() =
        runTest {
            val expectedCharacter = createCharacter()

            coEvery { characterRepository.getCharacters(PAGE_NUMBER) } returns
                    Either.Success(createCharacter())

            coEvery { favouriteCharactersUseCase.run() } returns
                    Either.Error(Throwable("Error getting favourites characters"))

            val result = getCharactersUseCase.run(Params(PAGE_NUMBER))

            Assertions.assertEquals(expectedCharacter, result.getRightValue())
        }


    companion object {

        private const val PAGE_NUMBER = 1

        fun createCharacter(isOnFavourites: Boolean = false) = Characters(
            info = Info(1, 1),
            characterList = listOf(
                Character(
                    id = 1,
                    name = "Rick",
                    status = "Alive",
                    species = "Human",
                    type = null,
                    gender = "Male",
                    origin = Origin("originName", "originUrl"),
                    location = Location("locationName", "locationUrl"),
                    image = "image1",
                    episode = emptyList(),
                    url = "",
                    created = "",
                    isInFavourites = isOnFavourites
                )
            )
        )

        val FAVOURITE_CHARACTER = listOf(
            FavouriteCharacter(
                id = 1,
                name = "Rick",
                status = "Alive",
                species = "Human",
                image = "image1"
            )
        )

        @JvmStatic
        fun methodSourceTestGetCharactersSuccess(): Stream<Arguments> = Stream.of(
            Arguments.of(
                Named.of("with elements", Either.Success(FAVOURITE_CHARACTER)),
                Named.of(
                    "is on favourites",
                    createCharacter(isOnFavourites = true)
                )
            ),
            Arguments.of(
                Named.of(
                    "without elements",
                    Either.Success(emptyList<FavouriteCharacter>())
                ),
                Named.of(
                    "is not on favourites",
                    createCharacter(isOnFavourites = false)
                )
            ),
        )
    }

}