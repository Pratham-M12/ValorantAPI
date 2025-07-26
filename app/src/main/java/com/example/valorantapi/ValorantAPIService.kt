package com.example.valorantapi

import retrofit2.http.GET
import retrofit2.Call

//Data classes defined later
import com.example.valorantapi.model.AgentResponse
import com.example.valorantapi.model.MapResponse
import com.example.valorantapi.model.PlayerStats
import com.example.valorantapi.model.WeaponResponse

interface ValorantAPIService{
    @GET("v1/agents")
    fun getAgents(): Call<AgentResponse>

    @GET ("v1/maps")
    fun getMaps(): Call<MapResponse>

    @GET ("v1/weapons")
    fun getWeapons(): Call<WeaponResponse>

    @GET ("v1/playerstats")
    fun getPlayerStats(): Call<PlayerStats>
}