package com.example.alcanzar.presentation.statedata

import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje

data class PerfilUiState(
    val loading: Boolean = false,
    val usuario: Usuario? = null,
    val viajesPendientes: List<Viaje> = emptyList(),
    val historialViajes: List<Viaje> = emptyList()
)
