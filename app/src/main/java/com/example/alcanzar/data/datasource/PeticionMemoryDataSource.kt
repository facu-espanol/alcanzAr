package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Peticion

object PeticionMemoryDataSource {

    val peticiones = mutableListOf(

        Peticion(
            titulo = "Cortadora de pasto",
            descripcion = "Necesito una cortadora para este fin de semana",
            pais = "Argentina",
            ciudad = "Puerto Madryn",
            barrio = "Pujol 2",
            fecha = "15/06/2026",
            hora = "09:00"
        ),

        Peticion(
            titulo = "Escalera",
            descripcion = "Necesito una escalera para pintar una pared",
            pais = "Argentina",
            ciudad = "Puerto Madryn",
            barrio = "El Doradillo",
            fecha = "20/06/2026",
            hora = "14:00"
        )
    )
}