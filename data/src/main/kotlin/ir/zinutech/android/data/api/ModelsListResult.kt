package ir.zinutech.android.data.api

import com.google.gson.annotations.SerializedName

data class ModelsListResult(
    val page: Int,
    val totalPageCount: Int,

    @SerializedName("wkda")
    val modelsList: Map<String, String>)