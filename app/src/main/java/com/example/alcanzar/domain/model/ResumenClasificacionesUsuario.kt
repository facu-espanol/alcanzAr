package com.example.alcanzar.domain.model

data class ResumenCalificacionesUsuario(
    val promedioConductor: Double = 0.0,
    val promedioPasajero: Double = 0.0,
    val totalConductor: Int = 0,
    val totalPasajero: Int = 0
)