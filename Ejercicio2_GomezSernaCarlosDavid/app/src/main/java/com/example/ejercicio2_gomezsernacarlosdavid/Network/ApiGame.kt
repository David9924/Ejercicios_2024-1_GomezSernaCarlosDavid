package com.example.ejercicio2_gomezsernacarlosdavid.Network

import retrofit2.http.GET

interface ApiGame {

    @GET("api/v2/Characters/{id}")
    fun getDetail(

    )
}