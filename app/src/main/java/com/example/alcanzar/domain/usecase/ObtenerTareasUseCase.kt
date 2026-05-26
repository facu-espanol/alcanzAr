package com.example.pruebaarquitecturalimpia.domain.usecase

import com.example.pruebaarquitecturalimpia.domain.model.Tarea
import com.example.pruebaarquitecturalimpia.domain.repository.TareaRepository
import kotlinx.coroutines.flow.Flow

class ObtenerTareasUseCase(private val repository: TareaRepository) {

    /* El invoke nos permite ejecutar directamente "obtenerTareasUseCase()"*/
    operator fun invoke(): Flow<List<Tarea>> {
        return repository.observarTareas()
    }
}