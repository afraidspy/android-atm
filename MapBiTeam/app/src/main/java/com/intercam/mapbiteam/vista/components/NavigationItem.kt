package com.intercam.mapbiteam.vista.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
        object Home : NavigationItem("home", Icons.Default.Home, "Home")
        object Mapa : NavigationItem("mapa", Icons.Default.Map, "Mapa")
        object Configuracion : NavigationItem("configuracion", Icons.Default.Accessibility, "Conf")
        object Perfil : NavigationItem("perfil", Icons.Default.Settings, "Perfil")

}

