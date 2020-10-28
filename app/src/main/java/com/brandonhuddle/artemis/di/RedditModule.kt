package com.brandonhuddle.artemis.di

import com.brandonhuddle.artemis.BuildConfig
import com.brandonhuddle.artemis.reddit.RedditApi
import com.brandonhuddle.artemis.reddit.adapters.CommentsDeserializer
import com.brandonhuddle.artemis.reddit.entities.CommentsResponse
import com.brandonhuddle.artemis.repositories.RedditRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RedditModule {
    @Provides
    fun provideRedditApi(): RedditApi {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                            .header(
                                    "User-Agent",
                                    "android:com.brandonhuddle.artemis:" +
                                            "v" + BuildConfig.VERSION_NAME +
                                            " (by /u/ElidedGhost)"
                            )
                        .build()
                )
            }
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(CommentsResponse::class.java, CommentsDeserializer())
            .create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://reddit.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(RedditApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRedditRepository(redditApi: RedditApi): RedditRepository
            = RedditRepository(redditApi)
}