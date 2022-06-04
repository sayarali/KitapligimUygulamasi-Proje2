package com.alisayar.kitapligimuygulamas_proje2.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/books/v1/"

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {

    @GET("volumes?maxResults=40&printType=books")
    suspend fun getBooksData(@Query("q") search: String): BookModel

    @GET("volumes/{id}")
    suspend fun getBookDetails(@Path("id") id: String): Item

}

object BooksApi{
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}