package com.example.alcanzar.domain.model
data class Calificacion(
    val id: String = "",
    val desdeUsuarioId: String = "",
    val haciaUsuarioId: String = "",
    val viajeId: String = "",
    val rol: String = "", // "CONDUCTOR" o "PASAJERO"
    val puntaje: Int = 0, // 1 a 5
    val comentario: String = "",
    val creadoEn: Long = System.currentTimeMillis()
)