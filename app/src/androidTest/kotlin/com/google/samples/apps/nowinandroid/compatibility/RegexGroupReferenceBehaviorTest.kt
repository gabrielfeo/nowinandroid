/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.compatibility

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Instrumented test to demonstrate the behavior of java.util.regex.Matcher
 * replacement group handling between Android SDK 33 and 34+.
 */
@RunWith(AndroidJUnit4::class)
class RegexGroupReferenceBehaviorTest {
    @Test
    fun invalidGroupReference_breakingChangeBetweenApi33And34() {
        val pattern = Pattern.compile("(\\d+)\\.(\\d)\\b")
        val result = pattern.matcher("1.0").replaceAll("$$1.$$20")
        // This behavior succeeds in API 33, but the matcher.replaceAll fails with IllegalArgumentException, "invalid group reference" in API 34
        assertEquals("1.00", result)
    }
}
