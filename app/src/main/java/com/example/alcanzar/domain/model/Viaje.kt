package com.example.alcanzar.domain.model

data class Viaje(
    val destino: String,
    val horaSalida: String,
    val vehiculo: String, // "auto" o "moto"
    val plazas: Int
)