package br.com.hillan.appempresas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.hillan.appempresas.R
import br.com.hillan.appempresas.databinding.FragmentLoginBinding
import br.com.hillan.appempresas.ui.MainViewModel
import br.com.hillan.appempresas.utils.ERROR_UNKNOWN
import br.com.hillan.appempresas.utils.ResultCall
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var errorShow: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureDefaultView()
        setLoginObserver()
        setListeners()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setListeners() {
        binding.editTextTextEmailAddress.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (errorShow) {
                    errorShow = false
                    configureDefaultView()
                }
                binding.editTextTextEmailAddress.performClick()
                return false
            }
        })

        binding.LoginButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email: String = binding.editTextTextEmailAddress.text.toString()
                val password: String = binding.editTextTextPassword.text.toString()

                mainViewModel.login(email, password)

            }
        })
    }

    private fun setLoginObserver() {
        mainViewModel.tryingToLogin.observe(viewLifecycleOwner, { resultGetAccess ->

            when (resultGetAccess) {
                is ResultCall.Error -> {
                    hideProgress()
                    configureErrorLogin(resultGetAccess.error)
                }
                is ResultCall.Loading -> {
                    showProgress()
                }
                is ResultCall.Success -> {
                    mainViewModel.loadEnterprisesList()
                    mainViewModel.setTryLoginUsed()
                    hideProgress()
                    navToHome()
                }
                is ResultCall.Used -> {
                    Unit
                }
            }

        })
    }

    private fun configureErrorLogin(message: String = ERROR_UNKNOWN) {

        binding.editTextTextEmailAddress.setErrorView()
        binding.editTextTextPassword.setErrorView()
        binding.errorMessage.text = message
        binding.errorMessage.visibility = View.VISIBLE

        binding.LoginButton.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.steel_grey,
                null
            )
        )
        errorShow = true
    }

    private fun showProgress() {
        binding.progressBackground.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBackground.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun configureDefaultView() {
        binding.editTextTextEmailAddress.setDefaultView()
        binding.editTextTextPassword.setDefaultView()

        binding.errorMessage.visibility = View.GONE
        binding.LoginButton.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.blue_pool,
                null
            )
        )
    }

    private fun navToHome() {
        val directions =
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(directions)
    }

}