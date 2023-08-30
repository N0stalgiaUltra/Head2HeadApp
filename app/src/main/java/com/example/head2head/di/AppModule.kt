package com.example.head2head.di

import com.example.head2head.data.remote.FootballAPI
import com.example.head2head.view.MainViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

val viewModelModule = module{
    viewModel {
        MainViewModel(
            get()
        )
    }
}
/*TODO: Modulo do Repository*/