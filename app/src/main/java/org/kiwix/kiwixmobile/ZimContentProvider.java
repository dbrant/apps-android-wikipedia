/*
 * Copyright 2013
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU  General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */

package org.kiwix.kiwixmobile;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.wikipedia.offline.OfflineHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class ZimContentProvider extends ContentProvider {

    public static final String TAG_KIWIX = "kiwix";

    public static final Uri CONTENT_URI = Uri.parse("content://org.kiwix.zim.base/");

    public static final Uri UI_URI = Uri.parse("content://org.kiwix.ui/");

    private static final String VIDEO_PATTERN = "([^\\s]+(\\.(?i)(3gp|mp4|m4a|webm|mkv|ogg|ogv))$)";

    private static final Pattern PATTERN = Pattern.compile(VIDEO_PATTERN, Pattern.CASE_INSENSITIVE);

    private static String getFilePath(Uri articleUri) {
        String filePath = articleUri.toString();
        int pos = articleUri.toString().indexOf(CONTENT_URI.toString());
        if (pos != -1) {
            filePath = articleUri.toString().substring(
                    CONTENT_URI.toString().length());
        }
        // Remove fragment (#...) as not supported by zimlib
        pos = filePath.indexOf("#");
        if (pos != -1) {
            filePath = filePath.substring(0, pos);
        }
        return filePath;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        String mimeType;

        // This is the code which makes a guess based on the file extenstion
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString().toLowerCase());
        mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        // This is the code which retrieve the mimeType from the libzim
        // "slow" and still bugyy
        if (mimeType.isEmpty()) {
            String t = uri.toString();
            int pos = uri.toString().indexOf(CONTENT_URI.toString());
            if (pos != -1) {
                t = uri.toString().substring(
                        CONTENT_URI.toString().length());
            }
            // Remove fragment (#...) as not supported by zimlib
            pos = t.indexOf("#");
            if (pos != -1) {
                t = t.substring(0, pos);
            }

            mimeType = OfflineHelper.kiwix().getMimeType(t);

            // Truncate mime-type (everything after the first space
            mimeType = mimeType.replaceAll("^([^ ]+).*$", "$1");
        }

        Log.d(TAG_KIWIX, "Getting mime-type for " + uri.toString() + " = " + mimeType);
        return mimeType;
    }

    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
      /*
      Matcher matcher = PATTERN.matcher(uri.toString());
      if (matcher.matches()) {
          try {
              return saveVideoToCache(uri);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      */
        return loadContent(uri);
    }

    private ParcelFileDescriptor loadContent(Uri uri) throws FileNotFoundException {
        ParcelFileDescriptor[] pipe;
        try {
            pipe = ParcelFileDescriptor.createPipe();
            new TransferThread(uri, new AutoCloseOutputStream(pipe[1])).start();
        } catch (IOException e) {
            Log.e(TAG_KIWIX, "Exception opening pipe", e);
            throw new FileNotFoundException("Could not open pipe for: "
                    + uri.toString());
        }
        return (pipe[0]);
    }

    /*
    private ParcelFileDescriptor saveVideoToCache(Uri uri) throws IOException {
        String filePath = getFilePath(uri);

        String fileName = uri.toString();
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length());

        File f = new File(FileUtils.getFileCacheDir(getContext()), fileName);

        JNIKiwix.JNIKiwixString mime = new JNIKiwix.JNIKiwixString();
        JNIKiwix.JNIKiwixInt size = new JNIKiwix.JNIKiwixInt();
        byte[] data = OfflineHelper.kiwix().getContent(filePath, mime, size);

        FileOutputStream out = new FileOutputStream(f);

        out.write(data, 0, data.length);
        out.flush();

        return ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_ONLY);
    }
    */

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri url, String[] projection, String selection,
                        String[] selectionArgs, String sort) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues initialValues) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String where,
                      String[] whereArgs) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int delete(@NonNull Uri uri, String where, String[] whereArgs) {
        throw new RuntimeException("Operation not supported");
    }

    static class TransferThread extends Thread {

        @NonNull private final Uri articleUri;
        @NonNull private final String articleZimUrl;
        @NonNull private final OutputStream out;

        TransferThread(@NonNull Uri articleUri, @NonNull OutputStream out) throws IOException {
            this.articleUri = articleUri;
            Log.d(TAG_KIWIX, "Retrieving: " + articleUri.toString());

            String filePath = getFilePath(articleUri);

            this.out = out;
            this.articleZimUrl = filePath;
        }

        @Override
        public void run() {
            try {
                JNIKiwix.JNIKiwixString mime = new JNIKiwix.JNIKiwixString();
                JNIKiwix.JNIKiwixInt size = new JNIKiwix.JNIKiwixInt();
                byte[] data = OfflineHelper.kiwix().getContent(articleZimUrl, mime, size);
                out.write(data, 0, data.length);
                out.flush();

                Log.d(TAG_KIWIX, "reading  " + articleZimUrl
                        + "(mime: " + mime.value() + ", size: " + size.value() + ") finished.");
            } catch (IOException | NullPointerException e) {
                Log.e(TAG_KIWIX, "Exception reading article " + articleZimUrl + " from zim file",
                        e);
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG_KIWIX,
                            "Custom exception by closing out stream for article " + articleZimUrl,
                            e);
                }
            }
        }
    }
}
