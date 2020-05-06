package com.example.moviesapiretrofit.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviesapiretrofit.ui.adapter.OpinionAdapter
import com.example.moviesapiretrofit.R
import com.example.moviesapiretrofit.data.api.ApiService
import com.example.moviesapiretrofit.data.model.Opinion
import com.example.moviesapiretrofit.util.toast
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy{
        ApiService.create()
    }

    private val opinionAdapter =
        OpinionAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val extras = intent.extras
        val title = extras?.getString("title")
        val description = extras?.getString("description")
        val coverImage = extras?.getString("cover")
        val id = extras?.getInt("id")

        tvTitle1.text = title
        tvTitle1.isSelected = true
        tvDescription1.text = description
        Glide.with(ivCover1.context)
            .load(coverImage)
            .transform(CenterCrop(), RoundedCorners(25))
            .into(ivCover1)



        tvDescription1.setOnTouchListener { view, motionEvent ->
            cardView1.parent.requestDisallowInterceptTouchEvent(true)
            false }

        tvDescription1.movementMethod = ScrollingMovementMethod()

            loadOpinions(id!!)
            rvOpinions.layoutManager = LinearLayoutManager(this)
            rvOpinions.adapter = opinionAdapter

        itemsswipetorefresh1?.setColorSchemeResources(R.color.colorPrimary)
        itemsswipetorefresh1?.setOnRefreshListener{
            loadOpinions(id!!)
            itemsswipetorefresh1?.isRefreshing = false
        }

        btComment.setOnClickListener {
            createOpinion(coverImage,id)
        }

    }

    private fun loadOpinions(a: Int){
        val call = apiService.getOpinionsById(a)
        call.enqueue(object: retrofit2.Callback<ArrayList<Opinion>> {
            override fun onFailure(call: Call<ArrayList<Opinion>>, t: Throwable) {
                toast(t.localizedMessage)
                Log.e("OkHttp", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Opinion>>,
                response: Response<ArrayList<Opinion>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        rvOpinions.visibility = View.VISIBLE
                        progressBar1!!.visibility = View.GONE
                        opinionAdapter.opinions = it
                        opinionAdapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    private fun createOpinion(i: String?,b: Int?){
        val intent = Intent(this,
            OpinionActivity::class.java)
        intent.putExtra("cover", i )
        intent.putExtra("id", b )
        startActivity(intent)
    }

}
