package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.databinding.FragmentItemDetailBinding
import com.example.demo.db.ItemResponse
import com.example.demo.db.Rating
import com.example.demo.utils.Constants
import com.example.demo.ui.viewmodel.FavItemViewModel
import com.google.gson.Gson

class FragmentItemDetailDialog : DialogFragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    private var itemObj: ItemResponse? = null
    private lateinit var favItemViewModel: FavItemViewModel

    companion object {
        const val TAG = "FragmentDetailsDialog"
        fun newInstance(bundle: Bundle): FragmentItemDetailDialog {
            val fragment = FragmentItemDetailDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favItemViewModel = ViewModelProvider(this)[FavItemViewModel::class.java]
        setupView()
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    private fun setupView() {
        val jsonItem = arguments?.getString(Constants.ITEM_DATA)
        val isFromFavorite = arguments?.getBoolean(Constants.IS_FROM_FAVORITE)
        if(isFromFavorite == true) binding.ivAddToFav.visibility = GONE
        jsonItem?.let { it ->
            itemObj = Gson().fromJson(it, ItemResponse::class.java)
            itemObj?.let { item ->
                binding.tvProductTittle.text = item.title
                binding.tvDesc.text = item.description
                binding.tvPrice.text =
                    context?.resources?.getString(R.string.rupee_symbol).plus(" ")
                        .plus(item.price.toString())
                binding.tvRating.text = item.rating?.rate.toString()
                binding.tvRatingCount.text = item.rating?.count.toString()
                binding.overallRating.rating = item.rating?.rate?.toFloat()!!
                context?.let {
                    Glide.with(it)
                        .load(item.image)
                        .into(binding.imageView)
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.ivAddToFav.setOnClickListener {
            itemObj?.let {
                context?.let { context ->
                    favItemViewModel.insert(context, it)
                    Toast.makeText(context, context.resources.getString(R.string.added_to_favorites),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ivCloseDialog.setOnClickListener{
            dialog?.cancel()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}