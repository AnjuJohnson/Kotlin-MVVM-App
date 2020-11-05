package com.example.mvvmsampleapp.data.network

import com.example.mvvmsampleapp.data.network.responses.AuthResponse
import com.example.mvvmsampleapp.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("login")


    //this function will get suspended.Suspending fn is a fn that can be paused and resumed at a later time.
    // So these type of fn can execute
    //a long running operations and wait for it to complete without blocking the thread.
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse> //changed Call<ResponseBody>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>


    @GET("quotes")
    suspend fun getQuotes():Response<QuotesResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor

        ): MyApi {
            //to bind the newtowrk inceptor
            val okkHttpClient = OkHttpClient.Builder()
                //should not create instance of NetworkConnectionInterceptor here.so use constructor injection
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
//can be call invoke by MyApi()

    }
}