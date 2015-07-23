package ua.cashregister.storage;

/** Клас призначено для збереження назв констант налаштувань
 * та роботи з внутрішнім сховищем налаштувань
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SettingRRO {

    // назва файлу налаштувань
    // формується додатком
    // не змінювати
    public final static String STORAGE_NAME = "ua.cashregister_preferences";


    // формування налаштувань при першому старті
    // до першого запуску - false
    public final static String FIRST_START = "menuSettingRRO_FIRST_START";

    // ключі
    // режим продажу
    public final static String MODE_SALE = "menuSettingRRO_mode_sale";

    // ключі
    // режим НДІ
    // true - використовувати
    public final static String MODE_NDI = "menuSettingRRO_mode_ndi";


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SettingRRO.STORAGE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    // отримати зі сховища boolean
    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    // покласти в сховище boolean
    public static void putBoolean(Context context, String key, Boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    // отримати зі сховища String
    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    // перевірка режиму продажу
    public static boolean checkModeSale(Context context, String modeSale) {
        return getString(context, SettingRRO.MODE_SALE)
                .equals(modeSale);
    }

    public static boolean checkModeNDI(Context context) {
        return getBoolean(context, SettingRRO.MODE_NDI);
    }




   /*
    <string name="menuSettingRRO_mode_rro">Автономний</string>
    <boolean name="menuSettingRRO_print_summary_articles" value="true" />
    <boolean name="menuSettingRRO_cut_ribbon_after_print" value="true" />
    <boolean name="menuSettingRRO_smaller_font_control_tape" value="true" />
    <boolean name="menuSettingRRO_permit_cancellation" value="true" />
    <boolean name="menuSettingRRO_permit_return" value="true" />
    <boolean name="menuSettingRRO_view_fiscal_data_serial_number" value="true" />
    <boolean name="menuSettingRRO_accumulation_of_data_in_the_X_reports" value="false" />
    <boolean name="menuSettingRRO_mode_ndi" value="true" />
    <boolean name="menuSettingRRO_mode_departure_date" value="true" />
    <boolean name="menuSettingRRO_making_delivery" value="true" />
    <boolean name="menuSettingRRO_print_uidch" value="true" />
    <string name="menuSettingRRO_rate_rs_232">4800</string>
    <boolean name="menuSettingRRO_view_fiscal_data_fiscal_number" value="true" />
    <string name="menuSettingRRO_contrast">Normal</string>
    <string name="menuSettingRRO_font_of_reports">Середній</string>
    <string name="menuSettingRRO_change_in_tax_rates">ПДВ А = +/- XX%</string>
    <boolean name="menuSettingRRO_permit_overflow_control_tape" value="false" />
    <string name="menuSettingRRO_keyboard_settings">Автоповтор</string>
    <boolean name="menuSettingRRO_view_fiscal_data_tax_number" value="true" />
    <string name="menuSettingRRO_mode_sale">Без місць</string>
    */


}
