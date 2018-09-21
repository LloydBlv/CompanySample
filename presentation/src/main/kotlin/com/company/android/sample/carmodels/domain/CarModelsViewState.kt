package com.company.android.sample.carmodels.domain

import ir.zinutech.android.domain.entities.ModelEntity

data class CarModelsViewState(
    var isLoading: Boolean = true,
    var carModels: List<ModelEntity>? = null,
    var pageCarModels: List<ModelEntity>? = null
)