package com.example.pruebaarquitecturalimpia.domain.usecase

import com.example.pruebaarquitecturalimpia.domain.repository.TareaRepository

class AlternarEstadoTareaUseCase(val repository: TareaRepository) {

    suspend operator fun invoke(id: Int) {
        repository.alternarEstado(id)
    }
}