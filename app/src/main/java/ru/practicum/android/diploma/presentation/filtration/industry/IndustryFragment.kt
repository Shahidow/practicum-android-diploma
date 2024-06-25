package ru.practicum.android.diploma.presentation.filtration.industry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.presentation.filtration.industry.recyclerview.IndustryAdapter

class IndustryFragment : Fragment(), IndustryAdapter.ItemIndustryClickInterface {

    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IndustryViewModel by viewModel<IndustryViewModel>()
    private lateinit var adapter: IndustryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IndustryAdapter(this)
        binding.industryRecyclerView.adapter = adapter

        viewModel.industries.observe(viewLifecycleOwner) { industries ->
            adapter.setIndustryList(industries)
        }

        viewModel.selectedIndustry.observe(viewLifecycleOwner) { selectedIndustry ->
            adapter.setSelectedIndustry(selectedIndustry)
            binding.industryApplyButton.isEnabled = selectedIndustry != null
        }

        binding.industryApplyButton.setOnClickListener {
            viewModel.selectedIndustry.value?.let { industry ->
                viewModel.setSelectedIndustry(industry)
                findNavController().popBackStack()
            }
        }

        binding.industryInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                viewModel.filterIndustries(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.industrySelectionBackImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onItemIndustryClick(industry: IndustryDomain) {
        val selectedIndustry = viewModel.selectedIndustry.value
        if (selectedIndustry?.name == industry.name) {
            // Если та же самая отрасль нажата снова, убираем выбор
            viewModel.setSelectedIndustry(null)
        } else {
            // Set the selected industry
            viewModel.setSelectedIndustry(industry)
        }
    }
}
