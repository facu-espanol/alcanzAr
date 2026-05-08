package com.example.alcanzar.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.alcanzar.bienvenida.BienvenidaActivity
import com.example.alcanzar.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val usuarioCorrecto = "admin"
    private val passwordCorrecta = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val darkMode = prefs.getBoolean("dark_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var passwordVisible = false

        binding.ivVerPassword.setOnClickListener {
            passwordVisible = !passwordVisible

            if (passwordVisible) {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.etUsuario.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.etPassword.requestFocus()
                true
            } else {
                false
            }
        }

        binding.etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnLogin.performClick()
                true
            } else {
                false
            }
        }
        binding.btnLogin.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val password = binding.etPassword.text.toString()

            if (usuario == usuarioCorrecto && password == passwordCorrecta) {
                startActivity(Intent(this, BienvenidaActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}