package com.example.alcanzar.presentation.state

data class EstadoCalificacion(
    val puntaje: Int = 0,
    val comentario: String = "",
    val cargando: Boolean = false,
    val exito: Boolean = false,
    val error: String? = null
)