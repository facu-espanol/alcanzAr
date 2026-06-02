package com.example.alcanzar.presentation.ui.viajes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.presentation.ui.components.AppDrawer
import com.example.alcanzar.ui.theme.AlcanzarPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViajesScreen(
    viajes: List<Viaje>,
    usuariosConductores: Map<String, Usuario> = emptyMap(),
    onRefresh: () -> Unit,
    onViajeClick: (Viaje) -> Unit,
    onConductorClick: (String) -> Unit,
    onPerfilClick: () -> Unit,
    onInicioClick: () -> Unit,
    onPeticionesClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onCrearViajeClick: () -> Unit,
    onCrearPeticionClick: () -> Unit,
    onCerrarSesionClick: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onInicioClick = { scope.launch { drawerState.close(); onInicioClick() } },
                onPeticionesClick = { scope.launch { drawerState.close(); onPeticionesClick() } },
                onViajesClick = { scope.launch { drawerState.close() } },
                onAcercaClick = { scope.launch { drawerState.close(); onAcercaClick() } },
                onCrearViajeClick = { scope.launch { drawerState.close(); onCrearViajeClick() } },
                onCrearPeticionClick = { scope.launch { drawerState.close(); onCrearPeticionClick() } },
                onMiPerfilClick = { scope.launch { drawerState.close(); onPerfilClick() } },
                onCerrarSesionClick = { scope.launch { drawerState.close(); onCerrarSesionClick() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AlcanzAR", color = Color.White) },
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = AlcanzarPrimary)
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    text = "Viajes cercanos",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 18.dp, top = 20.dp, bottom = 12.dp)
                )

                PullToRefreshBox(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        scope.launch {
                            isRefreshing = true
                            onRefresh()
                            delay(1000)
                            isRefreshing = false
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(viajes) { viaje ->
                            ViajeCard(
                                viaje = viaje,
                                conductor = usuariosConductores[viaje.conductorId],
                                onClick = { onViajeClick(viaje) },
                                onConductorClick = onConductorClick
                            )
                        }
                    }
                }
            }
        }
    }
}
