package com.example.alcanzar.domain.usecase

import com.example.alcanzar.domain.repository.PeticionRepository

class PostularseAPeticionUseCase(
    private val repository: PeticionRepository
) {
    fun execute(peticionId: String, userId: String, postularse: Boolean, onResult: (Boolean) -> Unit) {
        repository.postularseAPeticion(peticionId, userId, postularse, onResult)
    }
}
