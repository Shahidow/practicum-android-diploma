package ru.practicum.android.diploma.presentation.filtration.industry

import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding

class IndustryViewHolder(
    binding: ItemIndustryViewBinding,
    private val itemIndustryClickListener: IndustryAdapter.ItemIndustryClickInterface?
) : RecyclerView.ViewHolder(binding.root) {
    private val industryName: TextView = binding.textViewIndustry
    private val industryRadioButton: RadioButton = binding.radioButtonIndustry
    private var itemIndustry: IndustryAdapter.Industry? = null

    init {
        // меняем состояние радиокнопки
        industryRadioButton.isChecked = !industryRadioButton.isChecked
        itemView.setOnClickListener {
            if (itemIndustry != null) {
                itemIndustryClickListener?.onItemIndustryClick(
                    // записываем и отправляем индастри с новым состоянием радиокнопки
                    IndustryAdapter.Industry(
                        itemIndustry!!.name,
                        industryRadioButton.isChecked
                    )
                )
            }
        }
    }

    fun bind(industry: IndustryAdapter.Industry) {
        itemIndustry = industry
        industryName.text = industry.name
        industryRadioButton.isChecked = industry.check
    }
}

