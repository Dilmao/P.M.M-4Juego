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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondGameScreen(navController: NavController) {
    var puntuacion by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        topBar = { MySecondTopBar(navController, puntuacion) }
    ) {innerPadding ->
        var eleccionP by rememberSaveable { mutableStateOf("") }
        var mensaje by rememberSaveable { mutableStateOf("") }
        var showAlert by rememberSaveable { mutableStateOf(false) }
        var botonPulsado by rememberSaveable { mutableStateOf(false) }
        val context = LocalContext.current

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //El usuario elije que sacar
            OutlinedTextField(
                value = eleccionP,
                onValueChange = { eleccionP = it },
                label = { Text(text = "¿Piedra, Papel o Tijera?") })
            Spacer(modifier = Modifier.height(20.dp))

            //Boton para jugar
            Button(onClick = {
                botonPulsado = true
            }) {
                Text(text = "Jugar")
            }

            if (botonPulsado) {
                val result = mySecondJugar(eleccionP, context, puntuacion)
                puntuacion = result.first
                showAlert = result.second
                mensaje = result.third

                botonPulsado = false
                eleccionP = ""
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

@Composable
fun mySecondJugar(
    eleccionP: String,
    context: Context,
    p: Int
): Triple<Int, Boolean, String> {
    //Declaracion de variables
    var puntuacion = p
    var eleccionM = ""
    var showAlert = true
    var mensaje = "Yo he sacado "

    //La maquina elige que sacar
    when (Random.nextInt(3)) {
        0 -> eleccionM = "Piedra"
        1 -> eleccionM = "Papel"
        2 -> eleccionM = "Tijera"
    }
    mensaje += "$eleccionM. "

    //Se comprueba el ganador y se modifica el mensaje en consecuencia
    if (eleccionP.equals(eleccionM, ignoreCase = true)) {
        mensaje += "Hemos empatado"
    } else if (
        eleccionP.equals("Piedra", ignoreCase = true) && eleccionM == "Papel" ||
        eleccionP.equals("Papel", ignoreCase = true) && eleccionM == "Tijera" ||
        eleccionP.equals("Tijera", ignoreCase = true) && eleccionM == "Piedra"
    ) {
        mensaje += "¡¡He ganado!!"
    } else if (
        eleccionP.equals("Piedra", ignoreCase = true) && eleccionM == "Tijera" ||
        eleccionP.equals("Papel", ignoreCase = true) && eleccionM == "Piedra" ||
        eleccionP.equals("Tijera", ignoreCase = true) && eleccionM == "Papel"
    ) {
        mensaje += "¡¡Has ganado!!"
        puntuacion++
    } else {
        //Mensaje de error y se evita mostrar el mensaje de resultado
        Toast.makeText(context, "El valor introducido no es valido", Toast.LENGTH_LONG).show()
        showAlert = false
    }

    return Triple(puntuacion, showAlert, mensaje)
}

//topBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySecondTopBar(navController: NavController, puntuacion: Int) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Piedra, Papel o Tijera") },
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