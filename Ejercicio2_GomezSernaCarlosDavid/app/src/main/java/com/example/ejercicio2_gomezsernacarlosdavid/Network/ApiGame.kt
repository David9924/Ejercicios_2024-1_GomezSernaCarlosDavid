package com.example.ejercicio2_gomezsernacarlosdavid.Network

import com.example.ejercicio2_gomezsernacarlosdavid.Modelos.Datos
import com.example.ejercicio2_gomezsernacarlosdavid.Modelos.Detalles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiGame {

    @GET
    fun getDetail(
        @Url url: String?
    ):Call<ArrayList<Datos>>

    @GET("api/v2/Characters/{id}")
    fun getgotdetailApiary(
        @Path("id") id: Int?

    ): Call<Detalles>
}