package ru.practicum.android.diploma.presentation.filtration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding

class FiltrationFragment : Fragment() {

    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltrationViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var filterChangeListener: FilterChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("filter_prefs", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterChangeListener = context as? FilterChangeListener
    }

    override fun onDetach() {
        super.onDetach()
        filterChangeListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Загрузка данных из SharedPreferences
        loadFilterPreferences()

        // Обработчики событий
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

        // Слушаем изменения в полях фильтрации и обновляем видимость кнопок
        binding.filtrationWorkplaceEditText.addTextChangedListener(textWatcher)
        binding.filtrationIndustryText.addTextChangedListener(textWatcher)
        binding.expectedSalaryEditText.addTextChangedListener(textWatcher)
        binding.noSalaryCheckBox.setOnCheckedChangeListener { _, _ -> updateButtonVisibility() }

        binding.resetButton.setOnClickListener {
            resetFilters()
            filterChangeListener?.onFilterChanged(emptyMap())
        }

        binding.applyButton.setOnClickListener {
            saveFilterPreferences()
            val newFilterParams = loadCurrentFilterParameters()
            Log.d("!!!", "FiltrationFragment - Применение новых фильтров: $newFilterParams")
            filterChangeListener?.onFilterChanged(newFilterParams)
            findNavController().popBackStack()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Для детекта, чтобы не ругался что блок пустой
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateButtonVisibility()
        }

        override fun afterTextChanged(s: Editable?) {
            // Для детекта, чтобы не ругался что блок пустой
        }
    }


    // Обновление видимости кнопок
    private fun updateButtonVisibility() {
        val isAnyFieldFilled = binding.filtrationWorkplaceEditText.text?.isNotEmpty() == true ||
            binding.filtrationIndustryText.text.isNotEmpty() ||
            binding.expectedSalaryEditText.text?.isNotEmpty() == true ||
            binding.noSalaryCheckBox.isChecked

        binding.applyButton.isVisible = isAnyFieldFilled
        binding.resetButton.isVisible = isAnyFieldFilled
    }

    // Сброс всех фильтров
    private fun resetFilters() {
        with(sharedPreferences.edit()) {
            remove("area")
            remove("industry")
            remove("salary")
            remove("only_with_salary")
            apply()
        }

        Log.d("!!!", "FiltrationFragment - Сбрасываем фильтры SharedPreferences")

        binding.filtrationWorkplaceEditText.text = null
        binding.filtrationIndustryText.text = ""
        binding.expectedSalaryEditText.text = null
        binding.noSalaryCheckBox.isChecked = false
        updateButtonVisibility()
        filterChangeListener?.onFilterChanged(emptyMap())
    }

    // Загрузка данных из SharedPreferences
    private fun loadFilterPreferences() {
        binding.filtrationWorkplaceEditText.setText(sharedPreferences.getString("area", ""))
        binding.filtrationIndustryText.text = sharedPreferences.getString("industry", "")
        binding.expectedSalaryEditText.setText(sharedPreferences.getString("salary", ""))
        binding.noSalaryCheckBox.isChecked = sharedPreferences.getBoolean("only_with_salary", false)
        updateButtonVisibility()
    }

    // Сохранение данных в SharedPreferences
    private fun saveFilterPreferences() {
        with(sharedPreferences.edit()) {
            putString("area", binding.filtrationWorkplaceEditText.text.toString())
            putString("industry", binding.filtrationIndustryText.text.toString())
            putString("salary", binding.expectedSalaryEditText.text.toString())
            putBoolean("only_with_salary", binding.noSalaryCheckBox.isChecked)
            apply()
        }
        Log.d("!!!", "FiltrationFragment - Сохранили фильтры: area=${binding.filtrationWorkplaceEditText.text}, industry=${binding.filtrationIndustryText.text}, salary=${binding.expectedSalaryEditText.text}, only_with_salary=${binding.noSalaryCheckBox.isChecked}")
    }

    // Загрузка текущих параметров фильтрации из SharedPreferences
    private fun loadCurrentFilterParameters(): Map<String, String> {
        val filterParams = mutableMapOf<String, String>()
        sharedPreferences.getString("area", null)?.takeIf { it.isNotEmpty() }?.let { filterParams["area"] = it }
        sharedPreferences.getString("industry", null)?.takeIf { it.isNotEmpty() }?.let { filterParams["industry"] = it }
        sharedPreferences.getString("salary", null)?.takeIf { it.isNotEmpty() }?.let { filterParams["salary"] = it }
        if (sharedPreferences.getBoolean("only_with_salary", false)) {
            filterParams["only_with_salary"] = "true"
        }
        return filterParams
    }
}
