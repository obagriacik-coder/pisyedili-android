package com.pisyedili.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Basit koyu tema
            MaterialTheme(colorScheme = darkColorScheme()) {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    "Pis Yedili — Menü",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                ) { inner ->
                    MenuScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(inner)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
private fun SubtleLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun OptionRadio(
    selected: Boolean,
    onSelect: () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(end = 20.dp)
            .height(IntrinsicSize.Min)
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun PlayerTextField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    // durumlar
    var lang by remember { mutableStateOf("TR") }
    var table by remember { mutableStateOf("GREEN") }
    var deck by remember { mutableStateOf("GIRLS") }

    var p1 by remember { mutableStateOf("Oyuncu 1") }
    var p2 by remember { mutableStateOf("Oyuncu 2") }
    var p3 by remember { mutableStateOf("Oyuncu 3") }
    var p4 by remember { mutableStateOf("Oyuncu 4") }

    val allNamesOk = listOf(p1, p2, p3, p4).all { it.trim().isNotEmpty() }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 8.dp, bottom = 20.dp)
    ) {
        // Dil
        SectionCard {
            SectionTitle("Dil / Language")
            Row {
                OptionRadio(selected = lang == "TR", onSelect = { lang = "TR" }, label = "TR")
                OptionRadio(selected = lang == "EN", onSelect = { lang = "EN" }, label = "EN")
            }
        }

        Spacer(Modifier.height(12.dp))

        // Masa rengi
        SectionCard {
            SectionTitle(if (lang == "TR") "Masa Rengi" else "Table Color")
            Row(Modifier.fillMaxWidth()) {
                OptionRadio(
                    selected = table == "GREEN",
                    onSelect = { table = "GREEN" },
                    label = if (lang == "TR") "Koyu Yeşil" else "Dark Green",
                    modifier = Modifier.weight(1f)
                )
                OptionRadio(
                    selected = table == "BLUE",
                    onSelect = { table = "BLUE" },
                    label = if (lang == "TR") "Koyu Mavi" else "Dark Blue",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Deste
        SectionCard {
            SectionTitle(if (lang == "TR") "Deste" else "Deck")
            Row(Modifier.fillMaxWidth()) {
                OptionRadio(
                    selected = deck == "GIRLS",
                    onSelect = { deck = "GIRLS" },
                    label = if (lang == "TR") "Güzel Kızlar" else "Pretty Girls",
                    modifier = Modifier.weight(1f)
                )
                OptionRadio(
                    selected = deck == "CLASSIC",
                    onSelect = { deck = "CLASSIC" },
                    label = if (lang == "TR") "Klasik" else "Classic",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Oyuncu isimleri
        SectionCard {
            SectionTitle(if (lang == "TR") "Oyuncu İsimleri" else "Player Names")
            Spacer(Modifier.height(6.dp))
            SubtleLabel(if (lang == "TR") "İsim 1" else "Name 1")
            PlayerTextField(value = p1, onChange = { p1 = it }, label = "")
            Spacer(Modifier.height(10.dp))
            SubtleLabel(if (lang == "TR") "İsim 2" else "Name 2")
            PlayerTextField(value = p2, onChange = { p2 = it }, label = "")
            Spacer(Modifier.height(10.dp))
            SubtleLabel(if (lang == "TR") "İsim 3" else "Name 3")
            PlayerTextField(value = p3, onChange = { p3 = it }, label = "")
            Spacer(Modifier.height(10.dp))
            SubtleLabel(if (lang == "TR") "İsim 4" else "Name 4")
            PlayerTextField(value = p4, onChange = { p4 = it }, label = "")
        }

        Spacer(Modifier.height(18.dp))

        // Başlat butonu
        Button(
            enabled = allNamesOk,
            onClick = { /* burada oyun ekranına geçeceksin (navigation ekleyince) */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(if (lang == "TR") "Oyunu Başlat" else "Start Game")
        }

        Spacer(Modifier.height(10.dp))

        // Özet
        Text(
            text = "Seçimler → Dil=$lang, Masa=$table, Deste=$deck, Oyuncular=$p1, $p2, $p3, $p4",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.12f)
        )
    ) {
        Column(Modifier.padding(16.dp), content = content)
    }
}
