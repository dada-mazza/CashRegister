package ua.cashregister.reports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ua.cashregister.R;
import ua.cashregister.print.PrintActivity;
import ua.cashregister.storage.Print;
import ua.cashregister.storage.Shift;

/**
 * Перехід 1 - (логін) - 2
 * Меню звітність
 * */

public class Reports extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }

    // заглушка
    // передає назву звіту в наступну активність
    private void gag(String reports, Class aClass) {
        Intent intent = new Intent(this, aClass);
        intent.putExtra(Print.PRINT, reports);
        startActivity(intent);
    }

    public void onClickControlTape(View view) {
        // Контрольна стрічка
        // TODO код друку

        // відмітка - контрольна стрічка роздрукована
        Shift.closeShift(this, Shift.CONTROL_TYPE);

        gag(getResources().getString(R.string.control_tape), PrintActivity.class);
    }

    public void onClickReportOfKinds(View view) {
        // Звіт по видах
        // TODO код друку
        gag(getResources().getString(R.string.report_of_kinds), PrintActivity.class);
    }

    public void onClickReportAboutSpoiled(View view) {
        // Звіт по зіпсованим
        // TODO код друку
        gag(getResources().getString(R.string.report_about_spoiled), PrintActivity.class);
    }

    public void onClickReportCashier(View view) {
        // Звіт касира
        // TODO код друку
        gag(getResources().getString(R.string.report_cashier), PrintActivity.class);
    }

    public void onClickReportAuditor(View view) {
        // Звіт ревізора
        // TODO код друку
        gag(getResources().getString(R.string.report_auditor), PrintActivity.class);
    }

    public void onClickReportOfGoodsAndServices(View view) {
        // Звіт по реалізованим товарам та послугам
        // TODO код друку
        gag(getResources().getString(R.string.report_of_goods_and_services), PrintActivity.class);
    }


    public void onClickReportX(View view) {
        // Звіт Х
        // TODO код друку

        // перевірити зміну касира
        // якщо закрита - вивести повідомлення
        // якщо вікрита перевірити чи роздрукована контрольна стрічка
        // якщо не роздрукована вивести повідомлення
        // якщо роздрукована - роздрукувати Х-звіт
        if (!Shift.getBoolean(this, Shift.CASHIERS_SHIFT)) {
            Toast.makeText(this, R.string.casier_shift_close, Toast.LENGTH_SHORT)
                    .show();
        } else if (Shift.getBoolean(this, Shift.CONTROL_TYPE)) {
            Toast.makeText(this, R.string.print_control_type, Toast.LENGTH_SHORT)
                    .show();
        } else {
            // закриття зміни касира - друк Х-звіту
            Shift.closeShift(this, Shift.CASHIERS_SHIFT);
            gag(getResources().getString(R.string.report_x), PrintActivity.class);
        }
    }

    public void onClickReportZ(View view) {
        // Звіт Y
        // TODO код друку

        // перевірити зміну фіскальну
        // якщо закрита - вивести повідомлення
        // якщо вікрита перевірити чи роздрукована контрольна стрічка
        // якщо не роздрукована вивести повідомлення
        // якщо роздрукована - роздрукувати Х-звіт
        if (!Shift.getBoolean(this, Shift.FISCAL_SHIFT)) {
            Toast.makeText(this, R.string.fiscal_shift_close, Toast.LENGTH_SHORT)
                    .show();
        } else if (Shift.getBoolean(this, Shift.CASHIERS_SHIFT)) {
            Toast.makeText(this, R.string.close_casier_shift, Toast.LENGTH_SHORT)
                    .show();
        } else {
            Shift.closeShift(this, Shift.FISCAL_SHIFT);
            gag(getResources().getString(R.string.report_z), PrintActivity.class);
        }
    }


    public void onClickReportDatesPeriodicReduced(View view) {
        // Скорочений періодичний звіт по датам
        // TODO код друку
        gag(getResources().getString(R.string.reduced_periodic_report_by_dates), ReportsByDate.class);

    }

    public void onClickReportNumbersPeriodicReduced(View view) {
        // Скорочений періодичний звіт по номерам
        // TODO код друку
        gag(getResources().getString(R.string.reduced_periodic_report_by_numbers), ReportsByNumbers.class);
    }

    public void onClickReportDatesPeriodicComplete(View view) {
        // Повний періодичний звіт по датам
        // TODO код друку
        gag(getResources().getString(R.string.complete_periodic_report_by_dates), ReportsByDate.class);
    }

    public void onClickReportNumbersPeriodicComplete(View view) {
        // Повний періодичний звіт по номерам
        // TODO код друку
        gag(getResources().getString(R.string.complete_periodic_report_by_numbers), ReportsByNumbers.class);
    }
}
