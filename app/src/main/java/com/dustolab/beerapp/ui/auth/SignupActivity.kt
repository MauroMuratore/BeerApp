package com.dustolab.beerapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.dustolab.beerapp.R
import com.dustolab.beerapp.logic.repository.UserRepository
import com.dustolab.beerapp.logic.usecase.auth.SignupUseCase
import com.dustolab.beerapp.ui.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class SignupActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
    }

    fun switchToLogin(v: View){
        startActivity(Intent(this, LoginActivity::class.java))
    }

     fun createUser(v: View){
        val username = findViewById<EditText>(R.id.etUsername).text.toString()
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()
        val confermaPassword = findViewById<EditText>(R.id.etConfermaPassword).text.toString()

        if( username.isNotEmpty() && email.isNotEmpty() &&
            password.isNotEmpty() && confermaPassword.isNotEmpty()){
            if(password==confermaPassword){
                val signupUseCase = SignupUseCase(username, email, password)
                signupUseCase.useCase()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Le password sono diverse", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "Compilare tutti i campi", Toast.LENGTH_SHORT).show()
        }
    }

}
