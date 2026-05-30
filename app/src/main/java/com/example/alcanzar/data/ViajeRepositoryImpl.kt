package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.ViajesMemoryDataSource
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.repository.ViajeRepository

class ViajeRepositoryImpl : ViajeRepository {

    override fun obtenerViajes(): List<Viaje> {
        return ViajesMemoryDataSource.viajes
    }

    override fun guardarViaje(viaje: Viaje) {
        ViajesMemoryDataSource.viajes.add(viaje)
    }
}