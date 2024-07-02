package ru.practicum.android.diploma.presentation.filtration.industry.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class IndustryAdapter(var selectedIndustry: IndustryDomain?, val onItemIndustryClick: (IndustryDomain) -> Unit) :
    RecyclerView.Adapter<IndustryViewHolder>() {
    var industriesList = mutableListOf<IndustryDomain>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = ItemIndustryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    override fun getItemCount() = industriesList.size

    override fun onBindViewHolder(industryViewHolder: IndustryViewHolder, position: Int) {
        val industry = industriesList[position]

        val listener = View.OnClickListener {
            selectedIndustry = industry
            notifyDataSetChanged()
            onItemIndustryClick(industry)
        }
        val selected = selectedIndustry?.id == industry.id

        industryViewHolder.bind(industry, selected)
        industryViewHolder.itemView.setOnClickListener(listener)
        industryViewHolder.radioButtonIndustry.setOnClickListener(listener)
    }
}
