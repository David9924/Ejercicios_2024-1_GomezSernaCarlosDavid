package com.example.formulas_ejercicio1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formulas_ejercicio1.databinding.ActivityMainBinding
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formulas = resources.getStringArray(R.array.Formulas)
        val spinner = findViewById<Spinner>(R.id.spinner)

        if(spinner != null)
        {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, formulas
            )

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    binding.editTextNumber.text.clear()
                    binding.editTextNumber2.text.clear()

                    val elegirformula = formulas[position]
                    //val elegformula2 = parent.getItemAtPosition(position).toString().toInt()
                    val num1 = binding.editTextNumber.text.toString()
                    val num2 = binding.editTextNumber2.text.toString()
                    when(elegirformula){
                        formulas[0] -> {
                            binding.textView5.visibility = View.VISIBLE
                            binding.textView6.visibility = View.VISIBLE
                            binding.textView7.visibility = View.INVISIBLE
                            binding.textView8.visibility = View.INVISIBLE
                            binding.textView9.visibility = View.INVISIBLE
                            binding.textView10.visibility = View.INVISIBLE
                            binding.imageView.visibility = View.VISIBLE
                            binding.imageView2.visibility = View.INVISIBLE
                            binding.imageView3.visibility = View.INVISIBLE

                        }
                        formulas[1] -> {
                            binding.textView7.visibility = View.VISIBLE
                            binding.textView8.visibility = View.VISIBLE
                            binding.textView5.visibility = View.INVISIBLE
                            binding.textView6.visibility = View.INVISIBLE
                            binding.textView9.visibility = View.INVISIBLE
                            binding.textView10.visibility = View.INVISIBLE
                            binding.imageView.visibility = View.INVISIBLE
                            binding.imageView2.visibility = View.INVISIBLE
                            binding.imageView3.visibility = View.VISIBLE
                        }
                        formulas[2] -> {
                            binding.textView9.visibility = View.VISIBLE
                            binding.textView10.visibility = View.VISIBLE
                            binding.textView5.visibility = View.INVISIBLE
                            binding.textView6.visibility = View.INVISIBLE
                            binding.textView7.visibility = View.INVISIBLE
                            binding.textView8.visibility = View.INVISIBLE
                            binding.imageView.visibility = View.INVISIBLE
                            binding.imageView2.visibility = View.VISIBLE
                            binding.imageView3.visibility = View.INVISIBLE
                        }
                    }

                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }


        }

        binding.button.setOnClickListener {
           var num = 0
            var num2 = 0


            if(binding.editTextNumber.text.isNotEmpty()){
                num =  binding.editTextNumber.text.toString().toInt()
            android.widget.Toast.makeText( this, getString((R.string.validacion)), android.widget.Toast.LENGTH_LONG).show()
            }
            else{
                binding.editTextNumber.error = getString((R.string.valor))
                //android.widget.Toast.makeText(this, "Ingresa un número por favor", android.widget.Toast.LENGTH_LONG).show()
        }
            if(binding.editTextNumber2.text.isNotEmpty()){
                num2 = binding.editTextNumber2.text.toString().toInt()
                //android.widget.Toast.makeText( this, "Resultado obtenido", android.widget.Toast.LENGTH_LONG).show()
            }
            else{
                binding.editTextNumber2.error = getString((R.string.valor))
                //android.widget.Toast.makeText(this, "Ingresa un número por favor", android.widget.Toast.LENGTH_LONG).show()
            }


            if(binding.editTextNumber.text.isNotEmpty() && binding.editTextNumber2.text.isNotEmpty()) {
                val resultado = when(spinner.selectedItemPosition){
                    0 -> formula1(num,num2)
                    1 -> formula2(num,num2)
                    2 -> formula3(num,num2)
                    else -> getString(R.string.valor_i)
                }
                binding.textView3.visibility = View.VISIBLE
                binding.textView3.text = resultado

            }
            else
            {
                binding.textView3.visibility = android.view.View.INVISIBLE
            }




        }


    }

    //Funcion para ley de Ohm
    fun formula1(num: Int,num2: Int): String
    {

        if(num >= 0 && num2 >= 0) {
            val v = num* num2
            return v.toString()
        }
        else{
            android.widget.Toast.makeText(this, getString((R.string.valor_s)), android.widget.Toast.LENGTH_LONG).show()
            return getString((R.string.valor_i))
        }
    }

    //Funcion para aceleracion a = F/m
    fun formula2(num: Int,num2: Int): String
    {

        if( num2 > 0) {
            val a = num / num2
            return a.toString()
        }
        else{
            android.widget.Toast.makeText(this, getString((R.string.valor_s)), android.widget.Toast.LENGTH_LONG).show()
            return getString((R.string.valor_i))
        }
    }


    //Funcion para area de un cuadrado bxh/2
    fun formula3(num: Int,num2: Int): String
    {

        if(num >= 0 && num2 >= 0) {
            val A = (num* num2)/2
            return A.toString()
        }
        else{
            android.widget.Toast.makeText(this, getString((R.string.valor_s)), android.widget.Toast.LENGTH_LONG).show()
            return getString((R.string.valor_i))
        }
    }




}