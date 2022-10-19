package com.intercam.mapbiteam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.intercam.mapbiteam.ui.theme.MapBiTeamTheme
import com.intercam.mapbiteam.vista.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapBiTeamTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main" ){
                    composable(route="main"){
                        login(navController)
                    }
                    composable(route= "mapa"){
                        mapa(navController)
                    }
                }

            }
        }
    }
}





