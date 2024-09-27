package com.example.data_source_rest.character

import com.example.common.Either
import com.example.data_source_rest.model.CharacterDTO
import javax.inject.Inject

class CharacterDataSourceImpl @Inject constructor(
    private val characterApiRest: CharacterApiRest
) : CharacterDataSource {

    override fun getCharacterById(id: Int): Either<Throwable, CharacterDTO> {
        val call = characterApiRest.getCharacterById(id)
        val response = call.execute()

        return if (response.isSuccessful) {
            val character: CharacterDTO = response.body()!!
            Either.Success(character)
        } else {
            Either.Error(Throwable("Error getting character"))
        }
    }

}