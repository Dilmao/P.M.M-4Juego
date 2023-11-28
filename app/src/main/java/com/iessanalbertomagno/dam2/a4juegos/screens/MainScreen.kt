package com.iessanalbertomagno.dam2.a4juegos.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iessanalbertomagno.dam2.a4juegos.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = { MyMainTopBar() }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Button(onClick = { navController.navigate(route = AppScreens.FirstGameScreen.route) }
                    , modifier = Modifier.padding(10.dp)) {
                    Text(text = "Juego 1")
                }
                Button(onClick = { navController.navigate(route = AppScreens.SecondGameScreen.route) }
                    , modifier = Modifier.padding(10.dp)) {
                    Text(text = "Juego 2")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Button(onClick = { navController.navigate(route = AppScreens.ThirdGameScreen.route) }
                    , modifier = Modifier.padding(10.dp)) {
                    Text(text = "Juego 3")
                }
                Button(onClick = { navController.navigate(route = AppScreens.FourthGameScreen.route) }
                    , modifier = Modifier.padding(10.dp)) {
                    Text(text = "Juego 4")
                }
            }
        }
    }
}

// topBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMainTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Seleccion de juegos") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}