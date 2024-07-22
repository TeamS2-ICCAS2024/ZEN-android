package com.iccas.zen.presentation.report.reportComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R
import com.iccas.zen.ui.theme.Orange30

@Composable
fun ReportTitle(
    backOnClick: () -> Unit,
    highlightText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable { backOnClick() }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Orange30)) {
                    append(highlightText)
                }
            },
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            "Report",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )
    }
}