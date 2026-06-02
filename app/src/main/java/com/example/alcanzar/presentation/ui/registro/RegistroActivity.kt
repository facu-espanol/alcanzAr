package com.example.alcanzar.presentation.ui.registro

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.CrearCuentaUseCase
import com.example.alcanzar.domain.usecase.IniciarSesionUseCase
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.viewmodel.LoginViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream

class RegistroActivity : ComponentActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val userDataSource = UserFirestoreDataSource(db)
        val loginRepository = LoginRepositoryImpl(userDataSource)

        val iniciarSesionUseCase = IniciarSesionUseCase(loginRepository)
        val crearCuentaUseCase = CrearCuentaUseCase(loginRepository)

        viewModel = LoginViewModel(iniciarSesionUseCase, crearCuentaUseCase)

        setContent {
            AlcanzARTheme {
                RegistroScreen(
                    onCrearCuenta = { usuario, password, nombre, fotoUri, onResult ->
                        val fotoBase64 = if (fotoUri != null) {
                            uriToBase64(fotoUri)
                        } else {
                            ""
                        }
                        
                        viewModel.crearCuenta(usuario, password, nombre, fotoBase64) { userId ->
                            if (userId != null) {
                                SessionManager.guardarUsuarioId(this, userId)
                                onResult(true)
                            } else {
                                onResult(false)
                            }
                        }
                    },
                    onRegistroSuccess = {
                        startActivity(Intent(this, BienvenidaActivity::class.java))
                        finish()
                    },
                    onVolverLogin = {
                        finish()
                    }
                )
            }
        }
    }

    private fun uriToBase64(uri: Uri): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (originalBitmap == null) return ""

            // Redimensionar para que no exceda 400px (suficiente para perfil y ligero para Firestore)
            val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
            val targetWidth = 400
            val targetHeight = (targetWidth / ratio).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)

            val baos = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
            val bytes = baos.toByteArray()

            val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
            "data:image/jpeg;base64,$base64"
        } catch (e: Exception) {
            ""
        }
    }
}
