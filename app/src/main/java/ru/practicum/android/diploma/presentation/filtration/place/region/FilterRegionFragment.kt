package ru.practicum.android.diploma.presentation.filtration.place.region

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterRegionBinding
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.presentation.filtration.place.FilterPlaceAdapter
import ru.practicum.android.diploma.ui.root.ActivityViewModel

class FilterRegionFragment : Fragment() {

    private var _binding: FragmentFilterRegionBinding? = null
    private val binding get() = _binding!!
    private val filterRegionViewModel by viewModel<FilterRegionViewModel>()
    private var adapter: FilterPlaceAdapter? = null
    private val activityViewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun Fragment.hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity?.currentFocus ?: View(requireContext())
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentId: String? = activityViewModel.country.value?.id
        observeViewState()
        prepareView()
        filterRegionViewModel.getRegions(parentId)
        adapter = FilterPlaceAdapter(onItemRegionClick = { area -> onItemRegionClick(area) })
        binding.regionRecyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun prepareView() {
        binding.regionSelectionBackImageView.setOnClickListener { findNavController().navigateUp() }
        binding.iconRegionClear.setOnClickListener {
            binding.regionInput.setText("")
        }
        binding.regionInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iconRegionSearch.isVisible = s.isNullOrEmpty()
                binding.iconRegionClear.isVisible = !s.isNullOrEmpty()
                filterRegionViewModel.searchRegionInList(s.toString())
            }
        })
    }

    private fun observeViewState() {
        filterRegionViewModel.filterRegionState.observe(viewLifecycleOwner) {
            when (it) {
                is FilterRegionViewState.NoInternetConnection -> setStateNoInternetConnection()
                is FilterRegionViewState.NotFoundRegionInRegionInList -> setStateNotFoundRegionInList()
                is FilterRegionViewState.ListOfRegionIsEmpty -> setStateListOfRegionIsEmpty()
                is FilterRegionViewState.ListOfRegion -> setStateListOfRegion(it.listOfRegion)
            }
        }
    }

    private fun setStateListOfRegion(listOfRegion: List<AreaDomain>) {
        binding.apply {
            regionRecyclerView.isVisible = true
            regionPlaceholderLayout.isVisible = false
        }
        adapter?.setAreaList(listOfRegion)
    }

    private fun setStateListOfRegionIsEmpty() {
        binding.apply {
            regionRecyclerView.isVisible = false
            regionPlaceholderLayout.isVisible = true
            regionPlaceholderMessage.text = this@FilterRegionFragment.getString(R.string.failed_to_get_list)
            Glide.with(this@FilterRegionFragment)
                .load(R.drawable.placeholder_carpet)
                .centerCrop()
                .into(regionPlaceholderImage)
        }
    }

    private fun setStateNotFoundRegionInList() {
        binding.apply {
            regionRecyclerView.isVisible = false
            regionPlaceholderLayout.isVisible = true
            regionPlaceholderMessage.text = this@FilterRegionFragment.getString(R.string.no_region)
            Glide.with(this@FilterRegionFragment)
                .load(R.drawable.placeholder_cat)
                .centerCrop()
                .into(regionPlaceholderImage)
        }
    }

    private fun setStateNoInternetConnection() {
        binding.apply {
            regionRecyclerView.isVisible = false
            regionPlaceholderLayout.isVisible = true
            regionPlaceholderMessage.text = this@FilterRegionFragment.getString(R.string.no_internet_connection)
            Glide.with(this@FilterRegionFragment)
                .load(R.drawable.placeholder_skull)
                .centerCrop()
                .into(regionPlaceholderImage)
        }
    }

    private fun onItemRegionClick(area: AreaDomain) {
        activityViewModel.region.value = area
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
