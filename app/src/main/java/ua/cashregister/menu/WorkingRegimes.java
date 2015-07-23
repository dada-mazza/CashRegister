package ua.cashregister.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import ua.cashregister.R;
import ua.cashregister.register.menu.CashRegister;
import ua.cashregister.reports.Reports;
import ua.cashregister.setting.SettingRROActivity;
import ua.cashregister.storage.Shift;

/**
 * Екран 1
 * Режими роботи
 */

public class WorkingRegimes extends Activity {

    private final static String FOR_LOGIN = "ua.cashregister.FOR_LOGIN";

    private int DIALOG_ALERT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_regims);

    }

    // Передача наступного кроку після авторизації
    private void transferActivity(Class activityClass) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra(FOR_LOGIN, activityClass);
        startActivity(intent);
    }

    // Перевірка тривалості зміни
    private boolean lengthOfShifts() {

        // TODO прописати перевірку початку зміни і відмітки часу роботи

        // Перевірити час роботи
        // спочатку перевірити чи відкрита фіскальна зміна
        // за промовчуванням зміна закрита
        // false - зміна відкрита
        // якщо зміна відкрита перевірити час роботи

        if (Shift.getBoolean(this, Shift.FISCAL_SHIFT)) {

            // отримати
            long startTime = Shift.getLong(this, Shift.START_TIME_OF_SHIFT);


            long nowTime = Calendar.getInstance().getTimeInMillis();

            long day = 24 * 60 * 60 * 60 * 1000;

            return ((nowTime - startTime) > day);
        }

        return false;

    }

    // перехід по пункту Реєстрація
    public void onClickRegistration(View v) {

        if (lengthOfShifts()) {
            showDialog(DIALOG_ALERT);
        } else {
            transferActivity(CashRegister.class);
        }
    }

    // перехід по пункту Звітування
    public void onClickReports(View v) {

        transferActivity(Reports.class);
    }

    // перехід по пункту Налаштування
    public void onClickSettingsRRO(View v) {

        transferActivity(SettingRROActivity.class);
    }

    // створення повідомлення про тривалість зміни, при перевищенні 24 годин
    protected Dialog onCreateDialog(int id) {

        // створення повідомлення
        if (id == DIALOG_ALERT) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // заголовок
            alertDialog.setTitle(R.string.attention);
            // повідомлення
            alertDialog.setMessage(R.string.shift_more_24_hours);
            // іконка
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            // кнопка відповіді
            alertDialog.setPositiveButton(R.string.yes, null);
            // створення діаллогу
            return alertDialog.create();
        }

        return super.onCreateDialog(id);
    }
}
