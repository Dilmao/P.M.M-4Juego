package com.iessanalbertomagno.dam2.a4juegos.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FourthGameScreen(navController: NavController) {
    var puntuacion by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        topBar = { MyFouthTopBar(navController, puntuacion) }
    ) {innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding))
        {
            Text(text = "Hola desde Fourth Game Screen")
        }
    }
}

// topBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFouthTopBar(navController: NavController, puntuacion: Int) {
    CenterAlignedTopAppBar(
        title = { Text(text = "JUEGO SIN NOMBRE") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
            }
        },
        actions = {
            Icon(imageVector = Icons.Filled.Star, contentDescription = "Score")
            Text(text = "$puntuacion")
            Spacer(modifier = Modifier.width(20.dp))
        }
    )
}