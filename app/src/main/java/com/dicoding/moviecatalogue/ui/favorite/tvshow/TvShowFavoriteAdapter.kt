package com.dicoding.moviecatalogue.ui.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.databinding.ItemsTvshowBinding
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity
import com.dicoding.moviecatalogue.ui.tvshow.TvShowAdapter

class TvShowFavoriteAdapter : RecyclerView.Adapter<TvShowFavoriteAdapter.TvShowFavoriteViewHolder>(){
    private val listFavTvShows = ArrayList<TvShowEntity>()

    fun setTvShows(tvshows: List<TvShowEntity>?) {
        if (tvshows == null) return
        this.listFavTvShows.clear()
        this.listFavTvShows.addAll(tvshows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowFavoriteViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowFavoriteViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvShowFavoriteViewHolder, position: Int) {
        val movie = listFavTvShows[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return listFavTvShows.size
    }

    class TvShowFavoriteViewHolder (private val binding: ItemsTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (tvshow : TvShowEntity) {
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