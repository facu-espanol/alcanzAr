package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.model.Calificacion
import com.example.alcanzar.domain.repository.CalificacionRepository

class CrearCalificacionUseCase(
    private val repository: CalificacionRepository
) {
    suspend operator fun invoke(calificacion: Calificacion) {
        require(calificacion.puntaje in 1..5)
        repository.crearCalificacion(calificacion)
    }
}