package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.repository.PeticionRepository

class GuardarPeticionUseCase(
    private val repository: PeticionRepository
) {

    operator fun invoke(
        peticion: Peticion
    ) {
        repository.guardarPeticion(peticion)
    }
}