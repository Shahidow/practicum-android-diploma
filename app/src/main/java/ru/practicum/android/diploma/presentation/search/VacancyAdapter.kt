package ru.practicum.android.diploma.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyViewBinding
import ru.practicum.android.diploma.databinding.ProgressbarVacancyListBinding
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.IsLastPage

class VacancyAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var domainVacancyList = arrayListOf<DomainVacancy>()
    private var itemVacancyClickListener: ItemVacancyClickInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_DATA) {
            VacancyViewHolder(
                ItemVacancyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                itemVacancyClickListener
            )
        } else {
            ProgressbarViewHolder(
                ProgressbarVacancyListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            )
        }
    }

    override fun getItemCount() = domainVacancyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacancyViewHolder) {
            holder.bind(domainVacancyList[position])
        }
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

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) {
            ITEM_DATA
        } else {
            if (IsLastPage.IS_LAST_PAGE) ITEM_DATA else ITEM_PROGRESSBAR
        }
    }

    companion object {
        const val CORNER_RADIUS: Float = 12f
        const val WHITESPACE = " "
        const val ITEM_DATA = 0
        const val ITEM_PROGRESSBAR = 1
    }
}
