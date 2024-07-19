package com.iccas.zen.presentation.heart

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.heart.heartComponents.BluetoothRecord
import com.iccas.zen.presentation.heart.heartComponents.HeartRateRecord
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import kotlinx.coroutines.delay
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

@Composable
fun MeasureBaseScreen(
    measureHeartViewModel: MeasureHeartViewModel,
    navController: NavController
) {
    val activity = LocalContext.current as ComponentActivity
    val bluetoothAdapter: BluetoothAdapter? by lazy { BluetoothAdapter.getDefaultAdapter() }
    var timerValue by remember { mutableStateOf(30) }
    var timerRunning by remember { mutableStateOf(false) }
    var timerDisplay by remember { mutableStateOf("00:30") }

    val handler = remember {
        Handler(Handler.Callback { msg ->
            when (msg.what) {
                MeasureBaseScreen.STATE_LISTENING -> measureHeartViewModel.updateStatus("Listening")
                MeasureBaseScreen.STATE_CONNECTING -> measureHeartViewModel.updateStatus("Connecting")
                MeasureBaseScreen.STATE_CONNECTED -> {
                    measureHeartViewModel.updateStatus("Connected")
                    timerRunning = true
                    measureHeartViewModel.clearHeartRates()
                }
                MeasureBaseScreen.STATE_CONNECTION_FAILED -> measureHeartViewModel.updateStatus("Connection Failed")
                MeasureBaseScreen.STATE_MESSAGE_RECEIVED -> {
                    val readBuff = msg.obj as ByteArray
                    val tempMsg = String(readBuff, 0, msg.arg1)
                    val heartRate = tempMsg.toIntOrNull()
                    if (heartRate != null && heartRate > 0) {
                        measureHeartViewModel.updateHeartRate(heartRate)
                    }
                    measureHeartViewModel.updateReceivedData(tempMsg)
                }
            }
            true
        })
    }

    val requestBluetoothPermissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.BLUETOOTH_CONNECT] == true &&
            permissions[Manifest.permission.BLUETOOTH_SCAN] == true) {
            startServer(bluetoothAdapter, handler, measureHeartViewModel)
        } else {
            Log.e("Bluetooth", "Bluetooth permissions denied")
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            requestBluetoothPermissionsLauncher.launch(
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
            )
        } else {
            startServer(bluetoothAdapter, handler, measureHeartViewModel)
        }
    }

    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            while (timerValue > 0) {
                delay(1000L)
                timerValue -= 1
                timerDisplay = updateTimerDisplay(timerValue)
            }
            timerRunning = false
            measureHeartViewModel.calculateAverageHeartRate()
            val  currentTime = LocalDateTime.now().toString()
            measureHeartViewModel.averageHeartRate.value?.let {
                measureHeartViewModel.saveBase(1,
                    it, currentTime)
            }

            navController.navigate("base_result")
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
                text = timerDisplay,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            HeartRateRecord(type = measureHeartViewModel.receivedData.collectAsState().value)
            BluetoothRecord(type = measureHeartViewModel.statusText.collectAsState().value)
            Spacer(modifier = Modifier.height(70.dp))

            Image(
                painter = painterResource(id = R.drawable.heart_comfort_img),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Measuring baseline.\nPlease rest :)",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                lineHeight = 27.sp
            )
        }
    }
}

private fun startServer(bluetoothAdapter: BluetoothAdapter?, handler: Handler, viewModel: MeasureHeartViewModel) {
    val serverClass = ServerClass(bluetoothAdapter, handler, viewModel)
    serverClass.start()
}

private fun updateTimerDisplay(timeLeft: Int): String {
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    return String.format("%02d:%02d", minutes, seconds)
}

private class ServerClass(
    private val bluetoothAdapter: BluetoothAdapter?,
    private val handler: Handler,
    private val viewModel: MeasureHeartViewModel
) : Thread() {
    private val serverSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothAdapter?.listenUsingRfcommWithServiceRecord("ZEN", UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66"))
    }

    override fun run() {
        var socket: BluetoothSocket?

        while (true) {
            socket = try {
                serverSocket?.accept()
            } catch (e: IOException) {
                Log.e("Bluetooth", "Socket's accept() method failed", e)
                handler.obtainMessage(MeasureBaseScreen.STATE_CONNECTION_FAILED).sendToTarget()
                null
            }

            socket?.also {
                handler.obtainMessage(MeasureBaseScreen.STATE_CONNECTED).sendToTarget()
                val sendReceive = SendReceive(it, handler, viewModel)
                sendReceive.start()
                serverSocket?.close()
                return
            }
        }
    }
}

private class SendReceive(
    private val bluetoothSocket: BluetoothSocket,
    private val handler: Handler,
    private val viewModel: MeasureHeartViewModel
) : Thread() {
    private val inputStream: InputStream = bluetoothSocket.inputStream

    override fun run() {
        val buffer = ByteArray(1024)
        var bytes: Int

        while (true) {
            try {
                bytes = inputStream.read(buffer)
                handler.obtainMessage(MeasureBaseScreen.STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget()
                val tempMsg = String(buffer, 0, bytes)
                val heartRate = tempMsg.toIntOrNull()
                if (heartRate != null && heartRate > 0) {
                    viewModel.updateHeartRate(heartRate)
                }
                viewModel.updateReceivedData(tempMsg)
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }
}

object MeasureBaseScreen {
    const val STATE_LISTENING = 1
    const val STATE_CONNECTING = 2
    const val STATE_CONNECTED = 3
    const val STATE_CONNECTION_FAILED = 4
    const val STATE_MESSAGE_RECEIVED = 5
}
