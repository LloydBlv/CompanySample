package ir.zinutech.android.data.entities

import com.google.gson.annotations.SerializedName

data class BuildDatesData(
    @SerializedName("wkda")
    val buildDatesList: Map<String, String>)