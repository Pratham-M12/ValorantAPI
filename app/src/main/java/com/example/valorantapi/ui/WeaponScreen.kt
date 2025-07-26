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
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.valorantapi.RetrofitInstance
import com.example.valorantapi.model.Weapon
import com.example.valorantapi.model.WeaponResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun WeaponsScreen(navController: NavController) {
    var weapons by remember { mutableStateOf<List<Weapon>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getWeapons().enqueue(object : Callback<WeaponResponse> {
            override fun onResponse(call: Call<WeaponResponse>, response: Response<WeaponResponse>) {
                weapons = response.body()?.data ?: emptyList()
                isLoading = false
            }
            override fun onFailure(call: Call<WeaponResponse>, t: Throwable) {
                isLoading = false; Log.e("WeaponsScreen", t.message ?: "")
            }
        })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Valorant Weapons",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        if (weapons.isEmpty() && !isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Failed to load weapons.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        else if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(weapons) { weapon ->
                    WeaponCard(weapon, navController)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun WeaponCard(weapon: Weapon, navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(weapon.uuid) { visible = true }

    AnimatedVisibility(visible = visible, enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it / 10 }))
    {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("weaponDetail/${weapon.uuid}") },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                weapon.displayIcon?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = weapon.displayName,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Column {
                    Text(
                        text = weapon.displayName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = weapon.category.substringAfter("::")
                            .replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    weapon.weaponStats?.let { stats ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Fire rate: ${stats.fireRate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Mag size: ${stats.magazineSize}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}