package ru.practicum.android.diploma.presentation.search

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.VACANCY_KEY

class SearchFragment : Fragment(), VacancyAdapter.ItemVacancyClickInterface {

    private val searchViewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var vacancyAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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

        searchViewModel.trackListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is SearchState.NoInternet -> setStateNetworkError()
                is SearchState.Error -> setStateServerError()
                is SearchState.Default -> setStateDefault()
                is SearchState.Loading -> setStateIsLoading()
                is SearchState.NoResults -> setStateEmptyResult()
                is SearchState.Success -> setSateIsData(it.vacancies, it.totalVacancies)
                is SearchState.Continuation -> setContinuationData()
            }
        }

        binding.iconClear.setOnClickListener {
            binding.searchInput.setText("")
            searchViewModel.clearSearchResults()
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Метод пустой, но обязателен к реализации
            }

            override fun afterTextChanged(p0: Editable?) {
                // Метод пустой, но обязателен к реализации
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iconSearch.isVisible = s.isNullOrEmpty()
                binding.iconClear.isVisible = !s.isNullOrEmpty()
                searchViewModel.searchDebounce(s.toString())
            }
        })

        vacancyAdapter = VacancyAdapter()
        vacancyAdapter?.setInItemVacancyClickListener(this)
        // binding.searchRecyclerView = LinearLayoutManager(context)
        binding.searchRecyclerView.adapter = vacancyAdapter
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtrationFragment)
        }

        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos =
                        (binding.searchRecyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter!!.itemCount
                    if (pos >= itemsCount - 1) {
                        searchViewModel.onLastItemReached()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemVacancyClick(domainVacancy: DomainVacancy) {
        val bundle = Bundle()
        bundle.putParcelable(VACANCY_KEY, domainVacancy)
        findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
    }

    private fun setStateNetworkError() {
        binding.apply {
            continuationProgressBar.isVisible = false
            foundResultsMessage.isVisible = false
            searchRecyclerView.isVisible = false
            searchProgressBar.isVisible = false
            searchPlaceholderLayout.isVisible = true
            searchPlaceholderMessage.text = this@SearchFragment.getString(R.string.no_internet_connection)
            Glide.with(this@SearchFragment)
                .load(R.drawable.placeholder_skull)
                .centerCrop()
                .into(searchPlaceholderImage)
        }
    }

    private fun setStateServerError() {
        binding.apply {
            continuationProgressBar.isVisible = false
            foundResultsMessage.isVisible = false
            searchRecyclerView.isVisible = false
            searchProgressBar.isVisible = false
            searchPlaceholderLayout.isVisible = true
            searchPlaceholderMessage.text = this@SearchFragment.getString(R.string.server_error)
            Glide.with(this@SearchFragment)
                .load(R.drawable.placeholder_server_error_towel)
                .centerCrop()
                .into(searchPlaceholderImage)
        }
    }

    private fun setStateDefault() {
        binding.apply {
            continuationProgressBar.isVisible = false
            foundResultsMessage.isVisible = false
            searchRecyclerView.isVisible = false
            searchProgressBar.isVisible = false
            searchPlaceholderLayout.isVisible = true
            searchPlaceholderMessage.text = ""
            Glide.with(this@SearchFragment)
                .load(R.drawable.placeholder_man_search)
                .centerCrop()
                .into(searchPlaceholderImage)
        }
    }

    private fun setStateIsLoading() {
        binding.apply {
            continuationProgressBar.isVisible = false
            foundResultsMessage.isVisible = false
            searchRecyclerView.isVisible = false
            searchPlaceholderLayout.isVisible = false
            searchProgressBar.isVisible = true
        }
    }

    private fun setStateEmptyResult() {
        binding.apply {
            continuationProgressBar.isVisible = false
            searchRecyclerView.isVisible = false
            searchProgressBar.isVisible = false
            foundResultsMessage.isVisible = true
            searchPlaceholderLayout.isVisible = true
            searchPlaceholderMessage.text = this@SearchFragment.getString(R.string.count_get_list_of_vacancies)
            foundResultsMessage.text = this@SearchFragment.getString(R.string.no_such_vacancies)
            Glide.with(this@SearchFragment)
                .load(R.drawable.placeholder_cat)
                .centerCrop()
                .into(searchPlaceholderImage)
        }
    }

    private fun setSateIsData(domainVacancyList: List<DomainVacancy>, totalVacancies: Int) {
        binding.apply {
            continuationProgressBar.isVisible = false
            foundResultsMessage.isVisible = true
            searchRecyclerView.isVisible = true
            searchProgressBar.isVisible = false
            searchPlaceholderLayout.isVisible = false
            searchPlaceholderMessage.text = this@SearchFragment.getString(R.string.count_get_list_of_vacancies)
            foundResultsMessage.text = java.lang.String(
                this@SearchFragment.getString(R.string.found)
                    + whitespace
                    + totalVacancies.toString()
                    + whitespace
                    + this@SearchFragment.getString(R.string.vacancy)
            )
            Glide.with(this@SearchFragment)
                .load(R.drawable.placeholder_cat)
                .centerCrop()
                .into(searchPlaceholderImage)
        }
        vacancyAdapter?.setVacancyList(ArrayList(domainVacancyList))
    }

    private fun setContinuationData() {
        binding.continuationProgressBar.isVisible = true
    }

    companion object {
        const val whitespace = " "
    }

}
