package ru.rlrent.application.network.di

import android.app.DownloadManager
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.android.rlrent.i_network.BuildConfig
import ru.rlrent.i_network.converter.gson.ResponseTypeAdapterFactory
import ru.rlrent.i_network.converter.gson.SafeConverterFactory
import ru.rlrent.i_network.generated.urls.ServerUrls.BASE_API_URL
import ru.rlrent.i_network.network.BaseUrl
import ru.rlrent.i_network.network.CallAdapterFactory
import ru.rlrent.i_network.network.calladapter.BaseCallAdapterFactory
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.logger.Logger

@Module
class NetworkModule {

    companion object {
        private const val HTTP_LOG_TAG = "OkHttp"
    }

    @Provides
    @PerApplication
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: BaseCallAdapterFactory,
        gson: Gson,
        apiUrl: BaseUrl
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl.toString())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    @Provides
    @PerApplication
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapterFactory(
                ResponseTypeAdapterFactory(
                    SafeConverterFactory()
                )
            )
            .create()
    }

    @Provides
    @PerApplication
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message -> Logger.d("$HTTP_LOG_TAG $message") }
        return HttpLoggingInterceptor(logger).apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    @Provides
    @PerApplication
    internal fun provideDownloadManager(context: Context): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    @PerApplication
    internal fun provideCallAdapterFactory(): BaseCallAdapterFactory =
        CallAdapterFactory()

    @Provides
    @PerApplication
    internal fun provideBaseUrl(): BaseUrl {
        return BaseUrl(BASE_API_URL, null)
    }
}