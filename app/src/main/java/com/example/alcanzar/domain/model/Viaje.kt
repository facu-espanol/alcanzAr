package com.example.alcanzar.domain.model

data class Viaje(
    val id: String = "",
    val paisOrigen: String = "",
    val ciudadOrigen: String = "",
    val paisDestino: String = "",
    val ciudadDestino: String = "",
    val fecha: String = "",
    val horaSalida: String = "",
    val vehiculo: String = "",
    val plazas: Int = 0,
    val costoSugerido: Double = 0.0,
    val idPasajeros: List<String> = emptyList()
)