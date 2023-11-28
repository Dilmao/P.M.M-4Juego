package com.iessanalbertomagno.dam2.a4juegos.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstGameScreen(navController: NavController) {
    // Estado para la puntuación del juego
    var puntuacion by rememberSaveable { mutableIntStateOf(0) }

    // Configuración del diseño de la pantalla
    Scaffold(
        topBar = { MyFirstTopBar(navController, puntuacion) }
    ) {innerPadding ->
        // Estados para la entrada del usuario y el control del juego
        var eleccionP by rememberSaveable { mutableStateOf("") }
        var dedosP by rememberSaveable { mutableStateOf("") }
        var mensaje by rememberSaveable { mutableStateOf("") }
        var showAlert by rememberSaveable { mutableStateOf(false) }
        var botonPulsado by rememberSaveable { mutableStateOf(false) }
        val context = LocalContext.current

        // Columna principal
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Entrada para la eleccion de pares o nones
            OutlinedTextField(
                value = eleccionP,
                onValueChange = { eleccionP = it },
                label = { Text(text = "Elige pares o nones") }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Entrada para la cantidad de dedos a levantados
            OutlinedTextField(
                value = dedosP,
                onValueChange = { dedosP = it },
                label = { Text(text = "Elige tu tirada (0-3)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(20.dp))
            
            // Botón para iniciar el juego
            Button(onClick = {
                botonPulsado = true
            }) {
                Text(text = "Jugar")
            }

            // Lógica del juego al presionar el botón
            if (botonPulsado) {
                val result = myFirstJugar(eleccionP, dedosP, context, puntuacion)
                puntuacion = result.first
                showAlert = result.second
                mensaje = result.third

                eleccionP = ""
                dedosP = ""
                botonPulsado = false
            }

            // Mostrar el mensaje resultante del juego
            if (showAlert) {
                AlertDialog(
                    title = { Text(text = "Mensaje") },
                    text = { Text(text = mensaje) },
                    onDismissRequest = { showAlert = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showAlert = false
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

// Función que implementa la lógica del juego
@Composable
fun myFirstJugar(
    eleccionP: String,
    dedosP: String,
    context: Context,
    p: Int
): Triple<Int, Boolean, String> {
    var puntuacion = p
    var showAlert = false
    var mensaje = ""

    try {
        val dedosNumerico = dedosP.toInt()

        if ((eleccionP.equals("pares", ignoreCase = true) || eleccionP.equals("nones", ignoreCase = true)) && dedosNumerico >= 0 && dedosNumerico <= 3) {
            val numAleatorio = Random.nextInt(4)
            val total = dedosNumerico + numAleatorio
            var ganador: String

            if (total % 2 == 0) {
                ganador = "pares"
            } else {
                ganador = "nones"
            }

            if (ganador.equals(eleccionP, ignoreCase = true)) {
                ganador += ". ¡¡TU GANAS!!"
                puntuacion++
            } else {
                ganador += ". ¡¡HE GANADO!!"
            }

            mensaje = "Yo he sacado un $numAleatorio, suman $total. Ganan $ganador"
            showAlert = true
        } else {
            // Mostrar un mensaje de error si los valores no son validos
            Toast.makeText(context, "Los valores introducidos no son válidos", Toast.LENGTH_SHORT).show()
        }
    } catch (e: NumberFormatException) {
        // Mostrar un mensaje de error si la tirada no es un número
        Toast.makeText(context, "La tirada introducida no es un número", Toast.LENGTH_SHORT).show()
    }

    return Triple(puntuacion, showAlert, mensaje)
}

// Barra superior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFirstTopBar(navController: NavController, puntuacion: Int) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Pares o Nones") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            // Barra de navegacion para volver
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
            }
        },
        actions = {
            // Icono de puntuacion
            Icon(imageVector = Icons.Filled.Star, contentDescription = "Score")
            Text(text = "$puntuacion")
            Spacer(modifier = Modifier.width(20.dp))
        }
    )
}