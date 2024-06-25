package ru.practicum.android.diploma.presentation.search

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyViewBinding
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

class VacancyViewHolder(
    binding: ItemVacancyViewBinding,
    private val itemVacancyClickListener: VacancyAdapter.ItemVacancyClickInterface?,
) : RecyclerView.ViewHolder(binding.root) {

    private val vacancyNameAndCity: TextView = binding.textViewVacancyNameAndCity
    private val employerName: TextView = binding.textViewEmployerName
    private val vacancySalary: TextView = binding.textViewVacancySalary
    private val employerLogoUrlImage: ImageView = binding.imageViewEmployerLogoUrl
    private var itemDomainVacancy: DomainVacancy? = null

    init {
        itemView.setOnClickListener {
            itemDomainVacancy?.let { item ->
                itemVacancyClickListener?.onItemVacancyClick(
                    item
                )
            }
        }
    }

    fun bind(domainVacancy: DomainVacancy) {
        itemDomainVacancy = domainVacancy
        vacancyNameAndCity.text = java.lang.String(
            domainVacancy.name
                + itemView.context.getString(R.string.comma)
                + VacancyAdapter.WHITESPACE
                + domainVacancy.city
        )
        employerName.text = domainVacancy.employerName
        vacancySalary.text = setSalary(domainVacancy)

        Glide.with(itemView)
            .load(domainVacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_android)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(VacancyAdapter.CORNER_RADIUS, itemView.context)))
            .into(employerLogoUrlImage)
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun setSalary(domainVacancy: DomainVacancy): String {
        var salary = ""
        if (domainVacancy.salaryFrom == null && domainVacancy.salaryTo == null) {
            salary = itemView.context.getString(R.string.salary_is_not_specified)
        } else {
            if (domainVacancy.salaryFrom != null) {
                salary =
                    java.lang.String(
                        itemView.context.getString(R.string.from)
                            + VacancyAdapter.WHITESPACE
                            + domainVacancy.salaryFrom.toString()
                    ).toString()
            }
            if (domainVacancy.salaryTo != null) {
                salary =
                    java.lang.String(
                        salary
                            + VacancyAdapter.WHITESPACE
                            + itemView.context.getString(R.string.to)
                            + VacancyAdapter.WHITESPACE
                            + domainVacancy.salaryTo
                    ).toString()
            }
        }
        return if (domainVacancy.salaryCurrency != null) {
            salary + VacancyAdapter.WHITESPACE + domainVacancy.salaryCurrency
        } else {
            salary
        }
    }
}
