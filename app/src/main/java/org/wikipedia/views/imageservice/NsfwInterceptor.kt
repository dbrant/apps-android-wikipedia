package org.wikipedia.views.imageservice

import android.graphics.Bitmap
import android.os.Build
import coil3.asImage
import coil3.intercept.Interceptor
import coil3.request.ImageResult
import coil3.request.SuccessResult
import coil3.toBitmap
import org.wikipedia.settings.Prefs
import org.wikipedia.util.log.L
import java.util.Collections

/**
 * Coil [Interceptor] that detects NSFW images on-device via [NsfwClassifier] and replaces
 * flagged images with a strongly blurred version before they reach the UI.
 *
 * Strategy: classify-then-display (inference is ~5–20 ms on mid-range devices, so the
 * brief additional decode latency is not perceptible). If [Prefs.isNsfwFilterEnabled] is
 * false the interceptor is a no-op. Errors in the classifier never block image display.
 *
 * [flaggedUrls] is a public, thread-safe set that the UI layer may query to show a
 * "Sensitive content – tap to reveal" overlay on affected views.
 */
class NsfwInterceptor : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val result = chain.proceed()

        if (!Prefs.isNsfwFilterEnabled || result !is SuccessResult) return result

        var score = 0f
        val millis = System.currentTimeMillis()
        return try {
            val bitmap = result.image.toBitmap()
            score = NsfwClassifier.getInstance(chain.request.context).score(bitmap)
            if (score >= NsfwClassifier.NSFW_THRESHOLD) {
                val dataKey = chain.request.data.toString()
                flaggedUrls.add(dataKey)
                // RenderEffect is applied at the ImageView layer on Android 12+.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) result else result.copy(image = blurBitmap(bitmap).asImage())
            } else {
                flaggedUrls.remove(chain.request.data.toString())
                result
            }
        } catch (e: Exception) {
            L.e(e)
            result
        }.also {
            L.d("NSFW classification took ${System.currentTimeMillis() - millis}ms, score=$score")
        }
    }

    private fun blurBitmap(src: Bitmap): Bitmap {
        val output = src.copy(src.config ?: Bitmap.Config.ARGB_8888, true)
        repeat(BLUR_PASSES) { boxBlur(output, BLUR_RADIUS) }
        return output
    }

    /**
     * Single-pass separable box blur. Running [BLUR_PASSES] times approximates a Gaussian
     * and produces a strong enough blur to obscure NSFW content without RenderScript or
     * any deprecated API.
     */
    private fun boxBlur(bitmap: Bitmap, radius: Int) {
        val w = bitmap.width
        val h = bitmap.height
        val source = IntArray(w * h)
        val temp = IntArray(w * h)
        val dest = IntArray(w * h)
        bitmap.getPixels(source, 0, w, 0, 0, w, h)
        boxBlurHorizontal(source, temp, w, h, radius)
        boxBlurVertical(temp, dest, w, h, radius)
        bitmap.setPixels(dest, 0, w, 0, 0, w, h)
    }

    private fun boxBlurHorizontal(source: IntArray, dest: IntArray, w: Int, h: Int, radius: Int) {
        val diameter = 2 * radius + 1
        for (y in 0 until h) {
            var a = 0; var r = 0; var g = 0; var b = 0
            for (x in -radius..radius) {
                val px = source[y * w + x.coerceIn(0, w - 1)]
                a += (px ushr 24) and 0xFF
                r += (px shr 16) and 0xFF
                g += (px shr 8) and 0xFF
                b += px and 0xFF
            }
            for (x in 0 until w) {
                dest[y * w + x] =
                    ((a / diameter) shl 24) or
                    ((r / diameter) shl 16) or
                    ((g / diameter) shl 8) or
                    (b / diameter)
                val leading = source[y * w + (x + radius + 1).coerceAtMost(w - 1)]
                val trailing = source[y * w + (x - radius).coerceAtLeast(0)]
                a += ((leading ushr 24) and 0xFF) - ((trailing ushr 24) and 0xFF)
                r += ((leading shr 16) and 0xFF) - ((trailing shr 16) and 0xFF)
                g += ((leading shr 8) and 0xFF) - ((trailing shr 8) and 0xFF)
                b += (leading and 0xFF) - (trailing and 0xFF)
            }
        }
    }

    private fun boxBlurVertical(source: IntArray, dest: IntArray, w: Int, h: Int, radius: Int) {
        val diameter = 2 * radius + 1
        for (x in 0 until w) {
            var a = 0; var r = 0; var g = 0; var b = 0
            for (y in -radius..radius) {
                val px = source[y.coerceIn(0, h - 1) * w + x]
                a += (px ushr 24) and 0xFF
                r += (px shr 16) and 0xFF
                g += (px shr 8) and 0xFF
                b += px and 0xFF
            }
            for (y in 0 until h) {
                dest[y * w + x] =
                    ((a / diameter) shl 24) or
                    ((r / diameter) shl 16) or
                    ((g / diameter) shl 8) or
                    (b / diameter)
                val leading = source[(y + radius + 1).coerceAtMost(h - 1) * w + x]
                val trailing = source[(y - radius).coerceAtLeast(0) * w + x]
                a += ((leading ushr 24) and 0xFF) - ((trailing ushr 24) and 0xFF)
                r += ((leading shr 16) and 0xFF) - ((trailing shr 16) and 0xFF)
                g += ((leading shr 8) and 0xFF) - ((trailing shr 8) and 0xFF)
                b += (leading and 0xFF) - (trailing and 0xFF)
            }
        }
    }

    companion object {
        const val BLUR_RADIUS = 32
        private const val BLUR_PASSES = 3

        /**
         * Thread-safe set of image URLs that were flagged as NSFW in this session.
         * The UI layer may use this to show a "Sensitive content" overlay or tap-to-reveal
         * affordance on the corresponding [android.widget.ImageView].
         */
        val flaggedUrls: MutableSet<String> = Collections.synchronizedSet(mutableSetOf())
    }
}
