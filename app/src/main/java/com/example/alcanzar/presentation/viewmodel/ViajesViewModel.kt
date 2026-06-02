package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.ObtenerUsuariosPorIdsUseCase
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.example.alcanzar.presentation.state.ViajesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViajesViewModel(
    private val obtenerViajesUseCase: ObtenerViajesUseCase,
    private val obtenerUsuariosPorIdsUseCase: ObtenerUsuariosPorIdsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ViajesUiState())
    val uiState = _uiState.asStateFlow()

    fun cargarViajes(currentUserId: String?) {
        obtenerViajesUseCase.execute { listaViajes ->
            val listaFiltrada = if (currentUserId != null) {
                listaViajes.filter { it.conductorId != currentUserId }
            } else {
                listaViajes
            }

            val conductorIds = listaFiltrada.map { it.conductorId }.distinct()
            
            if (conductorIds.isEmpty()) {
                _uiState.value = ViajesUiState(viajes = listaFiltrada)
                return@execute
            }

            obtenerUsuariosPorIdsUseCase(conductorIds) { usuarios ->
                val mapaUsuarios = usuarios.associateBy { it.id }
                _uiState.value = ViajesUiState(
                    viajes = listaFiltrada,
                    usuariosConductores = mapaUsuarios
                )
            }
        }
    }
}
