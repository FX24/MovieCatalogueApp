package com.dicoding.moviecatalogue.ui.favorite.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.moviecatalogue.databinding.FragmentTvShowFavoriteBinding
import com.dicoding.moviecatalogue.ui.tvshow.TvShowAdapter
import com.dicoding.moviecatalogue.ui.tvshow.TvShowViewModel
import com.dicoding.moviecatalogue.ui.viewmodel.ViewModelFactory
import com.dicoding.moviecatalogue.vo.Status

class TvShowFavoriteFragment : Fragment() {

    private lateinit var fragmentTvShowFavoriteBinding: FragmentTvShowFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentTvShowFavoriteBinding = FragmentTvShowFavoriteBinding.inflate(inflater, container, false)
        return fragmentTvShowFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowFavoriteViewModel::class.java]

            val adapter = TvShowFavoriteAdapter()

            fragmentTvShowFavoriteBinding.progressBar.visibility = View.VISIBLE
            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner, {tvshow ->
                if (tvshow != null) {
                    fragmentTvShowFavoriteBinding.progressBar.visibility = View.GONE
                    adapter.setTvShows(tvshow)
                    adapter.notifyDataSetChanged()
                }

            })

            with(fragmentTvShowFavoriteBinding.rvTvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }
}