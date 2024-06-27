package ru.practicum.android.diploma.data.dto

class VacancyRequest(
    private val text: String,
    private val page: Int?,
    private val filters: Map<String, String>?
) {
    fun map(): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = text
        if (page != null) {
            options["page"] = page.toString()
        }
        filters?.let { options.putAll(it.filter { it.value.isNotEmpty() }) }
        return options
    }
}
