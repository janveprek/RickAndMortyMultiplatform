import com.android.build.gradle.internal.lint.AndroidLintAnalysisTask
import com.android.build.gradle.internal.lint.LintModelWriterTask
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kover)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "11" }

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    androidTarget {
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = "1.8"
//            }
//        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.mockk.android)
                implementation(libs.mockk.agent)
                implementation(libs.coroutines.test)
                implementation(libs.junit)
                implementation(libs.kotest.assertions.core)
                implementation(libs.turbine)
            }
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            // Koin
            implementation(libs.koin.core)
            // Ktor
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.image.loader)
            implementation(libs.napier)
            api(libs.precompose)
            api(libs.precompose)
            api(libs.precompose.koin)
            api(libs.precompose.viewmodel)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

// Because of a bug in Compose Multiplatform version, https://github.com/JetBrains/compose-multiplatform/issues/4085
tasks.withType<AndroidLintAnalysisTask>{
    dependsOn("copyFontsToAndroidAssets")
}

tasks.withType<LintModelWriterTask>{
    dependsOn("copyFontsToAndroidAssets")
}

koverReport {
    filters {
        // used for exclusion of classes that should not be tested (UI, generated files, ...)
        excludes {
            classes("com.veprek.honza.rickandmorty.MainActivity*")
            classes("com.veprek.honza.rickandmorty.ComposableSingletons*")
            classes("com.veprek.honza.rickandmorty.app.*")
            classes("com.veprek.honza.rickandmorty.*.di.*")
            classes("com.veprek.honza.rickandmorty.data.*")
            classes("com.veprek.honza.rickandmorty.design.*")
            classes("com.veprek.honza.rickandmorty.navigation.system*")
            classes("comveprekhonzarickandmorty.character.data.*")
            classes("rickandmortymobile.composeapp.generated.resources.*")
            annotatedBy("androidx.compose.runtime.Composable")
        }
    }
    verify {
        rule {
            isEnabled = true
            bound {
                minValue = 75
            }
        }
    }
}

android {
    namespace = "com.veprek.honza.rickandmorty"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.veprek.honza"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.experimental {
    web.application {}
}
