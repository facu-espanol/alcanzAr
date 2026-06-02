package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Notificacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object NotificacionesMemoryDataSource {
    private val _notificaciones = MutableStateFlow(
        mutableListOf(
            Notificacion(
                id = "1",
                idNotificado = "user123",
                idReferencia = "trip001",
                titulo = "Oferta de viaje",
                mensaje = "Alguien se ofrece a llevarte",
                fecha = "10/05/2024",
                tipo = "VIAJE"
            ),
            Notificacion(
                id = "2",
                idNotificado = "user123",
                idReferencia = "req002",
                titulo = "Petición encontrada",
                mensaje = "Alguien tiene eso que necesitas",
                fecha = "11/05/2024",
                tipo = "PETICION"
            ),
            Notificacion(
                id = "3",
                idNotificado = "user123",
                idReferencia = "trip003",
                titulo = "Nuevo compañero",
                mensaje = "Alguien quiere viajar con vos",
                fecha = "12/05/2024",
                tipo = "VIAJE"
            ),
            Notificacion(
                id = "4",
                idNotificado = "user123",
                idReferencia = "trip004",
                titulo = "Calificación pendiente",
                mensaje = "¿Qué te pareció el conductor del viaje tal?",
                fecha = "13/05/2024",
                tipo = "CALIFICACION"
            )
        )
    )
    val notificaciones = _notificaciones.asStateFlow()

    fun eliminarNotificacion(id: String) {
        val currentList = _notificaciones.value.toMutableList()
        currentList.removeAll { it.id == id }
        _notificaciones.value = currentList
    }
}
