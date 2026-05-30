package com.example.alcanzar.presentation.ui.peticiones

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Peticion

@Composable
fun PeticionCard(
    peticion: Peticion
) {

    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Row(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text = peticion.titulo,
                    fontSize = 20.sp
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Text(
                    text = peticion.descripcion
                )

                Text(
                    text =
                        "${peticion.barrio}, ${peticion.ciudad}"
                )

                Text(
                    text =
                        "${peticion.fecha} - ${peticion.hora}"
                )
            }

            Icon(
                imageVector =
                    Icons.Default.Handyman,
                contentDescription = null
            )
        }
    }
}