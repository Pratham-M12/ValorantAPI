package com.example.valorantapi.model

import com.google.gson.annotations.SerializedName

data class AgentResponse (
    val status: Int,
    val data: List<Agent>
)

data class Agent(
    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("displayIcon")
    val displayIcon: String,

    @SerializedName("fullPortrait")
    val fullPortrait: String?,  // Nullable — not all agents may have one

    @SerializedName("isPlayableCharacter")
    val isPlayableCharacter: Boolean,

    @SerializedName("role")
    val role: Role?,  // Nested object

    @SerializedName("abilities")
    val abilities: List<Ability>  // List of objects
)

data class Role(
    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("displayIcon")
    val displayIcon: String
)

data class Ability(
    @SerializedName("slot")
    val slot: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("displayIcon")
    val displayIcon: String?
)