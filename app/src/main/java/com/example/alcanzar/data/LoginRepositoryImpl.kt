package com.example.alcanzar.data

import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val dataSource: UserFirestoreDataSource
) : LoginRepository {

    override fun iniciarSesion(usuario: String, password: String, onResult: (String?) -> Unit) {
        dataSource.login(usuario, password, onResult)
    }

    override fun crearCuenta(
        usuario: String, 
        password: String, 
        nombreCompleto: String, 
        fotoUrl: String, 
        onResult: (String?) -> Unit
    ) {
        dataSource.crearUsuario(usuario, password, nombreCompleto, fotoUrl, onResult)
    }
}
