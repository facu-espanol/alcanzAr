package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.NotificacionesMemoryDataSource
import com.example.alcanzar.domain.model.Notificacion
import com.example.alcanzar.domain.repository.NotificacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificacionRepositoryImpl : NotificacionRepository {
    override fun getNotificaciones(): Flow<List<Notificacion>> = flow {
        emit(NotificacionesMemoryDataSource.notificaciones)
    }

    override fun getNotificacionById(id: String): Flow<Notificacion?> = flow {
        emit(NotificacionesMemoryDataSource.notificaciones.find { it.id == id })
    }
}
