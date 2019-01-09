package org.wikipedia.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class FileUtil {
    public static final int JPEG_QUALITY = 85;
    private static final int KILOBYTE = 1000;

    public static File writeToFile(ByteArrayOutputStream bytes, File destinationFile) throws IOException {
        FileOutputStream fo = new FileOutputStream(destinationFile);
        try {
            fo.write(bytes.toByteArray());
        } finally {
            fo.flush();
            fo.close();
        }
        return destinationFile;
    }

    public static ByteArrayOutputStream compressBmpToJpg(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, bytes);
        return bytes;
    }

    /**
     * Reads the contents of a file, preserving line breaks.
     * @return contents of the given file as a String.
     * @throws IOException
     */
    public static String readFile(final InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String readStr;
            while ((readStr = reader.readLine()) != null) {
                stringBuilder.append(readStr).append('\n');
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    @NonNull
    public static String readTextFileFromRawRes(@RawRes int resourceId) {
        InputStream inputStream = WikipediaApp.getInstance().getResources().openRawResource(resourceId);
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(inputStream);
        }
        throw new RuntimeException("Failed to read raw resource id " + resourceId);
    }

    public static void closeSilently(@Nullable Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecursively(@NonNull File f) {
        if (f.isDirectory()) {
            for (File child : f.listFiles()) {
                deleteRecursively(child);
            }
        }
        f.delete();
    }

    public static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[:\\\\/*\"?|<>']", "_");
    }

    public static boolean isVideo(String mimeType) {
        return mimeType.contains("ogg") || mimeType.contains("video");
    }

    public static boolean isAudio(String mimeType) {
        return mimeType.contains("audio");
    }

    public static boolean isImage(String mimeType) {
        return mimeType.contains("image");
    }

    public static boolean isStl(String mimeType) {
        return mimeType.contains("/sla") || mimeType.contains("/stl");
    }


    public static float bytesToGB(long bytes) {
        return (float) bytes / KILOBYTE / KILOBYTE / KILOBYTE;
    }

    public static float bytesToMB(long bytes) {
        return (float) bytes / KILOBYTE / KILOBYTE;
    }

    public static String bytesToUserVisibleUnit(Context context, long bytes) {
        float sizeInGB = bytesToGB(bytes);
        if (sizeInGB >= 1.0) {
            return context.getString(R.string.size_gb, sizeInGB);
        }
        return context.getString(R.string.size_mb, bytesToMB(bytes));
    }

    private FileUtil() { }
}
