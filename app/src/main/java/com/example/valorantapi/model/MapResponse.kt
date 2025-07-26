package com.example.valorantapi.model

data class MapResponse(
    val status: Int,
    val data: List<ValorantMap>
)

data class ValorantMap(
    val uuid: String,
    val displayName: String,
    val splash: String
)