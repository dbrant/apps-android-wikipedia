package org.wikipedia.feed.offline;

import android.content.Context;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.feed.view.DefaultFeedCardView;
import org.wikipedia.offline.OfflineHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfflineCardView extends DefaultFeedCardView<OfflineCard> {

    @BindView(R.id.view_offline_collection_description) TextView descriptionTextView;

    public interface Callback {
        void onGoOnline();
    }

    public OfflineCardView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_card_offline, this);
        ButterKnife.bind(this);

        descriptionTextView.setText(String.format(context.getString(R.string.offline_collection_description),
                OfflineHelper.getZimName(), OfflineHelper.getZimDescription()));
    }

    @OnClick(R.id.view_offline_action_go_online) void onGoOnlineClick() {
        if (getCallback() != null) {
            getCallback().onGoOnline();
        }
    }
}