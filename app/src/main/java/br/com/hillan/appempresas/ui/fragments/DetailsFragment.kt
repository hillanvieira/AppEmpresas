package br.com.hillan.appempresas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import br.com.hillan.appempresas.R
import br.com.hillan.appempresas.databinding.FragmentDetailsBinding
import br.com.hillan.appempresas.ui.MainViewModel
import br.com.hillan.appempresas.utils.ENTERPRISES_IMAGE_URL
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: br.com.hillan.appempresas.ui.fragments.DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun configureView() {
        binding.detailToolbar.title = args.enterpriseName
        binding.textViewDetail.text = args.enterpriseDetails
        Glide.with(this)
            .load("$ENTERPRISES_IMAGE_URL${args.imageUrl}")
            .placeholder(R.drawable.logo_home)
            .error(R.drawable.logo_home)
            .centerCrop()
            .into(binding.imageViewDetail)
    }
}