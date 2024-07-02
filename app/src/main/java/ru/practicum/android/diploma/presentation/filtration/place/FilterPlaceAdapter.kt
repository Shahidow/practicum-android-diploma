package ru.practicum.android.diploma.presentation.filtration.place

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionViewBinding
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain

class FilterPlaceAdapter(val onItemRegionClick: (AreaDomain) -> Unit) : RecyclerView.Adapter<FilterPlaceViewHolder>() {

    private var regionsList = listOf<AreaDomain>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterPlaceViewHolder {
        return FilterPlaceViewHolder(
            ItemRegionViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = regionsList.size

    override fun onBindViewHolder(holder: FilterPlaceViewHolder, position: Int) {
        holder.bind(regionsList[position])
        holder.itemView.setOnClickListener {
            onItemRegionClick(regionsList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAreaList(placeList: List<AreaDomain>) {
        this.regionsList = placeList
        notifyDataSetChanged()
    }

}
