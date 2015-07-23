package ua.cashregister.register.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ua.cashregister.R;
import ua.cashregister.register.cashier.action.menu.ActionOfReceipt;
import ua.cashregister.print.PrintActivity;
import ua.cashregister.register.cashier.menu.DateOfDeparture;
import ua.cashregister.register.cashier.menu.TrainOrRoute;
import ua.cashregister.register.introduction.Introduction;
import ua.cashregister.register.withdrawal.Withdrawal;
import ua.cashregister.reports.Reports;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.Print;
import ua.cashregister.storage.SettingRRO;
import ua.cashregister.storage.Shift;

import java.util.Calendar;

/**
 * Перехід 1 - (логін)
 * при старті перевіряє відкриття фіскальної зміни
 * та зміни касира
 * виводить повідомлення про необхідність їх відкриття
 */


public class CashRegister extends Activity {

    private int DIALOG_FISCAL = 1;
    private int DIALOG_CASHIER = 2;
    private int DIALOG_REPORTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_register);

        // перевірка режиму без місць, якщо режим встановлено
        // кнопки ревізор та аудитор не відображати
        if (SettingRRO.checkModeSale(this, getResources()
                .getString(R.string.mode_sale_regimes_without_seats))) {
            Button bAuditor = (Button) findViewById(R.id.auditor);
            Button bPosadchyk = (Button) findViewById(R.id.posadchyk);
            bAuditor.setVisibility(View.GONE);
            bPosadchyk.setVisibility(View.GONE);
        }

        // перевірити чи відкрита фіскальна зміна
        // якщо зміна закрита викликати діалог відкриття зміни
        // потім перевірити чи відкрита зміна касира
        // якщо зміна закрита викликати діалог відкриття зміни
        if (!Shift.getBoolean(this, Shift.FISCAL_SHIFT)) {
            showDialog(DIALOG_FISCAL);
        } else if (!Shift.getBoolean(this, Shift.CASHIERS_SHIFT)) {
            showDialog(DIALOG_CASHIER);
        }


    }

    // пункти меню
    // пункт касир
    public void onClickCashier(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.CASH_REGISTER_MENU_ITEM, getResources().getString(R.string.cashier));

        // перевірка режиму продажу
        // для режиму продажу товари/послуги перехід до (Т)
        if (SettingRRO.checkModeSale(this, getResources().getString(R.string.mode_sale_regimes_products_and_services))) {
            Intent intent = new Intent(this, ActionOfReceipt.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DateOfDeparture.class);
            startActivity(intent);
        }
    }

    // пункт ревізор
    public void onClickAuditor(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.CASH_REGISTER_MENU_ITEM, getResources().getString(R.string.auditor));
        // викликати наступну активність
        Intent intent = new Intent(this, DateOfDeparture.class);
        startActivity(intent);
    }

    // пункт Посадчик
    public void onClickPosadchyk(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.CASH_REGISTER_MENU_ITEM, getResources().getString(R.string.posadchyk));
        // викликати наступну активність
        Intent intent = new Intent(this, TrainOrRoute.class);
        startActivity(intent);

    }

    // пункт Службове внесення
    public void onClickButtonOfficialIntroduction(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.CASH_REGISTER_MENU_ITEM, getResources().getString(R.string.official_introduction));
        // викликати наступну активність
        Intent intent = new Intent(this, Introduction.class);
        startActivity(intent);
    }

    // пункт Службове вилучення
    public void onClickButtonOfficialWithdrawal(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.CASH_REGISTER_MENU_ITEM, getResources().getString(R.string.official_withdrawal));
        // викликати наступну активність
        Intent intent = new Intent(this, Withdrawal.class);
        startActivity(intent);
    }

    // пункт Звітність
    public void onClickReportsCash(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.CASH_REGISTER_MENU_ITEM, getResources().getString(R.string.report));
        // викликати наступну активність через діалог підтвердження
        showDialog(DIALOG_REPORTS);
    }

    //--------------------------------------------------------------------------------

    // створення діалогів
    @Override
    protected Dialog onCreateDialog(int id) {

        // фіскальна зміна
        // створення повідомлення
        if (id == DIALOG_FISCAL) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            // заголовок
            dialog.setTitle(R.string.attention);
            // повідомлення
            dialog.setMessage(new StringBuffer()
                    .append(getResources().getString(R.string.fiscal_shift_close))
                    .append("\n")
                    .append(getResources().getString(R.string.need_print_reports))
                    .append("\n")
                    .append("1. ")
                    .append(getResources().getString(R.string.start_fiscal_report))
                    .append("\n")
                    .append("2. ")
                    .append(getResources().getString(R.string.start_cashier_report))
                    .toString());

            // іконка
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            // кнопка позитивної відповіді
            dialog.setPositiveButton(R.string.yes, mOnClickDialogFiscal);
            // кнопка негативної відповіді
            dialog.setNegativeButton(R.string.cancel, mOnClickDialogFiscal);
            // створення діаллогу
            return dialog.create();
        }

        // зміна касира
        // створення повідомлення
        if (id == DIALOG_CASHIER) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            // заголовок
            dialog.setTitle(R.string.attention);
            // повідомлення
            dialog.setMessage(new StringBuffer()
                    .append(getResources().getString(R.string.casier_shift_close))
                    .append("\n")
                    .append(getResources().getString(R.string.need_print_reports))
                    .append("\n")
                    .append("1. ")
                    .append(getResources().getString(R.string.start_cashier_report))
                    .toString());

            // іконка
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            // кнопка позитивної відповіді
            dialog.setPositiveButton(R.string.yes, mOnClickDialogCashier);
            // кнопка негативної відповіді
            dialog.setNegativeButton(R.string.cancel, mOnClickDialogCashier);
            // створення діаллогу
            return dialog.create();
        }

        // підтвердження переходу в меню звітність
        if (id == DIALOG_REPORTS) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            // заголовок
            dialog.setTitle(R.string.app_name);
            // повідомлення
            dialog.setMessage(R.string.go_to_the_menu_reporting);
            // іконка
            dialog.setIcon(android.R.drawable.ic_menu_help);
            // кнопка позитивної відповіді
            dialog.setPositiveButton(R.string.yes, mOnClickDialogReports);
            // кнопка негативної відповіді
            dialog.setNegativeButton(R.string.cancel, mOnClickDialogReports);
            // створення діалогу
            return dialog.create();
        }

        return super.onCreateDialog(id);
    }

    // слухач для діалогу фіскальний звіт
    DialogInterface.OnClickListener mOnClickDialogFiscal = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // дія кнопки позитивної відповіді
                case Dialog.BUTTON_POSITIVE:

                    Intent intent = new Intent(CashRegister.this, PrintActivity.class);
                    String reports = new StringBuilder()
                            .append(getResources().getString(R.string.start_fiscal_report))
                            .append("\n")
                            .append(getResources().getString(R.string.start_cashier_report))
                            .toString();
                    intent.putExtra(Print.PRINT, reports);

                    // відмітка часу - початок роботи
                    Long startTime = Calendar.getInstance().getTimeInMillis();
                    getSharedPreferences(Shift.STORAGE_NAME, MODE_PRIVATE).edit()
                            .putLong(Shift.START_TIME_OF_SHIFT, startTime).commit();

                    // відмітка - відкриття зміни фіскальної
                    Shift.openShift(CashRegister.this, Shift.FISCAL_SHIFT);
                    // відмітка - відкриття зміни касира
                    Shift.openShift(CashRegister.this, Shift.CASHIERS_SHIFT);
                    // контрольна стрічка
                    Shift.openShift(CashRegister.this, Shift.CONTROL_TYPE);

                    // TODO друк звітів
                    startActivity(intent);

                    break;

                // дія кнопки негативної відповіді
                case Dialog.BUTTON_NEGATIVE:
                    finish();
                    break;

            }
        }
    };

    // слухач для діалогу звіт касира
    DialogInterface.OnClickListener mOnClickDialogCashier = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // дія кнопки позитивної відповіді
                case Dialog.BUTTON_POSITIVE:
                    Intent intent = new Intent(CashRegister.this, PrintActivity.class);
                    String reports = new StringBuilder()
                            .append(getResources().getString(R.string.start_cashier_report))
                            .toString();
                    intent.putExtra(Print.PRINT, reports);

                    // відмітка - відкриття зміни касира
                    Shift.openShift(CashRegister.this, Shift.CASHIERS_SHIFT);
                    // контрольна стрічка
                    Shift.openShift(CashRegister.this, Shift.CONTROL_TYPE);

                    // TODO друк звітів
                    startActivity(intent);

                    break;
                // дія кнопки негативної відповіді
                case Dialog.BUTTON_NEGATIVE:
                    finish();
                    break;

            }
        }
    };

    // слухач для діалогу Звітність
    DialogInterface.OnClickListener mOnClickDialogReports = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // дія кнопки позитивної відповіді
                case Dialog.BUTTON_POSITIVE:
                    // перехід в меню звітування
                    Intent intent = new Intent(CashRegister.this, Reports.class);
                    startActivity(intent);

                    break;
                // дія кнопки негативної відповіді
                case Dialog.BUTTON_NEGATIVE:
                    break;

            }
        }
    };


}
