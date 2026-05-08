package com.example.alcanzar.peticiones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.alcanzar.databinding.ActivityPeticionesBinding

class PeticionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeticionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPeticionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lista = leerPeticiones()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            lista
        )

        binding.listViewPeticiones.adapter = adapter
    }

    private fun leerPeticiones(): List<String> {
        val lista = mutableListOf<String>()

        val inputStream = assets.open("peticiones.txt")
        val reader = inputStream.bufferedReader()

        reader.forEachLine {
            if (it.isNotBlank()) {
                lista.add(it)
            }
        }

        reader.close()
        return lista
    }
}