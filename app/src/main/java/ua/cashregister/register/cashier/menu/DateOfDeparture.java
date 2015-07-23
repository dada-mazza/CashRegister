package ua.cashregister.register.cashier.menu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import ua.cashregister.R;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.SettingRRO;

import java.util.Calendar;

/**
 * Перехід 1 - (логін) - 1 - 1
 * менню введення дати відправлення
 */

public class DateOfDeparture extends Activity {


    private TextView dateOfDeparture;

    private Calendar calendar;

    private int DIALOG_DATE = 1;

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_departure);

        dateOfDeparture = (TextView) findViewById(R.id.enterDateOfDeparture);

        calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);

        updateDate();
    }

    public void onClickNext(View view) {

        // Пеердача вибраної дати
        // TODO передача дати
        // Receipt.putLong(this, Receipt.DATE_OF_DEPARTURE, calendar.getTimeInMillis());
        Receipt.putString(this, Receipt.DATE_OF_DEPARTURE, String.format("%td.%tm.%tY", calendar, calendar, calendar));

        // перевірка режиму продажу
        // для режимн без місць перехід до вибору рейс та маршрут
        // інакше список рейсів
        if (SettingRRO.checkModeSale(DateOfDeparture.this, getResources()
                .getString(R.string.mode_sale_regimes_without_seats))) {

            Intent intent = new Intent(DateOfDeparture.this, TrainOrRoute.class);
            startActivity(intent);
            finish();

        } else {

            Intent intent = new Intent(this, Train.class);
            startActivity(intent);
            finish();
        }
    }

    // відображення діалогу вибору дати
    public void onClickEnterDateOfDeparture(View view) {

        showDialog(DIALOG_DATE);

    }

    protected Dialog onCreateDialog(int id) {

        // створення діалогу вибору дати
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, mSetDate, mYear, mMonth, mDay);
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    private OnDateSetListener mSetDate = new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            calendar.set(Calendar.DATE, mDay);
            calendar.set(Calendar.MONTH, mMonth);
            calendar.set(Calendar.YEAR, mYear);

            updateDate();
        }
    };


    private void updateDate() {

        dateOfDeparture.setText(String.format("%td.%tm.%tY", calendar, calendar, calendar));

    }

}

