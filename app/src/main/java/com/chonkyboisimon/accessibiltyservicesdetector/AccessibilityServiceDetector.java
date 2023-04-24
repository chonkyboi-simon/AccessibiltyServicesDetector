package com.chonkyboisimon.accessibiltyservicesdetector;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

public class AccessibilityServiceDetector {
    private static final String TAG = "AccessibilityServiceDet";

    //reliable method, internally it always checks if service string array element is ""
    static String[] getEnabledAccessibilityServices(Context context) {
        // Check if any accessibility service is enabled
        boolean isEmptyList = true;
        String enabledServices = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        String[] enabledServiceList=null;
        if (enabledServices != null) {
            enabledServiceList = enabledServices.split(":");
            //check for empty list
            for (String enabledService : enabledServiceList) {
                Log.d(TAG, "getEnabledAccessibilityServices: "+enabledService);
                if (enabledService!="") isEmptyList=false;
            }
            if (isEmptyList) {
                enabledServiceList=null;
                Log.d(TAG, "getEnabledAccessibilityServices: service list is empty");
            }
        } else {
            Log.d(TAG, "getEnabledAccessibilityServices: No enabled accessibility service");
        }

        return enabledServiceList;
    }

    //this function is NOT suitable with content observer
    //for reasons that I don't know, when content observer detected a change, calling this function may return the WRONG status of accessibility services
    static public boolean isAccessibilityEnabled_alt1(Context context){
        // Get the AccessibilityManager instance
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);

        // Check if at least one accessibility service is enabled
        boolean result = accessibilityManager.isEnabled();
        Log.d(TAG, "isAccessibilityEnabled_alt1: Accessibility Service enabled: "+result);
        return result;
    }

    //this function is NOT suitable with content observer
    //for reasons that I don't know, when content observer detected a change, calling this function may return the WRONG status of accessibility services
    static public boolean isAccessibilityEnabled_alt2(Context context) throws Settings.SettingNotFoundException {
        boolean result = Settings.Secure.getInt(
                context.getApplicationContext().getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED)==1?true:false;
        Log.d(TAG, "isAccessibilityEnabled_alt2: Accessibility Service enabled: "+result);

        return result;
    }


}