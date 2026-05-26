package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.repository.LoginRepository

class IniciarSesionUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(usuario: String, password: String): Boolean {
        return repository.iniciarSesion(usuario, password)
    }
}