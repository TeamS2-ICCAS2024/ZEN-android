package com.iccas.zen.presentation.tetris

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R
import com.iccas.zen.presentation.components.ReplayAndExitControlButtons
import com.iccas.zen.ui.theme.Green50
import com.iccas.zen.ui.theme.Red50
import com.iccas.zen.utils.MusicManager

@Composable
fun TetrisGameOverScreen(
    level: Int?,
    score: Int?,
    lives: Int?,
    onReplay: () -> Unit,
    onExit: () -> Unit
) {
    val context = LocalContext.current

    val musicInitialized = remember { mutableStateOf(false) }

    val stats = listOf(
        "level" to (level ?: 0),
        "score" to (score ?: 0)
    )

    // 게임 오버 시 배경 음악을 정지
    DisposableEffect(Unit) {
        if (!musicInitialized.value) {
            MusicManager.initializeTetrisMusic(context)
            musicInitialized.value = true
        }
        MusicManager.stopTetrisMusic()
        onDispose {
            MusicManager.stopTetrisMusic()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tetris_game_over),
            contentDescription = null,
            modifier = Modifier
                .width(240.dp)
                .padding(end = 20.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))

        stats.forEach { (label, value) ->
            Text(
                text = "$label: $value",
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                val heartIcon = if (index < (lives ?: 0)) {
                    R.drawable.tetris_game_heart_filled
                } else {
                    R.drawable.tetris_game_heart_unfilled
                }
                Image(
                    painter = painterResource(id = heartIcon),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        ReplayAndExitControlButtons(
            modifier = Modifier.size(110.dp, 50.dp),
            onReplay = onReplay,
            onExit = {
                // 메인 음악을 재생한 후 onExit 호출
            onExit()
            },
            replayButtonBackground = Green50,
            exitButtonBackground = Red50,
            replayIconColor = Color.Black,
            exitIconColor = Color.Black,
            iconSize = 30.dp
        )
    }
}
