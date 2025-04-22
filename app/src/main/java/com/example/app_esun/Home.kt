package com.example.app_esun

import android.content.Intent
import android.os.Bundle
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout


class Home : AppCompatActivity() {

    private lateinit var imageSwitcher: ImageSwitcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Ir al Search desde el Home
        val home_search = findViewById<ImageView>(R.id.home_search)
        home_search.setOnClickListener{
            irAlSearch()
        }

        //Ir a Favoritos desde el Home
        val home_favoritos = findViewById<ImageView>(R.id.home_favoritos)
        home_favoritos.setOnClickListener{
            irAFavoritos()
        }

        //Ir al Foro desde el Home
        val home_foro = findViewById<ImageView>(R.id.home_foro)
        home_foro.setOnClickListener{
            irAlForo()
        }

        //Ir a Perfil desde el Home
        val home_perfil = findViewById<ImageView>(R.id.home_perfil)
        home_perfil.setOnClickListener{
            irAlPerfil()
        }

        imageSwitcher = findViewById(R.id.imageSwitcher)

        // Configurar el ImageSwitcher
        imageSwitcher.setFactory {
            val imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER

            // Obtener dimensiones desde dimens.xml
            val width = resources.getDimensionPixelSize(R.dimen.image_switcher_width)
            val height = resources.getDimensionPixelSize(R.dimen.image_switcher_height)

            // Configurar el tamaño deseado
            val layoutParams = FrameLayout.LayoutParams(width, height)
            imageView.layoutParams = layoutParams
            imageView
        }

        // Carga de transision de imágenes en el ImageSwitcher
        val images = intArrayOf(
            R.drawable.image_learn1,
            R.drawable.image_learn2,
            R.drawable.image_learn3,
            R.drawable.image_learn4
        )

        var currentIndex = 0

        // Cambiar la imagen en el ImageSwitcher
        imageSwitcher.setImageResource(images[currentIndex])

        // Configurar un clic para cambiar la imagen
        imageSwitcher.setOnClickListener {
            currentIndex = (currentIndex + 1) % images.size
            imageSwitcher.setImageResource(images[currentIndex])
        }
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

    private fun irAlPerfil() {
        val intent = Intent(this, Perfil::class.java)
        startActivity(intent)
    }
}
