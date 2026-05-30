package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Viaje

interface ViajeRepository {

    fun obtenerViajes(): List<Viaje>

    fun guardarViaje(
        viaje: Viaje
    )

}