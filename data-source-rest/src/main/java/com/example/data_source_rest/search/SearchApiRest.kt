package com.example.data_source_rest.search

import com.example.data_source_rest.model.CharactersDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiRest {

    @GET("character/")
    fun getCharacterByName(@Query("name") name: String): Call<CharactersDTO>

}