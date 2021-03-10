package org.wikipedia.feed.whodied

import android.content.Context
import org.wikipedia.feed.view.ListCardItemView
import org.wikipedia.feed.view.ListCardRecyclerAdapter
import org.wikipedia.feed.view.ListCardView
import org.wikipedia.history.HistoryEntry
import org.wikipedia.views.DefaultViewHolder

class WhoDiedCardView(context: Context) : ListCardView<WhoDiedCard?>(context) {
    override fun setCard(card: WhoDiedCard) {
        super.setCard(card)
        header(card)
        set(RecyclerAdapter(card.items()))
        setLayoutDirectionByWikiSite(card.wikiSite(), layoutDirectionView)
    }

    private fun header(card: WhoDiedCard) {
        headerView().setTitle(card.title())
                .setLangCode(card.wikiSite().languageCode())
                .setCard(card)
                .setCallback(callback)
    }

    private inner class RecyclerAdapter constructor(items: List<WhoDiedItemCard>) : ListCardRecyclerAdapter<WhoDiedItemCard>(items) {
        override fun callback(): ListCardItemView.Callback? {
            return callback
        }

        override fun onBindViewHolder(holder: DefaultViewHolder<ListCardItemView>, i: Int) {
            val card = item(i)
            holder.view.setCard(card).setHistoryEntry(HistoryEntry(card.pageTitle(),
                    HistoryEntry.SOURCE_FEED_WHO_DIED))
        }
    }
}
