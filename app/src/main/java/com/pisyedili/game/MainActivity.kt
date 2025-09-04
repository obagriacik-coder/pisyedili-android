package com.pisyedili.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ColorLens
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val cs = darkColorScheme() // gerekirse colors.xml ile eşlersin
            MaterialTheme(colorScheme = cs) {
                Scaffold(
                    topBar = { FancyTopBar() }
                ) { inner ->
                    MenuScreen(
                        modifier = Modifier
                            .padding(inner)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FancyTopBar() {
    // Mor tonlarda gradient başlık
    val grad = Brush.horizontalGradient(
        listOf(Color(0xFF7C4DFF), Color(0xFF5E35B1))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(grad)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            "Pis Yedili — Menü",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/* ----------------------- Reusable UI ----------------------- */

@Composable
private fun SectionCard(
    icon: @Composable () -> Unit,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon()
                Spacer(Modifier.width(10.dp))
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun SingleChoiceChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEach { opt ->
            FilterChip(
                selected = selected == opt,
                onClick = { onSelected(opt) },
                label = { Text(opt) }
            )
        }
    }
}

@Composable
private fun PlayerField(
    label: String,
    value: String,
    onValue: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValue,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

/* ----------------------- Screen ----------------------- */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    var lang by remember { mutableStateOf("TR") }
    var table by remember { mutableStateOf("Koyu Yeşil") }
    var deck by remember { mutableStateOf("Güzel Kızlar") }

    var p1 by remember { mutableStateOf("Oyuncu 1") }
    var p2 by remember { mutableStateOf("Oyuncu 2") }
    var p3 by remember { mutableStateOf("Oyuncu 3") }
    var p4 by remember { mutableStateOf("Oyuncu 4") }

    Column(modifier) {

        SectionCard(icon = { Icon(Icons.Rounded.Flag, null) }, title = "Dil / Language") {
            SingleChoiceChips(options = listOf("TR", "EN"), selected = lang, onSelected = { lang = it })
        }

        SectionCard(icon = { Icon(Icons.Rounded.ColorLens, null) }, title = "Masa Rengi") {
            SingleChoiceChips(
                options = listOf("Koyu Yeşil", "Koyu Mavi"),
                selected = table,
                onSelected = { table = it }
            )
        }

        SectionCard(icon = { Icon(Icons.Rounded.Style, null) }, title = "Deste") {
            SingleChoiceChips(
                options = listOf("Güzel Kızlar", "Klasik"),
                selected = deck,
                onSelected = { deck = it }
            )
        }

        SectionCard(icon = { Icon(Icons.Rounded.People, null) }, title = "Oyuncu İsimleri") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PlayerField("İsim 1", p1) { p1 = it }
                PlayerField("İsim 2", p2) { p2 = it }
                PlayerField("İsim 3", p3) { p3 = it }
                PlayerField("İsim 4", p4) { p4 = it }
            }
        }

        Spacer(Modifier.height(8.dp))

        val enabled = listOf(p1, p2, p3, p4).all { it.isNotBlank() }

        // Alt “özet + başlat” alanı
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                .padding(16.dp)
        ) {
            Text(
                "Seçimler",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Dil: $lang • Masa: $table • Deste: $deck\n" +
                        "Oyuncular: $p1, $p2, $p3, $p4",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { /* TODO: oyun ekranına geç */ },
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Oyunu Başlat", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(12.dp))
    }
}
