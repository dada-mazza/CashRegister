package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ua.cashregister.R;
import ua.cashregister.list.ListActivity;
import ua.cashregister.storage.Lists;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.RequestCode;
import ua.cashregister.storage.SettingRRO;

import java.util.Random;

public class TicketSeat extends Activity {

    private LinearLayout layoutQuantitySeat;
    private LinearLayout layoutListOfSeat;
    private LinearLayout layoutEnterNumberSeat;

    private TextView quantitySeat;
    private TextView enterNumberSeat;
    private TextView selectFromListOfSeats;

    private Integer seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_seat);


        layoutQuantitySeat = (LinearLayout) findViewById(R.id.layoutQuantitySeat);
        layoutListOfSeat = (LinearLayout) findViewById(R.id.layoutListOfSeat);
        layoutEnterNumberSeat = (LinearLayout) findViewById(R.id.layoutEnterNumberSeat);

        quantitySeat = (TextView) findViewById(R.id.quantitySeat);
        selectFromListOfSeats = (TextView) findViewById(R.id.selectFromListOfSeats);
        enterNumberSeat = (TextView) findViewById(R.id.enterNumberSeat);


        if (SettingRRO.checkModeSale(TicketSeat.this, getString(R.string.mode_sale_regimes_without_seats))) {

            layoutListOfSeat.setVisibility(View.GONE);
            layoutEnterNumberSeat.setVisibility(View.GONE);

            // відобразити кількість вільних місць
            seat = countingNumberOfSeatsAvailable();
            quantitySeat.setText(seat.toString());

        } else if (SettingRRO.checkModeNDI(TicketSeat.this)
                || (Receipt.checkTransition(TicketSeat.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.posadchyk))
                && checkOperationInformation())) {

            layoutQuantitySeat.setVisibility(View.GONE);
            layoutEnterNumberSeat.setVisibility(View.GONE);

        } else {

            layoutQuantitySeat.setVisibility(View.GONE);
            layoutListOfSeat.setVisibility(View.GONE);
        }

    }


    // список місць
    // виклик активності зі списком місць
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOfSeats(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_seat));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_seat);
        startActivityForResult(intent, RequestCode.SEAT);
    }

    // обробка результату активностей
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.SEAT) {
            // вибір зі списку товарів
            if (resultCode == RESULT_OK) {
                selectFromListOfSeats.setText(data.getStringExtra(Lists.VALUE));
            } else {
                selectFromListOfSeats.setText("");
                Toast.makeText(this,
                        getResources().getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // далі
    public void onClickNext(View view) {
        // перевірка заповнення полів
        if (checkData()) {

            // перехід
            Intent intent = new Intent(TicketSeat.this, AdditionalCharge.class);
            startActivity(intent);
            finish();
        }
    }

    // перевірка оперативної інформації
    private boolean checkOperationInformation() {
        // TODO прописати метод перевірки стан Оперативної інформації
        return false;
    }

    // підрахунок кількості вільних місць
    private Integer countingNumberOfSeatsAvailable() {

        // TODO прописати метод
        return new Random().nextInt(100);
    }

    // збереження даних
    private boolean saveData(TextView tv) {
        Receipt.putString(TicketSeat.this, Receipt.SEAT, tv.getText().toString());
        return true;
    }

    // перевірити активне поле
    // заповнення поля
    // занести дані
    private boolean checkData() {

        // кількість місць
        if (layoutQuantitySeat.getVisibility() == View.VISIBLE) {
            if (seat == 0) {
                Toast.makeText(
                        TicketSeat.this,
                        getString(R.string.no_free_seat).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        // список місць
        else if (layoutListOfSeat.getVisibility() == View.VISIBLE) {
            if (selectFromListOfSeats.getText().length() == 0) {
                Toast.makeText(
                        TicketSeat.this,
                        getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {

                return saveData(selectFromListOfSeats);
            }
        }
        // номер місця
        else if (layoutEnterNumberSeat.getVisibility() == View.VISIBLE) {
            if (enterNumberSeat.getText().length() == 0) {
                Toast.makeText(
                        TicketSeat.this,
                        getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                return saveData(enterNumberSeat);
            }
        }
        return false;
    }

}
