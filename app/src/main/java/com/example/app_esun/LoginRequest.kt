package com.example.app_esun

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class LoginRequest(
    email: String,
    password: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener
) : StringRequest(
    Method.POST,
    "http://192.168.100.5/app_esun/Login.php", // Usa tu IP local
    listener,
    errorListener
) {
    private val params: Map<String, String>

    init {
        params = HashMap<String, String>().apply {
            put("email", email) // Cambiado a "email" para coincidir con el PHP
            put("password", password) // Cambiado a "password" para coincidir con el PHP
        }
    }

    override fun getParams(): Map<String, String> = params

    override fun getBodyContentType(): String {
        return "application/x-www-form-urlencoded; charset=UTF-8"
    }
}