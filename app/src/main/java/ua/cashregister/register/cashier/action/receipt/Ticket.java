package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ua.cashregister.R;
import ua.cashregister.list.ListActivity;
import ua.cashregister.storage.Lists;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.RequestCode;
import ua.cashregister.storage.SettingRRO;

import java.util.Random;

public class Ticket extends Activity {


    private LinearLayout layoutFromStation;
    private LinearLayout layoutTransfer;
    private LinearLayout layoutWay;
    private LinearLayout layoutNumberCertificate;
    private LinearLayout layoutSurnameOfPassenger;
    private LinearLayout layoutQuantityBaggage;

    private TextView fromStation;
    private TextView toStation;
    private Spinner benefit;

    private Switch switchTransfer;
    private RadioGroup way;

    private TextView enterNumberCertificate;
    private TextView enterSurnameOfPassenger;
    private TextView enterQuantityBaggage;

    private final int MAX_DISTANCE = 400;
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        layoutFromStation = (LinearLayout) findViewById(R.id.layoutFromStation);
        layoutTransfer = (LinearLayout) findViewById(R.id.layoutTransfer);
        layoutWay = (LinearLayout) findViewById(R.id.layoutWay);
        layoutNumberCertificate = (LinearLayout) findViewById(R.id.layoutNumberCertificate);
        layoutSurnameOfPassenger = (LinearLayout) findViewById(R.id.layoutSurnameOfPassenger);
        layoutQuantityBaggage = (LinearLayout) findViewById(R.id.layoutQuantityBaggage);

        fromStation = (TextView) findViewById(R.id.fromStation);
        toStation = (TextView) findViewById(R.id.toStation);

        benefit = (Spinner) findViewById(R.id.benefit);

        switchTransfer = (Switch) findViewById(R.id.switchTransfer);

        way = (RadioGroup) findViewById(R.id.way);

        enterNumberCertificate = (TextView) findViewById(R.id.enterNumberCertificate);
        enterSurnameOfPassenger = (TextView) findViewById(R.id.enterSurnameOfPassenger);
        enterQuantityBaggage = (TextView) findViewById(R.id.enterQuantityBaggage);


