package com.dicoding.moviecatalogue.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.databinding.ItemsMovieBinding
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovie = ArrayList<TvShowEntity>()

    fun setMovies(movies: List<TvShowEntity>?) {
        if (movies == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movies)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemsMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    class MovieViewHolder (private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder (binding.root){
        fun bind (movie: TvShowEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvReleaseDate.text = movie.release_date

                Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/original/${movie.poster}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
                        .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_MOVIE, movie.id.toString())
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}