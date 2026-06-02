package com.example.alcanzar.domain.model

data class Notificacion(
    val id: String,
    val titulo: String,
    val mensaje: String,
    val fecha: String,
    val leida: Boolean = false,
    val tipo: String // "VIAJE", "PETICION", "CALIFICACION"
)
