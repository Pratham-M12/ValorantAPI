package com.example.valorantapi.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.valorantapi.RetrofitInstance
import com.example.valorantapi.model.Weapon
import com.example.valorantapi.model.WeaponResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun WeaponDetailScreen(weaponUuid: String, navController: NavController) {
    var selectedWeapon by remember { mutableStateOf<Weapon?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getWeapons().enqueue(object : Callback<WeaponResponse> {
            override fun onResponse(call: Call<WeaponResponse>, response: Response<WeaponResponse>) {
                val weapons = response.body()?.data ?: emptyList()
                selectedWeapon = weapons.find { it.uuid == weaponUuid }
                isLoading = false
            }

            override fun onFailure(call: Call<WeaponResponse>, t: Throwable) {
                isLoading = false
                Log.e("WeaponDetailScreen", t.message ?: "Unknown error")
            }
        })
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        selectedWeapon?.let { weapon ->
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    AnimatedWeaponCard(weapon = weapon, navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Skins", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                items(weapon.skins?.filterNot {skin -> skin.displayName in listOf("Standard", "Random Favorite Skin", "Melee Standard") } ?: emptyList()) { skin ->
                    SkinCard(skin)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Weapon not found", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun AnimatedWeaponCard(weapon: Weapon, navController: NavController? = null) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(weapon.uuid) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + slideInVertically(tween(300)),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .clickable(enabled = navController != null) { navController?.navigate("weaponDetail/${weapon.uuid}") },
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    weapon.displayIcon?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = weapon.displayName,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = weapon.displayName, style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = weapon.category.substringAfter("::")
                                .replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                weapon.weaponStats?.let { stats ->
                    StatRow(label = "Fire Rate", value = "${stats.fireRate}")
                    StatRow(label = "Magazine Size", value = "${stats.magazineSize}")
                    StatRow(label = "Reload Time", value = "${stats.reloadTimeSeconds}s")
                    StatRow(label = "Equip Time", value = "${stats.equipTimeSeconds}s")
                    StatRow(label = "ADS Zoom", value = "${stats.adsStats?.zoomMultiplier}")
                } ?: Text("Stats not available", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SkinCard(skin: com.example.valorantapi.model.Skin) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            skin.displayIcon?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = skin.displayName,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = skin.displayName,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}