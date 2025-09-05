package com.pisyedili.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PisYediliMenuApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PisYediliMenuApp() {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pis Yedili — Menü") }
                )
            }
        ) { inner ->
            val scroll = rememberScrollState()

            // ---- State'ler
            var lang by remember { mutableStateOf("TR") }
            var tableColor by remember { mutableStateOf("GREEN") }
            var deck by remember { mutableStateOf("GIRLS") }
            var p1 by remember { mutableStateOf("Oyuncu 1") }
            var p2 by remember { mutableStateOf("Oyuncu 2") }
            var p3 by remember { mutableStateOf("Oyuncu 3") }
            var p4 by remember { mutableStateOf("Oyuncu 4") }

            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scroll)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                SectionCard(
                    icon = { Icon(Icons.Outlined.Flag, contentDescription = null) },
                    title = "Dil / Language"
                ) {
                    TwoChoiceRow(
                        left = "TR",
                        right = "EN",
                        selected = lang,
                        onSelect = { lang = it }
                    )
                }

                SectionCard(
                    icon = { Icon(Icons.Outlined.ColorLens, contentDescription = null) },
                    title = "Masa Rengi"
                ) {
                    TwoChoiceRow(
                        left = "Koyu Yeşil",
                        right = "Koyu Mavi",
                        selected = if (tableColor == "GREEN") "Koyu Yeşil" else "Koyu Mavi",
                    ) { sel ->
                        tableColor = if (sel == "Koyu Yeşil") "GREEN" else "BLUE"
                    }
                }

                SectionCard(
                    icon = { Icon(Icons.Outlined.Style, contentDescription = null) },
                    title = "Deste"
                ) {
                    TwoChoiceRow(
                        left = "Güzel Kızlar",
                        right = "Klasik",
                        selected = if (deck == "GIRLS") "Güzel Kızlar" else "Klasik",
                    ) { sel ->
                        deck = if (sel == "Güzel Kızlar") "GIRLS" else "CLASSIC"
                    }
                }

                SectionCard(
                    icon = { Icon(Icons.Outlined.People, contentDescription = null) },
                    title = "Oyuncu İsimleri"
                ) {
                    PlayerField(label = "İsim 1", value = p1, onChange = { p1 = it })
                    Spacer(Modifier.height(8.dp))
                    PlayerField(label = "İsim 2", value = p2, onChange = { p2 = it })
                    Spacer(Modifier.height(8.dp))
                    PlayerField(label = "İsim 3", value = p3, onChange = { p3 = it })
                    Spacer(Modifier.height(8.dp))
                    PlayerField(label = "İsim 4", value = p4, onChange = { p4 = it })
                }

                // Başlat butonu
                Button(
                    onClick = { /* TODO: Oyun ekranına geçiş */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Oyunu Başlat", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }

                // Özet
                SummaryCard(
                    text = "Seçimler → Dil=$lang, Masa=$tableColor, Deste=$deck, Oyuncular=$p1, $p2, $p3, $p4"
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    icon: @Composable () -> Unit,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                icon()
                Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun TwoChoiceRow(
    left: String,
    right: String,
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioChip(text = left, selected = selected == left) { onSelect(left) }
        RadioChip(text = right, selected = selected == right) { onSelect(right) }
    }
}

@Composable
private fun RadioChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text, fontSize = 18.sp) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    )
}

@Composable
private fun SummaryCard(text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Start
        )
    }
}
