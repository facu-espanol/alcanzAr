package com.example.alcanzar.presentation.ui.perfil

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.presentation.viewmodel.PerfilViewModel
import com.example.alcanzar.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    targetUserId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(targetUserId) {
        viewModel.cargarPerfil(context, targetUserId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Perfil de Usuario", 
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    Text(text = usuario?.nombreCompleto ?: "Usuario", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = AlcanzarTextPrimary)

                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        text = "Reputación en la comunidad",
                        style = MaterialTheme.typography.titleMedium,
                        color = AlcanzarTextSecondary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        RatingCard("Conductor", Icons.Default.DirectionsCar, usuario?.promedioConductor ?: 0.0, usuario?.totalConductor ?: 0, Modifier.weight(1f))
                        RatingCard("Pasajero", Icons.Default.Person, usuario?.promedioPasajero ?: 0.0, usuario?.totalPasajero ?: 0, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun RatingCard(title: String, icon: ImageVector, rating: Double, total: Int, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.elevatedCardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = AlcanzarPrimary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelLarge, color = AlcanzarTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(String.format(Locale.getDefault(), "%.1f", rating), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = AlcanzarPrimary)
                Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(20.dp))
            }
            Text("$total viajes", style = MaterialTheme.typography.bodySmall, color = AlcanzarTextMuted)
        }
    }
}
