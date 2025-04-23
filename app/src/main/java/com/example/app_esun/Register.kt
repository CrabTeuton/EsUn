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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Register : AppCompatActivity() {

    private lateinit var etnombre: EditText
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar la cola de Volley
        queue = Volley.newRequestQueue(this)

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
        val name = etnombre.text.toString().trim()
        val email = etemail.text.toString().trim()
        val password = etpassword.text.toString().trim()

        // Validación del formulario mejorada
        when {
            TextUtils.isEmpty(name) -> {
                etnombre.error = "Nombre requerido"
                return
            }
            TextUtils.isEmpty(email) -> {
                etemail.error = "Email requerido"
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etemail.error = "Email inválido"
                return
            }
            TextUtils.isEmpty(password) -> {
                etpassword.error = "Contraseña requerida"
                return
            }
            password.length < 6 -> {
                etpassword.error = "La contraseña debe tener al menos 6 caracteres"
                return
            }
        }

        val responseListener = Response.Listener<String> { response ->
            try {
                Log.d("RegisterResponse", "Respuesta: $response")
                val jsonResponse = JSONObject(response)
                val success = jsonResponse.getBoolean("success")

                if (success) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val error = jsonResponse.getString("error")
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
            }
        }

        val errorListener = Response.ErrorListener { error ->
            error.networkResponse?.let {
                val errorData = String(it.data)
                Log.e("RegisterError", "Error: ${it.statusCode}, $errorData")
                Toast.makeText(this, "Error del servidor: ${it.statusCode}", Toast.LENGTH_LONG).show()
            } ?: run {
                Log.e("RegisterError", "Error: ${error.message}")
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        val registerRequest = object : StringRequest(
            Method.POST,
            "http://192.168.100.5/app_esun/Register.php",
            responseListener,
            errorListener
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["email"] = email
                params["password"] = password
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }
        }

        queue.add(registerRequest)
    }

    private fun goToLogin() {
        startActivity(Intent(this, Login::class.java))
    }

    private fun goToMenu() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll(this)
    }
}