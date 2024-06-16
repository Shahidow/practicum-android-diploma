package ru.practicum.android.diploma.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchFragment : Fragment(), VacancyAdapter.ItemVacancyClickInterface {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var vacancyAdapter: VacancyAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

  /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacancyAdapter = VacancyAdapter()
        vacancyAdapter?.setInItemVacancyClickListener(this)
        binding.recyclerviewVacancy.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewVacancy.adapter = vacancyAdapter

        searchViewModel.vacancyList.observe(viewLifecycleOwner) { vacancyAdapter?.setVacancyList(it) }
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemVacancyClick(vacancy: Vacancy) {
        Log.d("search", "vacancy = ${vacancy.vacancyId}, ${vacancy.name}")
    }
}
