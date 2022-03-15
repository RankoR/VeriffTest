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
* Face detection leads to a native crash on Android 5 (see below)

```
I/DEBUG   (10991): *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***
I/DEBUG   (10991): Build fingerprint: 'Android/sdk_google_phone_armv7/generic:5.1.1/LMY48X/6695563:userdebug/test-keys'
I/DEBUG   (10991): Revision: '0'
I/DEBUG   (10991): ABI: 'arm'
I/DEBUG   (10991): pid: 11368, tid: 11506, name: pool-2-thread-1  >>> com.example.sdk.test <<<
I/DEBUG   (10991): signal 4 (SIGILL), code 1 (ILL_ILLOPC), fault addr 0xa00031fa
I/DEBUG   (10991):     r0 a023ef18  r1 00000400  r2 9fe220dc  r3 9fe127ad
I/DEBUG   (10991):     r4 a023ef18  r5 b6f9cf73  r6 ac8af7e4  r7 b6f9d03a
I/DEBUG   (10991):     r8 00000040  r9 b6fa1714  sl 00000001  fp a0234fc8
I/DEBUG   (10991):     ip b6f9d03a  sp a0353d00  lr 9fffbb6d  pc a00031fa  cpsr 88000030
I/DEBUG   (10991):
I/DEBUG   (10991): backtrace:
I/DEBUG   (10991):     #00 pc 002691fa  /data/app/com.example.sdk.test-2/lib/arm/libface_detector_v2_jni.so
I/DEBUG   (10991):     #01 pc 00261b69  /data/app/com.example.sdk.test-2/lib/arm/libface_detector_v2_jni.so
I/DEBUG   (10991):     #02 pc 000015bd  /system/bin/linker (__dl__ZN6soinfo12CallFunctionEPKcPFvvE+44)
I/DEBUG   (10991):     #03 pc 00001691  /system/bin/linker (__dl__ZN6soinfo9CallArrayEPKcPPFvvEjb+140)
I/DEBUG   (10991):     #04 pc 00001867  /system/bin/linker (__dl__ZN6soinfo16CallConstructorsEv+142)
I/DEBUG   (10991):     #05 pc 00003237  /system/bin/linker (__dl__Z9do_dlopenPKciPK17android_dlextinfo+194)
I/DEBUG   (10991):     #06 pc 00000f1d  /system/bin/linker (__dl__ZL10dlopen_extPKciPK17android_dlextinfo+24)
I/DEBUG   (10991):     #07 pc 001d274f  /system/lib/libart.so (art::JavaVMExt::LoadNativeLibrary(std::__1::basic_string<char, std::__1::char_traits<char>, std::__1::allocator<char> > const&, art::Handle<art::mirror::ClassLoader>, std::__1::basic_string<char, std::__1::char_traits<char>, std::__1::allocator<char> >*)+506)
I/DEBUG   (10991):     #08 pc 001f77a1  /system/lib/libart.so (art::Runtime_nativeLoad(_JNIEnv*, _jclass*, _jstring*, _jobject*, _jstring*)+528)
I/DEBUG   (10991):     #09 pc 0007980d  /data/dalvik-cache/arm/system@framework@boot.oat
I/art     ( 1941): Explicit concurrent mark sweep GC freed 3401(152KB) AllocSpace objects, 0(0B) LOS objects, 49% free, 1064KB/2MB, paused 13.846ms total 34.737ms
I/art     (11397): Background sticky concurrent mark sweep GC freed 16827(1002KB) AllocSpace objects, 8(186KB) LOS objects, 9% free, 9MB/10MB, paused 10.671ms total 36.628ms
E/ChimeraSrvcProxy(11397): Proxy without impl failing onBind()
I/ChimeraSrvcProxy(11397): com.google.android.gms.lockbox.service.LockboxBrokerService returning NullBinder for action com.google.android.gms.lockbox.service.START
W/ChimeraSrvcProxy(11397): Failed to read the buffer size file. Using default NullBinder reply size. java.io.FileNotFoundException: /data/data/com.google.android.gms/files/binder_buffer_size.txt: open failed: ENOENT (No such file or directory)
E/NativeCrashListener( 1008): Exception dealing with report
E/NativeCrashListener( 1008): android.system.ErrnoException: read failed: EAGAIN (Try again)
E/NativeCrashListener( 1008): 	at libcore.io.Posix.readBytes(Native Method)
E/NativeCrashListener( 1008): 	at libcore.io.Posix.read(Posix.java:165)
E/NativeCrashListener( 1008): 	at libcore.io.BlockGuardOs.read(BlockGuardOs.java:230)
E/NativeCrashListener( 1008): 	at android.system.Os.read(Os.java:350)
E/NativeCrashListener( 1008): 	at com.android.server.am.NativeCrashListener.consumeNativeCrashData(NativeCrashListener.java:240)
E/NativeCrashListener( 1008): 	at com.android.server.am.NativeCrashListener.run(NativeCrashListener.java:138)
```

Code coverage is around 70% according to JaCoCo report (it's approximate as I can't exclude non-testable things like DI, so I have to calculate it semi-manually).
