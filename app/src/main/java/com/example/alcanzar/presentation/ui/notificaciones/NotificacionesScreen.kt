package com.example.alcanzar.presentation.ui.notificaciones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Notificacion
import com.example.alcanzar.presentation.ui.components.AppDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(
    notificaciones: List<Notificacion>,
    onNotificacionClick: (Notificacion) -> Unit,
    onPerfilClick: () -> Unit,
    onInicioClick: () -> Unit,
    onPeticionesClick: () -> Unit,
    onViajesClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onCrearViajeClick: () -> Unit,
    onCrearPeticionClick: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onInicioClick = {
                    scope.launch {
                        drawerState.close()
                        onInicioClick()
                    }
                },
                onPeticionesClick = {
                    scope.launch {
                        drawerState.close()
                        onPeticionesClick()
                    }
                },
                onViajesClick = {
                    scope.launch {
                        drawerState.close()
                        onViajesClick()
                    }
                },
                onAcercaClick = {
                    scope.launch {
                        drawerState.close()
                        onAcercaClick()
                    }
                },
                onCrearViajeClick = {
                    scope.launch {
                        drawerState.close()
                        onCrearViajeClick()
                    }
                },
                onCrearPeticionClick = {
                    scope.launch {
                        drawerState.close()
                        onCrearPeticionClick()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Notificaciones", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, null, tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = onPerfilClick) {
                            Icon(Icons.Default.Person, null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notificaciones) { notificacion ->
                    NotificacionCard(
                        notificacion = notificacion,
                        onClick = { onNotificacionClick(notificacion) }
                    )
                }
            }
        }
    }
}
