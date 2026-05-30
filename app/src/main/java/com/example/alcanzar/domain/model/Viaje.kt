package com.example.alcanzar.domain.model

data class Viaje(
    val paisOrigen: String,
    val ciudadOrigen: String,
    val paisDestino: String,
    val ciudadDestino: String,
    val fecha: String,
    val horaSalida: String,
    val vehiculo: String,
    val plazas: Int,
    val costoSugerido: Double
)