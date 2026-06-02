package com.example.alcanzar.data.repository

import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.repository.UsuarioRepository
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioRepositoryImpl(
    private val firestore: FirebaseFirestore
) : UsuarioRepository {

    override fun getUsuarioById(
        id: String,
        onResult: (Usuario?) -> Unit
    ) {
        firestore.collection("usuarios")
            .document(id)
            .get()
            .addOnSuccessListener { snapshot ->
                val usuario = snapshot.toObject(Usuario::class.java)
                onResult(usuario)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}