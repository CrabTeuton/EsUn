package com.example.app_esun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ir al registro desde el main
        val main_register = findViewById<TextView>(R.id.main_register)
        main_register.setOnClickListener{
            irAlRegister()
        }

        //Ir al login desde el main
        val main_login = findViewById<TextView>(R.id.main_btn_login)
        main_login.setOnClickListener{
            irAlLogin()
        }

    }
    private fun irAlRegister() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    private fun irAlLogin(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

}