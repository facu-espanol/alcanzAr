package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.repository.ViajeRepository

class ObtenerViajesUseCase(
    private val repository: ViajeRepository
) {
    operator fun invoke(): List<Viaje> {
        return repository.obtenerViajes()
    }
}