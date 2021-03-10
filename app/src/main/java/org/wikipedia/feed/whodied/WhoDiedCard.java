package org.wikipedia.feed.whodied;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wikipedia.R;
import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.feed.model.CardType;
import org.wikipedia.feed.model.ListCard;
import org.wikipedia.util.DateUtil;
import org.wikipedia.util.L10nUtil;

import java.util.Date;
import java.util.List;

public class WhoDiedCard extends ListCard<WhoDiedItemCard> {
    public WhoDiedCard(@NonNull final List<WhoDiedItemCard> itemCards, @NonNull WikiSite wiki) {
        super(itemCards, wiki);
    }

    @Nullable
    @Override public String subtitle() {
        return DateUtil.getFeedCardDateString(new Date());
    }

    @Override
    protected int dismissHashCode() {
        return type().code() + wikiSite().hashCode();
    }

    @Override
    @NonNull
    public String title() {
        return L10nUtil.getStringForArticleLanguage(wikiSite().languageCode(), R.string.view_who_died_card_title);
    }

    @NonNull @Override public CardType type() {
        return CardType.WHO_DIED;
    }
}
