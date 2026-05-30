package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Viaje

object ViajesMemoryDataSource {

    val viajes = mutableListOf(

        Viaje(
            paisOrigen = "Argentina",
            ciudadOrigen = "Puerto Madryn",
            paisDestino = "Argentina",
            ciudadDestino = "UNPSJB",
            fecha = "10/06/2026",
            horaSalida = "08:30",
            vehiculo = "auto",
            plazas = 3,
            costoSugerido = 2500.0
        ),

        Viaje(
            paisOrigen = "Argentina",
            ciudadOrigen = "Puerto Madryn",
            paisDestino = "Argentina",
            ciudadDestino = "Centro",
            fecha = "10/06/2026",
            horaSalida = "09:15",
            vehiculo = "moto",
            plazas = 1,
            costoSugerido = 1800.0
        )
    )
}