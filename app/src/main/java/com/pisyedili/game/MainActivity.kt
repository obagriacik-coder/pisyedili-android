package com.pisyedili.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                MenuScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // <-- EKLENDİ
@Composable
fun MenuScreen() {
    // Durumlar
    var language by remember { mutableStateOf("TR") }           // TR / EN
    var tableColor by remember { mutableStateOf("GREEN") }      // GREEN / BLUE / BLACK
    var deck by remember { mutableStateOf("GIRLS") }            // GIRLS / CLASSIC / BIKINI
    var p1 by remember { mutableStateOf(TextFieldValue("Oyuncu 1")) }
    var p2 by remember { mutableStateOf(TextFieldValue("Oyuncu 2")) }
    var p3 by remember { mutableStateOf(TextFieldValue("Oyuncu 3")) }
    var p4 by remember { mutableStateOf(TextFieldValue("Oyuncu 4")) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pis Yedili — Menü") }) }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Dil seçimi
            SectionTitle("Dil / Language")
            RadioRow(
                options = listOf("TR", "EN"),
                selected = language,
                labels = mapOf("TR" to "TR", "EN" to "EN"),
            ) { language = it }

            // Masa rengi
            SectionTitle("Masa Rengi")
            RadioRow(
                options = listOf("GREEN", "BLUE", "BLACK"),
                selected = tableColor,
                labels = mapOf("GREEN" to "Koyu Yeşil", "BLUE" to "Koyu Mavi", "BLACK" to "Siyah")
            ) { tableColor = it }

            // Deste seçimi
            SectionTitle("Deste")
            RadioRow(
                options = listOf("GIRLS", "CLASSIC", "BIKINI"),
                selected = deck,
                labels = mapOf("GIRLS" to "Güzel Kızlar", "CLASSIC" to "Klasik", "BIKINI" to "Bikini/Mayolu")
            ) { deck = it }

            // Oyuncu isimleri
            SectionTitle("Oyuncu İsimleri")
            OutlinedTextField(
                value = p1, onValueChange = { p1 = it }, modifier = Modifier.fillMaxWidth(),
                label = { Text("İsim 1") })
            OutlinedTextField(
                value = p2, onValueChange = { p2 = it }, modifier = Modifier.fillMaxWidth(),
                label = { Text("İsim 2") })
            OutlinedTextField(
                value = p3, onValueChange = { p3 = it }, modifier = Modifier.fillMaxWidth(),
                label = { Text("İsim 3") })
            OutlinedTextField(
                value = p4, onValueChange = { p4 = it }, modifier = Modifier.fillMaxWidth(),
                label = { Text("İsim 4") })

            // Başlat
            val allNamesOk = listOf(p1.text, p2.text, p3.text, p4.text).all { it.isNotBlank() }
            Button(
                enabled = allNamesOk,
                onClick = {
                    // TODO: Sonraki adımda oyun ekranına geçiş (Navigation) eklenecek.
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Oyunu Başlat")
            }

            // Seçim özeti (debug için)
            Divider()
            Text(
                "Seçimler → Dil=$language, Masa=$tableColor, Deste=$deck, Oyuncular=${p1.text}, ${p2.text}, ${p3.text}, ${p4.text}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun RadioRow(
    options: List<String>,
    selected: String,
    labels: Map<String, String>,
    onSelected: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onSelected(option) }
            ) {
                RadioButton(
                    selected = (option == selected),
                    onClick = { onSelected(option) }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(labels[option] ?: option)
            }
        }
    }
}
