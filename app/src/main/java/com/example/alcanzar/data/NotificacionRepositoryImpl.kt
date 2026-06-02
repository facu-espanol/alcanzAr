package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.NotificacionesMemoryDataSource
import com.example.alcanzar.domain.model.Notificacion
import com.example.alcanzar.domain.repository.NotificacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificacionRepositoryImpl : NotificacionRepository {
    override fun getNotificaciones(): Flow<List<Notificacion>> = 
        NotificacionesMemoryDataSource.notificaciones

    override fun getNotificacionById(id: String): Flow<Notificacion?> = 
        NotificacionesMemoryDataSource.notificaciones.map { list ->
            list.find { it.id == id }
        }

    override fun eliminarNotificacion(id: String) {
        NotificacionesMemoryDataSource.eliminarNotificacion(id)
    }
}
