package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ua.cashregister.R;
import ua.cashregister.storage.Receipt;

import java.util.Calendar;

public class Subscription extends Activity {

    private Spinner typeOfSubscription;
    private Spinner typeOfBlank;

    private LinearLayout layoutNumberPaperOfBlank;
    private LinearLayout layoutSurnameOfPassenger;

    private Switch switchNominalSubscription;

    private TextView tvStartDate;
    private TextView tvFinalDate;

    private Calendar startDate;
    private Calendar finalDate;

    private int DIALOG_START_DATE = 1;
    private int DIALOG_FINAL_DATE = 2;

    private TextView enterQuantityOfMonthOrTrip;
    private TextView enterSurnameOfPassenger;
    private TextView enterNumberOfPaperBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);


        // список Тип абонемента
        typeOfSubscription = (Spinner) findViewById(R.id.typeOfSubscription);
        typeOfSubscription.setAdapter(createAdapter(R.array.list_of_types_of_subscription));

        // дати
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvFinalDate = (TextView) findViewById(R.id.tvFinalDate);

        startDate = Calendar.getInstance();
        finalDate = Calendar.getInstance();

        updateDate(startDate, tvStartDate);
        updateDate(finalDate, tvFinalDate);

        // тип абонемента Іменний/Не іменний
        switchNominalSubscription = (Switch) findViewById(R.id.switchNominalSubscription);
        layoutSurnameOfPassenger = (LinearLayout) findViewById(R.id.layoutSurnameOfPassenger);
        if (!switchNominalSubscription.isChecked()) {
            layoutSurnameOfPassenger.setVisibility(View.GONE);
        }

        // список Тип бланку
        layoutNumberPaperOfBlank = (LinearLayout) findViewById(R.id.layoutNumberPaperOfBlank);
        typeOfBlank = (Spinner) findViewById(R.id.typeOfBlank);
        typeOfBlank.setAdapter(createAdapter(R.array.list_of_types_of_blank));
        typeOfBlank.setSelection(1);
        typeOfBlank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                // дії в залежності від вибраного елемента

                // паперовий бланк
                if (position == 0) {
                    layoutNumberPaperOfBlank.setVisibility(View.VISIBLE);
                    Toast.makeText(Subscription.this, getString(R.string.enter_number_of_paper_blank),
                            Toast.LENGTH_SHORT).show();
                }
                // електронна картка
                else if (position == 1) {
                    layoutNumberPaperOfBlank.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        enterQuantityOfMonthOrTrip = (TextView) findViewById(R.id.enterQuantityOfMonthOrTrip);
        enterSurnameOfPassenger = (TextView) findViewById(R.id.enterSurnameOfPassenger);
        enterNumberOfPaperBlank = (TextView) findViewById(R.id.enterNumberOfPaperBlank);

    }

    // початкова дата
    public void onClickEnterStartDate(View view) {

        showDialog(DIALOG_START_DATE);

    }

    // кінцева дата
    public void onClickEnterFinalDate(View view) {

        showDialog(DIALOG_FINAL_DATE);

    }

    // перемикач іменний/не іменний
    public void onClickSwitchNominalSubscription(View view) {

        if (switchNominalSubscription.isChecked()) {
            layoutSurnameOfPassenger.setVisibility(View.VISIBLE);
            Toast.makeText(Subscription.this, getString(R.string.enter_surname_of_passenger).concat("!"), Toast.LENGTH_SHORT).show();
        } else {
            layoutSurnameOfPassenger.setVisibility(View.GONE);
        }
    }

    // далі
    public void onClickNext(View view) {

        StringBuilder toast = new StringBuilder();

        if (enterQuantityOfMonthOrTrip.getText().length() == 0) {
            toast.append(enterQuantityOfMonthOrTrip.getHint()).append("!");
        }

        if (finalDate.compareTo(startDate) < 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.discrepancy_dates));
        }

        if (switchNominalSubscription.isChecked() && enterSurnameOfPassenger.getText().length() == 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(enterSurnameOfPassenger.getHint()).append("!");
        }

        if (typeOfBlank.getSelectedItem().equals(typeOfBlank.getItemAtPosition(0))
                && enterNumberOfPaperBlank.getText().length() == 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(enterNumberOfPaperBlank.getHint()).append("!");
        }

        if (toast.length() != 0) {
            Toast.makeText(Subscription.this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        saveData();

        Intent intent = new Intent(Subscription.this, AdditionalCharge.class);
        startActivity(intent);
        finish();
    }

    // створит адаптер
    private ArrayAdapter<CharSequence> createAdapter(int arrayId) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayId,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;

    }

    // оновити відображення дати
    private void updateDate(Calendar calendar, TextView tvDate) {

        tvDate.setText(String.format(" %td.%tm.%tY", calendar, calendar, calendar));

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        // створення діалогу початкової дати
        if (id == DIALOG_START_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            startDate.set(Calendar.DATE, dayOfMonth);
                            startDate.set(Calendar.MONTH, monthOfYear);
                            startDate.set(Calendar.YEAR, year);

                            updateDate(startDate, tvStartDate);
                        }
                    },
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DATE));
            return tpd;
        }

        // створення діалогу кінцевої дати
        if (id == DIALOG_FINAL_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            finalDate.set(Calendar.DATE, dayOfMonth);
                            finalDate.set(Calendar.MONTH, monthOfYear);
                            finalDate.set(Calendar.YEAR, year);

                            updateDate(finalDate, tvFinalDate);
                        }
                    },
                    finalDate.get(Calendar.YEAR),
                    finalDate.get(Calendar.MONTH),
                    finalDate.get(Calendar.DATE));
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    // збереження даних
    private void saveData() {
        Receipt.putString(Subscription.this, Receipt.TYPE_OF_SUBSCRIPTION, typeOfSubscription.getSelectedItem().toString());
        Receipt.putString(Subscription.this, Receipt.QUANTITY_OF_MONTH_OR_TRIP, enterQuantityOfMonthOrTrip.getText().toString());
        Receipt.putString(Subscription.this, Receipt.START_DATE, tvStartDate.getText().toString());
        Receipt.putString(Subscription.this, Receipt.FINAL_DATE, tvFinalDate.getText().toString());
        Receipt.putString(Subscription.this, Receipt.NOMINAL_SUBSCRIPTION,
                switchNominalSubscription.isChecked() ? switchNominalSubscription.getTextOn().toString() : switchNominalSubscription.getTextOff().toString());
        Receipt.putString(Subscription.this, Receipt.TYPE_OF_SUBSCRIPTION_BLANK, typeOfBlank.getSelectedItem().toString());
        Receipt.putString(Subscription.this, Receipt.NUMBER_OF_PAPER_BLANK, enterNumberOfPaperBlank.getText().toString());
    }
}
