package com.example.alcanzar.presentation.ui.notificaciones

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.NotificacionRepositoryImpl
import com.example.alcanzar.domain.usecase.ObtenerNotificacionDetalleUseCase
import com.example.alcanzar.domain.usecase.ObtenerNotificacionesUseCase
import com.example.alcanzar.presentation.ui.calificacion.CalificacionActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.NotificacionesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class DetalleNotificacionActivity : ComponentActivity() {

    private lateinit var viewModel: NotificacionesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = NotificacionRepositoryImpl()
        val obtenerNotificacionesUseCase = ObtenerNotificacionesUseCase(repository)
        val obtenerNotificacionDetalleUseCase = ObtenerNotificacionDetalleUseCase(repository)
        viewModel = NotificacionesViewModel(obtenerNotificacionesUseCase, obtenerNotificacionDetalleUseCase)

        val notificacionId = intent.getStringExtra("NOTIFICACION_ID") ?: ""

        setContent {
            AlcanzARTheme {
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(notificacionId) {
                    viewModel.obtenerDetalle(notificacionId)
                }

                DetalleNotificacionScreen(
                    notificacion = uiState.notificacionSeleccionada,
                    onBackClick = { finish() },
                    onAccionClick = { tipo ->
                        when (tipo) {
                            "VIAJE" -> startActivity(Intent(this, ViajesActivity::class.java))
                            "PETICION" -> startActivity(Intent(this, PeticionesActivity::class.java))
                            "CALIFICACION" -> startActivity(Intent(this, CalificacionActivity::class.java))
                        }
                    }
                )
            }
        }
    }
}
