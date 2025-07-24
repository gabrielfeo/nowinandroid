/*
 * Copyright 2023 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.google.samples.apps.nowinandroid

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.TestedExtension
import com.gradle.develocity.agent.gradle.test.ImportJUnitXmlReports
import com.gradle.develocity.agent.gradle.test.JUnitXmlDialect
import org.gradle.api.Project

/**
 * Configure Gradle Build Scan for the project
 */
internal fun configureBuildScan(
    project: Project,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    importAndroidTestResults(project, commonExtension)
}

/**
 * Import Android test results into Gradle Build Scan, for better visibility and
 * troubleshooting of test results.
 *
 * Registers a `ImportJUnitXmlReports` task for each Android test task in the project.
 */
private fun importAndroidTestResults(
    project: Project,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    if (commonExtension !is TestedExtension) {
        return
    }
    project.afterEvaluate {
        commonExtension.testVariants.forEach { testVariant ->
            ImportJUnitXmlReports.register(
                tasks,
                tasks.named("connected${testVariant.name.capitalize()}"),
                JUnitXmlDialect.GENERIC,
            )
            commonExtension.testOptions.managedDevices.allDevices.forEach { device ->
                ImportJUnitXmlReports.register(
                    tasks,
                    tasks.named("${device.name}${testVariant.name.capitalize()}"),
                    JUnitXmlDialect.GENERIC,
                )
            }
        }
    }
}
