package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.PeticionFirestoreDataSource
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.repository.PeticionRepository

class PeticionRepositoryImpl(
    private val dataSource: PeticionFirestoreDataSource
) : PeticionRepository {

    override fun obtenerPeticiones(onResult: (List<Peticion>) -> Unit) {
        dataSource.obtenerPeticiones(onResult)
    }

    override fun guardarPeticion(peticion: Peticion, onResult: (Boolean) -> Unit) {
        dataSource.guardarPeticion(peticion, onResult)
    }

    override fun postularseAPeticion(peticionId: String, userId: String, postularse: Boolean, onResult: (Boolean) -> Unit) {
        dataSource.postularseAPeticion(peticionId, userId, postularse, onResult)
    }
}
