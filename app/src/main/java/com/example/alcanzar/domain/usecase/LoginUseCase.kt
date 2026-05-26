package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.repository.LoginRepository

class LoginUseCase(
    private val authRepository: LoginRepository
) {
    operator fun invoke(usuario: String, password: String): Boolean {
        return authRepository.login(usuario, password)
    }
}