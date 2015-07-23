package ua.cashregister.register.cashier.action.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import ua.cashregister.R;
import ua.cashregister.print.PrintActivity;
import ua.cashregister.storage.Print;
import ua.cashregister.storage.Receipt;

public class TypeOfPayment extends Activity {

    private LinearLayout layoutCash;
    private LinearLayout layoutCashless;
    private LinearLayout layoutCard;
    private LinearLayout layoutETicket;

    private TextView amountTotal;
    private TextView enterAmount;
    private TextView enterPassenger;
    private TextView amountChange;
    private TextView enterNumberDocument;
    private TextView enterAuthorizationCode;
    private TextView enterNumberETicket;

    private Spinner typeOfPayment;

    private Integer amount;
    private Integer introducedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_payment);

        layoutCash = (LinearLayout) findViewById(R.id.layoutCash);
        layoutCashless = (LinearLayout) findViewById(R.id.layoutCashless);
        layoutCard = (LinearLayout) findViewById(R.id.layoutCard);
        layoutETicket = (LinearLayout) findViewById(R.id.layoutETicket);

        amountTotal = (TextView) findViewById(R.id.amountTotal);
        enterAmount = (TextView) findViewById(R.id.enterAmount);
        enterPassenger = (TextView) findViewById(R.id.enterPassenger);
        amountChange = (TextView) findViewById(R.id.amountChange);
        enterNumberDocument = (TextView) findViewById(R.id.enterNumberDocument);
        enterAuthorizationCode = (TextView) findViewById(R.id.enterAuthorizationCode);
        enterNumberETicket = (TextView) findViewById(R.id.enterNumberETicket);

        introducedAmount = 0;
        amount = Integer.parseInt(Receipt.getString(TypeOfPayment.this, Receipt.PAYABLE));
        amountTotal.setText(amount.toString());
        amountChange.setText("0");

        // список тип розрахунку
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.list_of_types_of_payment,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeOfPayment = (Spinner) findViewById(R.id.typeOfPayment);
        // адаптер
        typeOfPayment.setAdapter(adapter);
        // заголовок
        typeOfPayment.setPrompt(getResources().getString(R.string.type_of_payment));
        // елемент по замовчуванню
        typeOfPayment.setSelection(0);
        // слухач
        typeOfPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                // дії в залежності від вибраного елемента

                if (position == 0) {
                    // готівка
                    layoutCash.setVisibility(View.VISIBLE);
                    layoutCashless.setVisibility(View.GONE);
                    layoutCard.setVisibility(View.GONE);
                    layoutETicket.setVisibility(View.GONE);

                } else if (position == 1) {
                    // безготівковий платіж
                    layoutCash.setVisibility(View.GONE);
                    layoutCashless.setVisibility(View.VISIBLE);
                    layoutCard.setVisibility(View.GONE);
                    layoutETicket.setVisibility(View.GONE);

                } else if (position == 2) {
                    // Картка
                    layoutCash.setVisibility(View.GONE);
                    layoutCashless.setVisibility(View.GONE);
                    layoutCard.setVisibility(View.VISIBLE);
                    layoutETicket.setVisibility(View.GONE);

                } else if (position == 3) {
                    // Електронний квиток
                    layoutCash.setVisibility(View.GONE);
                    layoutCashless.setVisibility(View.GONE);
                    layoutCard.setVisibility(View.GONE);
                    layoutETicket.setVisibility(View.VISIBLE);

                }

                Toast.makeText(TypeOfPayment.this, typeOfPayment.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        // розрахунок Здача
        enterAmount.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (enterAmount.getText().length() > 0) {
                    try {
                        introducedAmount = Integer.parseInt(enterAmount.getText().toString());
                    } catch (Exception e) {
                        enterAmount.setText(introducedAmount.toString());
                    }
                }

                Integer change = introducedAmount - amount;

                if (change > 0) {
                    amountChange.setText(change.toString());
                } else {
                    amountChange.setText("0");
                }

                return false;
            }
        });
    }

    // в залежності від вибраного типу платежу додати дані до фіскального чека
    // або вивести повідомлення про незаповненні дані
    public void onClickNext(View view) {

        String report = "";

        // готівка
        if (layoutCash.getVisibility() == View.VISIBLE) {

            if (enterPassenger.getText().length() == 0) {
                Toast.makeText(TypeOfPayment.this, R.string.did_not_enter_passenger_data, Toast.LENGTH_SHORT).show();
                return;
            }

            report = new StringBuilder(createFiscalCheck())
                    .append("\n")
                    .append(getResources().getString(R.string.passenger))
                    .append("\n")
                    .append(enterPassenger.getText().toString())
                    .toString();

        }
        // безготівковий платіж
        else if (layoutCashless.getVisibility() == View.VISIBLE) {

            if (enterNumberDocument.getText().length() == 0) {
                Toast.makeText(TypeOfPayment.this, R.string.did_not_enter_number_document, Toast.LENGTH_SHORT).show();
                return;
            }

            report = new StringBuilder(createFiscalCheck())
                    .append("\n")
                    .append(getResources().getString(R.string.payment_document))
                    .append("\n")
                    .append(enterNumberDocument.getText().toString())
                    .toString();

            Intent intent = new Intent(TypeOfPayment.this, PrintActivity.class);
            intent.putExtra(Print.PRINT, report);
            startActivity(intent);

        }
        // Картка
        else if (layoutCard.getVisibility() == View.VISIBLE) {

            if (enterAuthorizationCode.getText().length() == 0) {
                Toast.makeText(TypeOfPayment.this, R.string.did_not_enter_authorization_code, Toast.LENGTH_SHORT).show();
                return;
            }

            report = new StringBuilder(createFiscalCheck())
                    .append("\n")
                    .append(getResources().getString(R.string.authorization_code))
                    .append("\n")
                    .append(enterAuthorizationCode.getText().toString())
                    .toString();

        }
        // електронний квиток
        else if (layoutETicket.getVisibility() == View.VISIBLE) {

            if (enterNumberETicket.getText().length() == 0) {
                Toast.makeText(TypeOfPayment.this, R.string.did_not_enter_number_e_ticket, Toast.LENGTH_SHORT).show();
                return;
            }

            report = new StringBuilder(createFiscalCheck())
                    .append("\n")
                    .append(getResources().getString(R.string.e_ticket))
                    .append("\n")
                    .append(enterNumberETicket.getText().toString())
                    .toString();
        }

        // передача даних на друк
        printFiscalCheck(report);
    }

    // отримати дані з Blank
    private String createFiscalCheck() {
        // отримати дані з Blank
        return Receipt.getString(TypeOfPayment.this, Receipt.BLANK);
    }

    // передача даних на друк
    private void printFiscalCheck(String report) {

        Intent intent = new Intent(TypeOfPayment.this, PrintActivity.class);
        intent.putExtra(Print.PRINT, report);
        startActivity(intent);
        finish();
    }

}
