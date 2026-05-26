package com.example.alcanzar.peticiones

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun PeticionCard(
    peticion: Peticion
) {
    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            MaterialTheme.shapes.large
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
                    text = peticion.tipo,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Text(" ${peticion.desde}")
                Text(" ${peticion.destino}")
                Text(" ${peticion.horario}")
                Text(" ${peticion.extras}")
            }

            Spacer(
                Modifier.width(12.dp)
            )

            Icon(
                Icons.Default.AccountCircle,
                null,
                tint = Color.Gray,
                modifier =
                    Modifier.size(68.dp)
            )
        }
    }
}