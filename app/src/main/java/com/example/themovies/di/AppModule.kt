package com.example.themovies.di

import android.content.Context
import androidx.room.Room
import com.example.themovies.BuildConfig
import com.example.themovies.common.Constants
import com.example.themovies.common.DataMapper
import com.example.themovies.database.MovieDao
import com.example.themovies.database.MovieDatabase
import com.example.themovies.domain.MoviesInteractor
import com.example.themovies.network.ApiService
import com.example.themovies.repository.MovieRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // init logging interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val tokenInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val httpUrl = chain.request().url.newBuilder()
                    .addQueryParameter("api_key", Constants.API_KEY)
                    .addQueryParameter("language", Constants.ARG_LANGUAGE)
                    .addQueryParameter("region", Constants.ARG_REGION)
                    .build()

                return chain.proceed(
                    chain.request().newBuilder()
                        .url(httpUrl)
                        .build()
                )
            }

        }

        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Singleton
    @Provides
    fun provideDataMapper(): DataMapper {
        return DataMapper()
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
        movieDao: MovieDao,
        dataMapper: DataMapper
    ): MovieRepository {
        return MovieRepository(apiService, movieDao, dataMapper)
    }

    @Singleton
    @Provides
    fun provideMoviesInteractor(movieRepository: MovieRepository): MoviesInteractor {
        return MoviesInteractor(movieRepository)
    }
}