package com.example.data_source_rest.character

import com.example.common.Either
import com.example.data_source_rest.model.CharacterDTO

interface CharacterDataSource {

    fun getCharacterById(id: Int): Either<Throwable, CharacterDTO>

}