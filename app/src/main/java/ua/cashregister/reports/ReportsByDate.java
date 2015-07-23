package ua.cashregister.reports;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import android.widget.TextView;
import ua.cashregister.R;
import ua.cashregister.storage.Print;
import ua.cashregister.print.PrintActivity;

/**
 * Перехід 1 - (логін) - 2 - (9,11)
 * Меню звітність вибір дат переіоду
 * Створює діалоги вибору дати і передає їх в наступну активність
 * разом з назвою стартового звіту
 *
 * */

public class ReportsByDate extends Activity {


    private TextView startDateReports;
    private TextView finalDateReports;

    private Calendar startDate;
    private Calendar finalDate;

    private int DIALOG_START_DATE = 1;
    private int DIALOG_FINAL_DATE = 2;
    private int DIALOG_ALERT = 3;

    private int mStartYear;
    private int mStartMonth;
    private int mStartDay;

    private int mFinalYear;
    private int mFinalMonth;
    private int mFinalDay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_by_date);

        startDateReports = (TextView) findViewById(R.id.enterStartDate);
        finalDateReports = (TextView) findViewById(R.id.enterFinalDate);

        startDate = Calendar.getInstance();
        finalDate = Calendar.getInstance();

        mStartYear = startDate.get(Calendar.YEAR);
        mStartMonth = startDate.get(Calendar.MONTH);
        mStartDay = startDate.get(Calendar.DATE);

        mFinalYear = finalDate.get(Calendar.YEAR);
        mFinalMonth = finalDate.get(Calendar.MONTH);
        mFinalDay = finalDate.get(Calendar.DATE);

        updateStartDate();
        updateFinalDate();
    }


    public void onClickNext(View view) {

        // перевірка дат, кінцева далі початкової
        if (finalDate.compareTo(startDate) < 0) {
            // виклик діалогу помилки невідповідність порядку дат
            showDialog(DIALOG_ALERT);
        } else {
            Intent intent = new Intent(ReportsByDate.this, PrintActivity.class);
            String report = new StringBuilder()
                    .append(getIntent().getExtras().getString(Print.PRINT))
                    .append("\n")
                    .append(getResources().getString(R.string.start_date))
                    .append(String.format(" %td:%tm:%tY", startDate, startDate, startDate))
                    .append("\n")
                    .append(getResources().getString(R.string.final_date))
                    .append(String.format(" %td:%tm:%tY", finalDate, finalDate, finalDate))
                    .toString();

            intent.putExtra(Print.PRINT, report);
            startActivity(intent);
            finish();
        }
    }

    public void onClickEnterStartDate(View view) {

        showDialog(DIALOG_START_DATE);

    }

    public void onClickEnterFinalDate(View view) {

        showDialog(DIALOG_FINAL_DATE);

    }

    protected Dialog onCreateDialog(int id) {

        // створення діалогу початкової дати
        if (id == DIALOG_START_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, mSetStartDate, mStartYear, mStartMonth, mStartDay);
            return tpd;
        }

        // створення діалогу кінцевої дати
        if (id == DIALOG_FINAL_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, mSetFinalDate, mFinalYear, mFinalMonth, mFinalDay);
            return tpd;
        }

        // створення повідомлення про помилку в датах
        if (id == DIALOG_ALERT) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // заголовок
            alertDialog.setTitle(R.string.attention);
            // повідомлення
            alertDialog.setMessage(R.string.discrepancy_dates);
            // іконка
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            // кнопка відповіді
            alertDialog.setPositiveButton(R.string.yes, null);
            // створення діаллогу
            return alertDialog.create();
        }

        return super.onCreateDialog(id);
    }

    OnDateSetListener mSetStartDate = new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mStartYear = year;
            mStartMonth = monthOfYear;
            mStartDay = dayOfMonth;

            startDate.set(Calendar.DATE, mStartDay);
            startDate.set(Calendar.MONTH, mStartMonth);
            startDate.set(Calendar.YEAR, mStartYear);

            updateStartDate();
        }
    };

    OnDateSetListener mSetFinalDate = new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mFinalYear = year;
            mFinalMonth = monthOfYear;
            mFinalDay = dayOfMonth;

            finalDate.set(Calendar.DATE, mFinalDay);
            finalDate.set(Calendar.MONTH, mFinalMonth);
            finalDate.set(Calendar.YEAR, mFinalYear);

            updateFinalDate();
        }
    };


    public void updateStartDate() {

        startDateReports.setText(
                new StringBuilder(getResources().getString(R.string.start_date))
                        .append(String.format(" %td:%tm:%tY", startDate, startDate, startDate))
                        .toString());
    }


    public void updateFinalDate() {

        finalDateReports.setText(
                new StringBuilder(getResources().getString(R.string.final_date))
                        .append(String.format(" %td:%tm:%tY", finalDate, finalDate, finalDate))
                        .toString());
    }


}

