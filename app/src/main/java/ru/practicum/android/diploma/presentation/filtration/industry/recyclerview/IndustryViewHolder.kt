package ru.practicum.android.diploma.presentation.filtration.industry.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding
import ru.practicum.android.diploma.domain.search.models.IndustryGroup

class IndustryViewHolder(
    private val binding: ItemIndustryViewBinding,
    private val itemIndustryClickListener: IndustryAdapter.ItemIndustryClickInterface?
) : RecyclerView.ViewHolder(binding.root) {
    private var itemIndustry: IndustryGroup? = null

    init {
        itemView.setOnClickListener {
        binding.radioButtonIndustry.setOnClickListener {
            itemIndustry?.let { industry ->
                itemIndustryClickListener?.onItemIndustryClick(industry)
            }
        }
        itemView.setOnClickListener(null)
    }

    fun bind(industry: IndustryGroup, isSelected: Boolean) {
        itemIndustry = industry
        binding.textViewIndustry.text = industry.name
        binding.radioButtonIndustry.isChecked = isSelected
    }
}

