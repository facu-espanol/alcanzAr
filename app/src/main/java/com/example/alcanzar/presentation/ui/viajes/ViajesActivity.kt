package com.example.alcanzar.presentation.ui.viajes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.viewmodel.ViajesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class ViajesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViajesViewModel(
            ObtenerViajesUseCase(
                ViajeRepositoryImpl()
            )
        )

        setContent {
            AlcanzARTheme {

                val uiState by vm.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    vm.cargarViajes()
                }

                ViajesScreen(
                    viajes = uiState.viajes,

                    onPerfilClick = {
                        startActivity(
                            Intent(this, PerfilActivity::class.java)
                        )
                    },

                    onInicioClick = {
                        startActivity(
                            Intent(this, BienvenidaActivity::class.java)
                        )
                        finish()
                    },

                    onAcercaClick = {
                        startActivity(
                            Intent(this, AcercaActivity::class.java)
                        )
                    }
                )
            }
        }
    }
}