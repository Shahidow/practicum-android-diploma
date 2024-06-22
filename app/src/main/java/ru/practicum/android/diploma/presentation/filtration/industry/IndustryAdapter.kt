package ru.practicum.android.diploma.presentation.filtration.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryViewBinding

class IndustryAdapter : RecyclerView.Adapter<IndustryAdapter.IndustryViewHolder>() {
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

    class IndustryViewHolder(
        binding: ItemIndustryViewBinding,
        private val itemIndustryClickListener: ItemIndustryClickInterface?
    ) : RecyclerView.ViewHolder(binding.root) {
        private val industryName: TextView = binding.textViewIndustry
        private val industryRadioButton: RadioButton = binding.radioButtonIndustry
        private var itemIndustry: Industry? = null

        init {
            // меняем состояние радиокнопки
            industryRadioButton.isChecked = !industryRadioButton.isChecked
            itemView.setOnClickListener {
                if (itemIndustry != null) {
                    itemIndustryClickListener?.onItemIndustryClick(
                        // записываем и отправляем индастри с новым состоянием радиокнопки
                        Industry(
                            itemIndustry!!.name,
                            industryRadioButton.isChecked
                        )
                    )
                }
            }
        }

        fun bind(industry: Industry) {
            itemIndustry = industry
            industryName.text = industry.name
            industryRadioButton.isChecked = industry.check
        }
    }

    data class Industry(
        val name: String,
        val check: Boolean
    )
}
