package com.example.app_esun

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {

    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        queue = Volley.newRequestQueue(this)

        val loginRegister = findViewById<TextView>(R.id.login_register)
        loginRegister.setOnClickListener { goToRegister() }

        val loginMain = findViewById<ImageView>(R.id.login_main)
        loginMain.setOnClickListener { goToMain() }

        val loginButton = findViewById<Button>(R.id.login_btn_home)
        loginButton.setOnClickListener { loginUser() }
    }

    private fun goToRegister() {
        startActivity(Intent(this, Register::class.java))
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun loginUser() {
        val email = findViewById<EditText>(R.id.login_email).text.toString().trim()
        val password = findViewById<EditText>(R.id.login_password).text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val responseListener = Response.Listener<String> { response ->
            Log.d("LoginResponse", "Respuesta: $response")
            try {
                val jsonResponse = JSONObject(response)
                when {
                    jsonResponse.getBoolean("success") -> {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Home::class.java))
                        finish()
                    }
                    else -> {
                        val error = jsonResponse.getString("error")
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: JSONException) {
                Log.e("LoginError", "Error parsing JSON: ${e.message}")
                Toast.makeText(this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show()
            }
        }

        val errorListener = Response.ErrorListener { error ->
            error.networkResponse?.let {
                val errorData = String(it.data)
                Log.e("LoginError", "Error ${it.statusCode}: $errorData")
                Toast.makeText(this, "Error del servidor: ${it.statusCode}", Toast.LENGTH_LONG).show()
            } ?: run {
                Log.e("LoginError", "Error de conexión: ${error.message}")
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        val loginRequest = LoginRequest(email, password, responseListener, errorListener)
        queue.add(loginRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll(this)
    }
}