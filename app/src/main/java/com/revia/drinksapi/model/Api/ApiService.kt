package com.revia.drinksapi.model.Api


import com.revia.drinksapi.model.response.Drinks
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("search.php")
    suspend fun getDrinksDataSearched(@Query("s") query:String): Response<Drinks>
}