package com.dicoding.justview.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.justview.R
import com.dicoding.justview.core.data.Resource
import com.dicoding.justview.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HomeAdapter()
        adapter.onItemClick = {
            navigateToDetail(it.viewId)
        }

        binding.rvViews.adapter = adapter
        binding.rvViews.setHasFixedSize(true)
        binding.favoriteButton.setOnClickListener {
            navigateToFavorite()
        }

        viewModel.allView.observe(viewLifecycleOwner, {
            if (it != null)
                when (it) {
                    is Resource.Error -> {
                        binding.rvViews.visibility = View.GONE
                        binding.statusImage.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    is Resource.Loading -> {
                        binding.rvViews.visibility = View.GONE
                        binding.statusImage.visibility = View.GONE
                        binding.progress.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.rvViews.visibility = View.VISIBLE
                        binding.statusImage.visibility = View.GONE
                        binding.progress.visibility = View.GONE
                        adapter.submitList(it.data)
                    }
                }
        })
    }

    private fun navigateToDetail(identifier: String = "") {
        val nav = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
        nav.identifier = identifier
        findNavController().navigate(nav)
    }

    private fun navigateToFavorite() {
        try {
            val uri = Uri.parse("favoriteviews://justview")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireActivity().startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "404", Toast.LENGTH_SHORT).show()
        }
    }
}