package dev.maruffirdaus.kuizee.application

import android.app.Application
import com.google.android.material.color.DynamicColors

class KuizeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}