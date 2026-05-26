package com.example.alcanzar.data

import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.repository.ViajeRepository

class ViajeRepositoryImpl : ViajeRepository {

    override fun obtenerViajes(): List<Viaje> {
        return listOf(
            Viaje(
                destino = "UNPSJB",
                horaSalida = "08:30",
                vehiculo = "auto",
                plazas = 3
            ),
            Viaje(
                destino = "Centro",
                horaSalida = "09:15",
                vehiculo = "moto",
                plazas = 1
            ),
            Viaje(
                destino = "Terminal",
                horaSalida = "14:00",
                vehiculo = "auto",
                plazas = 2
            )
        )
    }
}