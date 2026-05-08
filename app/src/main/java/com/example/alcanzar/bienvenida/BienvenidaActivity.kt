package com.example.alcanzar.bienvenida

import com.example.alcanzar.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.alcanzar.databinding.ActivityBienvenidaBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatDelegate
import com.example.alcanzar.acerca.AcercaActivity
import com.example.alcanzar.perfil.Perfil
import com.example.alcanzar.peticiones.PeticionesActivity

class BienvenidaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBienvenidaBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Ajuste status bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())

            val params = view.layoutParams
            params.height = (56 * resources.displayMetrics.density).toInt() + statusBar.top
            view.layoutParams = params

            view.setPadding(0, statusBar.top, 0, 0)

            insets
        }

        // Drawer
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            0,
            0
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Perfil
        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }

        // 🔥 MENÚ
        binding.navigationView.setNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_inicio -> {
                    // Ya estás acá
                }

                R.id.nav_buscar_peticiones -> {
                    startActivity(Intent(this, PeticionesActivity::class.java))
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
}