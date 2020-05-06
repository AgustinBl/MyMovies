package com.example.moviesapiretrofit.ui.main

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapiretrofit.R
import com.example.moviesapiretrofit.data.api.ApiService
import com.example.moviesapiretrofit.util.toast
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt


class OpinionActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val extras = intent.extras
        val cover = extras?.getString("cover")
        val id = extras?.getInt("id")

        btChooseRate.setOnClickListener {
            showDialog()
        }

        Glide.with(ivCover2.context)
            .load(cover)
            .transform(CenterCrop())
            .into(ivCover2)

        Glide.with(ivCover3.context)
            .load(cover)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(ivCover3)

        tvPost.setOnClickListener {
            when{
                etNameUser.text.toString().isEmpty() -> {
                    etNameUser.error = "Ingresar Nombre"
                }
                etRate.text.toString().isEmpty() -> {
                    etRate.error = "Escoge puntuación"
                }
                etComment.text.toString().isEmpty() -> {
                    etComment.error = "Escribe un comentario"
                }
                else ->
                    getOpinion(id!!)
            }

        }
    }


    private fun showDialog() {

        val array = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Elegir Puntaje.")

        // Set items form alert dialog
        builder.setItems(array) { _, which ->
            // Get the dialog selected item
            val selected = array[which]

            try {
                etRate.setText(selected)
                toast("Puntaje escogido: $selected.")
            } catch (e: IllegalArgumentException) {
                toast("$selected Puntaje no soportado.")
            }
        }

        // Create a new AlertDialog using builder object
        val dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun getOpinion(i: Int){
        tvPost.isClickable = false
        val user = etNameUser.text.toString()
        val rate = etRate.text.toString()
        val comment = etComment.text.toString()

        val call = apiService.storeOpinion(i,
            user, comment, rate.toInt()
        )
        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    toast("Opinion creada")
                    finish()
                } else {
                    toast("Ocurrió un error")
                    tvPost.isClickable = true
                }
            }

        })

    }


}

