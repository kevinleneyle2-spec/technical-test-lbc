package fr.leboncoin.data.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.data.BuildConfig
import fr.leboncoin.data.local.database.AlbumDatabase
import fr.leboncoin.data.local.database.FavoriteAlbumDatabase
import fr.leboncoin.data.network.api.AlbumApiService
import fr.leboncoin.data.repository.AlbumRepository
import fr.leboncoin.data.repository.FavoriteAlbumRepository
import fr.leboncoin.data.repository.OfflineAlbumRepository
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAlbumRepository(apiService: AlbumApiService): AlbumRepository = AlbumRepository(apiService)

    @Provides
    @Singleton
    fun provideOfflineAlbumRepository(@ApplicationContext context: Context): OfflineAlbumRepository {
        return OfflineAlbumRepository(AlbumDatabase.getDatabase(context).albumDao())
    }

    @Provides
    @Singleton
    fun provideFavoriteAlbumRepository(@ApplicationContext context: Context): FavoriteAlbumRepository {
        return FavoriteAlbumRepository(FavoriteAlbumDatabase.getDatabase(context).albumDao())
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AlbumApiService = retrofit.create(AlbumApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(AlbumApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
}