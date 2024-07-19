package com.iccas.zen.presentation.heart

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.heart.heartComponents.BaselineRecord
import com.iccas.zen.presentation.heart.heartComponents.MeasureTimeRecord
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.utils.formatLocalDateTime

@Composable
fun RecommendMeasureBaseScreen(
    navController: NavController
) {
    val measureHeartViewModel: MeasureHeartViewModel = viewModel()
    val latestBaseData by measureHeartViewModel.latestBaseData.collectAsState()

    measureHeartViewModel.getLatestBase(1)

    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(50.dp))
        Log.d("latestBaseData", "$latestBaseData")

        latestBaseData?.let { response ->
            if (response.status == 200) {
                val formattedMeasureTime = response.data?.let { formatLocalDateTime(it.measureTime) }
                BaselineRecord(type = "Latest Base Record: ${response.data?.baseHeart}")
                Spacer(modifier = Modifier.height(5.dp))
                MeasureTimeRecord(type = "Measured At: $formattedMeasureTime")
            } else {
                BaselineRecord(type = "Latest Base Record: -")
                Spacer(modifier = Modifier.height(5.dp))
                MeasureTimeRecord(type = "Measured At: -")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heart_speech_bubble),
                    contentDescription = null,
                    modifier = Modifier
                        .width(340.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(bottom = 50.dp),
                    text = "We recommend\nmeasuring baseline\n heart rate first.",
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heart_positive_response_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .clickable { navController.navigate("measure_base") },
                    contentScale = ContentScale.Crop
                )

                if (latestBaseData?.status == 200) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.heart_negative_response_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(105.dp)
                            .clickable { navController.navigate("tetris_game") },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(
                            id = R.drawable.heart_negative_response_disabled_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(105.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
