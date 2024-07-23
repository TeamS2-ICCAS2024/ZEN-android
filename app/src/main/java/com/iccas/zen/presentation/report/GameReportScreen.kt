package com.iccas.zen.presentation.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.components.TitleWithHighligher
import com.iccas.zen.presentation.report.reportComponents.ReportTitle
import com.iccas.zen.presentation.report.viewModel.ReportViewModel
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.utils.formatDuration
import com.iccas.zen.utils.formatLocalDateTime

@Composable
fun GameReportScreen(navController: NavController, gameId: Long) {
    val viewModel: ReportViewModel = viewModel()

    LaunchedEffect(gameId) {
        viewModel.getTetrisResult(gameId)
    }

    val tetrisResult = viewModel.tetrisResult.collectAsState().value

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ReportTitle(
                backOnClick = { navController.navigate("report/game") },
                highlightText = "Anger Control"
            )
            Spacer(modifier = Modifier.height(10.dp))
            tetrisResult?.data?.let { result ->
                TitleWithHighligher(title = "heart rate", highLighterWidth = 100.dp)

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeartRateGraph(
                        heartRates = result.heartRateList,
                        baseHeartRate = result.baseHeartRate.baseHeart,
                        averageHeartRate = result.averageHearRate,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.White)
                            .border(1.dp, Color.Black)
                            .padding(vertical = 5.dp, horizontal = 1.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    GraphLegend(
                        baseHeartRate = result.baseHeartRate.baseHeart,
                        maxHeartRate = result.heartRateList.maxOrNull() ?: 100,
                        minHeartRate = result.heartRateList.minOrNull() ?: 0
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                TitleWithHighligher(title = "overview", highLighterWidth = 95.dp)
                Spacer(modifier = Modifier.height(5.dp))

                // 3행 2열 형식의 Overview details
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .padding(end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OverviewColumn(title = "level", value = result.level.toString())
                        Spacer(modifier = Modifier.height(15.dp))
                        OverviewColumn(title = "play time", value = formatDuration(result.playTime))
                        Spacer(modifier = Modifier.height(15.dp))
                        OverviewColumn(title = "when", value = formatLocalDateTime(result.gameStartTime, pattern =
                        "yyyy-MM-dd\nHH:mm"))
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OverviewColumn(title = "score", value = result.score.toString())
                        Spacer(modifier = Modifier.height(15.dp))
                        OverviewColumn(title = "lives", value = result.lives.toString())
                        Spacer(modifier = Modifier.height(15.dp))
                        OverviewColumn(title = "average HR", value = result.averageHearRate.toInt().toString())
                    }
                }
            } ?: run {
                Text(
                    text = "Loading...",
                    color = Brown40,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun HeartRateGraph(
    heartRates: List<Int>,
    baseHeartRate: Int,
    averageHeartRate: Float,
    modifier: Modifier = Modifier
) {
    val maxRate = (heartRates.maxOrNull() ?: 100).toFloat()
    val minRate = (heartRates.minOrNull() ?: 0).toFloat()
    val rateRange = maxRate - minRate

    Canvas(modifier = modifier) {
        val xInterval = if (heartRates.size > 1) size.width / (heartRates.size - 1) else size.width
        val yInterval = if (rateRange > 0) size.height / rateRange else size.height

        // Draw heart rate line
        val path = Path().apply {
            moveTo(0f, size.height - (heartRates[0] - minRate) * yInterval)
            heartRates.forEachIndexed { index, rate ->
                val x = index * xInterval
                val y = size.height - (rate - minRate) * yInterval
                lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Draw base heart rate line
        val baseY = size.height - (baseHeartRate - minRate) * yInterval
        drawLine(
            color = Color.Blue,
            start = androidx.compose.ui.geometry.Offset(0f, baseY),
            end = androidx.compose.ui.geometry.Offset(size.width, baseY),
            strokeWidth = 3.dp.toPx()
        )

        // Draw limit heart rate line
        val limitY = size.height - (baseHeartRate + 10 - minRate) * yInterval
        drawLine(
            color = Color.DarkGray,
            start = androidx.compose.ui.geometry.Offset(0f, limitY),
            end = androidx.compose.ui.geometry.Offset(size.width, limitY),
            strokeWidth = 3.dp.toPx()
        )
    }
}

@Composable
fun GraphLegend(baseHeartRate: Int, maxHeartRate: Int, minHeartRate: Int) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            LegendItem(color = Color.Blue, label = "Base HR: $baseHeartRate")
            LegendItem(color = Color.DarkGray, label = "Limit HR: ${baseHeartRate + 10}")
        }
        Column(
            modifier = Modifier.padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            LegendItem(color = Color.Black, label = "Max HR: $maxHeartRate")
            LegendItem(color = Color.Black, label = "Min HR: $minHeartRate")
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(15.dp,4.dp)) {
            drawRect(color = color)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun OverviewColumn(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 20.sp, textAlign = TextAlign.Center)
    }
}
