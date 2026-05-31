package com.example.alcanzar.presentation.ui.viajes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.presentation.ui.components.AppDrawer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViajesScreen(
    viajes: List<Viaje>,
    onRefresh: () -> Unit,
    onViajeClick: (Viaje) -> Unit,
    onPerfilClick: () -> Unit,
    onInicioClick: () -> Unit,
    onPeticionesClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onCrearViajeClick: () -> Unit,
    onCrearPeticionClick: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

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
            )
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "AlcanzAR",
                            color = Color.White
                        )
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                null,
                                tint = Color.White
                            )
                        }
                    },

                    actions = {
                        IconButton(
                            onClick = onPerfilClick
                        ) {
                            Icon(
                                Icons.Default.Person,
                                null,
                                tint = Color.White
                            )
                        }
                    },

                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor =
                                Color(0xFF1976D2)
                        )
                )
            }
        ) { padding ->

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
            ) {

                Text(
                    text = "Viajes cercanos",
                    fontSize = 22.sp,
                    modifier =
                        Modifier.padding(
                            start = 18.dp,
                            top = 20.dp,
                            bottom = 12.dp
                        )
                )

            PullToRefreshBox(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        onRefresh()
                        delay(1000) // Solamente muestra el icono de la flechita cargando este tiempo
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
                    items(viajes) {
                        ViajeCard(it, onClick = { onViajeClick(it) })
                    }
                }
            }
            }
        }
    }
}