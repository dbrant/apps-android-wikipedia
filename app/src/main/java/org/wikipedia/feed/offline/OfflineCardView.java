package org.wikipedia.feed.offline;

import android.content.Context;

import org.wikipedia.R;
import org.wikipedia.feed.view.DefaultFeedCardView;

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
    }

    @OnClick(R.id.view_offline_action_go_online) void onGoOnlineClick() {
        if (getCallback() != null) {
            getCallback().onGoOnline();
        }
    }
}