package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Peticion

interface PeticionRepository {
    fun obtenerPeticiones(): List<Peticion>
}