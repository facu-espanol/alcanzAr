package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Notificacion
import com.example.alcanzar.domain.repository.NotificacionRepository
import kotlinx.coroutines.flow.Flow

class ObtenerNotificacionesUseCase(private val repository: NotificacionRepository) {
    operator fun invoke(): Flow<List<Notificacion>> = repository.getNotificaciones()
}
