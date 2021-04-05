package org.wikipedia.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.wikipedia.WikipediaApp
import org.wikipedia.dataclient.Service
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.util.log.L

class CommonsDraweeView(context: Context, attrs: AttributeSet?) : FaceAndColorDetectImageView(context, attrs) {
    private var disposable: Disposable? = null

    fun loadImage(commonsTitle: String) {
        cancel()
        disposable = ServiceFactory.get(WikiSite(Service.COMMONS_URL))
                .getImageInfo(commonsTitle, WikipediaApp.getInstance().appOrSystemLanguageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> loadImage(Uri.parse(response.query()!!.firstPage()!!.imageInfo()!!.thumbUrl)) }) { L.e(it) }
    }

    private fun cancel() {
        disposable?.dispose()
        disposable = null
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }
}
