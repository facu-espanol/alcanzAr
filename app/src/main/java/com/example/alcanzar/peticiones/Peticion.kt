// Peticion.kt
package com.example.alcanzar.peticiones
data class Peticion(
    val tipo: String,
    val desde: String,
    val destino: String,
    val horario: String,
    val extras: String
)