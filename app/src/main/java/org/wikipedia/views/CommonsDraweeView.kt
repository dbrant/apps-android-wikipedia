package org.wikipedia.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.wikipedia.Constants
import org.wikipedia.WikipediaApp
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.util.log.L

class CommonsDraweeView : FaceAndColorDetectImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var disposable: Disposable? = null

    fun loadImage(commonsTitle: String) {
        cancel()
        disposable = ServiceFactory.get(Constants.commonsWikiSite)
                .getImageInfo(commonsTitle, WikipediaApp.instance.appOrSystemLanguageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> loadImage(Uri.parse(response.query!!.firstPage()!!.imageInfo()!!.thumbUrl)) }) { L.e(it) }
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
