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
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity
import com.example.alcanzar.presentation.ui.crearviaje.CrearViajeActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.NotificacionesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class NotificacionesActivity : ComponentActivity() {

    private lateinit var viewModel: NotificacionesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización manual para este ejemplo de Clean Architecture sin DI
        val repository = NotificacionRepositoryImpl()
        val obtenerNotificacionesUseCase = ObtenerNotificacionesUseCase(repository)
        val obtenerNotificacionDetalleUseCase = ObtenerNotificacionDetalleUseCase(repository)
        viewModel = NotificacionesViewModel(obtenerNotificacionesUseCase, obtenerNotificacionDetalleUseCase)

        setContent {
            AlcanzARTheme {
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    viewModel.cargarNotificaciones()
                }

                NotificacionesScreen(
                    notificaciones = uiState.notificaciones,
                    onNotificacionClick = { notificacion ->
                        val intent = Intent(this, DetalleNotificacionActivity::class.java).apply {
                            putExtra("NOTIFICACION_ID", notificacion.id)
                        }
                        startActivity(intent)
                    },
                    onPerfilClick = { startActivity(Intent(this, PerfilActivity::class.java)) },
                    onInicioClick = {
                        startActivity(Intent(this, BienvenidaActivity::class.java))
                        finish()
                    },
                    onPeticionesClick = { startActivity(Intent(this, PeticionesActivity::class.java)) },
                    onViajesClick = { startActivity(Intent(this, ViajesActivity::class.java)) },
                    onAcercaClick = { startActivity(Intent(this, AcercaActivity::class.java)) },
                    onCrearViajeClick = { startActivity(Intent(this, CrearViajeActivity::class.java)) },
                    onCrearPeticionClick = { startActivity(Intent(this, CrearPeticionActivity::class.java)) }
                )
            }
        }
    }
}
