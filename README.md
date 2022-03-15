# Veriff test task

[![Android CI](https://github.com/RankoR/VeriffTest/actions/workflows/main.yml/badge.svg)](https://github.com/RankoR/VeriffTest/actions/workflows/main.yml)

## How to use

Include the `:sdk` module in `build.gradle` or `build.gradle.kts`:

```groovy
implementation project(':sdk')
```

or

```kotlin
implementatin(project(":sdk"))
```

Add the following activities to the `AndroidManifest.xml`:

```xml
<activity
    android:name="com.example.sdk.presentation.id.IdRecognitionActivity"
    android:exported="false"/>

<activity
    android:name="com.example.sdk.presentation.face.FaceRecognitionActivity"
    android:exported="false"/>
```

Add the `CAMERA` permission and the camera requirement to the `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-feature android:name="android.hardware.camera.any"/>
```

In the `Activity` (or activities) you're going to use the SDK, call `registerActivity` after the activity is created:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // ...

    VeriffSdk.registerActivity(this)
```

Then set up the callbacks:

```kotlin
VeriffSdk.onTextDocumentResult = { result ->
    // Handle the text document result
}
```

ID recognition result can be one of:

* `TextDocumentResult.Success` — successful recognition. The result contains recognized text blocks.
* `TextDocumentResult.Failure` — got a failure on recognition. The result contains an error message.
* `TextDocumentResult.Cancelled` — flow is cancelled by user.


```kotlin
VeriffSdk.onFaceResult = { result ->
    // Handle the face detection result
}
```

Face recognition result can be one of:

* `FaceResult.Success` — successful detection. The result contains the image file and recognized face data.
* `FaceResult.Failure` — got a failure on recognition. The result contains an error message.
* `FaceResult.Cancelled` — flow is cancelled by user.

When you're ready, launch the flows.

#### ID recognition flow

```kotlin
VeriffSdk.launchIdRecognition()
```

#### Face recognition flow

```kotlin
VeriffSdk.launchFaceRecognition()
```

## Notes on this project

There are some trade-offs in this project, as I have a very limited time for the implementation.

Some of them:

* UI tests are incomplete. To fix it we have to somehow fake the camera
* Working with camera can be implemented in a better way. It also has two leaks, but seems that they're caused by the CameraX itself
* SDK returns only text messages with error, as it's impossible to put a `Throwable` in a parcel. The better way is to make a `sealed class Error` with possible error causes.
* DI isn't internal in `core` and `core-ui` modules, so it's available for the external modules
* `VeriffSdk` object itself is not tested

Code coverage is around 70% according to JaCoCo report (it's approximate as I can't exclude non-testable things like DI, so I have to calculate it semi-manually).
