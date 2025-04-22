package com.example.app_esun

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class LoginRequest(
    email: String,
    password: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener
) : StringRequest(
    Method.POST,
    "http://192.168.3.63/Login.php",
    listener,
    errorListener
) {
    private val params: MutableMap<String, String>

    init {
        params = HashMap()
        params["user_email"] = email
        params["user_password"] = password
    }

    override fun getParams(): Map<String, String> {
        return params
    }

    override fun getBodyContentType(): String {
        return "application/x-www-form-urlencoded; charset=UTF-8"
    }
}
