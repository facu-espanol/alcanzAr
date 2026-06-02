package com.example.alcanzar.presentation.ui.detalleviaje

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.usecase.ObtenerUsuariosPorIdsUseCase
import com.example.alcanzar.domain.usecase.ParticiparEnViajeUseCase
import com.example.alcanzar.presentation.ui.components.DetailItem
import com.example.alcanzar.presentation.ui.components.UserAvatar
import com.example.alcanzar.presentation.viewmodel.DetalleViajeViewModel
import com.example.alcanzar.presentation.viewmodel.ParticipacionResult
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class DetalleViajeActivity : ComponentActivity() {

    companion object {
        var viajeSeleccionado: Viaje? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val viajeDataSource = ViajeFirestoreDataSource(db)
        val userDataSource = UserFirestoreDataSource(db)
        
        val viajeRepository = ViajeRepositoryImpl(viajeDataSource)
        val loginRepository = LoginRepositoryImpl(userDataSource)
        
        val viewModel = DetalleViajeViewModel(
            ParticiparEnViajeUseCase(viajeRepository),
            ObtenerUsuariosPorIdsUseCase(loginRepository)
        )

        viajeSeleccionado?.let { viewModel.setViaje(it) }

        setContent {
            AlcanzARTheme {
                val viaje by viewModel.viajeState.collectAsState()
                val usuariosInscriptos by viewModel.usuariosInscriptos.collectAsState()
                
                viaje?.let {
                    DetalleViajeContent(
                        viaje = it,
                        inscriptos = usuariosInscriptos,
                        onBack = { finish() },
                        onParticipar = {
                            viewModel.alternarParticipacion(this) { result ->
                                when (result) {
                                    ParticipacionResult.Success -> {
                                        Toast.makeText(this, "Operación exitosa", Toast.LENGTH_SHORT).show()
                                    }
                                    ParticipacionResult.SinCupos -> {
                                        Toast.makeText(this, "No quedan plazas disponibles", Toast.LENGTH_LONG).show()
                                    }
                                    ParticipacionResult.Error -> {
                                        Toast.makeText(this, "Error al procesar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleViajeContent(
    viaje: Viaje, 
    inscriptos: List<Usuario>, 
    onBack: () -> Unit, 
    onParticipar: () -> Unit
) {
    val context = LocalContext.current
    val userId = SessionManager.obtenerUsuarioId(context) ?: ""
    val yaParticipa = viaje.idPasajeros.contains(userId)
    val esCreador = viaje.conductorId == userId
    val estaLleno = viaje.idPasajeros.size >= viaje.plazas

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Viaje", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailItem(Icons.Default.LocationOn, "Origen", "${viaje.ciudadOrigen}, ${viaje.paisOrigen}")
                    DetailItem(Icons.Default.LocationOn, "Destino", "${viaje.ciudadDestino}, ${viaje.paisDestino}")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        DetailItem(Icons.Default.Event, "Fecha", viaje.fecha, Modifier.weight(1f))
                        DetailItem(Icons.Default.Schedule, "Hora", viaje.horaSalida, Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        DetailItem(Icons.Default.DirectionsCar, "Vehículo", viaje.vehiculo, Modifier.weight(1f))
                        DetailItem(Icons.Default.Group, "Plazas", "${viaje.plazas}", Modifier.weight(1f))
                    }
                    DetailItem(Icons.Default.Payments, "Costo sugerido", "$${viaje.costoSugerido}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de Inscriptos / Pasajeros
            Text(
                text = "Pasajeros inscriptos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            if (esCreador) {
                if (inscriptos.isEmpty()) {
                    Text("Aún no hay pasajeros inscriptos", fontSize = 14.sp, color = Color.Gray)
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        items(inscriptos) { usuario ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                UserAvatar(fotoUrl = usuario.fotoUrl)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = usuario.nombreCompleto,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier.width(80.dp),
                                    maxLines = 2,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            } else {
                Surface(
                    color = if (estaLleno) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Group, null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (estaLleno) "Viaje completo" else "${viaje.idPasajeros.size}/${viaje.plazas} personas anotadas",
                            fontWeight = FontWeight.Bold,
                            color = if (estaLleno) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (!esCreador) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onParticipar,
                        modifier = Modifier.weight(1f),
                        enabled = yaParticipa || !estaLleno,
                        colors = if (yaParticipa) 
                            ButtonDefaults.buttonColors(containerColor = Color.Gray) 
                        else if (estaLleno)
                            ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                        else
                            ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = when {
                                yaParticipa -> "DARSE DE BAJA"
                                estaLleno -> "VIAJE COMPLETO"
                                else -> "SUMARSE AL VIAJE"
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (yaParticipa) {
                        FloatingActionButton(
                            onClick = { /* Chat */ },
                            containerColor = Color(0xFF1976D2),
                            contentColor = Color.White,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Chat, "Chat")
                        }
                    }
                }
            } else {
                Text(
                    "Sos el conductor de este viaje",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
