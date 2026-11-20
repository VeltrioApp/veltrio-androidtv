package app.veltrio.tv

import android.app.Application
import app.veltrio.shared.Storage

class VeltrioTvApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Storage.init(this)
    }
}
