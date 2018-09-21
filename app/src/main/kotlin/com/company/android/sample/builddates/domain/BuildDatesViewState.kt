package com.company.android.sample.builddates.domain

import ir.zinutech.android.domain.entities.BuildDateEntity

data class BuildDatesViewState(
    var isLoading: Boolean = true,
    var buildDates: List<BuildDateEntity>? = null,
    var pageBuildDates: List<BuildDateEntity>? = null
)