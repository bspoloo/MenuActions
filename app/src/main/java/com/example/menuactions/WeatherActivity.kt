package com.example.menuactions

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menuactions.classes.WeatherWebClientSocket
import com.example.menuactions.databinding.ActivityWeatherBinding
import com.example.menuactions.dto.Weather
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.log

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding;
    private lateinit var weatherClient: WeatherWebClientSocket
    private var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWeatherBinding.inflate(layoutInflater);
        setContentView(binding.root);
        //setContentView(R.layout.activity_weather)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        weatherClient = WeatherWebClientSocket()
        disposable = weatherClient.connect("ws://10.8.221.102:8080")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { message -> this.showData(message) },
                { error -> binding.textViewDescription.append("\n❌ Error: ${error.message}") }
            )
    }

    fun showData(message : String){
        Log.d("Message server", message)
        val data = message.split(":")
        binding.textViewTemperature.text = data.get(3)
        binding.textViewDescription.text = data.get(2)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}