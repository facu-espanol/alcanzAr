package com.example.alcanzar.data

import com.example.alcanzar.domain.model.Calificacion
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.repository.CalificacionRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CalificacionRepositoryImpl(
    private val firestore: FirebaseFirestore
) : CalificacionRepository {

    override suspend fun crearCalificacion(calificacion: Calificacion) {

        val id = firestore.collection("calificaciones").document().id
        val final = calificacion.copy(id = id)

        firestore.collection("calificaciones")
            .document(id)
            .set(final)
            .await()

        actualizarUsuario(final)
    }

    private suspend fun actualizarUsuario(calificacion: Calificacion) {

        val ref = firestore.collection("usuarios")
            .document(calificacion.haciaUsuarioId)

        firestore.runTransaction { tx ->

            val snap = tx.get(ref)
            val user = snap.toObject(Usuario::class.java)!!

            if (calificacion.rol == "CONDUCTOR") {

                val total = user.totalConductor + 1
                val promedio =
                    (user.promedioConductor * user.totalConductor + calificacion.puntaje) / total

                tx.update(ref, mapOf(
                    "promedioConductor" to promedio,
                    "totalConductor" to total
                ))
            }

            if (calificacion.rol == "PASAJERO") {

                val total = user.totalPasajero + 1
                val promedio =
                    (user.promedioPasajero * user.totalPasajero + calificacion.puntaje) / total

                tx.update(ref, mapOf(
                    "promedioPasajero" to promedio,
                    "totalPasajero" to total
                ))
            }
        }.await()
    }
}