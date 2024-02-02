package com.revia.drinksapi.model

import com.revia.drinksapi.model.Api.ApiClient
import com.revia.drinksapi.model.Api.ApiService


class Repository {
    private var Api: ApiService = ApiClient.createService(ApiService::class.java)


    suspend fun getDrinksDataSearched(string: String)=Api.getDrinksDataSearched(string)
}