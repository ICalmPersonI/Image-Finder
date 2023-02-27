package com.calmperson.imagefinder.hilt

import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiRepository
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGoogleSearchApiRepository(): GoogleSearchApiRepository = GoogleSearchApiRepositoryImpl()
}