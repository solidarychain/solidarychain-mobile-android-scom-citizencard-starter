# README

##

encodedLicense="eyJ2ZXJzaW9uIjoxLCJ0eXBlIjoiQ0xJRU5UIiwiaW5mbyI6eyJhcHBsaWNhdGlvbiI6Im5ldHdvcmsuc29saWRhcnkubW9iaWxlIiwiYXBpS2V5IjoiMDY3NTk2OWMtY2U2Yi00NzM1LTgyMWQtNzFmYzZhYThiZDlmIiwicGxhdGZvcm1zIjpbIklPUyIsIkFORFJPSUQiXSwib3BzIjpbIlJFQURfUFVCTElDX0RBVEEiLCJSRUFEX0FERFJFU1MiXSwiZXhwaXJhdGlvbkRhdGUiOjE1OTA5NjYwMDAwMDB9LCJzaWduYXR1cmUiOiJPM3lUVFg1ZDlORlkyNTBheTNlZjkxOU8vcm1LbVM5MkNYYUxOYWN5M2x5SzMzcGJJNjFOSUh1dGViUXdhcUczVkdCazMwNXFSMHhidVFXRVRiRDRKeUtINUttUU9yb2RJWnAyY3pRNGdRUUUxVUp5OGRjUHZMa21CWk15aVl0T1oyaEdrWVZ6cjlsNHRLVUdzK2FjNDU4MnBNOUdocUdXMzFqOVNxM0ZYWllIYi94czlDZTRLcXlPcU96eTh1SjVLRlZVV3dLaElwUXROb2N3Yld5dEZ2cUZrY1NLU1BVWkUrR3N0M1RhRVd0TVo5TlY5V08xY0txS0pZMjhzamhaYmZ5TE9iYlYyTDhWdzJETzJHTXl2dk1yNFFxQnJtaU16bDhyUmlQdlJkWVVFTXdvc2ZUZHA1cHg3bVhFK2kvRFdobGo5eWw3Z1RZRzcxQ2tycTllRVE9PSJ9"


## How to install AAR SCom SDK Dependency

> SCom sdk requires gson and okhttp

1. copy `cc-android-sdk-1.6.0.aar` to `libs/` and add `libs/cc-android-sdk-1.6.0.aar` to `build.gradle`
2. update `android/app/build.gradle` with `cc-android-sdk`, `gson` and `okhttp`

```
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
core:3.1.1'
    implementation files('libs/cc-android-sdk-1.6.0.aar')
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
}
```

3. sync project
