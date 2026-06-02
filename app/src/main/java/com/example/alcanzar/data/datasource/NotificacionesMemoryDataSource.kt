package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Notificacion

object NotificacionesMemoryDataSource {
    val notificaciones = mutableListOf(
        Notificacion(
            id = "1",
            titulo = "Oferta de viaje",
            mensaje = "Alguien se ofrece a llevarte",
            fecha = "10/05/2024",
            tipo = "VIAJE"
        ),
        Notificacion(
            id = "2",
            titulo = "Petición encontrada",
            mensaje = "Alguien tiene eso que necesitas",
            fecha = "11/05/2024",
            tipo = "PETICION"
        ),
        Notificacion(
            id = "3",
            titulo = "Nuevo compañero",
            mensaje = "Alguien quiere viajar con vos",
            fecha = "12/05/2024",
            tipo = "VIAJE"
        ),
        Notificacion(
            id = "4",
            titulo = "Calificación pendiente",
            mensaje = "¿Qué te pareció el conductor del viaje tal?",
            fecha = "13/05/2024",
            tipo = "CALIFICACION"
        )
    )
}
