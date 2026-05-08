package com.example.alcanzar.acerca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alcanzar.databinding.ActivityAcercaBinding

class AcercaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAcercaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAcercaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}