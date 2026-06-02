package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Notificacion
import com.example.alcanzar.domain.repository.NotificacionRepository
import kotlinx.coroutines.flow.Flow

class ObtenerNotificacionDetalleUseCase(private val repository: NotificacionRepository) {
    operator fun invoke(id: String): Flow<Notificacion?> = repository.getNotificacionById(id)
}
