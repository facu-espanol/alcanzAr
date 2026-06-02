package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.example.alcanzar.presentation.state.ViajesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViajesViewModel(
    private val obtenerViajesUseCase: ObtenerViajesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ViajesUiState())
    val uiState = _uiState.asStateFlow()

    fun cargarViajes() {
        // En ViajesViewModel.kt -> cargarViajes()
        obtenerViajesUseCase.execute { lista ->
            // Ya no hardcodeamos nombres. Si viene de la DB se usa,
            // y si no, ponemos un aviso de "Nombre no disponible"
            val listaLimpia = lista.map { viaje ->
                if (viaje.conductorNombre.isBlank()) {
                    viaje.copy(conductorNombre = "Usuario AlcanzAR")
                } else {
                    viaje
                }
            }
            _uiState.value = ViajesUiState(viajes = listaLimpia)
        }
    }
}