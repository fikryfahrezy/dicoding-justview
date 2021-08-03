package com.dicoding.justview.favorite.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.justview.detail.DetailActivity
import com.dicoding.justview.favorite.R
import com.dicoding.justview.favorite.databinding.FavoriteFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: FavoriteFragmentBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(layoutInflater)
        adapter = FavoriteAdapter(ClickListener { it, type ->
            when (type) {
                ClickType.SHORT -> navigateToDetail(it.viewId)
                ClickType.LONG ->
                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage(R.string.message_confirmation)
                        .setPositiveButton(getString(R.string.delete)) { _, _ ->
                            viewModel.setFavoriteView(it)
                            getData()
                        }
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.cancel()
                        }
                        .show()
            }
        })

        loadKoinModules(favoriteModule)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvViews.adapter = adapter
        binding.rvViews.setHasFixedSize(true)

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            getData(text.toString())
        }

        viewModel.favoriteViews.observe(viewLifecycleOwner, {
            binding.emptyView.root.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.updateData(it)
        })

        itemTouchHelper.attachToRecyclerView(binding.rvViews)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun navigateToDetail(identifier: String = "") {
        val uri = Uri.parse("viewdetail://justview")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.putExtra(DetailActivity.EXTRA_DATA, identifier)
        requireActivity().startActivity(intent)
    }

    private fun getData(search: String = "") {
        lifecycleScope.launch {
            viewModel.queryChannel.send(search)
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val data = adapter.getSwipedData(viewHolder.adapterPosition)

            MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.message_confirmation)
                .setPositiveButton(getString(R.string.delete)) { _, _ ->
                    viewModel.setFavoriteView(data)
                    getData()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                    adapter.notifyItemChanged(viewHolder.adapterPosition)
                }
                .show()
        }
    })
}