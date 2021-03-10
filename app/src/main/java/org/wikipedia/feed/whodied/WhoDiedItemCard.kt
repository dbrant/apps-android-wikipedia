package org.wikipedia.feed.whodied

import android.net.Uri
import android.text.TextUtils
import org.wikipedia.feed.model.Card
import org.wikipedia.feed.model.CardType
import org.wikipedia.page.PageTitle

class WhoDiedItemCard(private val title: PageTitle) : Card() {
    fun pageTitle(): PageTitle {
        return title
    }

    override fun title(): String {
        return title.displayText
    }

    override fun subtitle(): String? {
        return title.description
    }

    override fun image(): Uri? {
        return if (TextUtils.isEmpty(title.thumbUrl)) null else Uri.parse(title.thumbUrl)
    }

    override fun type(): CardType {
        return CardType.BECAUSE_YOU_READ_ITEM
    }
}
