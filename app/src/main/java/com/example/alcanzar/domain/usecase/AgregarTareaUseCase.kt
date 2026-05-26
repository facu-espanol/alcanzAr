package com.example.pruebaarquitecturalimpia.domain.usecase

import com.example.pruebaarquitecturalimpia.domain.repository.TareaRepository

class AgregarTareaUseCase(private val repository: TareaRepository) {

    // agregarTareaUseCase("Aprender Clean Arch en Android")
    suspend operator fun invoke(titulo: String) {
        val tituloSanitizado = titulo.trim()
        if (tituloSanitizado.isNotBlank()) {
            repository.agregarTarea(tituloSanitizado)
        }
    }
}