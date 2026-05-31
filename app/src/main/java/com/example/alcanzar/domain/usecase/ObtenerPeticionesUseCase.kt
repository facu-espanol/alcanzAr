package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.repository.PeticionRepository

class ObtenerPeticionesUseCase(
    private val repository: PeticionRepository
) {
    fun execute(onResult: (List<Peticion>) -> Unit) {
        repository.obtenerPeticiones(onResult)
    }
}
