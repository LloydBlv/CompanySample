package ir.zinutech.android.data.api

import com.google.gson.annotations.SerializedName

data class ManufacturersListResult(
    val page: Int,
    val totalPageCount: Int,

    @SerializedName("wkda")
    val manufacturersList: Map<String, String>)