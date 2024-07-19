package com.iccas.zen.presentation.heart.heartComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iccas.zen.R


@Composable
fun RecordStatus(
    iconId: Int,
    type: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = type)
    }
}

@Composable
fun BaselineRecord(type: String) {
    RecordStatus(iconId = R.drawable.heart_baseline_icon, type = type, modifier = Modifier.width(17.dp))
}

@Composable
fun HeartRateRecord(type: String) {
    RecordStatus(iconId = R.drawable.heart_rate_icon, type = type, modifier = Modifier.width(17.dp))
}

@Composable
fun BluetoothRecord(type: String) {
    RecordStatus(iconId = R.drawable.heart_bluetooth, type = type, modifier = Modifier.size(17.dp))
}

@Composable
fun MeasureTimeRecord(type: String) {
    RecordStatus(iconId = R.drawable.heart_measure_calendar, type = type, modifier = Modifier.size(17.dp))
}



