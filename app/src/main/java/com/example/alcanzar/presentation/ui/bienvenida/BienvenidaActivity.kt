package com.example.alcanzar.presentation.ui.bienvenida

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity
import com.example.alcanzar.presentation.ui.crearviaje.CrearViajeActivity
import com.example.alcanzar.presentation.ui.login.LoginActivity
import com.example.alcanzar.presentation.ui.notificaciones.NotificacionesActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.perfil.SesionActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.ui.theme.AlcanzARTheme

class BienvenidaActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcanzARTheme {
                BienvenidaScreen(
                    onPerfilClick = {
                        // Al hacer clic en el ícono de perfil, vamos a MI SESIÓN
                        startActivity(
                            Intent(this, SesionActivity::class.java)
                        )
                    },

                    onAcercaClick = {
                        startActivity(
                            Intent(this, AcercaActivity::class.java)
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
                    onCrearViajeClick = {
                        startActivity(
                            Intent(this, CrearViajeActivity::class.java)
                        )
                    },
                    onCrearPeticionClick = {
                        startActivity(
                            Intent(
                                this,
                                CrearPeticionActivity::class.java
                            )
                        )
                    },
                    onNotificacionesClick = {
                        startActivity(
                            Intent(
                                this,
                                NotificacionesActivity::class.java
                            )
                        )
                    },
                    onCerrarSesionClick = {
                        SessionManager.cerrarSesion(this)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}