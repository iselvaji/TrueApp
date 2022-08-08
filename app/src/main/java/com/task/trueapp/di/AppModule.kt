package com.task.trueapp.di

import com.task.trueapp.data.WebContentParser
import com.task.trueapp.data.remote.WebApiService
import com.task.trueapp.data.remote.WebApiServiceImpl
import com.task.trueapp.data.repository.WebContentRepositoryImpl
import com.task.trueapp.domain.repository.WebContentRepository
import com.task.trueapp.domain.repository.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

/**
 * App module - contains the Hilt Dependency injection providers used in the application
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun provideWebContentApi(client: HttpClient): WebApiService {
        return WebApiServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideWebContentParser() : WebContentParser {
        return WebContentParser()
    }

    @Provides
    @Singleton
    fun provideWebContentRepository(api: WebApiService, parser: WebContentParser) : WebContentRepository {
        return WebContentRepositoryImpl(api, parser)
    }

    @Provides
    @Singleton
    fun provideWebContentUseCase(repository: WebContentRepository) : WebContentUseCase {
        return WebContentUseCase(GetNthCharFromWebContent(repository), GetEveryNthCharFromWebContent(repository), GetWordsCountFromWebContent(repository))
    }

}