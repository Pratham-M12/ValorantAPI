package com.example.valorantapi.model

import com.google.gson.annotations.SerializedName


data class WeaponResponse(val data: List<Weapon>)
data class Weapon(
    val uuid: String,
    val displayName: String,
    val category: String,      // e.g., "Rifle", "Sidearm"
    val displayIcon: String?,
    val weaponStats: WeaponStats?,
    @SerializedName("skins")
    val skins: List<Skin>?
)

data class WeaponStats(
    @SerializedName("fireRate")
    val fireRate: Double,

    @SerializedName("magazineSize")
    val magazineSize: Int,

    @SerializedName("equipTimeSeconds")
    val equipTimeSeconds: Double,

    @SerializedName("reloadTimeSeconds")
    val reloadTimeSeconds: Double,

    @SerializedName("firstBulletAccuracy")
    val firstBulletAccuracy: Double,

    @SerializedName("adsStats")
    val adsStats: AdsStats?
)

data class AdsStats(
    @SerializedName("zoomMultiplier")
    val zoomMultiplier: Double
)
