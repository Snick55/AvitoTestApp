package com.snick55.avitotestapp.domain.entities

data class Filter(
    val title: String,
    val filterBy: FilterType,
    val filterValue: String
) {
    override fun toString(): String {
        return title
    }
}

enum class FilterType {
    EMPTY,
    AGE,
    YEAR,
    COUNTRY
}