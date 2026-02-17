package fr.leboncoin.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.data.utils.AnalyticsHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalyticsHelper(): AnalyticsHelper {
        return AnalyticsHelper()
    }
}