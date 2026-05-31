package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.ObtenerPeticionesUseCase
import com.example.alcanzar.presentation.state.PeticionesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PeticionesViewModel(
    private val obtenerPeticionesUseCase: ObtenerPeticionesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PeticionesUiState())
    val uiState = _uiState.asStateFlow()

    fun cargarPeticiones() {
        obtenerPeticionesUseCase.execute { lista ->
            _uiState.value = PeticionesUiState(peticiones = lista)
        }
    }
}
