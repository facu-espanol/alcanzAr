package com.example.alcanzar.presentation.ui.notificaciones

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.NotificacionRepositoryImpl
import com.example.alcanzar.domain.usecase.EliminarNotificacionUseCase
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
        val eliminarNotificacionUseCase = EliminarNotificacionUseCase(repository)

        viewModel = NotificacionesViewModel(
            obtenerNotificacionesUseCase,
            obtenerNotificacionDetalleUseCase,
            eliminarNotificacionUseCase
        )

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
                        val context = this@DetalleNotificacionActivity
                        val refId = uiState.notificacionSeleccionada?.idReferencia

                        when (tipo) {
                            "VIAJE" -> {
                                // Aquí podrías usar refId para ir al detalle del viaje directamente
                                context.startActivity(Intent(context, ViajesActivity::class.java))
                            }
                            "PETICION" -> {
                                context.startActivity(Intent(context, PeticionesActivity::class.java))
                            }
                            "CALIFICACION" -> {
                                val intent = Intent(context, CalificacionActivity::class.java).apply {
                                    putExtra("VIAJE_ID", refId)
                                }
                                context.startActivity(intent)                            }
                        }
                    }
                )
            }
        }
    }
}