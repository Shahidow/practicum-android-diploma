package ru.practicum.android.diploma.presentation.filtration.place.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
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
    private val tag: String = "filter"

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
                is FilterCountryViewState.NoInternetConnection -> {
                    setSateNoInternetConnection()
                }

                is FilterCountryViewState.EmptyCountryList -> {
                    Log.e(tag, "somth wrong! :_(")
                    setStateEmptyCountryList()
                }

                is FilterCountryViewState.CountryList -> {
                    setStateCountryList(viewState.countryList)
                }

                else -> { // это пустой метод
                }
            }
        }
        binding.countryBackImageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setStateCountryList(countryList: List<AreaDomain>) {
        binding.apply {
            countryRecyclerView.isVisible = true
            countryPlaceholderLayout.isVisible = false
        }
        adapter?.setAreaList(countryList)

    }

    private fun setStateEmptyCountryList() {
        binding.apply {
            countryRecyclerView.isVisible = false
            countryPlaceholderLayout.isVisible = true
            countryPlaceholderMessage.text = this@FilterCountryFragment.getString(R.string.failed_to_get_list)
            Glide.with(this@FilterCountryFragment)
                .load(R.drawable.placeholder_carpet)
                .centerCrop()
                .into(countryPlaceholderImage)
        }
    }

    private fun setSateNoInternetConnection() {
        binding.apply {
            countryRecyclerView.isVisible = false
            countryPlaceholderLayout.isVisible = true
            countryPlaceholderMessage.text = this@FilterCountryFragment.getString(R.string.no_internet_connection)
            Glide.with(this@FilterCountryFragment)
                .load(R.drawable.placeholder_skull)
                .centerCrop()
                .into(countryPlaceholderImage)
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
