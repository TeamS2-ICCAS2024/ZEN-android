package com.iccas.zen.presentation.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithNavBar
import com.iccas.zen.presentation.home.components.TitleSticker

@Composable
fun CollectionScreen(navController: NavController) {
    var selectedCharacter by remember { mutableStateOf<CharacterInfo?>(null) }

    BasicBackgroundWithNavBar(navController = navController) {
        Spacer(modifier = Modifier.height(40.dp))

        TitleSticker(R.drawable.home_title_blue_sticker, "Collection")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // First section
            SectionTitle("2/3 Red House", R.drawable.temp_background)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CollectionItem(
                    imageResId = R.drawable.temp_char,
                    label = "MOZZI",
                    onClick = { selectedCharacter = CharacterInfo("MOZZI", R.drawable.temp_char, "Character description here.") }
                )
                CollectionItem(
                    imageResId = R.drawable.temp_char,
                    label = "BAO",
                    onClick = { selectedCharacter = CharacterInfo("BAO", R.drawable.temp_char, "Character description here.") }
                )
                CollectionItem(
                    imageResId = R.drawable.question_mark,
                    label = null,
                    onClick = { selectedCharacter = null }
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Second section
            SectionTitle("0/3 Blue House", R.drawable.temp_background)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CollectionItem(
                    imageResId = R.drawable.question_mark,
                    label = null,
                    onClick = { selectedCharacter = null }
                )
                CollectionItem(
                    imageResId = R.drawable.question_mark,
                    label = null,
                    onClick = { selectedCharacter = null }
                )
                CollectionItem(
                    imageResId = R.drawable.question_mark,
                    label = null,
                    onClick = { selectedCharacter = null }
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }

    selectedCharacter?.let { character ->
        CharacterPopup(character = character, onDismiss = { selectedCharacter = null })
    }
}

@Composable
fun CollectionItem(imageResId: Int, label: String?, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .width(110.dp)
            .padding(4.dp)
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(90.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(2.dp, Color.White, RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = label ?: "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
        if (label != null) {
            Text(text = label, fontSize = 20.sp, color = Color.Black)
        } else {
            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}

@Composable
fun SectionTitle(text: String, iconResId: Int? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontSize = 20.sp, color = Color.Black)
        if (iconResId != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}