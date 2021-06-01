package com.dicoding.moviecatalogue.ui.favorite.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.dicoding.moviecatalogue.ui.viewmodel.ViewModelFactory


class MovieFavoriteFragment : Fragment() {

    private lateinit var fragmentFavoriteMovieBinding: FragmentFavoriteMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentFavoriteMovieBinding =
            FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]

            val movieFavoriteAdapter = MovieFavoriteAdapter()

            fragmentFavoriteMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getfavMovies().observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    fragmentFavoriteMovieBinding.progressBar.visibility = View.GONE
                    movieFavoriteAdapter.submitList(movies)
                }
            })

            with(fragmentFavoriteMovieBinding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = movieFavoriteAdapter
            }
        }
    }
}