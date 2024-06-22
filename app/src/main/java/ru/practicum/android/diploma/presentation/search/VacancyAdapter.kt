package ru.practicum.android.diploma.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyViewBinding
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

class VacancyAdapter :
    RecyclerView.Adapter<VacancyViewHolder>() {
    private var domainVacancyList = arrayListOf<DomainVacancy>()
    private var itemVacancyClickListener: ItemVacancyClickInterface? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(
            ItemVacancyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemVacancyClickListener
        )
    }

    override fun getItemCount() = domainVacancyList.size
    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(domainVacancyList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setVacancyList(domainVacancyList: ArrayList<DomainVacancy>) {
        this.domainVacancyList = domainVacancyList
        notifyDataSetChanged()
    }

    interface ItemVacancyClickInterface {
        fun onItemVacancyClick(domainVacancy: DomainVacancy)
    }

    fun setInItemVacancyClickListener(itemClickListener: ItemVacancyClickInterface) {
        this.itemVacancyClickListener = itemClickListener
    }

    companion object {
        const val CORNER_RADIUS: Float = 12f
        const val WHITESPACE = " "
    }
}
