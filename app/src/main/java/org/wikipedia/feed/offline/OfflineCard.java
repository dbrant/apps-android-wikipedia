package org.wikipedia.feed.offline;

import android.support.annotation.NonNull;

import org.wikipedia.feed.model.Card;
import org.wikipedia.feed.model.CardType;

public class OfflineCard extends Card {
    @Override @NonNull
    public String title() {
        return "";
    }

    @NonNull @Override public CardType type() {
        return CardType.SEARCH_BAR;
    }
}