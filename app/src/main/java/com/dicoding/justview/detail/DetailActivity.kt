package com.dicoding.justview.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.dicoding.justview.R
import com.dicoding.justview.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail_label)

        val argsId = args.identifier
        val extraId = intent.getStringExtra(EXTRA_DATA)
        val identifier =
            if (argsId.isEmpty()) if (!extraId.isNullOrEmpty()) extraId else "" else argsId
        init(identifier)
    }

    private fun init(identifier: String = "") {
        viewModel.getOneView(identifier).observe(this, { data ->
            data?.let {
                binding.property = data

                var favStatus = it.isFavorite
                changeFavButton(favStatus)

                binding.favButton.setOnClickListener {
                    favStatus = !favStatus
                    changeFavButton(favStatus)
                    viewModel.setFavoriteView(data, favStatus)
                }
            }
        })
    }

    private fun changeFavButton(isFavorite: Boolean) {
        if (isFavorite)
            binding.favButton.text = resources.getString(R.string.not_favorite_button)
        else
            binding.favButton.text = resources.getString(R.string.do_favorite_button)
    }
}