package com.pisyedili.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

@Composable
fun MenuScreen() {
    // Durumlar
    var language by remember { mutableStateOf("TR") }           // TR / EN
    var tableColor by remember { mutableStateOf("GREEN") }       // GREEN / BLUE / BLACK
    var deck by remember { mutableStateOf("GIRLS") }             // GIRLS / CLASSIC / BIKINI
    var p1 by remember { mutableStateOf(TextFieldValue("Oyuncu 1")) }
    var p2 by remember { mutableStateOf(TextFieldValue("Oyuncu 2")) }
    var p3 by remember { mutableStateOf(TextFieldValue("Oyuncu 3")) }
    var p4 by remember { mutableStateOf(TextFieldValue("Oyuncu 4")) }

    Scaffold(
        topBar = { SmallTopAppBar(title = { Text("Pis Yedili — Menü") }) }
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
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChoiceChip(selected = language == "TR", onClick = { language = "TR" }, label = "TR")
                ChoiceChip(selected = language == "EN", onClick = { language = "EN" }, label = "EN")
            }

            // Masa rengi
            SectionTitle("Masa Rengi")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChoiceChip(selected = tableColor == "GREEN", onClick = { tableColor = "GREEN" }, label = "Koyu Yeşil")
                ChoiceChip(selected = tableColor == "BLUE", onClick = { tableColor = "BLUE" }, label = "Koyu Mavi")
                ChoiceChip(selected = tableColor == "BLACK", onClick = { tableColor = "BLACK" }, label = "Siyah")
            }

            // Deste seçimi
            SectionTitle("Deste")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChoiceChip(selected = deck == "GIRLS", onClick = { deck = "GIRLS" }, label = "Güzel Kızlar")
                ChoiceChip(selected = deck == "CLASSIC", onClick = { deck = "CLASSIC" }, label = "Klasik")
                ChoiceChip(selected = deck == "BIKINI", onClick = { deck = "BIKINI" }, label = "Bikini/Mayolu")
            }

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
                    // Şimdilik sadece seçimleri SnackBar/Toast gibi göstermek yerine logik olarak placeholder.
                    // Sonraki adımda oyun ekranına navigasyon ekleyeceğiz.
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChoiceChip(selected: Boolean, onClick: () -> Unit, label: String) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(label) })
}
