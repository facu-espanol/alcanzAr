package com.example.alcanzar.presentation.ui.calificacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.CalificacionRepositoryImpl
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.usecase.CrearCalificacionUseCase
import com.example.alcanzar.presentation.rating.CalificacionViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class CalificacionActivity : ComponentActivity() {

    companion object {
        var viajeParaCalificar: Viaje? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val repository = CalificacionRepositoryImpl(db)
        val useCase = CrearCalificacionUseCase(repository)
        val viewModel = CalificacionViewModel(useCase)
        val viajeId = intent.getStringExtra("VIAJE_ID")
        val miId = SessionManager.obtenerUsuarioId(this) ?: ""

        if (viajeId != null) {
            val viajeRepo = ViajeRepositoryImpl(ViajeFirestoreDataSource(db))
            viajeRepo.obtenerViajePorId(viajeId) { viaje ->
                val viajeFinal = viaje ?: createStagingViaje(miId)
                renderScreen(viajeFinal, miId, viewModel)
            }
        } else if (viajeParaCalificar != null) {
            renderScreen(viajeParaCalificar!!, miId, viewModel)
        } else {
            renderScreen(createStagingViaje(miId), miId, viewModel)
        }
    }

    private fun createStagingViaje(miId: String) = Viaje(
        id = "staging_id",
        conductorId = "admin_user",
        conductorNombre = "Facundo (Admin)",
        ciudadOrigen = "Staging",
        ciudadDestino = "Prueba de Estética",
        idPasajeros = listOf(miId)
    )

    private fun renderScreen(viaje: Viaje, miId: String, viewModel: CalificacionViewModel) {
        setContent {
            AlcanzARTheme {
                CalificacionScreen(
                    viewModel = viewModel,
                    viaje = viaje,
                    miId = miId,
                    onFinish = {
                        // Navegación inmediata al finalizar
                        finish()
                    }
                )
            }
        }
    }
}
