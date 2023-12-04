package com.example.ejercicio2_gomezsernacarlosdavid.ui.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio2_gomezsernacarlosdavid.MainActivity3
import com.example.ejercicio2_gomezsernacarlosdavid.Modelos.Datos
import com.example.ejercicio2_gomezsernacarlosdavid.Modelos.Detalles
import com.example.ejercicio2_gomezsernacarlosdavid.Network.ApiGame
import com.example.ejercicio2_gomezsernacarlosdavid.Network.RetrofitService
import com.example.ejercicio2_gomezsernacarlosdavid.R
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.ActivityMain2Binding

import com.example.ejercicio2_gomezsernacarlosdavid.ui.adapters.Adaptador
import com.example.ejercicio2_gomezsernacarlosdavid.utils.Constants
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)



            val call = RetrofitService.getRetrofit()
                .create(ApiGame::class.java)
                .getDetail("api/v2/Characters")



            call.enqueue(object : Callback<ArrayList<Datos>> {
                override fun onResponse(
                    call: Call<ArrayList<Datos>>,
                    response: Response<ArrayList<Datos>>
                ) {
                    binding.pbConexion.visibility = View.INVISIBLE

                    Toast.makeText(this@MainActivity2, "Hay sistema", Toast.LENGTH_SHORT).show()
                    Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                    val adaptador = Adaptador(response.body()!!) { dato ->
                        Toast.makeText(
                            this@MainActivity2,
                            "Personaje seleccionado",
                            Toast.LENGTH_SHORT
                        ).show()

                        val bundle = bundleOf(
                            "id" to dato.id

                        )

                        val intent = Intent(this@MainActivity2, MainActivity3::class.java)

                        intent.putExtras(bundle)

                        startActivity(intent)

                    }

                    binding.rvMenu.layoutManager =
                        LinearLayoutManager(this@MainActivity2, RecyclerView.VERTICAL, false)

                    binding.rvMenu.adapter = adaptador


                }

                override fun onFailure(call: Call<ArrayList<Datos>>, t: Throwable) {
                    binding.pbConexion.visibility = View.INVISIBLE
                    Toast.makeText(this@MainActivity2, "No hay sistema", Toast.LENGTH_SHORT).show()



                }

            })

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser
        binding.tvUser.text = user?.email

        if (user?.isEmailVerified != true) {
            binding.tvCorreoNoVerificado.visibility = View.VISIBLE
        }

        binding.btnSignOut.setOnClickListener {


            firebaseAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()


        }
    }

}