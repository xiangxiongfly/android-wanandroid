plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mywanandroid"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.mywanandroid"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")
    implementation("androidx.activity:activity-ktx:1.13.0")
    implementation("androidx.fragment:fragment-ktx:1.8.6")

    // DataStore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Gson
    implementation(libs.gson)
    implementation(libs.gsonfactory)

    // Coil
    implementation("io.coil-kt.coil3:coil:3.4.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.4.0")

    // 下拉刷新
    implementation("io.github.scwang90:refresh-layout-kernel:3.0.0-alpha")
    implementation("io.github.scwang90:refresh-header-classics:3.0.0-alpha")

    // RecyclerView
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.3.4")

    // Shape
    implementation("com.github.getActivity:ShapeView:10.0")
    implementation("com.github.getActivity:ShapeDrawable:3.3")

    // Toast
    implementation("com.github.getActivity:Toaster:15.0")

    // 轮播图
    implementation("com.github.zhpanvip:bannerviewpager:3.5.15")

    // Flex
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    // TitleBar
    implementation("com.github.getActivity:TitleBar:10.8")

    // WebView
    implementation("io.github.justson:agentweb-core:v5.1.1-androidx")
}