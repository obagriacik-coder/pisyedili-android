package com.pisyedili.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                var lang by remember { mutableStateOf("TR") }
                var table by remember { mutableStateOf("GREEN") }
                var deck by remember { mutableStateOf("GIRLS") }
                var p1 by remember { mutableStateOf("Oyuncu 1") }
                var p2 by remember { mutableStateOf("Oyuncu 2") }
                var p3 by remember { mutableStateOf("Oyuncu 3") }
                var p4 by remember { mutableStateOf("Oyuncu 4") }

                Scaffold(topBar = { SmallTopAppBar(title = { Text("Pis Yedili — Menü") }) }) { inner ->
                    Column(
                        Modifier.fillMaxSize().padding(inner).padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // language
                        Card { Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Dil:")
                            FilterChip(selected = lang=="TR", onClick = { lang="TR" }, label = { Text("TR") })
                            FilterChip(selected = lang=="EN", onClick = { lang="EN" }, label = { Text("EN") })
                        } }
                        // table color
                        Card { Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Masa Rengi:")
                            FilterChip(selected = table=="GREEN", onClick={table="GREEN"}, label={Text("Koyu yeşil")})
                            FilterChip(selected = table=="BLUE", onClick={table="BLUE"}, label={Text("Koyu mavi")})
                            FilterChip(selected = table=="BLACK", onClick={table="BLACK"}, label={Text("Siyah")})
                        } }
                        // deck
                        Card { Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Deste:")
                            FilterChip(selected = deck=="GIRLS", onClick={deck="GIRLS"}, label={Text("Güzel kızlar")})
                            FilterChip(selected = deck=="CLASSIC", onClick={deck="CLASSIC"}, label={Text("Klasik")})
                            FilterChip(selected = deck=="BIKINI", onClick={deck="BIKINI"}, label={Text("Bikini/Mayolu")})
                        } }
                        // names
                        Card {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value=p1, onValueChange={p1=it}, label={Text("İsim 1")}, modifier=Modifier.fillMaxWidth())
                                OutlinedTextField(value=p2, onValueChange={p2=it}, label={Text("İsim 2")}, modifier=Modifier.fillMaxWidth())
                                OutlinedTextField(value=p3, onValueChange={p3=it}, label={Text("İsim 3")}, modifier=Modifier.fillMaxWidth())
                                OutlinedTextField(value=p4, onValueChange={p4=it}, label={Text("İsim 4")}, modifier=Modifier.fillMaxWidth())
                            }
                        }
                        Button(
                            enabled = listOf(p1,p2,p3,p4).all{ it.isNotBlank() },
                            onClick = { /* next step */ },
                            modifier = Modifier.align(Alignment.End),
                            shape = RoundedCornerShape(12.dp)
                        ) { Text("Oyunu Başlat") }
                    }
                }
            }
        }
    }
}
