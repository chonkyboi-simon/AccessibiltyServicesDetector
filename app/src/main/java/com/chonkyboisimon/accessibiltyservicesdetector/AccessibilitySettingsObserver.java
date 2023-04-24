package com.chonkyboisimon.accessibiltyservicesdetector;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

// Custom ContentObserver to monitor changes to accessibility settings
public class AccessibilitySettingsObserver extends ContentObserver {
    private Context mContext;
    private OnAccessibilitySettingsChangeListener mListener;
    private static final String TAG = "AccessibilitySettingsOb";

    public interface OnAccessibilitySettingsChangeListener {
        void onAccessibilitySettingsChanged();
    }

    public AccessibilitySettingsObserver(Context context, OnAccessibilitySettingsChangeListener listener) {
        super(new Handler());
        mContext = context;
        mListener = listener;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        mListener.onAccessibilitySettingsChanged();
    }

    // Check if any accessibility service is enabled
    private boolean isAccessibilityServiceEnabled(Context context) throws Settings.SettingNotFoundException {
        return AccessibilityServiceDetector.isAccessibilityEnabled_alt2(context);
    }

    // Register the ContentObserver
    public void register() {
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES),
                false,
                this);
    }

    // Unregister the ContentObserver
    public void unregister() {
        mContext.getContentResolver().unregisterContentObserver(this);
    }
}
