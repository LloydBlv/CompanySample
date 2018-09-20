package ir.zinutech.android.data.entities

import com.google.gson.annotations.SerializedName

data class ManufacturerData(
    val page: Int,
    val totalPageCount: Int,

    @SerializedName("wkda")
    val manufacturersList: Map<String, String>)