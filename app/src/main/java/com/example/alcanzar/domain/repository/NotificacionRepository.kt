package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Notificacion
import kotlinx.coroutines.flow.Flow

interface NotificacionRepository {
    fun getNotificaciones(): Flow<List<Notificacion>>
    fun getNotificacionById(id: String): Flow<Notificacion?>
}
