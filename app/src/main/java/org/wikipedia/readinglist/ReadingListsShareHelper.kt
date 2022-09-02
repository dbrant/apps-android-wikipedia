package org.wikipedia.readinglist

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import org.wikipedia.R
import org.wikipedia.notifications.NotificationCategory
import org.wikipedia.notifications.NotificationPresenter
import org.wikipedia.readinglist.database.ReadingList
import org.wikipedia.util.ShareUtil
import org.wikipedia.util.log.L
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

object ReadingListsShareHelper {

    fun exportReadingListCsv(context: Context, readingList: ReadingList?) {
        if (readingList == null) {
            return
        }
        try {
            val fileName = ShareUtil.cleanFileName(readingList.title) + ".csv"

            val outStream: OutputStream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentResolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                val uri = contentResolver.insert(MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues)
                contentResolver.openOutputStream(uri!!)!!
            } else {
                val outFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
                FileOutputStream(outFile)
            }

            outStream.use { stream ->
                OutputStreamWriter(stream).use { writer ->
                    readingList.pages.forEach {
                        writer.appendLine(it.displayTitle + ", " + it.apiTitle)
                    }
                    writer.flush()
                }
            }

            val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)

            val builder = NotificationCompat.Builder(context, NotificationCategory.MENTION.id)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            val notificationText = "Your reading list was exported successfully to your Downloads."

            NotificationPresenter.showNotification(context, builder, 0,
                    "Exported \"" + readingList.title + "\"",
                    notificationText,
                    notificationText,
                    null,
                    R.drawable.ic_icon_list, R.color.accent50, intent)
        } catch (e: Exception) {
            L.e(e)
        }
    }
}
