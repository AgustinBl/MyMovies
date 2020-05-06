package com.example.moviesapiretrofit.data.api

import com.example.moviesapiretrofit.data.model.Movie
import com.example.moviesapiretrofit.data.model.Opinion
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movies")
    fun getMovies(): Call<ArrayList<Movie>>

    @GET("movies/{id}/opinions")
    fun getOpinionsById(@Path("id") idMovie: Int): Call<ArrayList<Opinion>>

    @POST("movies/{id}/opinions")
    abstract fun storeOpinion(@Path("id") idMovie: Int,
        @Query("user") user: String,
        @Query("comment") comment: String,
        @Query("rate") rate: Int
        ): Call<Void>

    companion object Factory{
        private const val BASE_URL = "http://192.168.0.12:8080/api/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
