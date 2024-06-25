package ru.practicum.android.diploma.presentation.filtration.place.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterCountryBinding

class FilterCountryFragment : Fragment() {

    private var _binding: FragmentFilterCountryBinding? = null
    private val binding get() = _binding!!

    private val filterCountryViewModel by viewModel<FilterCountryViewModel>()

    // private var filterCountryAdapter: FilterCountryAdapter? = null
    private val tag: String = "filter_country"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewState()
        // filterCountryViewModel.getCountryList()

        // filterCountryAdapter = FilterCountryAdapter()
        // filterCountryAdapter?.setInItemCountryClickListener(this)
        // binding.countryRecyclerView.adapter = filterCountryAdapter
    }

    private fun observeViewState() {
        filterCountryViewModel.filtrationParams.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is FilterCountryViewState.EmptyCountryList -> {
                    Log.e(tag, "somth wrong! :_(")
                }

                is FilterCountryViewState.CountryList -> {
                    // filterCountryAdapter.setCountryList(viewState.countryList)
                }

                else -> { // это пустой метод}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
