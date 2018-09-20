package ir.zinutech.android.data.api

import com.google.gson.annotations.SerializedName

data class BuildDatesListResult(
    @SerializedName("wkda")
    val buildDatesList: Map<String, String>)