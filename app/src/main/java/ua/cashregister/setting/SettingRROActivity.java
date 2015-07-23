package ua.cashregister.setting;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import ua.cashregister.R;
import ua.cashregister.storage.SettingRRO;
/**
 * Перехід 1 - (логін) - 3
 * Клас налаштування
 * використовується внутрішнє сховище
 * */

public class SettingRROActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_rro);


        if (!SettingRRO.getBoolean(this, SettingRRO.FIRST_START)) {

            SettingRRO.putBoolean(this, SettingRRO.FIRST_START, true);

            finish();
        }
    }
}
