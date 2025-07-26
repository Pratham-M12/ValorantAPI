package com.example.valorantapi.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.valorantapi.RetrofitInstance
import com.example.valorantapi.model.Agent
import com.example.valorantapi.model.AgentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

@Composable
fun AgentScreen(navController: NavController) {
    var agents by remember { mutableStateOf<List<Agent>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

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
                text = "Valorant Tracker",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(agents) { agent ->
                    AgentCard(agent = agent, navController = navController)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }

    // API Call
    LaunchedEffect(Unit) {
        RetrofitInstance.api.getAgents().enqueue(object : Callback<AgentResponse> {
            override fun onResponse(call: Call<AgentResponse>, response: Response<AgentResponse>) {
                if (response.isSuccessful) {
                    agents = response.body()?.data?.filter { it.isPlayableCharacter && it.displayIcon.isNotEmpty() } ?: emptyList()
                } else {
                    Log.e("AgentScreen", "Response Not Successful")
                }
                isLoading = false
            }
            override fun onFailure(call: Call<AgentResponse>, t: Throwable) {
                Log.e("AgentScreen", "Error: ${t.message}")
                isLoading = false
            }
        })
    }
}

@Composable
fun AgentCard(agent: Agent, navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500)) +
                scaleIn(initialScale = 0.95f, animationSpec = tween(500)) +
                slideInVertically(initialOffsetY = { it / 4 }, animationSpec = tween(500))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("agentDetail/${agent.uuid}") },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(agent.displayIcon),
                    contentDescription = agent.displayName,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = agent.displayName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = agent.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}