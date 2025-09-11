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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NestjsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNestjsBinding;

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