        // окрім режиму посадчик
        if (!Receipt.checkTransition(Ticket.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.posadchyk))) {
            layoutFromStation.setVisibility(View.GONE);
        }

        // окрім режиму "З Місцями", "АВТО"
        if (!SettingRRO.checkModeSale(Ticket.this, getString(R.string.mode_sale_regimes_with_seats))
                || !SettingRRO.checkModeSale(Ticket.this, getString(R.string.mode_sale_regimes_auto))) {
            layoutTransfer.setVisibility(View.GONE);
            layoutWay.setVisibility(View.GONE);
        }

        // перехід багажна квитанція
        if (Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.baggage_check))) {
            layoutNumberCertificate.setVisibility(View.GONE);
            layoutSurnameOfPassenger.setVisibility(View.GONE);
        }

        // окрім багажна квитанція
        if (!Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.baggage_check))) {
            layoutQuantityBaggage.setVisibility(View.GONE);
        }

        spinnerBenefit();
    }

    //  список станцій відправлення
    public void onClickSelectFromStation(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.from_station));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_station);
        startActivityForResult(intent, RequestCode.FROM_STATION);

    }

    //  список станцій прибуття
    public void onClickSelectToStation(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.to_station));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_station);
        startActivityForResult(intent, RequestCode.TO_STATION);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.TRANSFER && resultCode != RESULT_OK) {
            switchTransfer.setChecked(false);
        } else if (requestCode == RequestCode.FROM_STATION) {
            resultSelectFromStation(resultCode, data);
        } else if (requestCode == RequestCode.TO_STATION) {
            resultSelectToStation(resultCode, data);
        }
    }

    private void resultSelectFromStation(int resultCode, Intent data) {

        // вибір зі списку
        if (resultCode == RESULT_OK) {
            fromStation.setText(data.getStringExtra(Lists.VALUE));
        } else {
            fromStation.setText("");
            Toast.makeText(this,
                    getResources().getString(R.string.from_station_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void resultSelectToStation(int resultCode, Intent data) {

        // вибір зі списку
        if (resultCode == RESULT_OK) {

            if (totalDistance() > MAX_DISTANCE) {

                // анулювати вибір
                toStation.setText("");

                // вивести повідомлення про перевищення
                Toast.makeText(
                        Ticket.this,
                        new StringBuilder()
                                .append(getString(R.string.exceeding_maximum_permissible_length_of_route))
                                .append("\n")
                                .append(MAX_DISTANCE).append(" ").append(getString(R.string.km)).append("!")
                                .toString(),
                        Toast.LENGTH_SHORT)
                        .show();

            } else {
                toStation.setText(data.getStringExtra(Lists.VALUE));
            }
        } else {
            toStation.setText("");
            Toast.makeText(this,
                    getResources().getString(R.string.to_station_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }

    }

    // метод підрахунку дистанції
    private int totalDistance() {
        //TODO прописати реалізацію підрахунку дистанції
        distance = new Random().nextInt(800);
        return distance;
    }

    //  список пільг
    private void spinnerBenefit() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.array_list_of_benefit,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        benefit.setAdapter(adapter);
        benefit.setSelection(0);
        benefit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                // дії в залежності від вибраного елемента
                Toast.makeText(Ticket.this, benefit.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
                // занести дані про станцію
                Receipt.putString(Ticket.this, Receipt.BENEFIT, benefit.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    // пересадка
    public void onClickSwitchTransfer(View view) {

        if (switchTransfer.isChecked()) {
            Intent intent = new Intent(Ticket.this, TicketTransfer.class);
            startActivityForResult(intent, RequestCode.TRANSFER);
        }
    }

    public void onClickNext(View view) {

        StringBuilder toast = new StringBuilder();

        // окрім режиму посадчик, перевірити станцію відправлення
        if (fromStation.getText().length() == 0 && layoutFromStation.getVisibility() == View.VISIBLE) {

            toast.append(getString(R.string.from_station_not_selected)).append("!");
        }

        // перевірити станцію прибуття
        if (toStation.getText().length() == 0) {

            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.to_station_not_selected)).append("!");
        }

        // перевірити кількість багажу
        if (enterQuantityBaggage.getText().length() == 0 && layoutQuantityBaggage.getVisibility() == View.VISIBLE) {

            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.did_not_enter_quantity_baggage)).append("!");
        }

        // при незаповнених полях вивести повідомлення
        if (toast.length() != 0) {
            Toast.makeText(this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        // зберегти "від станції"
        if (!Receipt.checkTransition(Ticket.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.posadchyk))) {

            Receipt.putString(Ticket.this, Receipt.FROM_STATION, fromStation.getText().toString());
        }

        // зберегти "до станції"
        Receipt.putString(Ticket.this, Receipt.TO_STATION, fromStation.getText().toString());


        // окрім режиму "З Місцями", "АВТО"
        if (!SettingRRO.checkModeSale(Ticket.this, getString(R.string.mode_sale_regimes_with_seats))
                || !SettingRRO.checkModeSale(Ticket.this, getString(R.string.mode_sale_regimes_auto))) {
            // занести дані про пересадку
            Receipt.putString(
                    Ticket.this,
                    Receipt.TRANSFER,
                    switchTransfer.isChecked() ? switchTransfer.getTextOn().toString() : switchTransfer.getTextOff().toString());

            // занести дані напрямок
            Receipt.putString(Ticket.this,
                    Receipt.WAY,
                    ((RadioButton) findViewById(way.getCheckedRadioButtonId())).getText().toString());
        }

        // перехід багажна квитанція
        if (Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.baggage_check))) {

            // занести дані багажу
            Receipt.putString(Ticket.this,
                    Receipt.BAGGAGE,
                    enterQuantityBaggage.getText().toString());
        }

        // окрім багажна квитанція
        if (!Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.baggage_check))) {

            // занести дані посвідчення та пасажира
            Receipt.putString(Ticket.this,
                    Receipt.SURNAME_OF_PASSENGER,
                    enterSurnameOfPassenger.getText().toString());

            Receipt.putString(Ticket.this,
                    Receipt.CERTIFICATE,
                    enterNumberCertificate.getText().toString());
        }

        Intent intent = null;

        if (Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.ticket))) {

            intent = new Intent(Ticket.this, TicketSeat.class);

        } else if (Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.baggage_check))) {

            intent = new Intent(Ticket.this, AdditionalCharge.class);

        } else if (Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.subscription))) {

            intent = new Intent(Ticket.this, Subscription.class);

        } else if (Receipt.checkTransition(Ticket.this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getString(R.string.tariff_fine))) {

            intent = new Intent(Ticket.this, TariffFine.class);
        }

        startActivity(intent);
        finish();
    }

}

