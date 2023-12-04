package com.example.ejercicio2_gomezsernacarlosdavid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ejercicio2_gomezsernacarlosdavid.Modelos.Detalles
import com.example.ejercicio2_gomezsernacarlosdavid.Network.ApiGame
import com.example.ejercicio2_gomezsernacarlosdavid.Network.RetrofitService
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.ActivityMain2Binding
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.ActivityMain3Binding
import com.example.ejercicio2_gomezsernacarlosdavid.ui.activites.MainActivity2
import com.example.ejercicio2_gomezsernacarlosdavid.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getInt("id")


        val call = RetrofitService.getRetrofit()
            .create(ApiGame::class.java)
            .getgotdetailApiary(id)

        call.enqueue(object: Callback<Detalles>{
            override fun onResponse(
                call: Call<Detalles>,
                response: Response<Detalles>
            ) {
                with(binding) {
                    pbConexion.visibility = View.INVISIBLE
                    text3.text = response.body()?.id.toString()
                    text1.text = response.body()?.title
                    text4.text = response.body()?.firstName
                    text5.text = response.body()?.lastName
                    text6.text = response.body()?.fullName
                    text7.text = response.body()?.family
                    text8.text = response.body()?.imageUrl
                    val houseImage: Int = when (response.body()?.family) {
                        getString(R.string.id24) -> R.drawable.stark
                        getString(R.string.id25) -> R.drawable.lannister
                        getString(R.string.id26) -> R.drawable.martell
                        getString(R.string.id27) -> R.drawable.mormont
                        getString(R.string.id28) -> R.drawable.strong
                        getString(R.string.id29) -> R.drawable.targaryen
                        getString(R.string.id30) -> R.drawable.tully
                        getString(R.string.id31) -> R.drawable.tyrell
                        getString(R.string.id32) -> R.drawable.velaryon
                        "House Greyjoy" -> R.drawable.greyjoy
                        "House Hightower" -> R.drawable.hightower
                        "House Cole" -> R.drawable.cole
                        "House Bolton" -> R.drawable.bolton
                        "House Baratheon" -> R.drawable.baratheon
                        "House Arryn" -> R.drawable.rrin
                        "House Tarly" -> R.drawable.tarli

                        else -> R.drawable.de
                    }
                    Glide.with(this@MainActivity3)
                        .load(response.body()?.imageUrl)
                        .into(image2)
                    Glide.with(this@MainActivity3)
                        .load(houseImage)
                        .into(image3)

                    Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")
                }
            }


            override fun onFailure(call: Call<Detalles>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


    }
}