package com.example.alcanzar.data.datasource

import com.google.firebase.firestore.FirebaseFirestore

class UserFirestoreDataSource(
    private val db: FirebaseFirestore
) {

    fun crearUsuario(usuario: String, password: String, onResult: (String?) -> Unit) {
        val data = hashMapOf(
            "usuario" to usuario,
            "password" to password,
            "fecha" to System.currentTimeMillis()
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
}
