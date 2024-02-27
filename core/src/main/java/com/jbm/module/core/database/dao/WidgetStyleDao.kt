package com.jbm.module.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jbm.module.core.database.model.WidgetStyleEntity

@Dao
interface WidgetStyleDao {
    @Query("SELECT * FROM widget_style")
    fun getAllStyle(): List<WidgetStyleEntity>

    @Query("SELECT * FROM widget_style WHERE id=:id")
    fun getWidgetStyleById(id: String): WidgetStyleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWidgetStyle(widgetStyleEntity: WidgetStyleEntity): Long

    @Delete
    fun deleteWidgetStyle(widgetStyleEntity: WidgetStyleEntity)
}
