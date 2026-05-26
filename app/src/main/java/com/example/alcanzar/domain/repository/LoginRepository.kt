package com.example.alcanzar.domain.repository

interface LoginRepository {
    fun iniciarSesion(usuario: String, password: String): Boolean
    fun crearCuenta(usuario: String, password: String): Boolean
}