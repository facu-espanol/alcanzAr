package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.repository.ViajeRepository

class ViajeRepositoryImpl(
    private val dataSource: ViajeFirestoreDataSource
) : ViajeRepository {

    override fun obtenerViajes(onResult: (List<Viaje>) -> Unit) {
        dataSource.obtenerViajes(onResult)
    }

    override fun guardarViaje(viaje: Viaje, onResult: (Boolean) -> Unit) {
        dataSource.guardarViaje(viaje, onResult)
    }

    override fun participarEnViaje(viajeId: String, userId: String, participar: Boolean, onResult: (Boolean) -> Unit) {
        dataSource.participarEnViaje(viajeId, userId, participar, onResult)
    }
}
