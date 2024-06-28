package ru.practicum.android.diploma.presentation.filtration.place

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionViewBinding
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain

class FilterPlaceViewHolder(private val binding: ItemRegionViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(region: AreaDomain) {
        binding.regionNameTextView.text = region.name
    }
}
