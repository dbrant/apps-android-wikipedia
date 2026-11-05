package org.wikipedia.annotations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.wikipedia.annotations.db.PageAnnotation
import org.wikipedia.database.AppDatabase
import org.wikipedia.util.log.L

class AnnotationsViewModel : ViewModel() {
    private val dao get() = AppDatabase.instance.pageAnnotationDao()

    // Null while the annotations are still loading.
    val annotations: StateFlow<List<PageAnnotation>?> = dao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val exceptionHandler = CoroutineExceptionHandler { _, t -> L.e(t) }

    fun delete(annotation: PageAnnotation) {
        viewModelScope.launch(exceptionHandler) {
            dao.delete(annotation.id)
        }
    }

    fun restore(annotation: PageAnnotation) {
        viewModelScope.launch(exceptionHandler) {
            dao.insert(annotation)
        }
    }

    suspend fun exportAsW3CJson(): String {
        return dao.getAll().toW3CJson()
    }
}
