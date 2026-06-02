package com.example.alcanzar.presentation.ui.crearviaje

import CrearViajeScreen
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.repository.UsuarioRepositoryImpl
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.GetUsuarioUseCase
import com.example.alcanzar.domain.usecase.GuardarViajeUseCase
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.CrearViajeViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class CrearViajeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        // Dependencias de Viaje
        val viajeDataSource = ViajeFirestoreDataSource(db)
        val viajeRepository = ViajeRepositoryImpl(viajeDataSource)
        val guardarViajeUseCase = GuardarViajeUseCase(viajeRepository)
        val vm = CrearViajeViewModel(guardarViajeUseCase)

        // Dependencias de Usuario para recuperar el nombre real
        val usuarioRepository = UsuarioRepositoryImpl(db)
        val getUsuarioUseCase = GetUsuarioUseCase(usuarioRepository)

        setContent {
            AlcanzARTheme {
                CrearViajeScreen(
                    onPerfilClick = {
                        startActivity(Intent(this, PerfilActivity::class.java))
                    },
                    onInicioClick = {
                        startActivity(Intent(this, BienvenidaActivity::class.java))
                    },
                    onPeticionesClick = {
                        startActivity(Intent(this, PeticionesActivity::class.java))
                    },
                    onViajesClick = {
                        startActivity(Intent(this, ViajesActivity::class.java))
                    },
                    onAcercaClick = {
                        startActivity(Intent(this, AcercaActivity::class.java))
                    },
                    onPublicarClick = { viaje ->
                        val userId = SessionManager.obtenerUsuarioId(this) ?: ""

                        // 1. Buscamos el perfil del usuario actual para obtener su nombre real
                        getUsuarioUseCase(userId) { usuario ->
                            val nombreConductor = usuario?.nombreCompleto ?: "Usuario AlcanzAR"

                            // 2. Seteamos ID y NOMBRE recuperado en el viaje
                            val viajeCompleto = viaje.copy(
                                conductorId = userId,
                                conductorNombre = nombreConductor
                            )

                            // 3. Guardamos el viaje con los datos reales
                            vm.guardarViaje(viajeCompleto) { success ->
                                if (success) {
                                    Toast.makeText(this, "Viaje publicado correctamente", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, ViajesActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error al publicar el viaje", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    onCrearPeticionClick = {
                        startActivity(Intent(this, CrearPeticionActivity::class.java))
                    }
                )
            }
        }
    }
}