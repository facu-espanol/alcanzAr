package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.example.alcanzar.presentation.state.ViajesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViajesViewModel(
    private val obtenerViajesUseCase: ObtenerViajesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(ViajesUiState())

    val uiState =
        _uiState.asStateFlow()

    fun cargarViajes() {
        obtenerViajesUseCase.execute { lista ->
            _uiState.value = ViajesUiState(viajes = lista)
        }
    }
}