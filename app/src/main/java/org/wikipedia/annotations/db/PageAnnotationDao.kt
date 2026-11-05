package org.wikipedia.annotations.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PageAnnotationDao {
    @Insert
    suspend fun insert(annotation: PageAnnotation): Long

    @Update
    suspend fun update(annotation: PageAnnotation)

    @Update
    suspend fun updateAll(annotations: List<PageAnnotation>)

    @Query("DELETE FROM PageAnnotation WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM PageAnnotation WHERE id = :id")
    suspend fun getById(id: Long): PageAnnotation?

    @Query("SELECT * FROM PageAnnotation WHERE lang = :lang AND namespace = :namespace AND apiTitle = :apiTitle ORDER BY startOffset")
    suspend fun getForPage(lang: String, namespace: String, apiTitle: String): List<PageAnnotation>

    @Query("SELECT * FROM PageAnnotation ORDER BY modified DESC")
    suspend fun getAll(): List<PageAnnotation>

    @Query("SELECT * FROM PageAnnotation ORDER BY modified DESC")
    fun observeAll(): Flow<List<PageAnnotation>>
}
