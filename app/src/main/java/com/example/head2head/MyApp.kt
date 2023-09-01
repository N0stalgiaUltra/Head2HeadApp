package com.example.head2head

import android.app.Application
import com.example.head2head.di.apiModule
import com.example.head2head.di.databaseModule
import com.example.head2head.di.localModule
import com.example.head2head.di.remoteModule
import com.example.head2head.di.repositoryModule
import com.example.head2head.di.retrofitModule
import com.example.head2head.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                retrofitModule,
                viewModelModule,
                apiModule,
                databaseModule,
                repositoryModule,
                localModule,
                remoteModule
            )
        }
    }
}