package com.example.alcanzar.domain.model

data class Usuario(
    val id: String = "",
    val usuario: String = "",
    val password: String = "",
    val nombreCompleto: String = "",

    val promedioConductor: Double = 0.0,
    val promedioPasajero: Double = 0.0,
    val totalConductor: Int = 0,
    val totalPasajero: Int = 0,
    val fotoUrl: String = "",
    val fechaRegistro: Long = 0
)

