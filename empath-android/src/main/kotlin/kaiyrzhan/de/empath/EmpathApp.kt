package kaiyrzhan.de.empath

import android.app.Application
import kaiyrzhan.de.empath.di.initKoin
import org.koin.android.ext.koin.androidContext

class EmpathApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EmpathApp)
        }
    }
}
