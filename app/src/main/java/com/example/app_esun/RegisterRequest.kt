package com.example.app_esun

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class RegisterRequest(
    name: String,
    email: String,
    password: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener
) : StringRequest(
    Method.POST,
    "http://localhost/app_esun/Register.php",
    listener,
    errorListener
) {
    private val params: Map<String, String>

    init {
        params = HashMap<String, String>().apply {
            put("name", name)
            put("email", email)
            put("password", password)
        }
    }

    override fun getParams(): Map<String, String> = params
}