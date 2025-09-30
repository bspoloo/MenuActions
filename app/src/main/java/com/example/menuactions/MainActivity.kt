package com.example.menuactions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menuactions.CRUD.AnimeActivity
import com.example.menuactions.CRUD.DBRoomActivity
import com.example.menuactions.databinding.ActivityMainBinding
import com.example.menuactions.databinding.ActivityMapsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater);

        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonGraphic : Button = findViewById<Button>(R.id.buttonGraphic);
        val buttonMaps : Button = findViewById<Button>(R.id.buttonGoogleMaps);
        val buttonBD : Button = findViewById<Button>(R.id.buttonBD);
        val buttonCantor : Button = findViewById<Button>(R.id.buttonCantor);
        val buttonOperations : Button = findViewById<Button>(R.id.buttonOperaciones);
        val buttonDBRoom : Button = findViewById<Button>(R.id.buttonDBRoom);
        val buttonExit : Button = findViewById<Button>(R.id.buttonExit);

        buttonGraphic.setOnClickListener {
            //Toast.makeText(this, "Ejecutando boton grafico", Toast.LENGTH_LONG).show();
            val intent = Intent(this, GraphicsActivity::class.java);
            startActivity(intent);
        }
        buttonMaps.setOnClickListener {
            //Toast.makeText(this, "Ejecutando boton Mapas", Toast.LENGTH_LONG).show();
            val intent = Intent(this, MapsActivity::class.java);
            startActivity(intent);
        }
        buttonBD.setOnClickListener {
            //Toast.makeText(this, "Ejecutando boton Base de datos", Toast.LENGTH_LONG).show();
            val intent = Intent(this, DatabaseActivity::class.java);
            startActivity(intent);
        }
        buttonCantor.setOnClickListener {
            //Toast.makeText(this, "Ejecutando boton Base de datos", Toast.LENGTH_LONG).show();
            val intent = Intent(this, CantorAcitivity::class.java);
            startActivity(intent);
        }
        buttonOperations.setOnClickListener {
            //Toast.makeText(this, "Ejecutando boton operaciones", Toast.LENGTH_LONG).show();
            val intent = Intent(this, OperationsActivity::class.java);
            startActivity(intent);
        }
        buttonDBRoom.setOnClickListener {
            val intent = Intent(this, DBRoomActivity::class.java);
            startActivity(intent);
        }
        binding.buttonAnime.setOnClickListener {
            val intent = Intent(this, AnimeActivity::class.java)
            startActivity(intent)
        }
        binding.buttonServies.setOnClickListener {
            val intent = Intent(this, ServiceWebActivity::class.java);
            startActivity(intent);
        }
        binding.buttonWeather.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java);
            startActivity(intent);
        }
        binding.buttonNestjs.setOnClickListener {
            val intent = Intent(this, NestjsActivity::class.java);
            startActivity(intent);
        }
        buttonExit.setOnClickListener {
            finish();
        }
    }
}