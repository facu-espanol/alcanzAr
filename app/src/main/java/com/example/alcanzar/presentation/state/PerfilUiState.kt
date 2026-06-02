package com.example.alcanzar.presentation.statedata

import com.example.alcanzar.domain.model.Usuario

data class PerfilUiState(
    val loading: Boolean = false,
    val usuario: Usuario? = null
)
