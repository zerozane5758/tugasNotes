package com.c14220203.tugasnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var _Nama = mutableListOf<String>()
    var _Tanggal = mutableListOf<String>()
    var _Deskripsi = mutableListOf<String>()

    private var arNotes = arrayListOf<dcNotes>()

    private lateinit var _rvNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _rvNotes = findViewById(R.id.rvNotes)
        val _btnTambah = findViewById<ImageButton>(R.id.btnTambah)

        // Arahkan ke InputActivity ketika tombol Tambah diklik
        _btnTambah.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTE)
        }
        TampilkanData()
    }

    private fun HapusData(notes: dcNotes) {
        arNotes.remove(notes)
        TampilkanData()
    }


    private fun TambahData(newNote: dcNotes) {
        arNotes.add(newNote)
    }


    private fun TampilkanData() {
        _rvNotes.layoutManager = LinearLayoutManager(this)
        val notesAdapter = adapterRV(arNotes)
        _rvNotes.adapter = notesAdapter

        notesAdapter.setOnItemClickCallback(object : adapterRV.OnItemClickCallback {
            override fun onItemClicked(data: dcNotes) {
                HapusData(data)
            }

            override fun onEditClicked(data: dcNotes, position: Int) {
                val intent = Intent(this@MainActivity, InputActivity::class.java).apply {
                    putExtra("NAMA", data.Nama)
                    putExtra("TANGGAL", data.Tanggal)
                    putExtra("DESKRIPSI", data.Deskripsi)
                    putExtra(EXTRA_POSITION, position)
                }
                startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK && data != null) {
            val newName = data.getStringExtra("NAMA")
            val newDate = data.getStringExtra("TANGGAL")
            val newDescription = data.getStringExtra("DESKRIPSI")

            if (newName != null && newDate != null && newDescription != null) {
                val newNote = dcNotes(newName, newDate, newDescription)
                TambahData(newNote)
                TampilkanData()
            }
        }

        if (requestCode == REQUEST_CODE_EDIT_NOTE && resultCode == RESULT_OK && data != null) {
            val updatedName = data.getStringExtra("NAMA")
            val updatedDate = data.getStringExtra("TANGGAL")
            val updatedDescription = data.getStringExtra("DESKRIPSI")
            val position = data.getIntExtra(EXTRA_POSITION, -1)

            if (updatedName != null && updatedDate != null && updatedDescription != null && position != -1) {
                val updatedNote = arNotes[position]
                updatedNote.Nama = updatedName
                updatedNote.Tanggal = updatedDate
                updatedNote.Deskripsi = updatedDescription
                TampilkanData()
            }
        }
    }


    companion object {
        const val REQUEST_CODE_ADD_NOTE = 1
        const val REQUEST_CODE_EDIT_NOTE = 2
        const val EXTRA_POSITION = "POSITION"
    }

}
