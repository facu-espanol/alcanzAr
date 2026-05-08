// PeticionAdapter.kt
package com.example.alcanzar.peticiones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.alcanzar.databinding.ItemPeticionBinding

class PeticionAdapter(
    context: Context,
    private val lista: List<Peticion>
) : ArrayAdapter<Peticion>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = if (convertView == null) {
            ItemPeticionBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        } else {
            ItemPeticionBinding.bind(convertView)
        }

        val peticion = lista[position]

        binding.txtDesde.text = peticion.desde
        binding.txtDestino.text = peticion.destino
        binding.txtHorario.text = peticion.horario
        binding.txtExtras.text = peticion.extras

        return binding.root
    }
}