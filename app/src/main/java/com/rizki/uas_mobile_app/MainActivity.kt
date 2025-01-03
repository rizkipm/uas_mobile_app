package com.rizki.crud_berita_user

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rizki.crud_berita_user.adapter.BeritaAdapter
import com.rizki.crud_berita_user.model.ModelBerita
import com.rizki.crud_berita_user.model.ResponseBerita
import com.rizki.crud_berita_user.service.ApiClient
import com.rizki.uas_mobile_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var recycleView : RecyclerView
    private lateinit var call : Call<ResponseBerita>
    private lateinit var beritaAdapter : BeritaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.refresh_layout)
        recycleView = findViewById(R.id.rv_berita)

        beritaAdapter =  BeritaAdapter{modelBerita: ModelBerita ->  beritaOnClick(modelBerita)}

        recycleView.adapter = beritaAdapter
        recycleView.layoutManager = LinearLayoutManager(
            applicationContext, LinearLayoutManager.VERTICAL,
            false
        )

        swipeRefresh.setOnRefreshListener {
            getData()
        }
        getData()

    }

    private fun beritaOnClick(berita: ModelBerita){
        Toast.makeText(applicationContext, berita.judul, Toast.LENGTH_SHORT).show()
    }

    private fun  getData(){
        swipeRefresh.isRefreshing = true
        call = ApiClient.retrofit.getAllBerita()
        call.enqueue(object : Callback<ResponseBerita> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseBerita>,
                response: Response<ResponseBerita>
            ) {
                swipeRefresh.isRefreshing = false
                if(response.isSuccessful){
                    beritaAdapter.submitList(response.body()?.data)
                    beritaAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}