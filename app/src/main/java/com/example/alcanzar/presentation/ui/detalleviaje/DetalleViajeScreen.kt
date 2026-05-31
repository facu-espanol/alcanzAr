package com.example.alcanzar.presentation.ui.detalleviaje

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.presentation.ui.components.DetailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleViajeScreen(
    viaje: Viaje,
    onBack: () -> Unit,
    onParticipar: () -> Unit
) {

    val context = LocalContext.current

    val userId =
        SessionManager.obtenerUsuarioId(context) ?: ""

    val yaParticipa =
        viaje.idPasajeros.contains(userId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle del Viaje",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    DetailItem(
                        Icons.Default.LocationOn,
                        "Origen",
                        "${viaje.ciudadOrigen}, ${viaje.paisOrigen}"
                    )

                    DetailItem(
                        Icons.Default.LocationOn,
                        "Destino",
                        "${viaje.ciudadDestino}, ${viaje.paisDestino}"
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 12.dp
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        DetailItem(
                            Icons.Default.Event,
                            "Fecha",
                            viaje.fecha,
                            Modifier.weight(1f)
                        )

                        DetailItem(
                            Icons.Default.Schedule,
                            "Hora",
                            viaje.horaSalida,
                            Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        DetailItem(
                            Icons.Default.DirectionsCar,
                            "Vehículo",
                            viaje.vehiculo,
                            Modifier.weight(1f)
                        )

                        DetailItem(
                            Icons.Default.Group,
                            "Plazas",
                            "${viaje.plazas}",
                            Modifier.weight(1f)
                        )
                    }

                    DetailItem(
                        Icons.Default.Payments,
                        "Costo sugerido",
                        "$${viaje.costoSugerido}"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = onParticipar,
                    modifier = Modifier.weight(1f),
                    colors =
                        if (yaParticipa)
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            )
                        else
                            ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                ) {

                    Text(
                        text =
                            if (yaParticipa)
                                "DARSE DE BAJA"
                            else
                                "SUMARSE AL VIAJE",
                        fontWeight = FontWeight.Bold
                    )
                }

                if (yaParticipa) {

                    FloatingActionButton(
                        onClick = {},
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Chat,
                            contentDescription = "Chat"
                        )
                    }
                }
            }
        }
    }
}