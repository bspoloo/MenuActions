package com.example.menuactions.CRUD

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.menuactions.Entities.Lugar
import com.example.menuactions.R
import com.example.menuactions.databases.DaoLugar
import com.example.menuactions.databinding.ActivityDbroomBinding
import kotlinx.coroutines.launch

class DBRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDbroomBinding
    //Database and their operatiions
    private lateinit var room: DaoLugar
    private lateinit var lugar: Lugar
    private var listaLugares: MutableList<Lugar> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDbroomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setContentView(R.layout.activity_dbroom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        room = Room.databaseBuilder(this, DaoLugar::class.java, "dbPruebas2").build()

        binding.buttonInsert.setOnClickListener {
            lugar = Lugar(
                binding.editTextId.text.toString().toInt(),
                binding.editTextNombre.text.toString(),
                binding.editTextDescripcion.text.toString(),
                binding.editTextLatitud.text.toString().toDouble(),
                binding.editTextLongitud.text.toString().toDouble()
            )
            agregarLugar(room, lugar)
        }
        binding.buttonList.setOnClickListener {
            listarLugares(room)
        }
        binding.buttonUpdate.setOnClickListener{
            val lugar = Lugar(
                binding.editTextId.text.toString().toInt(),
                binding.editTextNombre.text.toString(),
                binding.editTextDescripcion.text.toString(),
                binding.editTextLatitud.text.toString().toDouble(),
                binding.editTextLongitud.text.toString().toDouble()
            )
            actualizarLugar(room, lugar)
        }
        binding.buttonDelete.setOnClickListener{
            borrarLugar(room, binding.editTextId.text.toString().toInt())
        }
        binding.buttonExit.setOnClickListener {
            finish()
        }
    }
    private fun agregarLugar(room: DaoLugar, lugar: Lugar) {
        lifecycleScope.launch {
            room.daoLugar().insertarLugar(lugar)
            binding.textViewMessage.text = listarLugares(room).toString()
        }
    }

    private fun listarLugares(room: DaoLugar) {
        lifecycleScope.launch {
            listaLugares = room.daoLugar().obtenerLugares()
            binding.textViewMessage.text = listaLugares.toString()
        }
    }

    private fun actualizarLugar(room: DaoLugar, lugar: Lugar) {
        lifecycleScope.launch {
            room.daoLugar().actualizarLugar(lugar.id, lugar.nombre, lugar.descripcion, lugar.latitud, lugar.longitud)
            listarLugares(room)
        }
    }

    private fun borrarLugar(room: DaoLugar, id: Int) {
        lifecycleScope.launch {
            room.daoLugar().eliminarLugar(id)
            listarLugares(room)
           }
    }
}