package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcanzar.domain.usecase.EliminarNotificacionUseCase
import com.example.alcanzar.domain.usecase.ObtenerNotificacionDetalleUseCase
import com.example.alcanzar.domain.usecase.ObtenerNotificacionesUseCase
import com.example.alcanzar.presentation.state.NotificacionesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificacionesViewModel(
    private val obtenerNotificacionesUseCase: ObtenerNotificacionesUseCase,
    private val obtenerNotificacionDetalleUseCase: ObtenerNotificacionDetalleUseCase,
    private val eliminarNotificacionUseCase: EliminarNotificacionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificacionesUiState())
    val uiState = _uiState.asStateFlow()

    fun cargarNotificaciones() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            obtenerNotificacionesUseCase().collect { lista ->
                _uiState.value = _uiState.value.copy(notificaciones = lista, isLoading = false)
            }
        }
    }

    fun obtenerDetalle(id: String) {
        viewModelScope.launch {
            obtenerNotificacionDetalleUseCase(id).collect { notificacion ->
                _uiState.value = _uiState.value.copy(notificacionSeleccionada = notificacion)
            }
        }
    }

    fun eliminarNotificacion(id: String) {
        eliminarNotificacionUseCase(id)
    }
}
