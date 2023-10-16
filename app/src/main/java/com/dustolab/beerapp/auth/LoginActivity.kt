package com.dustolab.beerapp.auth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View

import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

import com.dustolab.beerapp.MainActivity
import com.dustolab.beerapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        auth = Firebase.auth

    }

    fun switchToSignup(v: View){
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun login(v: View){
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}