package com.jbm.module.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jbm.module.core.database.model.WidgetEntity

@Dao
interface WidgetDao {
    @Query("SELECT * FROM widget")
    fun getAllWidgets(): List<WidgetEntity>

    @Query("SELECT * FROM widget WHERE id=:id")
    fun getWidgetById(id: String): WidgetEntity?

    @Query("SELECT phraseId FROM widget WHERE id=:widgetId")
    fun getPhraseIdByWidgetId(widgetId: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWidget(widgetEntity: WidgetEntity): Long
}
