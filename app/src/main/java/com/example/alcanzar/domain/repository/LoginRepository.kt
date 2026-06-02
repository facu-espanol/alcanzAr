package com.example.alcanzar.domain.repository

interface LoginRepository {
    fun iniciarSesion(usuario: String, password: String, onResult: (String?) -> Unit)
    fun crearCuenta(
        usuario: String, 
        password: String, 
        nombreCompleto: String, 
        fotoUrl: String, 
        onResult: (String?) -> Unit
    )
}
