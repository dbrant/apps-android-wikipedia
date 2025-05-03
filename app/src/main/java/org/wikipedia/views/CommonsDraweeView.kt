package org.wikipedia.views

import android.content.Context
import android.util.AttributeSet
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.wikipedia.Constants
import org.wikipedia.WikipediaApp
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.util.log.L
import androidx.core.net.toUri

class CommonsDraweeView : FaceAndColorDetectImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var job: Job? = null

    fun loadImage(commonsTitle: String) {
        cancel()
        MainScope().launch(CoroutineExceptionHandler { _, t -> L.e(t) }) {
            val response = ServiceFactory.get(Constants.commonsWikiSite)
                .getImageInfo(commonsTitle, WikipediaApp.instance.appOrSystemLanguageCode)
            val url = response.query?.firstPage()?.imageInfo()?.thumbUrl
            url?.let {
                loadImage(it.toUri())
            }
        }
    }

    private fun cancel() {
        job?.cancel()
        job = null
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }
}
