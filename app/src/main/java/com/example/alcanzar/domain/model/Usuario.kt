package com.example.alcanzar.domain.model

data class Usuario(
    val id: String = "",
    val usuario: String = "",
    val nombreCompleto: String = "",
    val fotoUrl: String = "",
    val fechaRegistro: Long = 0
)
