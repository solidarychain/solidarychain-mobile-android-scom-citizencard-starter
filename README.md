# README

## Clone project

```shell
git clone https://github.com/solidarynetwork/solidarychain-mobile-android-scom-citizencard-starter-.git
```

## Start

1. create a file with your encodeLicense in `secrets.properties`

ex.

```
encodedLicense="YOUR LICENSE HERE"
```

## Install AAR SCom SDK Dependency

> the request your copy of SCom SDK from authors at [scom.pt](scom.pt)

> our SDK as kindly donated for our solidary project only, thanks to all the good people at [scom.pt](scom.pt)

1. create `libs/` folder in `app/libs`
2. copy `cc-android-sdk-1.6.0.aar` to `app/libs` folder
3. add `libs/cc-android-sdk-1.6.0.aar` to `app/build.gradle`
4. update `android/app/build.gradle` with `cc-android-sdk`, `gson` and `okhttp`

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

5. sync project
6. run project

> SCom SDK requires gson and okhttp
