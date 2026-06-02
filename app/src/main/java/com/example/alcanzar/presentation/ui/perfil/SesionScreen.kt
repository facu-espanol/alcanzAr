package com.example.alcanzar.presentation.ui.perfil

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.presentation.viewmodel.PerfilViewModel
import com.example.alcanzar.ui.theme.*
import com.example.alcanzar.presentation.ui.viajes.ViajeCard
import com.example.alcanzar.presentation.ui.detalleviaje.DetalleViajeActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesionScreen(
    viewModel: PerfilViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.cargarPerfil(context)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Mi Cuenta", 
                        fontWeight = FontWeight.Bold, 
                        color = Color.White 
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AlcanzarPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AlcanzarBackground)
        ) {
            if (state.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AlcanzarPrimary)
            } else {
                val usuario = state.usuario
                
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Avatar
                            Box(
                                modifier = Modifier.size(120.dp).clip(CircleShape).background(AlcanzarPrimary.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                val bitmap = remember(usuario?.fotoUrl) {
                                    if (!usuario?.fotoUrl.isNullOrBlank()) {
                                        try {
                                            val pureBase64 = if (usuario.fotoUrl.contains(",")) usuario.fotoUrl.split(",")[1] else usuario.fotoUrl
                                            val imageBytes = Base64.decode(pureBase64, Base64.DEFAULT)
                                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                        } catch (e: Exception) { null }
                                    } else null
                                }

                                if (bitmap != null) {
                                    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.Default.AccountCircle, null, modifier = Modifier.size(100.dp), tint = AlcanzarPrimary)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = usuario?.nombreCompleto ?: "Mi Nombre", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = AlcanzarTextPrimary)

                            Spacer(modifier = Modifier.height(32.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                RatingCard("Conductor", Icons.Default.DirectionsCar, usuario?.promedioConductor ?: 0.0, usuario?.totalConductor ?: 0, Modifier.weight(1f))
                                RatingCard("Pasajero", Icons.Default.Person, usuario?.promedioPasajero ?: 0.0, usuario?.totalPasajero ?: 0, Modifier.weight(1f))
                            }
                        }
                    }

                    // Viajes Pendientes
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Mis Viajes Pendientes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AlcanzarPrimary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (state.viajesPendientes.isEmpty()) {
                        item {
                            Text("No tienes viajes pendientes", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
                        }
                    } else {
                        items(state.viajesPendientes) { viaje ->
                            ViajeCard(
                                viaje = viaje,
                                conductor = usuario,
                                onClick = {
                                    val intent = Intent(context, DetalleViajeActivity::class.java).apply {
                                        putExtra("VIAJE", viaje)
                                    }
                                    context.startActivity(intent)
                                },
                                onConductorClick = { /* Soy yo mismo */ }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    // Historial de Viajes
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Historial de Mis Viajes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AlcanzarPrimary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (state.historialViajes.isEmpty()) {
                        item {
                            Text("No tienes viajes finalizados", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
                        }
                    } else {
                        items(state.historialViajes) { viaje ->
                            ViajeCard(
                                viaje = viaje,
                                conductor = usuario,
                                onClick = {
                                    val intent = Intent(context, DetalleViajeActivity::class.java).apply {
                                        putExtra("VIAJE", viaje)
                                    }
                                    context.startActivity(intent)
                                },
                                onConductorClick = { /* Soy yo mismo */ }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}
