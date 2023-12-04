package com.example.ejercicio2_gomezsernacarlosdavid.ui.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ejercicio2_gomezsernacarlosdavid.R
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var contrasenia: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }


        with(binding){
            binding.btnLogin.setOnClickListener {
                if(!validaCampos()) return@setOnClickListener

                //autenticamos al usuario
                autenticaUsuario(email, contrasenia)
            }

            binding.btnRegistrarse.setOnClickListener {
                if(!validaCampos()) return@setOnClickListener

                //registramos al usuario
                registraUsuario(email, contrasenia)
            }

            binding.tvRestablecerPassword.setOnClickListener {
                val resetEmail = EditText(it.context)
                resetEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                val passwordResetDialog = AlertDialog.Builder(it.context)
                    .setTitle(getString(R.string.contra))
                    .setMessage(getString(R.string.corr))
                    .setView(resetEmail)
                    .setPositiveButton(getString(R.string.env)){ _, _ ->
                        val mail = resetEmail.text.toString()
                        if(mail.isNotEmpty()){
                            firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                                Toast.makeText(this@Login,getString(R.string.id1) , Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(this@Login,getString(R.string.id2) , Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@Login,getString(R.string.id3) , Toast.LENGTH_SHORT).show()
                        }
                    }.setNegativeButton(getString(R.string.id3)){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }

    private fun validaCampos(): Boolean{
        email = binding.tietEmail.text.toString().trim() //para que quite espacios en blanco
        contrasenia = binding.tietContrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = getString(R.string.id4)
            binding.tietEmail.requestFocus()
            return false
        }

        if(contrasenia.isEmpty() || contrasenia.length < 6){
            binding.tietContrasenia.error = getString(R.string.id5)
            binding.tietContrasenia.requestFocus()
            return false
        }

        return true
    }

    private fun manejaErrores(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, getString(R.string.id6), Toast.LENGTH_SHORT).show()
                binding.tietEmail.error = getString(R.string.id7)
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this,getString(R.string.id8) , Toast.LENGTH_SHORT).show()
                binding.tietContrasenia.error = getString(R.string.id9)
                binding.tietContrasenia.requestFocus()
                binding.tietContrasenia.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this,getString(R.string.id9) , Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, getString(R.string.id10) , Toast.LENGTH_LONG).show()
                binding.tietEmail.error = (getString(R.string.id11))
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this,getString(R.string.id12) , Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this,getString(R.string.id13) , Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this,getString(R.string.id14) , Toast.LENGTH_LONG).show()
                binding.tietContrasenia.error = getString(R.string.id15)
                binding.tietContrasenia.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this,getString(R.string.id16) , Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this,getString(R.string.id17) , Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun autenticaUsuario(usr: String, psw: String){
        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            binding.progressBar.visibility = View.GONE

            if(authResult.isSuccessful){
                Toast.makeText(this,getString(R.string.id18) , Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity2::class.java))
                finish()
            }else{
                manejaErrores(authResult)
            }
        }
    }

    private fun registraUsuario(usr: String, psw: String){
        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.createUserWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->

            if(authResult.isSuccessful){

                val user_fb = firebaseAuth.currentUser

                user_fb?.sendEmailVerification()?.addOnSuccessListener {
                    Toast.makeText(this,getString(R.string.id19) , Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener {
                    Toast.makeText(this,getString(R.string.id20) , Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this,getString(R.string.id21) , Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity2::class.java))
                finish()
            }else{
                manejaErrores(authResult)
            }


        }
    }
}