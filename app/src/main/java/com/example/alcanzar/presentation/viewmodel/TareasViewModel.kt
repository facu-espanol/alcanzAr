package com.example.pruebaarquitecturalimpia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebaarquitecturalimpia.domain.model.Tarea
import com.example.pruebaarquitecturalimpia.domain.usecase.AgregarTareaUseCase
import com.example.pruebaarquitecturalimpia.domain.usecase.AlternarEstadoTareaUseCase
import com.example.pruebaarquitecturalimpia.domain.usecase.ObtenerTareasUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareasViewModel (
    obtenerTareasUseCase: ObtenerTareasUseCase,
    private val agregarTareaUseCase: AgregarTareaUseCase,
    private val alternarEstadoTareaUseCase: AlternarEstadoTareaUseCase
): ViewModel(){

    val uiState: StateFlow<List<Tarea>> = obtenerTareasUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onAgregarTarea(titulo: String) {
        viewModelScope.launch {
            agregarTareaUseCase(titulo)
        }
    }

    fun onAlternarEstado(id: Int) {
        viewModelScope.launch {
            alternarEstadoTareaUseCase(id)
        }
    }
}