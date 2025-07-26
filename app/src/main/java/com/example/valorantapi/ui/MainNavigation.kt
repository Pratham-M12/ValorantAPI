package com.example.valorantapi.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector){
    data object Agents : Screen("agents", "Agents", Icons.Default.Person)
    data object Maps : Screen("maps", "Maps", Icons.Default.Map)
    data object Weapons : Screen("weapons", "Weapons", Icons.Default.Build)
    data object Stats : Screen("stats", "Stats", Icons.Default.Stadium)
}

@Composable
fun MainNavigation(){
    val navController = rememberNavController()
    val items = listOf(Screen.Agents, Screen.Maps, Screen.Stats, Screen.Weapons)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination == screen.route,
                        onClick = {
                            navController.navigate(screen.route){
                                popUpTo(navController.graph.startDestinationId){ saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Agents.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Agents.route) { AgentScreen(navController) }
            composable("agentDetail/{agentUuid}") { backStackEntry ->
                val agentUuid = backStackEntry.arguments?.getString("agentUuid")
                agentUuid?.let { AgentDetailScreen(agentUuid = it) }
            }
            composable(Screen.Maps.route) { MapScreen() }
            composable(Screen.Weapons.route) { WeaponsScreen(navController) }
            composable("weaponDetail/{weaponUuid}") { backStackEntry ->
                val weaponUuid = backStackEntry.arguments?.getString("weaponUuid")
                weaponUuid?.let { WeaponDetailScreen(weaponUuid = it, navController = navController) }
            }
            composable(Screen.Stats.route) { StatsScreen() }
        }
    }
}