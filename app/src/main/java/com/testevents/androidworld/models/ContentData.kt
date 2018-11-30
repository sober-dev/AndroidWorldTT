package com.testevents.androidworld.models

import com.google.gson.annotations.SerializedName

data class ContentData(
    @SerializedName("bigImage") val bigImage: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("smallImage") val smallImage: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String
)
