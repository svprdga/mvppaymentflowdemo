package com.svprdga.mvppaymentflowdemo.data.network.abstraction

import com.svprdga.mvppaymentflowdemo.data.network.response.CharactersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {

    @GET("v1/public/characters")
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int): Observable<CharactersResponse>

}