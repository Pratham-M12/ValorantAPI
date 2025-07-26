package com.example.valorantapi.model

import com.google.gson.annotations.SerializedName

data class Skin(
    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("displayIcon")
    val displayIcon: String?
)
