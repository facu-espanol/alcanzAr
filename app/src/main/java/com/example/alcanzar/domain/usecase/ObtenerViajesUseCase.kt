package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.repository.ViajeRepository

class ObtenerViajesUseCase(
    private val repository: ViajeRepository
) {
    fun execute(onResult: (List<Viaje>) -> Unit) {
        repository.obtenerViajes(onResult)
    }
}