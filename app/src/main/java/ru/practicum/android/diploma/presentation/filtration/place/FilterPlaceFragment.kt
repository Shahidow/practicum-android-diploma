package ru.practicum.android.diploma.presentation.filtration.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceBinding
import ru.practicum.android.diploma.ui.root.ActivityViewModel

class FilterPlaceFragment : Fragment() {

    private var _binding: FragmentFilterPlaceBinding? = null
    private val binding get() = _binding!!
    private val filterPlaceViewModel by viewModel<FilterPlaceViewModel>()
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFilterPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        binding.workplaceApplyButton.setOnClickListener {
            activityViewModel.countryFilter.value = activityViewModel.country.value
            if (activityViewModel.region.value != null) {
                activityViewModel.regionFilter.value = activityViewModel.region.value
            }
            findNavController().navigateUp()
        }

        binding.countryClearImageView.setOnClickListener {
            activityViewModel.country.value = null
            activityViewModel.region.value = null
            binding.workplaceApplyButton.isVisible = false
        }

        binding.regionClearImageView.setOnClickListener {
            activityViewModel.region.value = null
        }

        binding.countrySelectionEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceFragment_to_filterCountryFragment)
        }

        binding.regionEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceFragment_to_filterRegionFragment)
        }

        binding.workplaceBackImageView.setOnClickListener {
            activityViewModel.country.value = activityViewModel.countryFilter.value
            activityViewModel.region.value = activityViewModel.regionFilter.value
            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        activityViewModel.country.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                onCountrySelected(country.name)
            } else {
                onCountryCleared()
            }
        }

        activityViewModel.region.observe(viewLifecycleOwner) { area ->
            if (area != null) {
                onRegionSelected(area.name)
            } else {
                onRegionCleared()
            }
        }
    }

    private fun onCountrySelected(countryName: String) {
        binding.countrySelectionEditText.setText(countryName)
        binding.workplaceApplyButton.isVisible = true
        binding.countryClearImageView.isVisible = true
        binding.countrySelectionArrowForward.isVisible = false
    }

    @SuppressLint("SetTextI18n")
    private fun onCountryCleared() {
        binding.countrySelectionEditText.setText("")
        binding.workplaceApplyButton.isVisible = false
        binding.countryClearImageView.isVisible = false
        binding.countrySelectionArrowForward.isVisible = true
    }

    private fun onRegionSelected(regionName: String) {
        binding.regionEditText.setText(regionName)
        binding.workplaceApplyButton.isVisible = true
        binding.regionArrowForward.isVisible = false
        binding.regionClearImageView.isVisible = true

    }

    @SuppressLint("SetTextI18n")
    private fun onRegionCleared() {
        binding.regionEditText.setText("")
        binding.regionArrowForward.isVisible = true
        binding.regionClearImageView.isVisible = false
    }
}
