package com.jbm.phrase.di

import com.jbm.phrase.ui.widget.glancesingleline.SingleLineWidgetViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
fun interface SingleLineWidgetEntryPoint {
    fun getViewModel(): SingleLineWidgetViewModel
}
