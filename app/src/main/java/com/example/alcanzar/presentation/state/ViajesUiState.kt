package com.example.alcanzar.presentation.state

import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje

data class ViajesUiState(
    val viajes: List<Viaje> = emptyList(),
    val usuariosConductores: Map<String, Usuario> = emptyMap()
)