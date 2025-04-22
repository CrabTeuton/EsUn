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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginRegister = findViewById<TextView>(R.id.login_register)
        loginRegister.setOnClickListener {
            goToRegister()
        }

        val loginMain = findViewById<ImageView>(R.id.login_main)
        loginMain.setOnClickListener {
            goToMain()
        }

        val loginButton = findViewById<Button>(R.id.login_btn_home)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun goToRegister() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginUser() {
        val user_email = findViewById<EditText>(R.id.login_email).text.toString()
        val user_password = findViewById<EditText>(R.id.login_password).text.toString()

        // Depuración: Imprimir valores antes de enviar la solicitud
        Log.d("LoginRequest", "Email: $user_email, Password: $user_password")

        if (user_email.isEmpty() || user_password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val responseListener = Response.Listener<String> { response ->
            try {
                handleLoginResponse(response)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val errorListener = Response.ErrorListener { error: VolleyError ->
            Log.e("LoginRequest", "Error en la solicitud: ${error.message}")
            Toast.makeText(this, "Error en la solicitud: ${error.message}", Toast.LENGTH_SHORT).show()
        }

        // Utiliza los nombres de los parámetros correctos aquí
        val loginRequest = LoginRequest(user_email, user_password, responseListener, errorListener)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(loginRequest)
    }

    private fun handleLoginResponse(response: String) {
        Log.d("LoginRequest", "Response from server: $response")

        try {
            val jsonResponse = JSONObject(response)
            val success = jsonResponse.optBoolean("success", false)

            Log.d("LoginRequest", "Success: $success")

            if (success) {
                Log.d("LoginRequest", "Inicio de sesión exitoso")
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Home::class.java)
                Log.d("LoginRequest", "Intent creado")
                startActivity(intent)
                Log.d("LoginRequest", "Intent iniciado")
                finish()

            } else {
                val error = jsonResponse.optString("error", "No hay mensaje de error")
                Log.e("LoginRequest", "Error en el inicio de sesión: $error")

                // Muestra el mensaje de error al usuario
                Toast.makeText(this, "Error en el inicio de sesión: $error", Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("LoginRequest", "Error parsing JSON: $response")
            Toast.makeText(this, "Email o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

}
