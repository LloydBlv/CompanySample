package com.company.android.sample.manufacturers.domain

import ir.zinutech.android.domain.entities.ManufacturerEntity

data class ManufacturersViewState(
    var isLoading: Boolean = true,
    var manufacturers: List<ManufacturerEntity>? = null
)