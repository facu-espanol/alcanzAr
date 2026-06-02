package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Calificacion

interface CalificacionRepository {
    suspend fun crearCalificacion(calificacion: Calificacion)

}