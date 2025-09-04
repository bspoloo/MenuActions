package com.example.menuactions

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menuactions.classes.DbHelper
import com.example.menuactions.databinding.ActivityDatabaseBinding
import com.example.menuactions.databinding.ActivityMainBinding
import com.example.menuactions.dataclasses.Lugar

class DatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDatabaseBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //setContentView(R.layout.activity_database)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dbHelper : DbHelper = DbHelper(this);
        binding.buttonInsert.setOnClickListener {
            val nombre = binding.editTextNombre.text.toString();
            val descripcion = binding.editTextDescripcion.text.toString();
            val latitud = binding.editTextLatitud.text.toString().toDouble();
            val longitud = binding.editTextLongitud.text.toString().toDouble();
            val lugar = Lugar(0, nombre, descripcion, latitud, longitud);
            val id : Long = dbHelper.insertLugar(lugar);

            binding.textViewMessage.setText("Lugar con id $id insertado exitosamente...");
        }
        binding.buttonUpdate.setOnClickListener {
            val idLugar = binding.editTextId.text.toString().toLong();
            val nombre = binding.editTextNombre.text.toString();
            val descripcion = binding.editTextDescripcion.text.toString();
            val latitud = binding.editTextLatitud.text.toString().toDouble();
            val longitud = binding.editTextLongitud.text.toString().toDouble();
            val lugar = Lugar(idLugar.toInt(), nombre, descripcion, latitud, longitud);

            val number : Int = dbHelper.updateLugar(lugar);

            binding.textViewMessage.setText("cantidad modificada $number ..");
        }

        binding.buttonDelete.setOnClickListener {
            val idLugar = binding.editTextId.text.toString().toLong();
            val number : Int = dbHelper.deleteLugar(idLugar);
            binding.textViewMessage.setText("cantidad modificada $number ..");
        };
        binding.buttonList.setOnClickListener {
            val lugares : String = dbHelper.listLugar();
            binding.textViewMessage.setText(lugares);
        }

        binding.buttonExit.setOnClickListener {
            finish();
        }
    }
}