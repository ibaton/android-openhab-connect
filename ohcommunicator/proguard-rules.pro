# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\matti\AppData\Local\Android\sdk1/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontoptimize
-dontobfuscate

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient

# WAsync + AHC dependencies
# TODO Check if Grizzly can be replaced with Apache Http Client
# For WAsync 1.4.3
-dontwarn com.ning.http.client.providers.**
-dontwarn com.ning.http.util.**