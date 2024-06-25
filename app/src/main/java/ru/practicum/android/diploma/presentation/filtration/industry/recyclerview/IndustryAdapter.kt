package ru.practicum.android.diploma.presentation.filtration.industry.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding
import ru.practicum.android.diploma.domain.search.models.IndustryGroup

class IndustryAdapter(private val itemIndustryClickListener: ItemIndustryClickInterface) :
    RecyclerView.Adapter<IndustryViewHolder>() {
    private var industryList = arrayListOf<IndustryGroup>()
    private var selectedIndustry: IndustryGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(
            ItemIndustryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemIndustryClickListener
        )
    }

    override fun getItemCount() = if (selectedIndustry == null) industryList.size else 1

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = selectedIndustry ?: industryList[position]
        val isSelected = industry == selectedIndustry
        holder.bind(industry, isSelected)
        holder.itemView.visibility = if (selectedIndustry == null || isSelected) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun setIndustryList(industryList: List<IndustryGroup>) {
        this.industryList = ArrayList(industryList)
        notifyDataSetChanged()
    }

    fun setSelectedIndustry(selectedIndustry: IndustryGroup?) {
        this.selectedIndustry = selectedIndustry
        notifyDataSetChanged()
    }

    interface ItemIndustryClickInterface {
        fun onItemIndustryClick(industry: IndustryGroup)
    }
}
