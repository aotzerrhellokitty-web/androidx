/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.a2ui.testapp

import android.app.Application
import com.posthog.kmp.PostHog
import com.posthog.kmp.PostHogConfig
import com.posthog.kmp.PostHogContext

class TestAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val apiKey = BuildConfig.POSTHOG_API_KEY
        if (BuildConfig.DEBUG && apiKey.isEmpty()) {
            error(
                "POSTHOG_API_KEY variable required by PostHog is missing or un-configured, " +
                    "this causes events to be silently missed. " +
                    "This error stops appearing once POSTHOG_API_KEY is configured"
            )
        }
        if (apiKey.isNotEmpty()) {
            PostHog.setup(
                config = PostHogConfig(
                    apiKey = apiKey,
                    host = BuildConfig.POSTHOG_HOST.ifEmpty { PostHogConfig.HOST_US },
                ),
                context = PostHogContext(this),
            )
            PostHog.capture(event = "app_opened")
        }
    }
}
