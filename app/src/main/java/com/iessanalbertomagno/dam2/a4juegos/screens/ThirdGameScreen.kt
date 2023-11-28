package com.iessanalbertomagno.dam2.a4juegos.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iessanalbertomagno.dam2.a4juegos.data.listaCartas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdGameScreen(navController: NavController) {
    var puntuacion by rememberSaveable { mutableIntStateOf(0) }
    var listaBarajada = remember { listaCartas.shuffled() }

    Scaffold(
        topBar = { MyThirdTopBar(navController, puntuacion) }
    ) { innerPadding ->
        var mensaje by rememberSaveable { mutableStateOf("") }
        var cartaSacada by rememberSaveable { mutableStateOf("0") }
        var numPartida by rememberSaveable { mutableIntStateOf(0) }
        var sumaCartas by rememberSaveable { mutableDoubleStateOf(0.0) }
        var botonJugarPulsado by rememberSaveable { mutableStateOf(false) }
        var showAlert by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Se muestra la carta sacada
            Text(text = "Carta sacada: $cartaSacada")
            Text(text = "Suma cartas: $sumaCartas")

            if (sumaCartas > 7.5) {
                mensaje = "Te has pasado, ¡¡He ganado!!"
                showAlert = true
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row {
                // Solo se mostrara el boton si aun quedan cartas por sacar
                if (numPartida < listaCartas.size+1) {
                    // Jugada del usuario
                    Button(onClick = {
                        cartaSacada = listaBarajada[numPartida].toString()
                        if (listaBarajada[numPartida] > 7) {
                            sumaCartas += 0.5
                        } else {
                            sumaCartas += listaBarajada[numPartida]
                        }
                        numPartida++
                    }) {
                        Text(text = "Siguiente carta")
                    }
                }
                
                Spacer(modifier = Modifier.width(20.dp))
                
                Button(onClick = {
                    botonJugarPulsado = true
                }) {
                    Text(text = "Plantarse")
                }
            }

            if (botonJugarPulsado) {
                val result = myThirdJugar(sumaCartas, listaBarajada, numPartida, puntuacion)
                puntuacion = result.first
                mensaje = result.second

                showAlert = true
                botonJugarPulsado = false
            }

            if (showAlert) {
                AlertDialog(
                    title = { Text(text = "Mensaje") },
                    text = { Text(text = mensaje) },
                    onDismissRequest = { showAlert = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showAlert = false
                                cartaSacada = "0"
                                sumaCartas = 0.0
                                listaBarajada = listaBarajada.shuffled()
                                numPartida = 0
                            }
                        ) {
                            Text("Ok")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showAlert = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("Volver")
                        }
                    }
                )
            }
        }
    }
}

fun myThirdJugar(
    sumaCartasP: Double,
    listaBarajada: List<Int>,
    nP: Int,
    p: Int
): Pair<Int, String> {
    var puntuacion = p
    var sumaCartasM = 0.0
    var numPartida = nP
    var mensaje = "Mi total es de "

    while (sumaCartasM < sumaCartasP) {
        sumaCartasM += listaBarajada[numPartida]
        numPartida++
    }
    mensaje += "$sumaCartasM. "

    if (sumaCartasM > 7.5) {
        mensaje += "Me he pasado,\n ¡¡Has ganado!!"
        puntuacion++
    } else if (sumaCartasP < sumaCartasM) {
        mensaje += "¡¡He ganado!!"
    } else {
        mensaje += "Hemos empatado,\n ¡¡He ganado!!"
    }

    //No hace falta retornar numPartida ya que se reiniciara ahora
    return Pair(puntuacion, mensaje)
}

// topBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyThirdTopBar(navController: NavController, puntuacion: Int) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Siete y medio") },
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