package org.wikipedia.feed.whodied

import org.wikipedia.R
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.feed.model.CardType
import org.wikipedia.feed.model.ListCard
import org.wikipedia.util.DateUtil.getFeedCardDateString
import org.wikipedia.util.L10nUtil.getStringForArticleLanguage
import java.util.*

class WhoDiedCard(itemCards: List<WhoDiedItemCard>, wiki: WikiSite) : ListCard<WhoDiedItemCard>(itemCards, wiki) {
    override fun subtitle(): String {
        return getFeedCardDateString(Date())
    }

    override fun dismissHashCode(): Int {
        return type().code() + wikiSite().hashCode()
    }

    override fun title(): String {
        return getStringForArticleLanguage(wikiSite().languageCode(), R.string.view_who_died_card_title)
    }

    override fun type(): CardType {
        return CardType.WHO_DIED
    }
}
