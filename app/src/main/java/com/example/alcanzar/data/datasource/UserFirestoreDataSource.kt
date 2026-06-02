package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Usuario
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class UserFirestoreDataSource(
    private val db: FirebaseFirestore
) {

    fun crearUsuario(
        usuario: String, 
        password: String, 
        nombreCompleto: String,
        fotoUrl: String,
        onResult: (String?) -> Unit
    ) {
        val data = hashMapOf(
            "usuario" to usuario,
            "password" to password,
            "nombreCompleto" to nombreCompleto,
            "fotoUrl" to fotoUrl,
            "fechaRegistro" to System.currentTimeMillis()
        )

        db.collection("usuarios")
            .add(data)
            .addOnSuccessListener { onResult(it.id) }
            .addOnFailureListener { onResult(null) }
    }

    fun login(usuario: String, password: String, onResult: (String?) -> Unit) {
        db.collection("usuarios")
            .whereEqualTo("usuario", usuario)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    onResult(it.documents[0].id)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun obtenerUsuariosPorIds(ids: List<String>, onResult: (List<Usuario>) -> Unit) {
        if (ids.isEmpty()) {
            onResult(emptyList())
            return
        }

        db.collection("usuarios")
            .whereIn(FieldPath.documentId(), ids)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val usuarios = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Usuario::class.java)?.copy(id = doc.id)
                }
                onResult(usuarios)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}
