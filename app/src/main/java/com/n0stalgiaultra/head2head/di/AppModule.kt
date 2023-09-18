package com.n0stalgiaultra.head2head.di

import androidx.room.Room
import com.n0stalgiaultra.head2head.data.local.AppDatabase
import com.n0stalgiaultra.head2head.data.local.TeamLocalDataSourceImp
import com.n0stalgiaultra.head2head.data.remote.FootballAPI
import com.n0stalgiaultra.head2head.data.remote.H2HRemoteDataSourceImpl
import com.n0stalgiaultra.head2head.domain.h2h.H2HRemoteDataSource
import com.n0stalgiaultra.head2head.domain.h2h.H2HRepository
import com.n0stalgiaultra.head2head.data.remote.H2HRepositoryImpl
import com.n0stalgiaultra.head2head.data.remote.TeamRemoteDataSourceImpl
import com.n0stalgiaultra.head2head.domain.team.TeamLocalDataSource
import com.n0stalgiaultra.head2head.domain.team.TeamRemoteDataSource
import com.n0stalgiaultra.head2head.domain.team.TeamRepository
import com.n0stalgiaultra.head2head.domain.team.TeamRepositoryImpl
import com.n0stalgiaultra.head2head.view.viewmodels.H2HViewModel
import com.n0stalgiaultra.head2head.view.viewmodels.TeamViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    factory <Interceptor>{
        Interceptor{
            chain ->
            val request = chain.request()
                .newBuilder()
                .header(FootballAPI.API_KEY, FootballAPI.API_KEY_VALUE)
                .build()
                chain.proceed(request)
        }
    }

    factory <HttpLoggingInterceptor>{
        HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger.DEFAULT
        ).setLevel(
            HttpLoggingInterceptor.Level.HEADERS
        )
    }

    //client
    factory {
        OkHttpClient.Builder().apply {
            addInterceptor(
                get<Interceptor>()
            )
            addInterceptor(
                get<HttpLoggingInterceptor>()
            )
        }.build()
    }

    //retrofit build
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(FootballAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

val apiModule = module{
    single(createdAtStart = false){
        get<Retrofit>().create(FootballAPI::class.java)
    }
}

val databaseModule = module{
    single {
        Room.databaseBuilder(context = androidContext(),
        klass = AppDatabase::class.java,
        name = AppDatabase.DATABASE_NAME)
            .build()
    }

    single {
        get<AppDatabase>().teamDao()
    }
}

val localTeamModule = module{
    single<TeamLocalDataSource> {
        TeamLocalDataSourceImp(get())
    }
}

val remoteTeamModule = module{
    single<TeamRemoteDataSource>{
        TeamRemoteDataSourceImpl(get())
    }
}

val remoteH2HModule = module{
    single<H2HRemoteDataSource>{
        H2HRemoteDataSourceImpl(get())
    }
}

val teamRepositoryModule = module{
    single<TeamRepository>{
        TeamRepositoryImpl(get(), get())
    }
}

val h2hRepositoryModule = module{
    single<H2HRepository> {
        H2HRepositoryImpl(get())
    }
}

val teamViewModel = module{
    viewModel {
        TeamViewModel(
            get()
        )
    }
}

val h2hViewModel = module{
    viewModel{
        H2HViewModel(
            get()
        )
    }
}
