package com.iccas.zen.presentation.yoga

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.io.ByteArrayOutputStream

class ImageAnalyzer(private val model: MoveNetModel) : ImageAnalysis.Analyzer {
    val coordinates: SnapshotStateList<Pair<Float, Float>> = mutableStateListOf()
    var onPoseClassified: ((String) -> Unit)? = null

    override fun analyze(imageProxy: ImageProxy) {
        val bitmap = imageProxy.toBitmap()
        val resizedBitmap = resizeBitmap(bitmap)
        val inputArray = convertBitmapToInputArray(resizedBitmap)
        val output = model.runMoveNetModel(inputArray)
        processOutput(output)

        // Pose classification
        val keypoints = extractKeypoints(output)
        val poseClass = model.classifyPose(keypoints)
        Log.d("PoseClassifier", "Predicted pose class: $poseClass")
        onPoseClassified?.invoke(poseClass)
        imageProxy.close()
    }

    private fun processOutput(output: Array<Array<FloatArray>>) {
        Log.d("ImageAnalyzer", "processOutput() called")
        coordinates.clear()
        val keypoints = output[0][0]  // 첫 번째 사람의 키포인트만 사용
        if (keypoints.size >= 51) { // 17 * 3 = 51
            for (i in 0 until 17) {
                val y = keypoints[i * 3]
                val x = keypoints[i * 3 + 1]
                val confidence = keypoints[i * 3 + 2]
                if (confidence > 0.1) {  // 신뢰도가 0.5 이상인 경우만 사용
                    coordinates.add(Pair(x, y))
                } else {
                    coordinates.add(Pair(-1f, -1f)) // 신뢰도가 낮은 경우 임의의 좌표로 설정
                }
            }
        }
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val modelHeight = 256 // 모델이 예상하는 높이
        val modelWidth = 256 // 모델이 예상하는 너비

        // 이미지를 회전
        val matrix = Matrix().apply {
            postScale(-1f, 1f, modelWidth / 2f, modelHeight / 2f) // 좌우 반전
            postRotate(90f) // 90도 회전
        }
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        val resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, modelWidth, modelHeight, true)

        // 높이와 너비가 32의 배수인지 확인하고 패딩 추가
        val newHeight = if (resizedBitmap.height % 32 == 0) resizedBitmap.height else (resizedBitmap.height / 32 + 1) * 32
        val newWidth = if (resizedBitmap.width % 32 == 0) resizedBitmap.width else (resizedBitmap.width / 32 + 1) * 32
        return Bitmap.createScaledBitmap(resizedBitmap, newWidth, newHeight, true)
    }

    private fun convertBitmapToInputArray(bitmap: Bitmap): Array<Array<Array<ByteArray>>> {
        val inputArray = Array(1) { Array(bitmap.height) { Array(bitmap.width) { ByteArray(3) } } }
        for (y in 0 until bitmap.height) {
            for (x in 0 until bitmap.width) {
                val pixel = bitmap.getPixel(x, y)
                inputArray[0][y][x][0] = (((pixel shr 16) and 0xFF) / 255.0f * 255).toInt().toByte() // R
                inputArray[0][y][x][1] = (((pixel shr 8) and 0xFF) / 255.0f * 255).toInt().toByte()  // G
                inputArray[0][y][x][2] = ((pixel and 0xFF) / 255.0f * 255).toInt().toByte()          // B
            }
        }
        return inputArray
    }
    private fun ImageProxy.toBitmap(): Bitmap? {
        Log.d("ImageProxyExt", "toBitmap() started")
        return try {
            if (format == ImageFormat.YUV_420_888) {
                val yBuffer = planes[0].buffer
                val uBuffer = planes[1].buffer
                val vBuffer = planes[2].buffer

                val ySize = yBuffer.remaining()
                val uSize = uBuffer.remaining()
                val vSize = vBuffer.remaining()

                val nv21 = ByteArray(ySize + uSize + vSize)

                yBuffer.get(nv21, 0, ySize)
                vBuffer.get(nv21, ySize, vSize)
                uBuffer.get(nv21, ySize + vSize, uSize)

                val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
                val out = ByteArrayOutputStream()
                yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
                val imageBytes = out.toByteArray()
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Log.d("ImageProxyExt", "Bitmap created successfully")
                bitmap
            } else {
                Log.e("ImageProxyExt", "Unsupported image format: $format")
                null
            }
        } catch (e: Exception) {
            Log.e("ImageProxyExt", "Failed to convert ImageProxy to Bitmap: ${e.message}", e)
            null
        }
    }
}

private fun extractKeypoints(output: Array<Array<FloatArray>>): FloatArray {
    val keypoints = output[0][0]  // 첫 번째 사람의 키포인트만 사용
    return keypoints.copyOfRange(0, 51) // 17 keypoints * 3 (y, x, confidence) = 51
}