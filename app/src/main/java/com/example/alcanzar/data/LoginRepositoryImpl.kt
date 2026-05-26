package com.example.alcanzar.data

import android.content.Context
import com.example.alcanzar.domain.repository.LoginRepository
import java.io.File

class LoginRepositoryImpl(
    private val context: Context
) : LoginRepository {

    private val archivoUsuarios: File
        get() = File(context.filesDir, "usuarios.csv")

    override fun iniciarSesion(usuario: String, password: String): Boolean {
        if (!archivoUsuarios.exists()) return false

        return archivoUsuarios.readLines().any { linea ->
            val partes = linea.split(";")
            partes.size >= 2 &&
                    partes[0] == usuario &&
                    partes[1] == password
        }
    }

    override fun crearCuenta(usuario: String, password: String): Boolean {
        if (usuario.isBlank() || password.isBlank()) return false

        if (!archivoUsuarios.exists()) {
            archivoUsuarios.createNewFile()
        }

        val usuarioYaExiste = archivoUsuarios.readLines().any { linea ->
            val partes = linea.split(";")
            partes.isNotEmpty() && partes[0] == usuario
        }

        if (usuarioYaExiste) return false

        archivoUsuarios.appendText("$usuario;$password\n")
        return true
    }
}