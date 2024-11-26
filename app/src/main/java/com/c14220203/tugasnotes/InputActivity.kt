package com.c14220203.tugasnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNama = findViewById<EditText>(R.id.etNamaInput)
        val etTanggal = findViewById<EditText>(R.id.etTanggalInput)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsiInput)
        val btnTambah = findViewById<Button>(R.id.btnTambahInput)

        val initialName = intent.getStringExtra("NAMA")
        val initialDate = intent.getStringExtra("TANGGAL")
        val initialDescription = intent.getStringExtra("DESKRIPSI")
        val position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1)

        if (initialName != null && initialDate != null && initialDescription != null) {
            etNama.setText(initialName)
            etTanggal.setText(initialDate)
            etDeskripsi.setText(initialDescription)
        }

        btnTambah.setOnClickListener {
            val nama = etNama.text.toString()
            val tanggal = etTanggal.text.toString()
            val deskripsi = etDeskripsi.text.toString()

            val resultIntent = Intent().apply {
                putExtra("NAMA", nama)
                putExtra("TANGGAL", tanggal)
                putExtra("DESKRIPSI", deskripsi)
                putExtra(MainActivity.EXTRA_POSITION, position)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
}
