package org.wikipedia.views.imageservice

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import org.wikipedia.util.log.L
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

/**
 * On-device NSFW image classifier backed by a TensorFlow Lite model.
 *
 * Expected model file: place `nsfw_classifier.tflite` in `app/src/main/assets/`.
 * The model must accept a float32 input tensor of shape [1, 224, 224, 3] (BGR channel order,
 * ImageNet mean-subtracted) and produce a float32 output tensor of shape [1, 2], where
 * index 0 = SFW probability and index 1 = NSFW probability (softmax).
 *
 * A pre-converted TFLite version of Yahoo's open_nsfw model (or any compatible
 * MobileNet/VGG-based two-class classifier) is suitable here.
 *
 * Thread safety: [score] is synchronized; concurrent callers are serialized.
 */
class NsfwClassifier private constructor(context: Context) {

    private val interpreter: Interpreter by lazy {
        val afd = context.assets.openFd(MODEL_FILENAME)
        FileInputStream(afd.fileDescriptor).use { fis ->
            val mappedBuffer = fis.channel.map(
                FileChannel.MapMode.READ_ONLY,
                afd.startOffset,
                afd.declaredLength
            )
            Interpreter(mappedBuffer)
        }
    }

    /**
     * Returns a probability in [0, 1] where values closer to 1 indicate NSFW content.
     * Returns 0 on any error so that failures are non-blocking (images still display).
     */
    @Synchronized
    fun score(bitmap: Bitmap): Float {
        return try {
            val inputBuffer = preprocessBitmap(bitmap)
            val output = Array(1) { FloatArray(OUTPUT_CLASSES) }
            interpreter.run(inputBuffer, output)
            // output[0][1] is the NSFW class probability
            output[0][1]
        } catch (e: Exception) {
            L.e(e)
            0f
        }
    }

    /**
     * Converts a [Bitmap] to a [ByteBuffer] suitable for the model.
     * Applies BGR channel order with ImageNet mean subtraction, matching open_nsfw preprocessing.
     */
    private fun preprocessBitmap(bitmap: Bitmap): ByteBuffer {
        val processedBitmap = yahooPreprocess(bitmap)
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * CHANNELS)
        byteBuffer.order(ByteOrder.nativeOrder())
        val pixels = IntArray(INPUT_SIZE * INPUT_SIZE)
        processedBitmap.getPixels(pixels, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)
        for (pixel in pixels) {
            // BGR channel order, ImageNet mean subtraction (used by VGG / open_nsfw)
            byteBuffer.putFloat(Color.blue(pixel) - MEAN_B)
            byteBuffer.putFloat(Color.green(pixel) - MEAN_G)
            byteBuffer.putFloat(Color.red(pixel) - MEAN_R)
        }
        return byteBuffer
    }

    /**
     * Yahoo/OpenNSFW preprocessing:
     * 1) Resize to 256x256
     * 2) JPEG round-trip in memory
     * 3) Center crop to 224x224
     */
    private fun yahooPreprocess(bitmap: Bitmap): Bitmap {
        val resized = bitmap.scale(PRE_RESIZE, PRE_RESIZE)
        val jpegRoundTrip = ByteArrayOutputStream().use { baos ->
            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val bytes = baos.toByteArray()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
        val source = jpegRoundTrip ?: resized
        val cropOffset = (PRE_RESIZE - INPUT_SIZE) / 2
        return Bitmap.createBitmap(source, cropOffset, cropOffset, INPUT_SIZE, INPUT_SIZE)
    }

    companion object {
        private const val MODEL_FILENAME = "nsfw_classifier.tflite"
        private const val PRE_RESIZE = 256
        private const val INPUT_SIZE = 224
        private const val CHANNELS = 3
        private const val OUTPUT_CLASSES = 2

        // ImageNet channel means (BGR order) used by open_nsfw / VGG-family models
        private const val MEAN_B = 104.0f
        private const val MEAN_G = 117.0f
        private const val MEAN_R = 123.0f

        @Volatile private var instance: NsfwClassifier? = null

        fun getInstance(context: Context): NsfwClassifier =
            instance ?: synchronized(this) {
                instance ?: NsfwClassifier(context.applicationContext).also { instance = it }
            }
    }
}
