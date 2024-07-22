package com.iccas.zen.presentation.report.reportComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Gray80

@Composable
fun ReportSelectBox(
    type: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(80.dp)
            .background(Gray80, RoundedCornerShape(50))
            .border(2.dp, Brown40, RoundedCornerShape(50))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = type,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Serif,
            color = Brown40
        )
    }
}