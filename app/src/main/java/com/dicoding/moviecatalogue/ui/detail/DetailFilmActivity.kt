package com.dicoding.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.remote.response.GenresItem
import com.dicoding.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.dicoding.moviecatalogue.databinding.ContentDetailFilmBinding
import com.dicoding.moviecatalogue.ui.viewmodel.ViewModelFactory
import com.dicoding.moviecatalogue.vo.Status

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV = "extra_tv"
    }

    private lateinit var activityDetailBinding: ActivityDetailFilmBinding
    private lateinit var detailContentDetailFilmBinding: ContentDetailFilmBinding

    private lateinit var viewModel: DetailFilmViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        detailContentDetailFilmBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailFilmViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            if (intent.hasExtra(EXTRA_MOVIE)) {
                val movieId = extras.getString(EXTRA_MOVIE)
                if (movieId != null) {
                    viewModel.setSelectedMovie(movieId)
                    activityDetailBinding.progressBar.visibility = View.VISIBLE
                    viewModel.movies.observe(this, { movieitem ->
                        if (movieitem != null) {
                            when (movieitem.status){
                                Status.LOADING -> activityDetailBinding.progressBar.visibility = View.VISIBLE
                                Status.SUCCESS -> {
                                    if (movieitem.data != null) {
                                        activityDetailBinding.progressBar.visibility = View.GONE
                                        populateMovie(movieitem.data)

                                    }
                                }
                                Status.ERROR -> {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "There's a problem", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }
            } else if (intent.hasExtra(EXTRA_TV)) {
                val tvId = extras.getString(EXTRA_TV)
                if (tvId != null) {
                    viewModel.setSelectedTvShow(tvId)
                    activityDetailBinding.progressBar.visibility = View.VISIBLE
                    viewModel.tvShow.observe(this, { tvshowitem ->
                        if (tvshowitem != null) {
                            when (tvshowitem.status) {
                                Status.LOADING -> activityDetailBinding.progressBar.visibility = View.VISIBLE
                                Status.SUCCESS -> {
                                    if (tvshowitem.data != null) {
                                        activityDetailBinding.progressBar.visibility = View.GONE
                                        populateTvShow(tvshowitem.data)
                                    }
                                }
                                Status.ERROR -> {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "There's a problem", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    })
                }
            }
        }
    }

    private fun populateMovie(film: MovieEntity) {
        with(detailContentDetailFilmBinding) {

            tvTitle.text = film.title
            tvGenre.text = film.genre
            tvOverview.text = film.overview
            tvRating.text = film.rating

            Glide.with(this@DetailFilmActivity)
                .load("https://image.tmdb.org/t/p/original/${film.poster}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgBigPoster)

            Glide.with(this@DetailFilmActivity)
                .load("https://image.tmdb.org/t/p/original/${film.poster}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .transform(RoundedCorners(20))
                .into(imgSmallPoster)

            supportActionBar?.title = film.title
        }
    }

    private fun populateTvShow(film: TvShowEntity) {
        with(detailContentDetailFilmBinding) {

            tvTitle.text = film.title
            tvGenre.text = film.genre
            tvOverview.text = film.overview
            tvRating.text = film.rating

            Glide.with(this@DetailFilmActivity)
                .load("https://image.tmdb.org/t/p/original/${film.poster}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgBigPoster)

            Glide.with(this@DetailFilmActivity)
                .load("https://image.tmdb.org/t/p/original/${film.poster}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .transform(RoundedCorners(20))
                .into(imgSmallPoster)

            supportActionBar?.title = film.title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu

        val extras = intent.extras
        if (extras != null) {
            if (intent.hasExtra(EXTRA_MOVIE)) {
                viewModel.movies.observe(this, { movieitem ->
                    if (movieitem != null) {
                        when (movieitem.status) {
                            Status.LOADING -> activityDetailBinding.progressBar.visibility =
                                View.VISIBLE
                            Status.SUCCESS -> {
                                if (movieitem.data != null) {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    val state = movieitem.data.favorite
                                    setFavoriteState(state)
                                }
                            }
                            Status.ERROR -> {
                                activityDetailBinding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "There's a problem", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            } else if (intent.hasExtra(EXTRA_TV)) {
                viewModel.tvShow.observe(this, { tvshowitem ->
                    if (tvshowitem != null) {
                        when (tvshowitem.status) {
                            Status.LOADING -> activityDetailBinding.progressBar.visibility =
                                View.VISIBLE
                            Status.SUCCESS -> {
                                if (tvshowitem.data != null) {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    val state = tvshowitem.data.favorite
                                    setFavoriteState(state)
                                }
                            }
                            Status.ERROR -> {
                                activityDetailBinding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "There's a problem", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                })
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val extras = intent.extras
        if (extras != null) {
            if (intent.hasExtra(EXTRA_MOVIE)) {
                if (item.itemId == R.id.action_favorite) {
                    viewModel.setFavMovie()
                }
            } else if (intent.hasExtra(EXTRA_TV)) {
                if (item.itemId == R.id.action_favorite) {
                    viewModel.setFavTvShow()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}