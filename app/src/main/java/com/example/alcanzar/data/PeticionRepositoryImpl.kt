package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.PeticionMemoryDataSource
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.repository.PeticionRepository

class PeticionRepositoryImpl : PeticionRepository {

    override fun obtenerPeticiones(): List<Peticion> {
        return PeticionMemoryDataSource.peticiones
    }

    override fun guardarPeticion(
        peticion: Peticion
    ) {
        PeticionMemoryDataSource
            .peticiones
            .add(peticion)
    }
}