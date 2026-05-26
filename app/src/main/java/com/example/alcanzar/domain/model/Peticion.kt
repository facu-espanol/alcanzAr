package com.example.alcanzar.domain.model

data class Peticion(
    val tipo: String,
    val desde: String,
    val destino: String,
    val horario: String,
    val extras: String
)