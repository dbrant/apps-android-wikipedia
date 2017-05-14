package org.wikipedia.offline;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;
import org.kiwix.kiwixmobile.JNIKiwix;
import org.wikipedia.util.log.L;

import java.io.IOException;

public final class OfflineHelper {

    private static JNIKiwix KIWIX = new JNIKiwix();
    private static boolean COMPILATION_LOADED;

    public static JNIKiwix kiwix() {
        return KIWIX;
    }

    public static boolean haveCompilation() {
        return COMPILATION_LOADED;
    }

    public static void loadCompilation() {
        if (COMPILATION_LOADED) {
            return;
        }
        // TODO: look for ZIM file(s) automatically, and throw if not found
        COMPILATION_LOADED = KIWIX.loadZIM(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wikipedia_en_wp1-0.8_2017-02.zim");
        if (!COMPILATION_LOADED) {
            L.e("Failed to open ZIM file.");
            //throw new IOException("Failed to open ZIM file.");
        }
    }

    @NonNull public static String getZimName() {
        JNIKiwix.JNIKiwixString str = new JNIKiwix.JNIKiwixString();
        KIWIX.getTitle(str);
        return StringUtils.defaultString(str.value());
    }

    @NonNull public static String getZimDescription() {
        return StringUtils.defaultString(KIWIX.getDescription());
    }

    public static void startSearch(@NonNull String term, int count) throws IOException {
        boolean success = KIWIX.searchSuggestions(term, count);
        if (!success) {
            throw new IOException("Failed to get suggestions for " + term);
        }
    }

    @NonNull public static String getNextSearchResult() throws IOException {
        JNIKiwix.JNIKiwixString title = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getNextSuggestion(title);
        if (!success || TextUtils.isEmpty(title.value())) {
            throw new IOException("Failed to get next suggestion.");
        }
        return title.value();
    }

    public static boolean titleExists(@NonNull String title) {
        if (!COMPILATION_LOADED) {
            return false;
        }
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        return KIWIX.getPageUrlFromTitle(title, url);
    }

    @NonNull public static String getHtml(@NonNull String title) throws IOException {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getPageUrlFromTitle(title, url);
        if (!success) {
            throw new IOException("Failed to get contents for " + title);
        }

        JNIKiwix.JNIKiwixString mimeType = new JNIKiwix.JNIKiwixString();
        JNIKiwix.JNIKiwixInt contentSize = new JNIKiwix.JNIKiwixInt();
        byte[] bytes = KIWIX.getContent(url.value(), mimeType, contentSize);
        return new String(bytes);
    }

    @NonNull public static String getRandomTitle() throws IOException {
        JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();
        boolean success = KIWIX.getRandomPage(url);
        if (!success || TextUtils.isEmpty(url.value())) {
            throw new IOException("Failed to get random page.");
        }
        Uri uri = Uri.parse(url.value());
        return uri.getLastPathSegment().replace(".html", "");
    }

    private OfflineHelper() {
    }
}
