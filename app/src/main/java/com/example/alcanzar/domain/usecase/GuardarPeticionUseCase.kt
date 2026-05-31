package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.repository.PeticionRepository

class GuardarPeticionUseCase(
    private val repository: PeticionRepository
) {
    fun execute(peticion: Peticion, onResult: (Boolean) -> Unit) {
        repository.guardarPeticion(peticion, onResult)
    }
}
