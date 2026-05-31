package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Viaje
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ViajeFirestoreDataSource(
    private val db: FirebaseFirestore
) {
    fun guardarViaje(viaje: Viaje, onResult: (Boolean) -> Unit) {
        db.collection("viajes")
            .add(viaje)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun obtenerViajes(onResult: (List<Viaje>) -> Unit) {
        db.collection("viajes")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val viajes = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Viaje::class.java)?.copy(id = doc.id)
                }
                onResult(viajes)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    fun participarEnViaje(viajeId: String, userId: String, participar: Boolean, onResult: (Boolean) -> Unit) {
        val op = if (participar) FieldValue.arrayUnion(userId) else FieldValue.arrayRemove(userId)
        db.collection("viajes").document(viajeId)
            .update("idPasajeros", op)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
