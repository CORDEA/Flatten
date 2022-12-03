package jp.cordea.flatten

import android.app.Application
import jp.cordea.flatten.repository.repositoryModule
import jp.cordea.flatten.ui.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, repositoryModule, presenterModule)
        }
    }
}
