package br.com.hillan.appempresas.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.hillan.appempresas.R
import br.com.hillan.appempresas.databinding.FragmentHomeBinding
import br.com.hillan.appempresas.model.Enterprise
import br.com.hillan.appempresas.ui.MainViewModel
import br.com.hillan.appempresas.ui.adaper.EnterpriseListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val listEnterprise: MutableList<Enterprise> = mutableListOf()
    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSearchView()
        handleSearch()
        configureHomeMessageHint()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun configureHomeMessageHint() {
            binding.mainText.visibility = INVISIBLE
            //TODO()
    }

    private fun handleSearch() {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainViewModel.enterprises.observe(viewLifecycleOwner) {
                        lifecycleScope.launch {
                            val jobUpdateListOnSearchQuery =
                                async { updateListOnSearchQuery(it, newText) }

                            jobUpdateListOnSearchQuery.await()
                            configureRecyclerView(listEnterprise)
                        }
                    }
                }

                return false
            }
        })
    }

    private suspend fun updateListOnSearchQuery(list: List<Enterprise>, query: String): Job =
        lifecycleScope.launch(Dispatchers.Default) {
            listEnterprise.clear()

            for (search in list) {
                if (search.enterpriseName.contains(
                        query,
                        true
                    ) && query != ""
                ) {
                    listEnterprise.add(search)
                }
            }
            return@launch
        }


    private fun configureSearchView() {
        val searchItem = binding.searchToolbar.menu.findItem(R.id.app_bar_search)
        searchView = searchItem.actionView as SearchView
        val searchHint = getString(R.string.searchHint)
        searchView.queryHint = searchHint

        val iconSearchView: ImageView = searchView.findViewById(
            resources.getIdentifier(
                "android:id/search_button",
                null,
                null
            )
        )
        iconSearchView.setImageResource(R.drawable.ic_search)

        val textSearchView: TextView = searchView.findViewById(
            resources.getIdentifier(
                "android:id/search_src_text",
                null,
                null
            )
        )
        textSearchView.setTextColor(Color.WHITE)
    }

    fun configureRecyclerView(enterprises: List<Enterprise>) {
        val recyclerView: RecyclerView = binding.searchRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = EnterpriseListAdapter(enterprises, requireContext())
        {
            navToDetails(it)
        }


    }

    private fun navToDetails(enterprise: Enterprise) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                enterprise.enterpriseName,
                enterprise.description,
                enterprise.photo
            )
        )
    }
}