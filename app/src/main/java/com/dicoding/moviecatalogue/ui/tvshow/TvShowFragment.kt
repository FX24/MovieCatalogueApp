package com.dicoding.moviecatalogue.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.moviecatalogue.ui.viewmodel.ViewModelFactory
import com.dicoding.moviecatalogue.vo.Status


class TvShowFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentTvShowBinding = FragmentTvShowBinding.inflate(inflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            val adapter = TvShowAdapter()

            fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
            viewModel.getTvShow().observe(viewLifecycleOwner, {tvshow ->
                if (tvshow != null) {
                    when (tvshow.status) {
                        Status.LOADING -> fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentTvShowBinding.progressBar.visibility = View.GONE
                            adapter.submitList(tvshow.data)
//                            adapter.setTvShows(tvshow.data)
//                            adapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            fragmentTvShowBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "There's a problem", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })

            with(fragmentTvShowBinding.rvTvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

}