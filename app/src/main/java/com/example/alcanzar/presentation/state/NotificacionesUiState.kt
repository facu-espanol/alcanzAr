package com.example.alcanzar.presentation.state

import com.example.alcanzar.domain.model.Notificacion

data class NotificacionesUiState(
    val notificaciones: List<Notificacion> = emptyList(),
    val notificacionSeleccionada: Notificacion? = null,
    val isLoading: Boolean = false
)
