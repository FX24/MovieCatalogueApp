package com.dicoding.moviecatalogue.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.databinding.ItemsTvshowBinding
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK){

//    private val listTvShows = ArrayList<TvShowEntity>()
//
//    fun setTvShows(tvshows: List<TvShowEntity>?) {
//        if (tvshows == null) return
//        this.listTvShows.clear()
//        this.listTvShows.addAll(tvshows)
//    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

//    override fun getItemCount(): Int {
//        return listTvShows.size
//    }

    class TvShowViewHolder (private val binding: ItemsTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
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