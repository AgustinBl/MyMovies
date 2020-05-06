package com.example.moviesapiretrofit.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapiretrofit.R
import com.example.moviesapiretrofit.data.api.ApiService
import com.example.moviesapiretrofit.data.model.Movie
import com.example.moviesapiretrofit.ui.adapter.MoviesListAdapter
import com.example.moviesapiretrofit.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesListActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy{
        ApiService.create()
    }

    private val movieAdapter =
        MoviesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsSwipeToRefresh()

        loadMovies()

        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = movieAdapter
    }

    private fun loadMovies(){
        val call = apiService.getMovies()
        call.enqueue(object: Callback<ArrayList<Movie>> {
            override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Movie>>,
                response: Response<ArrayList<Movie>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        rvMovies.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        movieAdapter.movies = it
                        movieAdapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    private fun itemsSwipeToRefresh(){
        itemsswipetorefresh.setColorSchemeResources(R.color.colorPrimary)
        itemsswipetorefresh.setOnRefreshListener{
            loadMovies()
            itemsswipetorefresh.isRefreshing = false
        }
    }
}
