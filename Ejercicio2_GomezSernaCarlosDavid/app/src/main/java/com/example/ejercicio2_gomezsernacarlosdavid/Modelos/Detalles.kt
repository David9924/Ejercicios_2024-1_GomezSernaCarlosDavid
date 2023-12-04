package com.example.ejercicio2_gomezsernacarlosdavid.Modelos

import com.google.gson.annotations.SerializedName

data class Detalles(

    val id: Int?,
    var firstName: String?,
    var lastName: String?,
    var fullName: String?,
    var title: String?,
    var family: String?,
    var image: String?,
    var imageUrl: String?

)
