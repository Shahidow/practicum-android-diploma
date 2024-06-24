package ru.practicum.android.diploma.presentation.filtration.place

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionViewBinding

class FilterRegionViewHolder(private val binding: ItemRegionViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(region: FilterRegionAdapter.Area) {
        binding.regionNameTextView.text = region.name
    }
}
