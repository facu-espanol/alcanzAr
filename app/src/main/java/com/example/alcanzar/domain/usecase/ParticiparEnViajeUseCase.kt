package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.repository.ViajeRepository

class ParticiparEnViajeUseCase(
    private val repository: ViajeRepository
) {
    fun execute(viajeId: String, userId: String, participar: Boolean, onResult: (Boolean) -> Unit) {
        repository.participarEnViaje(viajeId, userId, participar, onResult)
    }
}
