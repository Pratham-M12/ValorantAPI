package com.example.valorantapi.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.valorantapi.RetrofitInstance
import com.example.valorantapi.model.Agent
import com.example.valorantapi.model.AgentResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

@Composable
fun AgentDetailScreen(agentUuid: String) {
    var agent by remember { mutableStateOf<Agent?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(agentUuid) {
        RetrofitInstance.api.getAgents().enqueue(object : Callback<AgentResponse> {
            override fun onResponse(call: Call<AgentResponse>, response: Response<AgentResponse>) {
                if (response.isSuccessful) {
                    agent = response.body()?.data?.firstOrNull { it.uuid == agentUuid }
                }
                isLoading = false
            }

            override fun onFailure(call: Call<AgentResponse>, t: Throwable) {
                Log.e("AgentDetail", "Failed: ${t.message}")
                isLoading = false
            }
        })
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        agent?.let { a ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = a.displayName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                Image(
                    painter = rememberAsyncImagePainter(a.fullPortrait ?: a.displayIcon),
                    contentDescription = a.displayName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = a.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Role: ${a.role?.displayName ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Abilities:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                a.role?.let { role ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberAsyncImagePainter(role.displayIcon),
                            contentDescription = role.displayName,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Role: ${role.displayName}")
                    }
                }
                a.abilities.forEach { ability ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        ability.displayIcon?.let { icon ->
                            Image(
                                painter = rememberAsyncImagePainter(icon),
                                contentDescription = ability.displayName,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                        Column {
                            Text(
                                text = ability.displayName,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = ability.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        } ?: Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Agent not found")
        }
    }
}