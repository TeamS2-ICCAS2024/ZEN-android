package com.iccas.zen.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.ui.theme.Brown40

@Composable
fun TitleWithHighligher(
    title: String,
    highLighterWidth: Dp
    ) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .size(highLighterWidth, 15.dp)
                    .background(
                        Brown40.copy(alpha = 0.2f),
                        RoundedCornerShape(20.dp),
                    )
            )
        }
        Text(
            text = title,
            color = Brown40,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}