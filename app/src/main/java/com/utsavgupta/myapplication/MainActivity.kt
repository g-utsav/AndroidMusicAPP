package com.utsavgupta.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

//    lateinit var txtView
//    var getBtn = findViewById<Button>(R.id.get)
//    var clearBtn = findViewById<Button>(R.id.clear)

    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.recyclerView)
        var getBtn = findViewById<Button>(R.id.get)
        var clearBtn = findViewById<Button>(R.id.clear)

        getBtn.setOnClickListener{
            getData()
        }

        clearBtn.setOnClickListener {

        }






    }

    fun getData(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface :: class.java )

        val retrofitData = retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val data = response.body()?.data!!
                Log.d("On Resp", response.toString())

                myAdapter = MyAdapter(this@MainActivity, data)
                myRecyclerView.adapter=myAdapter
                myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("on Fail ", ""+t.message)
            }
        })
    }

}