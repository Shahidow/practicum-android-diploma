package ru.practicum.android.diploma.presentation.filtration.industry.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class IndustryViewHolder(private val binding: ItemIndustryViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val radioButtonIndustry = binding.radioButtonIndustry

    fun bind(industry: IndustryDomain, selected: Boolean) {
        binding.radioButtonIndustry.isChecked = selected
        binding.textViewIndustry.text = industry.name
    }
}
