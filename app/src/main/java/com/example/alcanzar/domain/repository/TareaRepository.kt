package com.example.pruebaarquitecturalimpia.domain.repository

import com.example.pruebaarquitecturalimpia.domain.model.Tarea
import kotlinx.coroutines.flow.Flow

interface TareaRepository {
    fun observarTareas(): Flow<List<Tarea>>
    suspend fun agregarTarea(titulo: String)
    suspend fun alternarEstado(id: Int)
}