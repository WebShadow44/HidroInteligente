package com.example.hidroponiainteligente

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hidroponiainteligente.ui.theme.HidroponiaInteligenteTheme
import kotlin.random.Random
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HidroponiaInteligenteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SensorDataScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SensorDataScreen(modifier: Modifier = Modifier) {
    // Estados para los valores de los sensores
    var temperatura by remember { mutableIntStateOf(25) }
    var humedad by remember { mutableIntStateOf(70) }
    var nutrientes by remember { mutableDoubleStateOf(6.5) }

    // Función para actualizar los valores de los sensores manualmente
    fun actualizarValores() {
        temperatura = Random.nextInt(20, 31) // Entre 20 y 30 °C
        humedad = Random.nextInt(60, 81) // Entre 60 y 80 %
        nutrientes = Random.nextDouble() * (7 - 5) + 5 // Entre 5.0 y 7.0
    }

    // Simulamos la actualización de datos cada 30 segundos automáticamente
    LaunchedEffect(Unit) {
        while (true) {
            delay(30000) // 30 segundos
            actualizarValores() // Actualización automática
        }
    }

    // Mostrar el logo, título, tarjetas y el botón de actualizar
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.fondo_azul)) // Fondo azul mar claro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.hidroponia), // Asegúrate de agregar un logo a la carpeta drawable
            contentDescription = "Logo de Hidroponía Inteligente",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Título
        Text(
            text = "Hidroponía Inteligente",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Tarjeta para la temperatura (rojo oscuro)
        SensorCard(
            title = "Temperatura",
            value = "$temperatura °C",
            iconId = R.drawable.temperatura, // Icono de temperatura
            color = Color(0xFFB71C1C) // Rojo oscuro
        )

        // Tarjeta para la humedad (azul oscuro)
        SensorCard(
            title = "Humedad",
            value = "$humedad %",
            iconId = R.drawable.humedad, // Icono de humedad
            color = Color(0xFF1A237E) // Azul oscuro
        )

        // Tarjeta para los nutrientes (verde oscuro)
        SensorCard(
            title = "Nutrientes",
            value = "%.2f".format(nutrientes),
            iconId = R.drawable.nutrientes, // Icono de nutrientes
            color = Color(0xFF1B5E20) // Verde oscuro
        )

        // Botón para actualizar manualmente los valores
        Button(
            onClick = {
                actualizarValores() // Actualización manual
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Actualizar Valores")
        }
    }
}

@Composable
fun SensorCard(
    title: String,
    value: String,
    iconId: Int, // Recibe el ID del icono
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.7f)) // Fondo más oscuro para mayor contraste
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Título y valor
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                Text(text = value, style = MaterialTheme.typography.headlineMedium, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SensorDataScreenPreview() {
    HidroponiaInteligenteTheme {
        SensorDataScreen()
    }
}
