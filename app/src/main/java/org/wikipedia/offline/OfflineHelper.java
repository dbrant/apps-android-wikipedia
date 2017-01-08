package org.wikipedia.offline;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import org.kiwix.kiwixmobile.JNIKiwix;
import org.wikipedia.util.log.L;

public class OfflineHelper {

    private static JNIKiwix KIWIX = new JNIKiwix();
    private static boolean offline;

    public static JNIKiwix kiwix() {
        return KIWIX;
    }

    public static boolean areWeOffline() {
        return offline;
    }

    public static void goOnline() {
        offline = false;
    }

    public static void goOffline() {
        boolean success = KIWIX.loadZIM(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wp1.0.8.zim");
        offline = true;
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
        if (!success) {
            throw new RuntimeException("Failed to get next suggestion.");
        }
        return title.value;
    }

    public static boolean titleExists(@NonNull String title) {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getPageUrlFromTitle(title, url);
        return success;
    }

    @NonNull public static String getHtml(@NonNull String title) {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getPageUrlFromTitle(title, url);
        if (!success) {
            throw new RuntimeException("Failed to get contents for " + title);
        }

        JNIKiwix.JNIKiwixString mimeType = new JNIKiwix.JNIKiwixString();
        JNIKiwix.JNIKiwixInt contentSize = new JNIKiwix.JNIKiwixInt();
        byte[] bytes = KIWIX.getContent(url.value, mimeType, contentSize);
        return new String(bytes);
    }

    @NonNull public static String getRandomTitle() {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getRandomPage(url);
        if (!success) {
            throw new RuntimeException("Failed to get random page.");
        }
        Uri uri = Uri.parse(url.value);
        return uri.getLastPathSegment().replace(".html", "");
    }
}
