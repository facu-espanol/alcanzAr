package com.example.pruebaarquitecturalimpia.data

import com.example.pruebaarquitecturalimpia.domain.model.Tarea
import com.example.pruebaarquitecturalimpia.domain.repository.TareaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareaRepositoryInMemoryImpl: TareaRepository {

    // MutableStateFlow actúa como el contenedor reactivo privado de la lista.
    private val _tareasFlow = MutableStateFlow<List<Tarea>>(emptyList())
    private var contadorIds = 1

    override fun observarTareas(): Flow<List<Tarea>> {
        // Se expone una versión de solo lectura hacia el exterior
        return _tareasFlow.asStateFlow()
    }

    override suspend fun agregarTarea(titulo: String) {
        val nuevaTarea = Tarea(id = contadorIds++, titulo = titulo)
        // update modifica el valor actual de forma atómica y segura
        _tareasFlow.update { listaActual -> listaActual + nuevaTarea }
    }

    override suspend fun alternarEstado(id: Int) {
        _tareasFlow.update { listaActual ->
            listaActual.map { tarea ->
                if (tarea.id == id) tarea.copy(estaCompletada = !tarea.estaCompletada)
                else tarea
            }
        }
    }
}