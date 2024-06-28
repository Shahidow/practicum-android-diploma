package ru.practicum.android.diploma.presentation.filtration.industry

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.presentation.filtration.industry.recyclerview.IndustryAdapter
import ru.practicum.android.diploma.ui.root.ActivityViewModel

class IndustryFragment : Fragment() {

    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!
    private var selectedIndustry: IndustryDomain? = null
    private val adapter = IndustryAdapter {
        selectedIndustry = it
        hideKeyboard()
        viewModel.selectIndustry()
    }
    private val viewModel: IndustryViewModel by viewModel()
    private var allIndustries: List<IndustryDomain>? = null
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.industryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.industryRecyclerView.adapter = adapter

        viewModel.getIndustries()

        viewModel.getState().observe(viewLifecycleOwner) {
            processIndustryState(it)
        }

        binding.industrySelectionBackImageView.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.industryInput.addTextChangedListener(textWatcherListener())

        binding.iconIndustryClear.setOnClickListener {
            binding.industryInput.setText("")
            binding.iconIndustryClear.isVisible = false
            binding.iconIndustrySearch.isVisible = true
            adapter.selectedIndustry = null
            adapter.industriesList.clear()
            adapter.notifyDataSetChanged()
        }

        binding.industryApplyButton.setOnClickListener {
            selectedIndustry?.let { activityViewModel.industry.value = it }
            findNavController().navigateUp()
        }
    }

    private fun textWatcherListener() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.iconIndustryClear.isVisible = binding.industryInput.text.toString().isNotEmpty()
            binding.iconIndustrySearch.isVisible = binding.industryInput.text.toString().isEmpty()

            if (binding.industryInput.text.toString().isNotEmpty()) {
                viewModel.searchDebounce(s.toString())
            } else {
                hideKeyboard()
                viewModel.getIndustries()
            }
        }

        override fun afterTextChanged(p0: Editable?) = Unit
    }

    private fun hideKeyboard() {
        val focusedView = requireActivity().currentFocus
        val inputMethodManager =
            requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
        }
    }

    private fun processIndustryState(data: FiltrationIndustryState) {
        binding.industryPlaceholderLayout.isVisible = data is FiltrationIndustryState.ServerError ||
            data is FiltrationIndustryState.InternetConnectionError ||
            data is FiltrationIndustryState.Empty
        binding.industryProgressBar.isVisible = data is FiltrationIndustryState.Loading
        binding.industryRecyclerView.isVisible = data is FiltrationIndustryState.Success ||
            data is FiltrationIndustryState.Selected
        binding.industryApplyButton.isVisible = data is FiltrationIndustryState.Selected

        when (data) {
            FiltrationIndustryState.InternetConnectionError -> {
                binding.industryPlaceholderImage.setImageResource(R.drawable.placeholder_skull)
                binding.industryPlaceholderMessage.setText(R.string.no_internet_connection)
            }

            FiltrationIndustryState.ServerError -> {
                binding.industryPlaceholderMessage.setText(R.string.server_error)
                binding.industryPlaceholderImage.setImageResource(R.drawable.placeholder_server_error_towel)
            }

            is FiltrationIndustryState.Success -> {
                allIndustries = data.industriesList
                adapter.industriesList.clear()
                adapter.industriesList = data.industriesList.toMutableList()
                adapter.notifyDataSetChanged()
            }

            FiltrationIndustryState.Empty -> {
                binding.industryPlaceholderMessage.setText(R.string.no_industry)
                binding.industryPlaceholderImage.setImageResource(R.drawable.placeholder_cat)
                hideKeyboard()
            }

            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getIndustries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
