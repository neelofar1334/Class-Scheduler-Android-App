plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.c196"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.c196"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2") // Testing Libraries
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Testing Libraries
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("junit:junit:4.12") // Testing Libraries
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    // Espresso Idling Resource
    androidTestImplementation ("androidx.test.espresso:espresso-idling-resource:3.5.1")


    //room dependencies
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")

}


