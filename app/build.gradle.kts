import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.stardust.co"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stardust.co"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["JPUSH_PKGNAME"] = "com.stardust.co"
        manifestPlaceholders["JPUSH_APPKEY"] = "5ebe797b43615b9e33a66fee"
        manifestPlaceholders["JPUSH_CHANNEL"] = "github"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
            //选择要添加的对应 cpu 类型的 .so 库。
            abiFilters.addAll(arrayOf("armeabi", "armeabi-v7a", "arm64-v8a"))
            // 还可以添加 'x86', 'x86_64', 'mips', 'mips64'
        }
    }
    signingConfigs {
        create("release") {
            keyAlias = "sxwdsoft"
            keyPassword = "sxwdsoft4989"
            storeFile = file("../keystore.jks")
            storePassword = "sxwdsoft4989"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig =  signingConfigs.getByName("release")
            isDebuggable = false
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources.excludes.addAll(arrayOf("META-INF/NOTICE.md", "META-INF/LICENSE.md"))
    }
    buildFeatures {
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.android.mail)
    implementation(libs.mail.android.activation)

    implementation(libs.utilcodex)
    implementation(libs.jetpackmvvm)
    implementation(libs.core)
    implementation(libs.brv)
    implementation(libs.channel)
    implementation(libs.permissionx)
    implementation(libs.appupdate)
    implementation(libs.cn.jpush)
    implementation(libs.joperate)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}