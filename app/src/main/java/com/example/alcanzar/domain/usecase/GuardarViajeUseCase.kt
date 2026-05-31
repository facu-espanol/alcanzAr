package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.repository.ViajeRepository

class GuardarViajeUseCase(
    private val repository: ViajeRepository
) {

    fun execute(viaje: Viaje, onResult: (Boolean) -> Unit) {
        repository.guardarViaje(viaje, onResult)
    }
}