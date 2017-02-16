# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Java\Android\sdk/tools/proguard/proguard-android.txt
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

# For more details butterknife and Proguard ERROR, see
#   https://github.com/JakeWharton/butterknife/issues/409
#   https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-butterknife-7.pro
#   http://stackoverflow.com/questions/33663162/butterknife-wont-work-with-proguard-enabled-used-recommended-proguard-rules

#************************************************************************
# ButterKnife 7
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
     @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#************************************************************************
#logback-android
-keep class ch.qos.** { *; }
-keep class org.slf4j.** { *; }
-keepattributes *Annotation*
#If you don't use the mailing features of logback (i.e., the SMTPAppender),
#you might encounter an error while exporting your app with ProGuard.
#To resolve this, add the following rule
-dontwarn ch.qos.logback.core.net.*

#************************************************************************