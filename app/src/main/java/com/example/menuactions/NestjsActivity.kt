package com.example.menuactions

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menuactions.classes.REST
import com.example.menuactions.databinding.ActivityNestjsBinding
import com.example.menuactions.dataclasses.Product
import com.example.menuactions.dto.ProductDto
import com.example.menuactions.interfaces.IAPIServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.time.measureTime

class NestjsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNestjsBinding;
    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNestjsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_nestjs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.19.184.104:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        // Crea una instancia de ApiService
        val apiService = retrofit.create(IAPIServices::class.java)
        // Comienza a observar los datos

        observarDatos(apiService)

        binding.buttonRecovery.setOnClickListener {
            this.getData();
        }
        binding.buttonInsert.setOnClickListener {
            val name : String = binding.editTextName.text.toString();
            val price : Double = binding.editTextPrice.text.toString().toDouble();
            val description : String = binding.editTextDescription.text.toString();
            val product : ProductDto = ProductDto (
                name,
                price,
                description
            );
            this.insertProduct(product)
        }
        binding.buttonUpdate.setOnClickListener {
            val id : Int = binding.editTextId.text.toString().toInt();
            val name : String = binding.editTextName.text.toString();
            val price : Double = binding.editTextPrice.text.toString().toDouble();
            val description : String = binding.editTextDescription.text.toString();
            val product : ProductDto = ProductDto (
                name,
                price,
                description
            );
            this.updateProduct(id,product)
        }
        binding.buttonDelete.setOnClickListener {
            val id : Int = binding.editTextId.text.toString().toInt();
            this.deleteProduct(id)
        }
    }

    private fun observarDatos(apiService: IAPIServices) {
        var startTime : Long = 0;
        val observable = Observable.interval(0, 10, TimeUnit.SECONDS)
            .flatMap {
                startTime = System.currentTimeMillis()
                apiService.getAllProductsReactive()
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        // Suscribirse para manejar la lista de datos

        val disposable = observable.subscribe({ listaDatos ->
            // Construir un texto para mostrar todos los mensajes en la lista
            val elapsed = (System.currentTimeMillis() - startTime);
            val mensajes = listaDatos.joinToString("\n") { dato ->
                "ID: ${dato.product_id}, Mensaje: ${dato.name}"
            }
            // Actualizar el TextView con los mensajes
            binding.textViewResult.text = mensajes

            binding.timeGetData.setText("El tiempo de retardo es: ${elapsed}ms");
        }, { error ->
            // Manejar error si ocurre
            binding.textViewResult.text = "Error: ${error.message}"
        })

        disposables.add(disposable)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Libera los recursos de RxJava cuando la actividad se destruye
        disposables.clear()
    }


    private fun getData(){
        val apiService = REST.getRestEngine().create(IAPIServices::class.java)
        val result : Call<List<Product>> = apiService.getAllProducts();

        result.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>?>,
                response: Response<List<Product>?>
            ) {
                if (response.isSuccessful){
                    Log.d("Success", response.body().toString())
                    binding.textViewResult.text = response.body().toString()
                }else {
                    Log.d("Error", "Error in resource response")
                }
            }

            override fun onFailure(
                call: Call<List<Product>?>,
                t: Throwable
            ) {
                Log.d("Error", "Error in response ${t.message.toString()}")
            }

        });
    }
    private fun insertProduct(product: ProductDto) {
        val apiService = REST.getRestEngine().create(IAPIServices::class.java)
        val result : Call<Product> = apiService.insertProduct(product);

        result.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product?>,
                response: Response<Product?>
            ) {
                if (response.isSuccessful){
                    Log.d("Success", response.body().toString())
                }else {
                    Log.d("Error", "Error in resource response")
                }
            }

            override fun onFailure(
                call: Call<Product?>,
                t: Throwable
            ) {
                Log.d("Error", "Error in response ${t.message.toString()}")
            }
        });
    }
    private fun updateProduct(id : Int, product : ProductDto) {
        val apiService = REST.getRestEngine().create(IAPIServices::class.java)
        val result : Call<Product> = apiService.updateProduct(id, product);

        result.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product?>,
                response: Response<Product?>
            ) {
                if (response.isSuccessful){
                    Log.d("Success", response.body().toString())
                }else {
                    Log.d("Error", "Error in resource response")
                }
            }

            override fun onFailure(
                call: Call<Product?>,
                t: Throwable
            ) {
                Log.d("Error", "Error in response ${t.message.toString()}")
            }
        });
    }
    private fun deleteProduct(id : Int) {
        val apiService = REST.getRestEngine().create(IAPIServices::class.java)
        val result : Call<Int> = apiService.deleteProduct(id);

        result.enqueue(object : Callback<Int> {
            override fun onResponse(
                call: Call<Int?>,
                response: Response<Int?>
            ) {
                if (response.isSuccessful){
                    Log.d("Success", response.body().toString())
                }else {
                    Log.d("Error", "Error in resource response")
                }
            }

            override fun onFailure(
                call: Call<Int?>,
                t: Throwable
            ) {
                Log.d("Error", "Error in response ${t.message.toString()}")
            }
        });
    }
}