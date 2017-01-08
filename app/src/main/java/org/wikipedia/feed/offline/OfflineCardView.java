package org.wikipedia.feed.offline;

import android.content.Context;

import org.wikipedia.R;
import org.wikipedia.feed.view.DefaultFeedCardView;
import org.wikipedia.util.FeedbackUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfflineCardView extends DefaultFeedCardView<OfflineCard> {
    public interface Callback {
        void onGoOnline();
    }

    public OfflineCardView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_card_offline, this);
        ButterKnife.bind(this);
        FeedbackUtil.setToolbarButtonLongPressToast(findViewById(R.id.voice_search_button));
    }

    @OnClick(R.id.search_container) void onGoOnlineClick() {
        if (getCallback() != null) {
            getCallback().onGoOnline();
        }
    }
}