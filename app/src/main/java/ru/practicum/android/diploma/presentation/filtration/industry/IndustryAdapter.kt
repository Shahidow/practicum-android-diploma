package ru.practicum.android.diploma.presentation.filtration.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding

class IndustryAdapter : RecyclerView.Adapter<IndustryViewHolder>() {
    private var industryList = arrayListOf<Industry>()
    private var itemIndustryClickListener: ItemIndustryClickInterface? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(
            ItemIndustryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemIndustryClickListener
        )
    }

    override fun getItemCount() = industryList.size
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(industryList[position])
    }

    fun setIndustryList(industryList: ArrayList<Industry>) {
        this.industryList = industryList
        notifyDataSetChanged()
    }

    interface ItemIndustryClickInterface {
        fun onItemIndustryClick(industry: Industry)
    }

    fun setInItemIndustryClickListener(itemClickListener: ItemIndustryClickInterface) {
        this.itemIndustryClickListener = itemClickListener
    }

    // удалить этот временный data class Industry когда будет готов нормальный
    data class Industry(
        val name: String,
        val check: Boolean
    )
}
