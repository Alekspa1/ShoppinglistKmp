package com.drag0n.shoppinglistkmp

import android.app.Application
import com.drag0n.shoppinglistkmp.data.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
        }
    }
}