package com.example.alcanzar.domain.repository

import com.example.alcanzar.domain.model.Usuario

interface UsuarioRepository {

    fun getUsuarioById(
        id: String,
        onResult: (Usuario?) -> Unit
    )
}