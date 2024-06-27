package ru.practicum.android.diploma.presentation.filtration

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.FilterParams
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.ui.root.ActivityViewModel

class FiltrationFragment : Fragment() {

    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltrationViewModel by viewModel()
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()

        viewModel.filtersState.observe(viewLifecycleOwner) { setFiltersFromSharedPrefs(it) }
        activityViewModel.countryFilter.observe(viewLifecycleOwner) { setCountry(it) }
        activityViewModel.regionFilter.observe(viewLifecycleOwner) { setRegion(it) }
        activityViewModel.industry.observe(viewLifecycleOwner) { setIndustry(it) }
        binding.resetButton.setOnClickListener { clearFilters() }
        binding.applyButton.setOnClickListener { saveFIlters() }

        binding.filtrationBackImageView.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.workplaceContainer.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_filterPlaceFragment)
        }
        binding.filtrationIndustryEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }

        binding.filtrationIndustryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val industryName: String = (binding.filtrationIndustryEditText as TextView).text.toString()
                binding.industryArrowForward.isVisible = industryName.isEmpty()
                binding.industryClear.isVisible = industryName.isNotEmpty()
            }
        })

        binding.industryClear.setOnClickListener {
            activityViewModel.industry.value = null
        }
    }

    private fun setFiltersFromSharedPrefs(filters: FilterParams?) {
        if (filters != null) {
            if (activityViewModel.countryFilter.value == null) {
                activityViewModel.countryFilter.value = filters.country
            }
            if (activityViewModel.regionFilter.value == null) {
                activityViewModel.regionFilter.value = filters.region
            }
            if (activityViewModel.industry.value == null) {
                activityViewModel.industry.value = filters.industry
            }
            if (filters.salary != null) {
                binding.expectedSalaryEditText.setText(filters.salary.toString())
            }
            binding.noSalaryCheckBox.isChecked = filters.showOnlyWithSalary
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCountry(country: AreaDomain?) {
        if (country != null) {
            binding.filtrationWorkplaceEditText.setText(country.name)
        } else {
            binding.filtrationWorkplaceEditText.setText("")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setRegion(region: AreaDomain?) {
        val editText = binding.filtrationWorkplaceEditText.text.toString()
        if (editText.isEmpty() && region == null) {
            binding.filtrationWorkplaceEditText.setText("")
        } else if (editText.isEmpty()) {
            binding.filtrationWorkplaceEditText.setText(region!!.name)
        } else if (region == null) {
            binding.filtrationWorkplaceEditText.setText(editText)
        } else {
            binding.filtrationWorkplaceEditText.setText("$editText, ${region.name}")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setIndustry(industry: IndustryDomain?) {
        if (industry != null) {
            binding.filtrationIndustryEditText.setText(industry.name)
        } else {
            binding.filtrationIndustryEditText.setText("")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clearFilters() {
        activityViewModel.country.value = null
        activityViewModel.region.value = null
        activityViewModel.regionFilter.value = null
        activityViewModel.countryFilter.value = null
        activityViewModel.industry.value = null
        binding.expectedSalaryEditText.setText("")
        binding.noSalaryCheckBox.isChecked = false
        binding.applyButton.isVisible = false
        binding.resetButton.isVisible = false
    }

    private fun saveFIlters() {
        val filters = FilterParams(
            activityViewModel.countryFilter.value,
            activityViewModel.regionFilter.value,
            activityViewModel.industry.value,
            binding.expectedSalaryEditText.text.toString().toIntOrNull(),
            binding.noSalaryCheckBox.isChecked
        )
        viewModel.setData(filters)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
