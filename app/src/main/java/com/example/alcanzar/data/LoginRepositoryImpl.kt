package com.example.alcanzar.data.repository

import com.example.alcanzar.domain.repository.LoginRepository

class AuthRepositoryImpl : LoginRepository {

    override fun login(usuario: String, password: String): Boolean {
        return usuario == "admin" && password == "123456"
    }
}