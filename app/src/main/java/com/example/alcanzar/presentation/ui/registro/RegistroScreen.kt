package com.example.alcanzar.presentation.ui.registro

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.ui.theme.*
@Composable
fun RegistroScreen(
    onCrearCuenta: (String, String, (Boolean) -> Unit) -> Unit,
    onRegistroSuccess: () -> Unit,
    onVolverLogin: () -> Unit
) {
    val context = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repetirPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = (-80).dp, y = (-70).dp)
                .background(
                    color = AlcanzarDecorativeBlue,
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 70.dp, y = 60.dp)
                .background(
                    color = AlcanzarDecorativeLightBlue,
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape
                    )
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                AlcanzarPrimary,
                                AlcanzarPrimaryLight
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Crear cuenta",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Crear cuenta",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Sumate a AlcanzAR y empezá a compartir viajes",
                fontSize = 15.sp,
                color = AlcanzarTextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 18.dp, vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 14.dp,
                        shape = RoundedCornerShape(28.dp)
                    ),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Registro",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Creá tus datos de acceso",
                        fontSize = 14.sp,
                        color = AlcanzarTextSecondary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        visualTransformation =
                            if (passwordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {
                                Icon(
                                    imageVector =
                                        if (passwordVisible) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },
                                    contentDescription = "Ver contraseña",
                                    tint = AlcanzarTextSecondary
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = repetirPassword,
                        onValueChange = { repetirPassword = it },
                        label = { Text("Repetir contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        visualTransformation =
                            if (passwordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    Button(
                        onClick = {
                            if (
                                usuario.isBlank() ||
                                password.isBlank() ||
                                repetirPassword.isBlank()
                            ) {
                                Toast.makeText(
                                    context,
                                    "Completá todos los campos",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            if (password != repetirPassword) {
                                Toast.makeText(
                                    context,
                                    "Las contraseñas no coinciden",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            val cuentaCreada = onCrearCuenta(usuario, password) { success ->
                                if (success) {
                                    Toast.makeText(context, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                                    onRegistroSuccess()
                                } else {
                                    Toast.makeText(context, "Ese usuario ya existe", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Crear cuenta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onVolverLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Ya tengo cuenta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}