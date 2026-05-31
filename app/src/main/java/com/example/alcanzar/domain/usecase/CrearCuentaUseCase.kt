package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.repository.LoginRepository

class CrearCuentaUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(usuario: String, password: String, onResult: (String?) -> Unit) {
        repository.crearCuenta(usuario, password, onResult)
    }
}
