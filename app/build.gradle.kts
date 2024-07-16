plugins {
    id("com.android.application")
}

android {
    namespace = "com.karimi.c196"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.karimi.c196"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(mapOf("room.schemaLocation" to "${projectDir}/schemas"))
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("com.itextpdf:itext7-core:7.1.15")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.8.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-android:2.8.2")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    testImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // Room dependencies
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
}
