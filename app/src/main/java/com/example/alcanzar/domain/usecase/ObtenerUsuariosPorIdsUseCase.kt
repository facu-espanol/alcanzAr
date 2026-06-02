package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.repository.LoginRepository

class ObtenerUsuariosPorIdsUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(ids: List<String>, onResult: (List<Usuario>) -> Unit) {
        repository.obtenerUsuariosPorIds(ids, onResult)
    }
}
