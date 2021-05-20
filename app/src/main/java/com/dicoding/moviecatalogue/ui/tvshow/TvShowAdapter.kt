package com.dicoding.moviecatalogue.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.FilmEntity
import com.dicoding.moviecatalogue.databinding.ItemsTvshowBinding
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>(){

    private val listTvShows = ArrayList<FilmEntity>()

    fun setTvShows(tvshows: List<FilmEntity>?) {
        if (tvshows == null) return
        this.listTvShows.clear()
        this.listTvShows.addAll(tvshows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val movie = listTvShows[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

    class TvShowViewHolder (private val binding: ItemsTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (tvshow : FilmEntity) {
            with(binding) {
                tvTitle.text = tvshow.title
                tvReleaseDate.text = tvshow.release_date

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/${tvshow.poster}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
                        .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_TV, tvshow.id.toString())
                    itemView.context.startActivity(intent)
                }
            }

        }
    }
}