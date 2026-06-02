package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.repository.NotificacionRepository

class EliminarNotificacionUseCase(private val repository: NotificacionRepository) {
    operator fun invoke(id: String) = repository.eliminarNotificacion(id)
}
