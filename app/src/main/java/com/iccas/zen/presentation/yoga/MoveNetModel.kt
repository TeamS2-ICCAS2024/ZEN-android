package com.iccas.zen.presentation.yoga

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.io.FileInputStream
import java.io.IOException

class MoveNetModel(context: Context) {
    private val interpreter: Interpreter
    val analyzer: ImageAnalyzer
    private val poseClassifierInterpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(context, "movenet.tflite"))
        poseClassifierInterpreter = Interpreter(loadModelFile(context, "denseModel.tflite"))
        analyzer = ImageAnalyzer(this)
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context, modelPath: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun runMoveNetModel(inputImage: Array<Array<Array<ByteArray>>>): Array<Array<FloatArray>> {
        val output = Array(1) { Array(6) { FloatArray(56) } }
        interpreter.run(inputImage, output)
        return output
    }

    fun classifyPose(keypoints: FloatArray): String {
        val inputArray = arrayOf(keypoints)
        val outputArray = Array(1) { FloatArray(5) } // Assuming 5 classes as per model output
        poseClassifierInterpreter.run(inputArray, outputArray)
        val outputList = outputArray[0].toList()
        val maxScore = outputList.maxOrNull() ?: 0f
        val classIndex = outputList.indexOf(maxScore)
        return if (maxScore > 0.7) getPoseClassName(classIndex) else "Not Detected"
    }

    private fun getPoseClassName(classIndex: Int): String {
        return when (classIndex) {
            0 -> "Cobra Pose"
            1 -> "Bridge Pose"
            2 -> "Cow Pose"
            3 -> "Child Pose"
            4 -> "Tree Pose"
            else -> "unknown"
        }
    }
}
