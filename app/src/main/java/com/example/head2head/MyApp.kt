package com.example.head2head

import android.app.Application
import com.example.head2head.di.apiModule
import com.example.head2head.di.databaseModule
import com.example.head2head.di.h2hRepositoryModule
import com.example.head2head.di.h2hViewModel
import com.example.head2head.di.localTeamModule
import com.example.head2head.di.remoteH2HModule
import com.example.head2head.di.remoteTeamModule
import com.example.head2head.di.teamRepositoryModule
import com.example.head2head.di.retrofitModule
import com.example.head2head.di.teamViewModel
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
                teamViewModel,
                h2hViewModel,
                apiModule,
                databaseModule,
                teamRepositoryModule,
                localTeamModule,
                remoteTeamModule,
                h2hRepositoryModule,
                remoteH2HModule
            )
        }
    }
}