package ua.cashregister.register.cashier.action.repay;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import ua.cashregister.R;
import ua.cashregister.register.cashier.action.receipt.ProductsAndServices;
import ua.cashregister.storage.Receipt;

import java.util.Calendar;

/**
 * перехід
 * з
 * TypeOfTicket
 * меню повернення
 * до ProductsAndServices
 */

public class Repay extends Activity {

    private TextView enterNumberRRO;
    private TextView enterNumberCheck;
    private TextView enterDateOfSale;

    private Calendar calendar;
    private int DIALOG_DATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay);

        enterNumberRRO = (TextView) findViewById(R.id.enterNumberRRO);
        enterNumberCheck = (TextView) findViewById(R.id.enterNumberCheck);
        enterDateOfSale = (TextView) findViewById(R.id.enterDateOfSale);

        calendar = Calendar.getInstance();

        updateDate();
    }

    public void onClickEnterDateOfSale(View view) {
        showDialog(DIALOG_DATE);
    }

    public void onClickNext(View view) {

        StringBuilder toast = checkField();
        // якщо повідомлення не пусте - вивести повідомлення
        if (toast.length() != 0) {
            Toast.makeText(this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        // занести значення полів
        saveDataRepay();

        // Intent intent = new Intent(Repay.this, BlankRepay.class);
        Intent intent = new Intent(Repay.this, ProductsAndServices.class);
        startActivity(intent);
        finish();

    }

    // перевірити заповненність полів, сформувати повідомлення про незаповнені
    private StringBuilder checkField() {
        // формування тексту повідомлення
        StringBuilder toast = new StringBuilder();

        // якщо не введено номер РРО
        if (enterNumberRRO.getText().length() == 0) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.enter_number_rro));
            toast.append("!");
        }

        // якщо не введено номер чека
        if (enterNumberCheck.getText().length() == 0) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.enter_number_check));
            toast.append("!");
        }

        // якщо не введено дату
        if (Calendar.getInstance().getTimeInMillis() < calendar.getTimeInMillis()) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.date_check_not_correct));
            toast.append("!");
        }

        return toast;
    }

    protected Dialog onCreateDialog(int id) {

        // створення діалогу вибору дати
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            calendar.set(Calendar.DATE, dayOfMonth);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.YEAR, year);

                            updateDate();
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
            );
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    private void updateDate() {

        enterDateOfSale.setText(String.format("%td.%tm.%tY", calendar, calendar, calendar));

    }

    // збереження даних
    private void saveDataRepay() {

        //TODO занесення введених даних
        Receipt.putString(this, Receipt.NUMBER_RRO, enterNumberRRO.getText().toString());
        Receipt.putString(this, Receipt.NUMBER_CHECK, enterNumberCheck.getText().toString());
        Receipt.putString(this, Receipt.DATE_OF_SALE, String.format("%td.%tm.%tY", calendar, calendar, calendar));
    }

}
