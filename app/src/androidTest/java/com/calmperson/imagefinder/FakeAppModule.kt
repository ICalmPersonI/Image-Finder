package com.calmperson.imagefinder

import com.calmperson.imagefinder.hilt.AppModule
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)

object FakeAppModule {

    @Singleton
    @Provides
    fun fakeRepository(): GoogleSearchApiRepository = FakeRepository()

}
