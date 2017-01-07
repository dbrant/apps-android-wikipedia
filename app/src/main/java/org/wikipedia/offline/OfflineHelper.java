package org.wikipedia.offline;

import android.os.Environment;

import org.kiwix.kiwixmobile.JNIKiwix;
import org.wikipedia.util.log.L;

public class OfflineHelper {

    private static JNIKiwix KIWIX = new JNIKiwix();

    public static void goOffline() {

        boolean success = KIWIX.loadZIM(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wp1.0.8.zim");
        if (success) {

            JNIKiwix.JNIKiwixString suggestion = new JNIKiwix.JNIKiwixString();

            KIWIX.getRandomPage(suggestion);
            L.d(suggestion.value);


            success = KIWIX.getPageUrlFromTitle("Barack Obama", suggestion);//    KIWIX.searchSuggestions("barack", 10);
            //KIWIX.getNextSuggestion(suggestion);
            L.d(suggestion.value);

/*
            JNIKiwix.JNIKiwixString url = new JNIKiwix.JNIKiwixString();

            success = KIWIX.getPageUrlFromTitle(suggestion.value, url);
            //String mimeType = KIWIX.getMimeType(url.value);
*/
            JNIKiwix.JNIKiwixString mimeType = new JNIKiwix.JNIKiwixString();
            JNIKiwix.JNIKiwixInt contentSize = new JNIKiwix.JNIKiwixInt();

            byte[] bytes = KIWIX.getContent(suggestion.value, mimeType, contentSize);

            L.d(Byte.toString(bytes[0]));

            String html = new String(bytes);
            L.d(html.substring(1, 5));

        }

    }

}
