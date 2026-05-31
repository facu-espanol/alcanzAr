package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Peticion

interface PeticionRepository {
    fun obtenerPeticiones(onResult: (List<Peticion>) -> Unit)
    fun guardarPeticion(peticion: Peticion, onResult: (Boolean) -> Unit)
    fun postularseAPeticion(peticionId: String, userId: String, postularse: Boolean, onResult: (Boolean) -> Unit)
}
