package kaiyrzhan.de.empath

import android.app.Application
import kaiyrzhan.de.empath.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

public class EmpathApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EmpathApp)
            initKoin(
                isDebug = BuildConfig.DEBUG,
            )
        }
    }
}
