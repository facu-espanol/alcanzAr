package com.example.alcanzar.presentation.ui.crearviaje

import CrearViajeScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.domain.usecase.GuardarViajeUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.CrearViajeViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import android.util.Log
import android.widget.Toast
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity

class CrearViajeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = ViajeFirestoreDataSource(FirebaseFirestore.getInstance())
        val repository = ViajeRepositoryImpl(dataSource)
        val guardarViajeUseCase = GuardarViajeUseCase(repository)
        val vm = CrearViajeViewModel(guardarViajeUseCase)

        setContent {
            AlcanzARTheme {

                CrearViajeScreen(

                    onPerfilClick = {
                        startActivity(
                            Intent(this, PerfilActivity::class.java)
                        )
                    },

                    onInicioClick = {
                        startActivity(
                            Intent(this, BienvenidaActivity::class.java)
                        )
                    },

                    onPeticionesClick = {
                        startActivity(
                            Intent(this, PeticionesActivity::class.java)
                        )
                    },

                    onViajesClick = {
                        startActivity(
                            Intent(this, ViajesActivity::class.java)
                        )
                    },

                    onAcercaClick = {
                        startActivity(
                            Intent(this, AcercaActivity::class.java)
                        )
                    },

                    onPublicarClick = { viaje ->
                        val userId = SessionManager.obtenerUsuarioId(this) ?: ""
                        val viajeConCreador = viaje.copy(conductorId = userId)
                        
                        vm.guardarViaje(viajeConCreador) { success ->
                            if (success) {
                                Toast.makeText(
                                    this,
                                    "Viaje publicado",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(this, ViajesActivity::class.java)
                                )
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al publicar viaje",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onCrearPeticionClick = {
                        startActivity(
                            Intent(
                                this,
                                CrearPeticionActivity::class.java
                            )
                        )
                    },

                    )
            }
        }
    }
}
