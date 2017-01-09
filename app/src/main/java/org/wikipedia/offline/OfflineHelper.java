package org.wikipedia.offline;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.kiwix.kiwixmobile.JNIKiwix;
import org.wikipedia.util.StringUtil;

public final class OfflineHelper {

    private static JNIKiwix KIWIX = new JNIKiwix();
    private static boolean OFFLINE;

    public static JNIKiwix kiwix() {
        return KIWIX;
    }

    public static boolean areWeOffline() {
        return OFFLINE;
    }

    public static void goOnline() {
        OFFLINE = false;
    }

    public static void goOffline() {
        // TODO: look for ZIM file(s) automatically, and throw if not found
        boolean success = KIWIX.loadZIM(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wp1.0.8.zim");
        OFFLINE = true;
    }

    @NonNull public static String getZimName() {
        JNIKiwix.JNIKiwixString str = new JNIKiwix.JNIKiwixString();
        KIWIX.getTitle(str);
        return StringUtil.emptyIfNull(str.value());
    }

    @NonNull public static String getZimDescription() {
        return StringUtil.emptyIfNull(KIWIX.getDescription());
    }

    public static void startSearch(@NonNull String term, int count) {
        boolean success = KIWIX.searchSuggestions(term, count);
        if (!success) {
            throw new RuntimeException("Failed to get suggestions for " + term);
        }
    }

    @NonNull public static String getNextSearchResult() {
        JNIKiwix.JNIKiwixString title = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getNextSuggestion(title);
        if (!success || TextUtils.isEmpty(title.value())) {
            throw new RuntimeException("Failed to get next suggestion.");
        }
        return title.value();
    }

    public static boolean titleExists(@NonNull String title) {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        return KIWIX.getPageUrlFromTitle(title, url);
    }

    @NonNull public static String getHtml(@NonNull String title) {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getPageUrlFromTitle(title, url);
        if (!success) {
            throw new RuntimeException("Failed to get contents for " + title);
        }

        JNIKiwix.JNIKiwixString mimeType = new JNIKiwix.JNIKiwixString();
        JNIKiwix.JNIKiwixInt contentSize = new JNIKiwix.JNIKiwixInt();
        byte[] bytes = KIWIX.getContent(url.value(), mimeType, contentSize);
        return new String(bytes);
    }

    @NonNull public static String getRandomTitle() {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getRandomPage(url);
        if (!success || TextUtils.isEmpty(url.value())) {
            throw new RuntimeException("Failed to get random page.");
        }
        Uri uri = Uri.parse(url.value());
        return uri.getLastPathSegment().replace(".html", "");
    }

    private OfflineHelper() {
    }
}
