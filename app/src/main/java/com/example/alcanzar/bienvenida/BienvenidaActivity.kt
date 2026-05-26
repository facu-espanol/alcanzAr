package com.example.alcanzar.bienvenida

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.acerca.AcercaActivity
import com.example.alcanzar.perfil.Perfil
import com.example.alcanzar.peticiones.PeticionesActivity
import com.example.alcanzar.ui.theme.AlcanzARTheme

class BienvenidaActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcanzARTheme {
                BienvenidaScreen(
                    onPerfilClick = {
                        startActivity(Intent(this, Perfil::class.java))
                    },
                    onAcercaClick = {
                        startActivity(Intent(this, AcercaActivity::class.java))
                    },
                    onPeticionesClick = {
                        startActivity(Intent(this, PeticionesActivity::class.java))
                    }
                )
            }
        }
    }
}