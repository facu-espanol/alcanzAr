package com.example.alcanzar.domain.model

data class Peticion(

    val titulo: String,

    val descripcion: String,

    val pais: String,
    val ciudad: String,
    val barrio: String,

    val fecha: String,
    val hora: String
)