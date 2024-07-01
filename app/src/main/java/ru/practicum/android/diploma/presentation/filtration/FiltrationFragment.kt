package ru.practicum.android.diploma.presentation.filtration

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        observeChanges()
        binding.resetButton.setOnClickListener { clearFilters() }
        binding.applyButton.setOnClickListener { saveFilters() }

        binding.filtrationBackImageView.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.filtrationWorkplaceEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_filterPlaceFragment)
        }
        binding.filtrationIndustryEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }

        binding.filtrationIndustryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.industryArrowForward.isVisible = s.isNullOrEmpty()
                binding.industryClear.isVisible = !s.isNullOrEmpty()
            }
        })

        binding.industryClear.setOnClickListener {
            activityViewModel.industry.value = null
        }

        binding.filtrationWorkplaceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.workplaceArrowForward.isVisible = s.isNullOrEmpty()
                binding.workplaceClear.isVisible = !s.isNullOrEmpty()
            }
        })

        binding.workplaceClear.setOnClickListener {
            activityViewModel.countryFilter.value = null
            activityViewModel.regionFilter.value = null
            activityViewModel.country.value = null
            activityViewModel.region.value = null
        }

        binding.expectedSalaryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.expectedSalaryEditText.isFocused) {
                    binding.salaryClearImageView.isVisible = !s.isNullOrEmpty()
                } else {
                    binding.salaryClearImageView.isVisible = false
                }
                compareFilters()
                resetButtonVisibility()
            }
        })

        binding.expectedSalaryEditText.setOnFocusChangeListener { _, hashFocus ->
            if(hashFocus && binding.expectedSalaryEditText.text.toString().isNotEmpty()) {
                binding.salaryClearImageView.isVisible = true
            } else {
                binding.salaryClearImageView.isVisible = false
            }
        }

        binding.salaryClearImageView.setOnClickListener {
            binding.expectedSalaryEditText.setText("")
        }

        binding.noSalaryCheckBox.setOnCheckedChangeListener { _, _ ->
            compareFilters()
            resetButtonVisibility()
        }
    }

    private fun observeChanges() {
        viewModel.getData()
        viewModel.resetButtonState.observe(viewLifecycleOwner) { binding.resetButton.isVisible = !it }
        viewModel.comparisonState.observe(viewLifecycleOwner) { binding.applyButton.isVisible = !it }
        viewModel.filtersState.observe(viewLifecycleOwner) { setFiltersFromSharedPrefs(it) }
        activityViewModel.countryFilter.observe(viewLifecycleOwner) { setCountry(it) }
        activityViewModel.regionFilter.observe(viewLifecycleOwner) { setRegion(it) }
        activityViewModel.industry.observe(viewLifecycleOwner) { setIndustry(it) }
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

    private fun resetButtonVisibility() {
        val filters = FilterParams(
            activityViewModel.countryFilter.value,
            activityViewModel.regionFilter.value,
            activityViewModel.industry.value,
            binding.expectedSalaryEditText.text.toString().toIntOrNull(),
            binding.noSalaryCheckBox.isChecked
        )
        viewModel.resetButtonVisibility(filters)
    }

    private fun compareFilters() {
        val filters = FilterParams(
            activityViewModel.countryFilter.value,
            activityViewModel.regionFilter.value,
            activityViewModel.industry.value,
            binding.expectedSalaryEditText.text.toString().toIntOrNull(),
            binding.noSalaryCheckBox.isChecked
        )
        viewModel.compareFilters(filters)
    }

    @SuppressLint("SetTextI18n")
    private fun setCountry(country: AreaDomain?) {
        if (country != null) {
            binding.filtrationWorkplaceEditText.setText(country.name)
        } else {
            binding.filtrationWorkplaceEditText.setText("")
        }
        compareFilters()
        resetButtonVisibility()
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
        compareFilters()
        resetButtonVisibility()
    }

    @SuppressLint("SetTextI18n")
    private fun setIndustry(industry: IndustryDomain?) {
        if (industry != null) {
            binding.filtrationIndustryEditText.setText(industry.name)
        } else {
            binding.filtrationIndustryEditText.setText("")
        }
        compareFilters()
        resetButtonVisibility()
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
        compareFilters()
        resetButtonVisibility()
    }

    private fun saveFilters() {
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
