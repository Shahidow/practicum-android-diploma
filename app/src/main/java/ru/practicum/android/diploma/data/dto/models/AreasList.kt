package ru.practicum.android.diploma.data.dto.models

import com.google.gson.annotations.SerializedName

data class AreasList(
    val areas: List<Areas>,
)

data class Areas(
    val id: String,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String?
)
