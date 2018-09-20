package ir.zinutech.android.data.entities

import com.google.gson.annotations.SerializedName

data class ModelsData(
    val page: Int,
    val totalPageCount: Int,

    @SerializedName("wkda")
    val modelsList: Map<String, String>)