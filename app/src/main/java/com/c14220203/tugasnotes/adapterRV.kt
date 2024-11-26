package com.c14220203.tugasnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.ActionMode.Callback
import androidx.recyclerview.widget.RecyclerView

class adapterRV(private val listNotes: ArrayList<dcNotes>) :
    RecyclerView.Adapter<adapterRV.listViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: dcNotes)
        fun onEditClicked(data: dcNotes, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class listViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _iNama = itemView.findViewById<TextView>(R.id.iNama)
        var _iTanggal = itemView.findViewById<TextView>(R.id.iTanggal)
        var _iDeskripsi = itemView.findViewById<TextView>(R.id.iDeskripsi)
        var _ibHapus = itemView.findViewById<Button>(R.id.ibHapus)
        var _ibEdit = itemView.findViewById<Button>(R.id.ibEdit)
        var _ibStart = itemView.findViewById<Button>(R.id.ibStart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemnotes, parent, false)
        return listViewHolder(view)
    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        val notes = listNotes[position]
        holder._iNama.text = notes.Nama
        holder._iTanggal.text = notes.Tanggal
        holder._iDeskripsi.text = notes.Deskripsi

        // Atur tampilan tombol berdasarkan status isFinished
        if (notes.isFinished) {
            holder._ibStart.text = "Finish"
            holder._ibStart.isEnabled = false
            holder._ibEdit.isEnabled = false
        } else {
            holder._ibStart.text = "Start"
            holder._ibHapus.isEnabled = true
            holder._ibEdit.isEnabled = true
        }

        holder._ibStart.setOnClickListener {
            notes.isFinished = !notes.isFinished
            saveData(holder.itemView)
            notifyItemChanged(position)
        }

        holder._ibHapus.setOnClickListener {
            onItemClickCallback.onItemClicked(listNotes[position])
        }

        holder._ibEdit.setOnClickListener {
            onItemClickCallback.onEditClicked(listNotes[position], position)
        }
    }

    private fun saveData(view: View) {
        val sharedPreferences = view.context.getSharedPreferences("NotesSP", 0)
        val editor = sharedPreferences.edit()
        val gson = com.google.gson.Gson()

        // Konversi listNotes menjadi JSON string
        val notesJson = gson.toJson(listNotes)
        editor.putString("notes_list", notesJson)
        editor.apply() // Terapkan perubahan
    }


    override fun getItemCount(): Int {
        return listNotes.size
    }


}