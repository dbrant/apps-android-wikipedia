package org.wikipedia.feed.whodied;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wikipedia.feed.view.ListCardItemView;
import org.wikipedia.feed.view.ListCardRecyclerAdapter;
import org.wikipedia.feed.view.ListCardView;
import org.wikipedia.history.HistoryEntry;
import org.wikipedia.views.DefaultViewHolder;

import java.util.List;

public class WhoDiedCardView extends ListCardView<WhoDiedCard> {

    public WhoDiedCardView(Context context) {
        super(context);
    }

    @Override public void setCard(@NonNull WhoDiedCard card) {
        super.setCard(card);
        header(card);
        set(new RecyclerAdapter(card.items()));
        setLayoutDirectionByWikiSite(card.wikiSite(), getLayoutDirectionView());
    }

    private void header(@NonNull final WhoDiedCard card) {
        headerView().setTitle(card.title())
                .setLangCode(card.wikiSite().languageCode())
                .setCard(card)
                .setCallback(getCallback());
    }

    private class RecyclerAdapter extends ListCardRecyclerAdapter<WhoDiedItemCard> {
        RecyclerAdapter(@NonNull List<WhoDiedItemCard> items) {
            super(items);
        }

        @Nullable @Override protected ListCardItemView.Callback callback() {
            return getCallback();
        }

        @Override
        public void onBindViewHolder(@NonNull DefaultViewHolder<ListCardItemView> holder, int i) {
            WhoDiedItemCard card = item(i);
            holder.getView().setCard(card)
                    .setHistoryEntry(new HistoryEntry(card.pageTitle(),
                            HistoryEntry.SOURCE_FEED_WHO_DIED));
        }
    }
}
