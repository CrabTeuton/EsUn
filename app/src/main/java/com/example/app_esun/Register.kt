package com.example.app_esun

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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

class Register : AppCompatActivity() {

    private lateinit var etnombre: EditText
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etnombre = findViewById(R.id.register_name)
        etemail = findViewById(R.id.register_email)
        etpassword = findViewById(R.id.register_password)

        val btnRegister = findViewById<Button>(R.id.register_btn_register)
        btnRegister.setOnClickListener {
            registerUser()
        }

        val registerLogin = findViewById<TextView>(R.id.register_login)
        registerLogin.setOnClickListener {
            goToLogin()
        }

        val registerHome = findViewById<ImageView>(R.id.register_home)
        registerHome.setOnClickListener {
            goToMenu()
        }
    }

    private fun registerUser() {
        val name = etnombre.text.toString()
        val email = etemail.text.toString()
        val password = etpassword.text.toString()

        // Validación del formulario
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonResponse = JSONObject(response)
                val success = jsonResponse.getBoolean("success")

                if (success) {
                    Log.d("RegisterRequest", "Registro exitoso")
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                    // Agregar código para ir a MainActivity después de un registro exitoso
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Esto cierra la actividad actual para que el usuario no pueda volver atrás.
                } else {
                    val error = jsonResponse.getString("error")
                    Log.e("RegisterRequest", "Error en el registro: $error")
                    Toast.makeText(this, "Error en el registro: $error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val errorListener = Response.ErrorListener { error: VolleyError ->
            Log.e("RegisterRequest", "Error en la solicitud: ${error.message}")
            Toast.makeText(this, "Error en la solicitud: ${error.message}", Toast.LENGTH_SHORT).show()
        }

        val registerRequest = RegisterRequest(name, email, password, responseListener, errorListener)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(registerRequest)
    }

    private fun goToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    private fun goToMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
