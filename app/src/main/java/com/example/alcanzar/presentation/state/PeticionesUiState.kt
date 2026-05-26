package com.example.alcanzar.presentation.state

import com.example.alcanzar.domain.model.Peticion

data class PeticionesUiState(
    val peticiones: List<Peticion> = emptyList()
)