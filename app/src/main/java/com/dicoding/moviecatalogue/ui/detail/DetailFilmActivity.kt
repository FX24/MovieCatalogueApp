package com.dicoding.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.source.local.entity.FilmDetailEntity
import com.dicoding.moviecatalogue.data.source.remote.response.GenresItem
import com.dicoding.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.dicoding.moviecatalogue.databinding.ContentDetailFilmBinding
import com.dicoding.moviecatalogue.ui.viewmodel.ViewModelFactory

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV = "extra_tv"
    }

    private lateinit var detailContentDetailFilmBinding: ContentDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        detailContentDetailFilmBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailFilmViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            if (intent.hasExtra(EXTRA_MOVIE)) {
                val movieId = extras.getString(EXTRA_MOVIE)
                if (movieId != null) {
                    viewModel.setSelectedMovie(movieId)
                    activityDetailBinding.progressBar.visibility = View.VISIBLE
                    viewModel.getMovieDetails().observe(this, {movieitem ->
                        activityDetailBinding.progressBar.visibility = View.GONE
                        populateFilm(movieitem)
                    })
                }
            }
            else if (intent.hasExtra(EXTRA_TV)){
                val tvId = extras.getString(EXTRA_TV)
                if (tvId != null) {
                    viewModel.setSelectedTvShow(tvId)
                    activityDetailBinding.progressBar.visibility = View.VISIBLE
                    viewModel.getTvShowDetails().observe(this, {tvshowitem ->
                        activityDetailBinding.progressBar.visibility = View.GONE
                        populateFilm(tvshowitem)
                    })
                }
            }
        }
    }

    private fun populateFilm(film: FilmDetailEntity) {
        with(detailContentDetailFilmBinding) {

            tvTitle.text = film.title
            tvGenre.text = getgenre(film.genre)
            tvOverview.text = film.overview
            tvRating.text = film.rating

            Glide.with(this@DetailFilmActivity)
                    .load("https://image.tmdb.org/t/p/original/${film.poster}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                    .into(imgBigPoster)

            Glide.with(this@DetailFilmActivity)
                    .load("https://image.tmdb.org/t/p/original/${film.poster}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                    .transform(RoundedCorners(20))
                    .into(imgSmallPoster)

            supportActionBar?.title = "${film.title}"
        }

    }

    private fun getgenre(genres: List<GenresItem>?): String {
        var genretxt = ""

        for (i in genres?.indices!!){
            if (i == 0) {
                genretxt = genres?.get(i)?.name
            } else {
                genretxt = "$genretxt, ${genres?.get(i)?.name}"
            }
        }
        return genretxt
    }
}