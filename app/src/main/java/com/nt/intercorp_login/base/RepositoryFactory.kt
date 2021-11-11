package com.nt.intercorp_login.base

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryFactory {

    private const val BASE_URL = "https://615a29a1601e6f0017e5a3ee.mockapi.io/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}