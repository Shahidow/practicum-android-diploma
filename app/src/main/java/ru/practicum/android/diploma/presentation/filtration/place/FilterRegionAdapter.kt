package ru.practicum.android.diploma.presentation.filtration.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionViewBinding

class FilterRegionAdapter(val onItemRegionClick: (Area) -> Unit) : RecyclerView.Adapter<FilterRegionViewHolder>() {

    private var regionsList = mutableListOf<Area>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterRegionViewHolder {
        return FilterRegionViewHolder(
            ItemRegionViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = regionsList.size

    override fun onBindViewHolder(holder: FilterRegionViewHolder, position: Int) {
        holder.bind(regionsList[position])
        holder.itemView.setOnClickListener {
            onItemRegionClick(regionsList[position])
        }
    }

    // удалить этот временный data class Area когда будет готов нормальный
    data class Area(
        val id: String,
        val name: String,
    )
}
