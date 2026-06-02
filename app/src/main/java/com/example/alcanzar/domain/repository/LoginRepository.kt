package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Usuario

interface LoginRepository {
    fun iniciarSesion(usuario: String, password: String, onResult: (String?) -> Unit)
    fun crearCuenta(
        usuario: String, 
        password: String, 
        nombreCompleto: String, 
        fotoUrl: String, 
        onResult: (String?) -> Unit
    )
    fun obtenerUsuariosPorIds(ids: List<String>, onResult: (List<Usuario>) -> Unit)
}
