package com.example.menuactions

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menuactions.classes.REST
import com.example.menuactions.databinding.ActivityServiceWebBinding
import com.example.menuactions.dataclasses.Album
import com.example.menuactions.interfaces.IAPIServices
import com.google.android.gms.common.internal.Objects
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceWebActivity : AppCompatActivity() {
    private lateinit var binding : ActivityServiceWebBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityServiceWebBinding.inflate(layoutInflater);
        setContentView(binding.root);

        //setContentView(R.layout.activity_service_web)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonRecovery.setOnClickListener {
            this.getData();
        }
    }

    private fun getData(){
        val apiService = REST.getRestEngine().create(IAPIServices::class.java)
        val result : Call<List<Album>> = apiService.listAlbums();

        result.enqueue(object : Callback<List<Album>> {
            override fun onResponse(
                call: Call<List<Album>?>,
                response: Response<List<Album>?>
            ) {
                if (response.isSuccessful){
                    Log.d("Success", response.body().toString())
                }else {
                    Log.d("Error", "Error in resource response")
                }
            }

            override fun onFailure(
                call: Call<List<Album>?>,
                t: Throwable
            ) {
                Log.d("Error", "Error in response ${t.message.toString()}")
            }

        });
    }
}