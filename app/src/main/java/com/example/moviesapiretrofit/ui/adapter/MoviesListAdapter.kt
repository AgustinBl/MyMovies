package com.example.moviesapiretrofit.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapiretrofit.R
import com.example.moviesapiretrofit.data.model.Movie
import com.example.moviesapiretrofit.ui.main.MovieActivity
import kotlinx.android.synthetic.main.item_movies.view.*

class MoviesListAdapter: RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    var movies = ArrayList<Movie>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(movie: Movie){
            with(itemView){
                setOnClickListener {

                    val intent = Intent(context,
                        MovieActivity::class.java)
                    intent.putExtra("title", movie.title );
                    intent.putExtra("description", movie.description );
                    intent.putExtra("cover", movie.cover)
                    intent.putExtra("id", movie.id)
                    context.startActivity(intent)
                }
                tvTitle.text = movie.title
                tvTitle.isSelected = true
                tvDescription.text = movie.description
                Glide.with(ivCover.context)
                    .load(movie.cover)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivCover)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movies, parent, false
            )
        )
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = movies[position]
        holder.bind(movie)
    }

}

