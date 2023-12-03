package com.example.ejercicio2_gomezsernacarlosdavid.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejercicio2_gomezsernacarlosdavid.R
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}