package com.jrcodev.numberstrivia.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {
    @GET("/random")
    suspend fun getRandomTrivia(): Response<String>

    @GET("/{number}")
    suspend fun getNumberTrivia(@Path("number") number: String): Response<String>
}

object NumbersApiService {
    private const val BASE_URL = "http://numbersapi.com/"
    private val api: NumbersApi = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(NumbersApi::class.java)

    suspend fun getRandomTrivia() = api.getRandomTrivia()
    suspend fun getNumberTrivia(number: String) = api.getNumberTrivia(number)
}