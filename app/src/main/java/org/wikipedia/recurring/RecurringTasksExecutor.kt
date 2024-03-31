package org.wikipedia.recurring

import android.content.Context
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.wikipedia.alphaupdater.AlphaUpdateChecker
import org.wikipedia.settings.RemoteConfigRefreshTask
import org.wikipedia.util.ReleaseUtil

class RecurringTasksExecutor(private val context: Context) {
    fun run() {
        Completable.fromAction {
            val allTasks = arrayOf( // Has list of all rotating tasks that need to be run
                    RemoteConfigRefreshTask(),
                    DailyEventTask(context),
                    TalkOfflineCleanupTask(context)
            )
            for (task in allTasks) {
                task.runIfNecessary()
            }
            if (ReleaseUtil.isAlphaRelease) {
                AlphaUpdateChecker(context).runIfNecessary()
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}
