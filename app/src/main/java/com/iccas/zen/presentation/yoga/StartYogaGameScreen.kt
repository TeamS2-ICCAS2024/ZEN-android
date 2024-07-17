package com.iccas.zen.presentation.yoga

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun StartYogaGameScreen(navController: NavHostController) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.CAMERA)
        } else {
            hasCameraPermission = true
        }
    }

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "To view your pose,\nPlease grant\ncamera permission !",
                fontSize = 23.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { if (hasCameraPermission) navController.navigate("countdown/yoga_game") },
                colors = ButtonDefaults.buttonColors(
                    if (hasCameraPermission) Color.Black else Color.Gray
                ),
                enabled = hasCameraPermission
            ) {
                Text(text = "Start Game")
            }
        }
    }
}
