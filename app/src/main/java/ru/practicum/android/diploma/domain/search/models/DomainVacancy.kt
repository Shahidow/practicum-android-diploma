package ru.practicum.android.diploma.domain.search.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainVacancy(
    val vacancyId: String,
    val name: String,
    val city: String?,
    val area: String?,
    val salaryFrom: String?,
    val salaryTo: String?,
    val salaryCurrency: String?,
    val employerName: String?,
    val employerLogoUrl: String?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String,
    val skills: List<String>,
    val contactEmail: String?,
    val contactName: String?,
    val contactPhoneNumbers: List<String>,
    val contactComment: List<String>,
    val url: String?,
    val isFavorite: Boolean?
) : Parcelable
