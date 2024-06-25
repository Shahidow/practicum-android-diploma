package ru.practicum.android.diploma.presentation.filtration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding

class FiltrationFragment : Fragment() {

    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltrationViewModel by viewModels()

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
            // переход к экрану выбора места работы
        }

        binding.industryContainer.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }

        viewModel.industry.observe(viewLifecycleOwner) { industry ->
            binding.filtrationIndustryText.text = industry?.name ?: ""
        }

        viewModel.workplace.observe(viewLifecycleOwner) { workplace ->
            binding.filtrationWorkplaceEditText.setText(workplace ?: "")
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetFilters()
        }

        binding.applyButton.setOnClickListener {
            // сохранение настроек и применение фильтрации
        }
    }
}

