package com.example.alcanzar.presentation.ui.notificaciones

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alcanzar.domain.model.Notificacion
import com.example.alcanzar.presentation.ui.components.AppDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(
    notificaciones: List<Notificacion>,
    onNotificacionClick: (Notificacion) -> Unit,
    onDeleteNotificacion: (String) -> Unit,
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
                },
                onNotificacionesClick = {
                    scope.launch { drawerState.close() }
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
            if (notificaciones.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("No tienes notificaciones", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = notificaciones,
                        key = { it.id }
                    ) { notificacion ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                // No eliminamos automáticamente al deslizar hasta el final
                                false 
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {
                                val color = when (dismissState.dismissDirection) {
                                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                                    else -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color, shape = MaterialTheme.shapes.medium)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    // El botón de eliminar solo es funcional si se deslizó lo suficiente
                                    if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                                        IconButton(onClick = { onDeleteNotificacion(notificacion.id) }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Confirmar eliminación",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        ) {
                            NotificacionCard(
                                notificacion = notificacion,
                                onClick = { onNotificacionClick(notificacion) }
                            )
                        }
                    }
                }
            }
        }
    }
}
