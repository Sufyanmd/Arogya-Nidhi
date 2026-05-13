package com.example.arogyanidhi.data.remote

import android.os.Bundle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsHelper @Inject constructor() {
    fun logEvent(name: String, params: Bundle? = null) {
        // No-op
    }

    fun logScreenView(screenName: String) {
        // No-op
    }
}
