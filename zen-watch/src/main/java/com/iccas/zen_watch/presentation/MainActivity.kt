package com.iccas.zen_watch.presentation

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var heartRateSensor: Sensor? = null
    private var heartRate: Int by mutableStateOf(0)

    private val bluetoothAdapter: BluetoothAdapter? by lazy { BluetoothAdapter.getDefaultAdapter() }

    private var bluetoothPermissionGranted by mutableStateOf(false)
    private var sensorPermissionGranted by mutableStateOf(false)
    private var statusText by mutableStateOf("Disconnected")
    private var messageText by mutableStateOf("")

    private val requestBluetoothPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        try {
            bluetoothPermissionGranted = isGranted
            if (isGranted) {
                checkSensorPermission()
            } else {
                Log.e("Permission", "Bluetooth permission denied")
            }
        } catch (e: Exception) {
            Log.e("Permission", "Exception in Bluetooth permission request: ${e.message}", e)
        }
    }

    private val requestSensorPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        try {
            sensorPermissionGranted = isGranted
            if (isGranted) {
                registerHeartRateSensorListener()
            } else {
                Log.e("Permission", "Sensor permission denied")
            }
        } catch (e: Exception) {
            Log.e("Permission", "Exception in Sensor permission request: ${e.message}", e)
        }
    }

    companion object {
        const val STATE_LISTENING = 1
        const val STATE_CONNECTING = 2
        const val STATE_CONNECTED = 3
        const val STATE_CONNECTION_FAILED = 4
        const val STATE_MESSAGE_RECEIVED = 5
    }

    private val handler = Handler(Handler.Callback { msg ->
        try {
            when (msg.what) {
                STATE_LISTENING -> statusText = "Listening"
                STATE_CONNECTING -> statusText = "Connecting"
                STATE_CONNECTED -> statusText = "Connected"
                STATE_CONNECTION_FAILED -> statusText = "Connection Failed"
                STATE_MESSAGE_RECEIVED -> {
                    val readBuff = msg.obj as ByteArray
                    val tempMsg = String(readBuff, 0, msg.arg1)
                    messageText = tempMsg
                }
            }
        } catch (e: Exception) {
            Log.e("Handler", "Exception in handler: ${e.message}", e)
        }
        true
    })

    private lateinit var sendReceive: SendReceive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        } catch (e: Exception) {
            Log.e("Sensor", "Exception in initializing sensors: ${e.message}", e)
        }

        checkBluetoothPermission()

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            MaterialTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Heart Rate: $heartRate BPM")
                        Text(text = statusText)
                        Text(text = messageText)
                        if (bluetoothPermissionGranted) {
                            BluetoothDeviceList(bluetoothAdapter) { device ->
                                ClientClass(device).start()
                            }
                        } else {
                            Text(text = "Bluetooth permission not granted")
                        }
                    }
                }
            }
        }
    }

    private fun startListeningForHeartRate() {
        try {
            if (sensorPermissionGranted) {
                heartRateSensor?.also { sensor ->
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
            } else {
                checkSensorPermission()
            }
        } catch (e: Exception) {
            Log.e("Sensor", "Exception in starting heart rate listener: ${e.message}", e)
        }
    }

    private fun checkSensorPermission() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
                requestSensorPermissionLauncher.launch(Manifest.permission.BODY_SENSORS)
            } else {
                sensorPermissionGranted = true
                registerHeartRateSensorListener()
                startListeningForHeartRate()
            }
        } catch (e: Exception) {
            Log.e("Permission", "Exception in checking sensor permission: ${e.message}", e)
        }
    }

    private fun checkBluetoothPermission() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                requestBluetoothPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                bluetoothPermissionGranted = true
                checkSensorPermission()
            }
        } catch (e: Exception) {
            Log.e("Permission", "Exception in checking Bluetooth permission: ${e.message}", e)
        }
    }

    private fun registerHeartRateSensorListener() {
        try {
            heartRateSensor?.also { sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
        } catch (e: Exception) {
            Log.e("Sensor", "Exception in registering heart rate sensor listener: ${e.message}", e)
        }
    }

    private inner class ClientClass(device1: BluetoothDevice) : Thread() {
        private val device: BluetoothDevice = device1
        private val socket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66"))
        }

        override fun run() {
            try {
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                socket?.connect()
                handler.obtainMessage(STATE_CONNECTED).sendToTarget()
                sendReceive = SendReceive(socket!!)
                sendReceive.start()
            } catch (e: IOException) {
                e.printStackTrace()
                handler.obtainMessage(STATE_CONNECTION_FAILED).sendToTarget()
            } catch (e: Exception) {
                Log.e("ClientClass", "Exception in ClientClass: ${e.message}", e)
            }
        }
    }

    private inner class SendReceive(socket: BluetoothSocket) : Thread() {
        private val bluetoothSocket: BluetoothSocket = socket
        private val inputStream: InputStream = bluetoothSocket.inputStream
        private val outputStream: OutputStream = bluetoothSocket.outputStream

        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int

            while (true) {
                try {
                    bytes = inputStream.read(buffer)
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget()
                } catch (e: IOException) {
                    e.printStackTrace()
                    break
                } catch (e: Exception) {
                    Log.e("SendReceive", "Exception in SendReceive: ${e.message}", e)
                }
            }
        }

        fun write(bytes: ByteArray) {
            try {
                outputStream.write(bytes)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                Log.e("SendReceive", "Exception in writing data: ${e.message}", e)
            }
        }
    }

    private fun sendHeartRateViaBluetooth(heartRate: Int) {
        try {
            val message = heartRate.toString()
            sendReceive.write(message.toByteArray())
        } catch (e: IOException) {
            Log.e("Bluetooth", "Failed to send data", e)
        } catch (e: Exception) {
            Log.e("Bluetooth", "Exception in sending heart rate: ${e.message}", e)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        try {
            if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
                heartRate = event.values[0].toInt()
                sendHeartRateViaBluetooth(heartRate)
            }
        } catch (e: Exception) {
            Log.e("Sensor", "Exception in onSensorChanged: ${e.message}", e)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 필요시 정확도 변화 처리
    }
}

@Composable
fun BluetoothDeviceList(bluetoothAdapter: BluetoothAdapter?, onDeviceSelected: (BluetoothDevice) -> Unit) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            permissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } catch (e: Exception) {
            Log.e("Permission", "Exception in BluetoothDeviceList: ${e.message}", e)
        }
    }

    if (permissionGranted) {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val devices = pairedDevices?.toList() ?: listOf()

        LazyColumn {
            items(devices) { device ->
                Text(
                    text = device.name ?: "Unknown Device",
                    modifier = Modifier.clickable {
                        try {
                            onDeviceSelected(device)
                        } catch (e: Exception) {
                            Log.e("Bluetooth", "Error selecting device", e)
                        }
                    }
                )
            }
        }
    } else {
        Text(text = "Bluetooth permission not granted")
    }
}
