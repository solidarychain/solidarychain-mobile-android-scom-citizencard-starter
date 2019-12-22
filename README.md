# README

## Clone project

```shell
git clone https://github.com/solidarynetwork/solidarychain-mobile-android-scom-citizencard-starter.git
```

## Create encoded license secrets file

1. create a file with your encodeLicense named `secrets.properties` in the root of the project

ex.

```
encodedLicense="YOUR-ENCODED-LICENSE-HERE"
```

## Install AAR SCom SDK Dependency

> request your copy of SCom SDK from authors at [scom.pt](https://scom.pt)

> our SDK was kindly donated for our solidarity project only, **thanks to all the good people** at [scom.pt](https://scom.pt)

1. create `libs/` folder in `app/libs`
2. copy `cc-android-sdk-1.6.0.aar` to `app/libs` folder
3. add `libs/cc-android-sdk-1.6.0.aar` to `app/build.gradle`
4. update `android/app/build.gradle` with `cc-android-sdk`, `gson` and `okhttp` (this step is already done, only here for reference)


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

## Change project name and package name

1. change manifest and package name ex `network.solidary.mobile` for your own reverse domain name