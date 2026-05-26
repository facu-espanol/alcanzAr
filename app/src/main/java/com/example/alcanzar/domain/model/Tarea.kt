package com.example.pruebaarquitecturalimpia.domain.model

data class Tarea(
    val id: Int,
    val titulo: String,
    val estaCompletada: Boolean = false
)