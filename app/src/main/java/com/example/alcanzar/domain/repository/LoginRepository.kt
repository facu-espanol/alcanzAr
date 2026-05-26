package com.example.alcanzar.domain.repository

interface LoginRepository {
    fun login(usuario: String, password: String): Boolean
}