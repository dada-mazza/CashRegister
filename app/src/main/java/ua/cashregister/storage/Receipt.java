package ua.cashregister.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Квитанція
 * клас містить поля квитанцій
 */
public class Receipt {

    // назва файлу сховища даних
    public final static String STORAGE_NAME = "ua.cashregister.print.storage.receipt";

    // ключ CashRegister
    // слугує для передачі відмітки пунктів меню переходу
    // касир
    // ревізор
    // посадчик
    // службове внесення
    // службове вилучення
    // звітність
    public final static String CASH_REGISTER_MENU_ITEM = STORAGE_NAME.concat("CASH_REGISTER_MENU_ITEM");

    // ключ TypeOfTicket
    // слугує для передачі відмітки пунктів меню переходу
    // новий
    // повтор
    // гашення
    // повернення
    // пробний квиток
    // відомість посадчика
    public final static String ACTION_OF_RECEIPT_MENU_ITEM = STORAGE_NAME.concat("ACTION_OF_RECEIPT_MENU_ITEM");

    // ключ TypeOfReceipt
    // слугує для передачі відмітки пунктів меню переходу
    // квиток
    // багажна квитанція
    // абонемент
    // Товари/Послуги
    // електронний квиток
    // Тарифний штраф
    // Адміністративний штраф
    // Зменшання поїздок на картці
    // Облік відсутніх пасажирів
    public final static String TYPE_OF_RECEIPT_MENU_ITEM = STORAGE_NAME.concat("ACTION_OF_RECEIPT_MENU_ITEM");

    // ключ дата відправлення
    // зберігає дату в мілісекундах
    public final static String DATE_OF_DEPARTURE = STORAGE_NAME.concat("DATE_OF_DEPARTURE");

    // ключ маршрут
    public static final String ROUTE = STORAGE_NAME.concat("ROUTE");

    // ключ № рейсу
    public static final String TRAIN = STORAGE_NAME.concat("TRAIN");

    // ключ № вагона
    public static final String WAGON = STORAGE_NAME.concat("WAGON");

    // ключ клас місця
    public static final String TYPES_CLASS = STORAGE_NAME.concat("TYPES_CLASS");

    // ключ перевізника
    public static final String CARRIER = STORAGE_NAME.concat("CARRIER");

    // ключ номер РРО
    public static final String NUMBER_RRO = STORAGE_NAME.concat("NUMBER_RRO");

    // ключ номер чека
    public static final String NUMBER_CHECK = STORAGE_NAME.concat("NUMBER_CHECK");

    // ключ дата продажу
    public static final String DATE_OF_SALE = STORAGE_NAME.concat("DATE_OF_SALE");

    // ключ товар
    public static final String GOOD = STORAGE_NAME.concat("GOOD");

    // ключ одиниця виміру
    public static final String UNIT_OF_MEASUREMENT = STORAGE_NAME.concat("UNIT_OF_MEASUREMENT");

    // ключ кількість товару
    public static final String QUANTITY_OF_GOODS = STORAGE_NAME.concat("QUANTITY_OF_GOODS");

    // ключ Blank
    public static final String BLANK = STORAGE_NAME.concat("BLANK");

    // ключ дистанція
    public static final String DISTANCE = STORAGE_NAME.concat("DISTANCE");

    // ключ від станції
    public static final String FROM_STATION = STORAGE_NAME.concat("FROM_STATION");

    // ключ до станції
    public static final String TO_STATION = STORAGE_NAME.concat("TO_STATION");

    // ключ станція пересадки
    public static final String STATION_OF_TRANSFER = STORAGE_NAME.concat("STATION_OF_TRANSFER");

    // ключ пільга
    public static final String BENEFIT = STORAGE_NAME.concat("BENEFIT");

    // ключ пересадка
    public static final String TRANSFER = STORAGE_NAME.concat("TRANSFER");

    // ключ напрямок
    public static final String WAY = STORAGE_NAME.concat("WAY");

    // ключ напрямок
    public static final String SURNAME_OF_PASSENGER = STORAGE_NAME.concat("SURNAME_OF_PASSENGER");

    // ключ напрямок
    public static final String CERTIFICATE = STORAGE_NAME.concat("CERTIFICATE");

    // ключ багаж
    public static final String BAGGAGE = STORAGE_NAME.concat("BAGGAGE");

    // ключ місце
    public static final String SEAT = STORAGE_NAME.concat("SEAT");

    // ключ додатковий збір
    public static final String ADDITIONAL_CHARGE = STORAGE_NAME.concat("ADDITIONAL_CHARGE");

    // стаття штрафу
    public static final String ARTICLE_FINE = STORAGE_NAME.concat("ARTICLE_FINE");

    //розмір штрафу
    public static final String SIZE_OF_FINE = STORAGE_NAME.concat("SIZE_OF_FINE");

    // тип абонемента
    public static final String TYPE_OF_SUBSCRIPTION = STORAGE_NAME.concat("TYPE_OF_SUBSCRIPTION");

    // кількість місяців/поїздок
    public static final String QUANTITY_OF_MONTH_OR_TRIP = STORAGE_NAME.concat("QUANTITY_OF_MONTH_OR_TRIP");

    // Дата дії початок
    public static final String START_DATE = STORAGE_NAME.concat("START_DATE");

    // Дата дії кінець
    public static final String FINAL_DATE = STORAGE_NAME.concat("FINAL_DATE");

    // Абонемент іменний
    public static final String NOMINAL_SUBSCRIPTION = STORAGE_NAME.concat("NOMINAL_SUBSCRIPTION");

    // Бланк абонементу
    public static final String TYPE_OF_SUBSCRIPTION_BLANK = STORAGE_NAME.concat("TYPE_OF_SUBSCRIPTION_BLANK");

    // паперового бланку
    public static final String NUMBER_OF_PAPER_BLANK = STORAGE_NAME.concat("NUMBER_OF_PAPER_BLANK");

    // до сплати
    public static final String PAYABLE = STORAGE_NAME.concat("PAYABLE");



    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Receipt.STORAGE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    // покласти в сховище
    public static void putString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    // отримати зі сховища
    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    // перевірка переходу
    public static boolean checkTransition(Context context, String key, String transition) {
        return getString(context, key)
                .equals(transition);
    }

    // покласти в сховище
    public static void clear (Context context) {
        getEditor(context).clear().commit();
    }


}
