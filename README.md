**About this app**

This app was made to demonstrate how to detect enabled accessibility services. It consists of two classes that work together to detect enabled accessibility services actively and passively:

In `AccessibilityServiceDetector.java`, both `isAccessibilityServiceEnabled_alt1()` and i`sAccessibilityServiceEnabled_alt2()` can be invoked to check whether any accessibility service apps are enabled. In addition, if you are wondering about the package names and accessibility service names, call `getEnabledAccessibilityServices()` to get a list of names in a `String[]`.

Since each accessibility service can be enabled anytime by long-pressing both volume keys (on Pixel devices) without accessing the Settings app, the `AccessibilitySettingsObserver.java` class was created to monitor the changes made to the accessibility service. See the sample code in the `MainActivity.java` to find out more about the functions of accessibility service monitoring.

***Important Note***
When using `AccessibilitySettingsObserver.java` to detect changes in accessibility service, both i`sAccessibilityServiceEnabled_alt1()` and `isAccessibilityServiceEnabled_alt2()` may not return the correct accessibility service if you call them immediately after the change was detected using the content observer. And this was the reason why the demo app has a button for manually updating the accessibility service. If you find the `isAccessibilityServiceEnabled_alt1()` and/or `isAccessibilityServiceEnabled_alt2()` are returning the incorrect result, then manually updating the accessibility service list will guarantee it works correctly.

With the timing issue said, testing has confirmed that `getEnabledAccessibilityServices()` works correctly under all conditions. It has an internal check for filtering invalid results.