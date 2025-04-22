package com.example.app_esun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        //Ir al Home desde el Perfil
        val perfil_home = findViewById<ImageView>(R.id.perfil_home)
        perfil_home.setOnClickListener{
            irAlHome()
        }

        //Ir al Search desde el Perfil
        val perfil_search = findViewById<ImageView>(R.id.perfil_search)
        perfil_search.setOnClickListener{
            irAlSearch()
        }

        //Ir a Favoritos desde el Perfil
        val perfil_favorito = findViewById<ImageView>(R.id.perfil_favoritos)
        perfil_favorito.setOnClickListener{
            irAFavoritos()
        }

        //Ir a Foro desde el Perfil
        val perfil_foro = findViewById<ImageView>(R.id.perfil_home)
        perfil_foro.setOnClickListener{
            irAlForo()
        }

    }

    private fun irAlHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    private fun irAlSearch() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    private fun irAFavoritos() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    private fun irAlForo() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }


}