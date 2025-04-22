package com.example.app_esun

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class RegisterRequest(
    name: String,
    email: String,
    password: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener
) : StringRequest(
    Method.POST,
    "http://192.168.3.63/Register.php",
    listener,
    errorListener
) {
    private var nameValue = name
    private var emailValue = email
    private var passwordValue = password

    override fun getParams(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        params["name"] = nameValue
        params["email"] = emailValue
        params["password"] = passwordValue
        return params
    }

    override fun getBodyContentType(): String {
        return "application/x-www-form-urlencoded; charset=UTF-8"
    }
}