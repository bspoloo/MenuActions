package com.example.menuactions.CRUD

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.menuactions.Entities.Anime
import com.example.menuactions.Entities.Lugar
import com.example.menuactions.R
import com.example.menuactions.databases.DaoAnime
import com.example.menuactions.databases.DaoLugar
import com.example.menuactions.databinding.ActivityAnimeBinding
import kotlinx.coroutines.launch

class AnimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeBinding;
    private lateinit var room : DaoAnime;
    private var animes: MutableList<Anime> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnimeBinding.inflate(layoutInflater);
        setContentView(binding.root)
        //setContentView(R.layout.activity_anime)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        room = Room.databaseBuilder(this, DaoAnime::class.java, "dbPruebas2").build()
        binding.buttonInsert.setOnClickListener {
            val anime = Anime(
                    0,
                binding.editTextNombre.text.toString(),
                binding.editTextUrlImage.text.toString(),
                binding.editTextTotalCaps.text.toString().toInt(),
                binding.editTextType.text.toString()
            )
            insertAnime(anime)
        }
        binding.buttonUpdate.setOnClickListener {
            val anime = Anime(
                binding.editTextId.text.toString().toInt(),
                binding.editTextNombre.text.toString(),
                binding.editTextUrlImage.text.toString(),
                binding.editTextTotalCaps.text.toString().toInt(),
                binding.editTextType.text.toString()
            )
            updateAnime(anime)
        }
        binding.buttonDelete.setOnClickListener {
            deleteAnime(binding.editTextId.text.toString().toInt())
        }
        binding.buttonList.setOnClickListener {
            listAllAnime()
        }
    }
    private fun insertAnime(anime: Anime){
        lifecycleScope.launch{
            room.daoAnime().insertAnime(anime);
            listAllAnime()
        }
    }
    private fun updateAnime(anime: Anime){
        lifecycleScope.launch{
            room.daoAnime().updateAnimeById(anime.id, anime.name, anime.urlImage, anime.totalCaps, anime.type);
            listAllAnime()
        }
    }
    private fun deleteAnime(id: Int){
        lifecycleScope.launch{
            room.daoAnime().deleteAnimeById(id);
            listAllAnime()
        }
    }
    private fun listAllAnime(){
        lifecycleScope.launch {
            animes = room.daoAnime().getAllAnime()
            binding.textViewMessage.text = animes.toString()
        }
    }
}