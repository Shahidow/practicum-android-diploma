package ru.practicum.android.diploma.presentation.filtration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class FiltrationFragment : Fragment() {

    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filtrationBackImageView.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.workplaceContainer.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_filterPlaceFragment)
        }

        binding.industryContainer.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }

        binding.filtrationIndustryEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }

        viewModel.industry.observe(viewLifecycleOwner) { industry ->
            setIndustryValue(industry)
        }

        viewModel.loadData()

        viewModel.workplace.observe(viewLifecycleOwner) { workplace ->
            binding.filtrationWorkplaceEditText.text = (workplace ?: "") as Editable?
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetFilters()
        }

        binding.applyButton.setOnClickListener {
            // сохранение настроек и применение фильтрации
        }

        binding.filtrationIndustryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val industryName: String = (binding.filtrationIndustryEditText as TextView).text.toString()
                binding.industryArrowForward.isVisible = industryName.isEmpty()
                binding.industryClear.isVisible = industryName.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.industryClear.setOnClickListener {
            (binding.filtrationIndustryEditText as TextView).text = ""
        }
    }

    private fun setIndustryValue(value: IndustryDomain?) {
        if (value != null) {
            (binding.filtrationIndustryEditText as TextView).text = value.name
        }
    }
}
