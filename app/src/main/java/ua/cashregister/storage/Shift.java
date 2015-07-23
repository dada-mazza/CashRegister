package ua.cashregister.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Містить константи для роботи зі змінами
 * використовується для роботи зі сховищем
 * для збереження міток відкриття закриття змін
 * старту та закінчення роботи РРО
 */
public class Shift {

    // назва файлу налаштувань
    public final static String STORAGE_NAME = "ua.cashregister.print.storage.shift";

    // відмітка часу старту зміни
    public final static String START_TIME_OF_SHIFT = STORAGE_NAME.concat("START_TIME_OF_SHIFT");

    // зміна відкрита
    public final static boolean SHIFT_OPEN = true;

    // зміна закрита
    public final static boolean SHIFT_CLOSE = false;

    // відмітка закриття/відкриття фіскальної зміни
    public final static String FISCAL_SHIFT = STORAGE_NAME.concat("FISCAL_SHIFT");

    // відмітка закриття/відкриття зміни касира
    public final static String CASHIERS_SHIFT = STORAGE_NAME.concat("CASHIERS_SHIFT");

    // відмітка друку контрольної чтрічки
    // true - потрібно друкувати
    // false - надрукована
    public final static String CONTROL_TYPE = STORAGE_NAME.concat("CONTROL_TYPE");

    // відкриття зміни
    public static void openShift(Context context, String key){
        putBoolean(context, key, SHIFT_OPEN);
    }

    // закриття зміни
    public static void closeShift(Context context, String key){
        putBoolean(context, key, SHIFT_CLOSE);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Shift.STORAGE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    // покласти в сховище
    public static void putBoolean(Context context, String key, Boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    // отримати зі сховища boolean
    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    // отримати зі сховища long
    public static Long getLong(Context context, String key) {
        return getSharedPreferences(context).getLong(key, 0);
    }




}
