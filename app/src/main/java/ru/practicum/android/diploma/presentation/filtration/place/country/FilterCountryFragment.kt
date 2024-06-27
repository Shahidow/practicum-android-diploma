package ru.practicum.android.diploma.presentation.filtration.place.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterCountryBinding
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.presentation.filtration.place.FilterPlaceAdapter
import ru.practicum.android.diploma.ui.root.ActivityViewModel

class FilterCountryFragment : Fragment() {

    private var _binding: FragmentFilterCountryBinding? = null
    private val binding get() = _binding!!
    private val filterCountryViewModel by viewModel<FilterCountryViewModel>()
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private var adapter: FilterPlaceAdapter? = null
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
        filterCountryViewModel.getCountryList()
        adapter = FilterPlaceAdapter(onItemRegionClick = { area -> onItemRegionClick(area) })
        binding.countryRecyclerView.adapter = adapter

        filterCountryViewModel.filtrationParams.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is FilterCountryViewState.EmptyCountryList -> {
                    Log.e(tag, "somth wrong! :_(")
                }

                is FilterCountryViewState.CountryList -> {
                    adapter!!.setAreaList(viewState.countryList)
                }

                else -> { // это пустой метод
                }
            }
        }
    }

    private fun onItemRegionClick(area: AreaDomain) {
        activityViewModel.country.value = area
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
