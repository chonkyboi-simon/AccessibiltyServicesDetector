package com.chonkyboisimon.accessibiltyservicesdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements AccessibilitySettingsObserver.OnAccessibilitySettingsChangeListener{
    TextView textViewResults;
    Button buttonManuallyUpdate;
    private static final String TAG = "MainActivity";
    private AccessibilitySettingsObserver mAccessibilitySettingsObserver=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResults=findViewById(R.id.textView_result);
        buttonManuallyUpdate=findViewById(R.id.button_manual_check);
        buttonManuallyUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccessibilityStatus();
            }
        });

        //initial detection
        updateAccessibilityStatus();

        //ongoing monitoring
        AccessibilitySettingsObserver mAccessibilitySettingsObserver = new AccessibilitySettingsObserver((Context) this, (AccessibilitySettingsObserver.OnAccessibilitySettingsChangeListener) this);
        mAccessibilitySettingsObserver.register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAccessibilitySettingsObserver!=null) mAccessibilitySettingsObserver.unregister();
    }

    @Override
    public void onAccessibilitySettingsChanged() {
        updateAccessibilityStatus();
    }


    private void updateAccessibilityStatus() {
        Date current = new Date();

        boolean enabled;
        enabled=AccessibilityServiceDetector.isAccessibilityEnabled_alt1(getApplicationContext());

        //method 1
        textViewResults.append("\n\n"+current.toString()+"\nAccessibility service enabled method 1: "+enabled);

        //method 2
        try {
            enabled=AccessibilityServiceDetector.isAccessibilityEnabled_alt2(getApplicationContext());
            textViewResults.append("\n"+current.toString()+"\nAccessibility service enabled method 2: "+enabled);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            textViewResults.append("\n"+current.toString()+"\nAccessibility service enabled method 2: "+e.getMessage());
        }

        //accessibility services
        String[] enabledServices= AccessibilityServiceDetector.getEnabledAccessibilityServices(getApplicationContext());
        if (enabledServices!=null){
            textViewResults.append("\nEnabled accessibility services:");
            int index =0;
            for (String enabledService : enabledServices) {
                index++;
                textViewResults.append("\n"+index+" ");
                if (enabledService!="") {
                    textViewResults.append(enabledService);
                } else {
                    textViewResults.append("empty");
                }

            }
        } else {
            textViewResults.append("\nEnabled accessibility services: Null");
        }
    }
}