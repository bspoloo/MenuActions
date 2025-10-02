package com.example.menuactions

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menuactions.classes.WeatherWebClientSocket
import com.example.menuactions.databinding.ActivityWeatherBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherClient: WeatherWebClientSocket
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        weatherClient = WeatherWebClientSocket()
        disposable = weatherClient.connect("ws://192.168.100.73:8080")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { message -> showData(message) },
                { error ->
                    binding.textViewDescription.text = "❌ Error: ${error.message}"
                    Log.e("WeatherActivity", "WebSocket error", error)
                }
            )
    }

    private fun showData(message: String) {
        Log.d("WeatherActivity", "Message received: $message")
        val data = message.split(":")

        if (data.size >= 4) {
            val temperature = data[3].trim()
            val weatherDescription = data[2].trim()

            binding.textViewTemperature.text = "${temperature}°"
            binding.textViewDescription.text = weatherDescription

            // Cambiar icono según el clima
            val weatherIcon = getWeatherIcon(weatherDescription)
            binding.imageViewWeather.setImageResource(weatherIcon)
        } else {
            Log.e("WeatherActivity", "Formato de mensaje inválido: $message")
            binding.textViewDescription.text = "Datos incompletos recibidos"
        }
    }

    private fun getWeatherIcon(weatherDescription: String): Int {
        return when {
            weatherDescription.contains("Soleado", ignoreCase = true) -> R.drawable.ic_sunny
            weatherDescription.contains("Nublado", ignoreCase = true) -> R.drawable.ic_cloudy
            weatherDescription.contains("Lluvioso", ignoreCase = true) -> R.drawable.ic_rainy
            weatherDescription.contains("Tormenta", ignoreCase = true) -> R.drawable.ic_storm
            else -> R.drawable.ic_sunny
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        //weatherClient.disconnect()
    }
}