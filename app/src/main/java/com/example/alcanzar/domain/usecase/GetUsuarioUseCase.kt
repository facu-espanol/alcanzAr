package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.repository.UsuarioRepository

class GetUsuarioUseCase(
    private val repository: UsuarioRepository
) {

    operator fun invoke(
        id: String,
        onResult: (Usuario?) -> Unit
    ) {
        repository.getUsuarioById(id, onResult)
    }
}