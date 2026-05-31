package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Viaje

interface ViajeRepository {
    fun obtenerViajes(onResult: (List<Viaje>) -> Unit)
    fun guardarViaje(viaje: Viaje, onResult: (Boolean) -> Unit)
    fun participarEnViaje(viajeId: String, userId: String, participar: Boolean, onResult: (Boolean) -> Unit)
}
