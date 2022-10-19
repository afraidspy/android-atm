package com.intercam.mapbiteam.vista.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.intercam.mapbiteam.MainActivity
import com.intercam.mapbiteam.MapsActivity
import com.intercam.mapbiteam.R


@Composable
fun BottomNavigation(navController: NavHostController) {

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Mapa,
        NavigationItem.Configuracion,
        NavigationItem.Perfil

    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavHost(navController = navController, startDestination = NavigationItem.Home.route){
        composable(route=NavigationItem.Home.route){
            MainActivity()
        }
        composable(route= NavigationItem.Mapa.route){
            MapsActivity()
        }
    }


}

