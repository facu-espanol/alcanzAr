package com.example.alcanzar.perfil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alcanzar.databinding.ActivityPerfilBinding

class Perfil : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}