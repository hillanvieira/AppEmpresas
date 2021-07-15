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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.hillan.appempresas.R
import br.com.hillan.appempresas.databinding.FragmentHomeBinding
import br.com.hillan.appempresas.model.Enterprise
import br.com.hillan.appempresas.ui.MainViewModel
import br.com.hillan.appempresas.ui.adaper.EnterpriseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    val listEnterprise: MutableList<Enterprise> = mutableListOf()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.loadEnterprisesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSearchView()
        configureHomeHint()
        handleSearch()
    }

    private fun handleSearch() {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.toString().isNotEmpty()) {
                    listEnterprise.clear()
                    for (search in mainViewModel.enterprises.value!!) {
                        if (search.enterpriseName.contains(newText, true) && newText != "") {
                            listEnterprise.add(search)
                        }
                    }
                    configureRecyclerView(listEnterprise)
                } else {
                    configureRecyclerView(emptyList())
                }
                return false
            }
        })
    }

    private fun configureHomeHint() {
        if (listEnterprise.isEmpty()) {
            binding.mainText.visibility = VISIBLE
        } else {
            binding.mainText.visibility = INVISIBLE
        }

        searchView.setOnSearchClickListener {
            binding.mainText.visibility = INVISIBLE
        }
        searchView.setOnCloseListener {
            binding.mainText.visibility = VISIBLE
            false
        }
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
            br.com.hillan.appempresas.ui.fragments.HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                enterprise.enterpriseName,
                enterprise.description,
                enterprise.photo
            )
        )
    }
}