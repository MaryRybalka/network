package com.example.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{login}")
    fun getUser(@Path("login") login: String?): Call<User>

    data class User (@SerializedName("name") val name: String?,
                     @SerializedName("avatar_url") val avatarURL:String?,
                     @SerializedName("html_url") val htmlURL:String?,
    )
}