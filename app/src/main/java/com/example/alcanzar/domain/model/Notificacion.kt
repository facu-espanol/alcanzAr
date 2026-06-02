package com.example.alcanzar.domain.model

data class Notificacion(
    val id: String,
    val idNotificado: String, // ID del usuario que recibe la notificación
    val idReferencia: String, // ID del objeto relacionado (Viaje, Peticion, etc)
    val titulo: String,
    val mensaje: String,
    val fecha: String,
    val leida: Boolean = false,
    val tipo: String // "VIAJE", "PETICION", "CALIFICACION"
)
