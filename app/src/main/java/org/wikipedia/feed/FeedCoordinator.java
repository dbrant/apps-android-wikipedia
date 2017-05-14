package org.wikipedia.feed;

import android.content.Context;
import android.support.annotation.NonNull;

import org.wikipedia.feed.aggregated.AggregatedFeedContentClient;
import org.wikipedia.feed.announcement.AnnouncementClient;
import org.wikipedia.feed.becauseyouread.BecauseYouReadClient;
import org.wikipedia.feed.continuereading.ContinueReadingClient;
import org.wikipedia.feed.mainpage.MainPageClient;
import org.wikipedia.feed.offline.OfflineClient;
import org.wikipedia.feed.random.RandomClient;
import org.wikipedia.feed.searchbar.SearchClient;
import org.wikipedia.offline.OfflineHelper;
import org.wikipedia.util.DeviceUtil;

class FeedCoordinator extends FeedCoordinatorBase {

    FeedCoordinator(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void buildScript(int age) {
        if (!DeviceUtil.isOnline(context) && OfflineHelper.haveCompilation()) {

            if (age == 0) {
                addPendingClient(new SearchClient());
                addPendingClient(new OfflineClient());
            }
            addPendingClient(new ContinueReadingClient());
            if (age == 0) {
                addPendingClient(new RandomClient());
            }

        } else {

            if (age == 0) {
                addPendingClient(new SearchClient());
                addPendingClient(new AnnouncementClient());
            }
            addPendingClient(new AggregatedFeedContentClient());
            addPendingClient(new ContinueReadingClient());
            if (age == 0) {
                addPendingClient(new MainPageClient());
            }
            addPendingClient(new BecauseYouReadClient());
            if (age == 0) {
                addPendingClient(new RandomClient());
            }

        }
    }
}
