package jp.numero.android_dagashi

import android.app.Application

class DagashiApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl()
    }
}