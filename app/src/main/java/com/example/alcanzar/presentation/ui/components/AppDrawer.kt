package com.example.alcanzar.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.R


@Composable
fun AppDrawer(
    onInicioClick: () -> Unit,
    onPeticionesClick: () -> Unit,
    onViajesClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onCrearViajeClick: () -> Unit,
    onCrearPeticionClick: () -> Unit,
    onMiPerfilClick: () -> Unit,
    onNotificacionesClick: () -> Unit = {},
    onCerrarSesionClick: () -> Unit = {}
) {

    ModalDrawerSheet(
        modifier = Modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DrawerHeader()

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DrawerSectionTitle("MI CUENTA")

            DrawerItem(
                icon = Icons.Default.Person,
                text = "Mi Perfil",
                onClick = onMiPerfilClick
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            DrawerSectionTitle("GENERAL")

            DrawerItem(
                icon = Icons.Default.Home,
                text = "Inicio",
                onClick = onInicioClick
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            DrawerSectionTitle("PETICIONES")

            DrawerItem(
                icon = Icons.Default.AddCircleOutline,
                text = "Realizar petición",
                onClick = onCrearPeticionClick
            )

            DrawerItem(
                icon = Icons.Default.Search,
                text = "Buscar peticiones",
                onClick = onPeticionesClick
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            DrawerSectionTitle("VIAJES")

            DrawerItem(
                icon = Icons.Default.AddLocationAlt,
                text = "Publicar viaje",
                onClick = onCrearViajeClick
            )

            DrawerItem(
                icon = Icons.Default.DirectionsCar,
                text = "Viajes cercanos",
                onClick = onViajesClick
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            DrawerSectionTitle("SOCIAL")

            DrawerItem(
                icon = Icons.AutoMirrored.Filled.Chat,
                text = "Mis chats",
                onClick = { }
            )

            DrawerItem(
                icon = Icons.Default.Notifications,
                text = "Notificaciones",
                onClick = onNotificacionesClick
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            DrawerSectionTitle("OTROS")

            DrawerItem(
                icon = Icons.Default.Info,
                text = "Acerca de",
                onClick = onAcercaClick
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            DrawerItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                text = "Cerrar sesión",
                onClick = onCerrarSesionClick
            )
            
            // Espacio extra al final para que la barra del sistema no tape el último item
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DrawerHeader() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1976D2))
            .padding(24.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.logo_alcanzar
                ),
                contentDescription = "Logo AlcanzAR",
                modifier = Modifier.size(64.dp)
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column {

                Text(
                    text = "AlcanzAR",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Movilidad colaborativa",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun DrawerSectionTitle(
    title: String
) {

    Text(
        text = title,
        modifier = Modifier.padding(
            start = 20.dp,
            top = 8.dp,
            bottom = 8.dp
        ),
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DrawerItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {

    NavigationDrawerItem(
        icon = {
            Icon(icon, contentDescription = null)
        },

        label = {
            Text(text)
        },

        selected = false,

        onClick = onClick,

        modifier = Modifier.padding(
            horizontal = 12.dp,
            vertical = 4.dp
        )
    )
}
