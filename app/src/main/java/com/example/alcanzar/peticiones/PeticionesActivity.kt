package com.example.alcanzar.peticiones

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alcanzar.R
import com.example.alcanzar.acerca.AcercaActivity
import com.example.alcanzar.bienvenida.BienvenidaActivity
import com.example.alcanzar.databinding.ActivityPeticionesBinding
import com.example.alcanzar.perfil.Perfil

class PeticionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeticionesBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPeticionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())

            val params = view.layoutParams
            params.height = (56 * resources.displayMetrics.density).toInt() + statusBar.top
            view.layoutParams = params

            view.setPadding(0, statusBar.top, 0, 0)

            insets
        }

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            0,
            0
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }

        val lista = leerPeticiones()
        val adapter = PeticionAdapter(this, lista)
        binding.listViewPeticiones.adapter = adapter

        binding.navigationView.setNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_inicio -> {
                    startActivity(Intent(this, BienvenidaActivity::class.java))
                    finish()
                }

                R.id.nav_buscar_peticiones -> {
                    // Ya estás acá
                }

                R.id.nav_acerca_de -> {
                    startActivity(Intent(this, AcercaActivity::class.java))
                }

                R.id.nav_modo -> {
                    val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                    val darkMode = prefs.getBoolean("dark_mode", false)

                    val nuevoModo = !darkMode

                    prefs.edit().putBoolean("dark_mode", nuevoModo).apply()

                    AppCompatDelegate.setDefaultNightMode(
                        if (nuevoModo) AppCompatDelegate.MODE_NIGHT_YES
                        else AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
            }

            binding.drawerLayout.closeDrawers()
            true
        }
    }

    private fun leerPeticiones(): List<Peticion> {
        val lista = mutableListOf<Peticion>()

        val inputStream = assets.open("peticiones.txt")
        val reader = inputStream.bufferedReader()

        reader.forEachLine { linea ->
            if (linea.isNotBlank()) {
                val partes = linea.split(";")

                if (partes.size >= 5) {
                    lista.add(
                        Peticion(
                            tipo = partes[0],
                            desde = partes[1],
                            destino = partes[2],
                            horario = partes[3],
                            extras = partes[4]
                        )
                    )
                }
            }
        }

        reader.close()
        return lista
    }
}