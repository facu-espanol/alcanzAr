package com.example.alcanzar.presentation.ui.peticiones

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.PeticionRepositoryImpl
import com.example.alcanzar.domain.usecase.ObtenerPeticionesUseCase
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.viewmodel.PeticionesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class PeticionesActivity : ComponentActivity() {

    private lateinit var viewModel: PeticionesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = PeticionRepositoryImpl(this)
        val useCase = ObtenerPeticionesUseCase(repository)
        viewModel = PeticionesViewModel(useCase)

        setContent {
            AlcanzARTheme {
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    viewModel.cargarPeticiones()
                }

                PeticionesScreen(
                    peticiones = uiState.peticiones,
                    onPerfilClick = {
                        startActivity(Intent(this, PerfilActivity::class.java))
                    },
                    onInicioClick = {
                        startActivity(Intent(this, BienvenidaActivity::class.java))
                        finish()
                    },
                    onAcercaClick = {
                        startActivity(Intent(this, AcercaActivity::class.java))
                    }
                )
            }
        }
    }
}