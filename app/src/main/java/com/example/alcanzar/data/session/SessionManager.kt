package com.example.alcanzar.data.session

import android.content.Context

object SessionManager {
    private const val PREFS_NAME = "alcanzar_prefs"
    private const val KEY_USER_ID = "user_id"

    var usuarioActualId: String? = null

    fun guardarUsuarioId(context: Context, id: String) {
        usuarioActualId = id
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(KEY_USER_ID, id)
            apply()
        }
    }

    fun obtenerUsuarioId(context: Context): String? {
        if (usuarioActualId != null) return usuarioActualId

        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        usuarioActualId = sharedPref.getString(KEY_USER_ID, null)
        return usuarioActualId
    }

    fun cerrarSesion(context: Context) {
        usuarioActualId = null
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove(KEY_USER_ID)
            apply()
        }
    }
}