package com.example.moviesapiretrofit.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapiretrofit.R
import com.example.moviesapiretrofit.data.model.Opinion
import kotlinx.android.synthetic.main.item_opinions.view.*

class OpinionAdapter: RecyclerView.Adapter<OpinionAdapter.ViewHolder>(){

    var opinions = ArrayList<Opinion>()


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        lateinit var context: Context

        fun bind(opinion: Opinion){
            with(itemView){
                tvUser.text = opinion.user
                tvComment.text = opinion.comment
                tvRt.text = "${opinion.rate} Pts"
                when(opinion.rate){
                    1,2,3,4 -> tvRt.setTextColor(Color.RED)
                    5,6,7 -> tvRt.setTextColor(Color.YELLOW)
                    8,9,10 -> tvRt.setTextColor(Color.GREEN)
                }
                setOnLongClickListener {
                    val builder = AlertDialog.Builder(context)

                    // Set the alert dialog title
                    builder.setTitle(tvUser.text)

                    // Display a message on alert dialog
                    builder.setMessage(tvComment.text)
                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Volver"){dialog, which ->

                    }

                    val dialog: AlertDialog = builder.create()

                    dialog.show()

                    true

                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_opinions,
                parent,
                false
            )
        )
    }

    override fun getItemCount()= opinions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(opinions[position])
    }



}