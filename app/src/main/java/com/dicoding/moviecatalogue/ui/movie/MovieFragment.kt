package com.dicoding.moviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.databinding.FragmentMovieBinding
import com.dicoding.moviecatalogue.ui.viewmodel.ViewModelFactory
import com.dicoding.moviecatalogue.vo.Status


class MovieFragment : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()

            fragmentMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getMovies().observe(viewLifecycleOwner, {movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> fragmentMovieBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
//                            movieAdapter.setMovies(movies.data)
//                            movieAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "There's a problem", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })

            with(fragmentMovieBinding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}