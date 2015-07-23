package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ua.cashregister.R;
import ua.cashregister.list.ListActivity;
import ua.cashregister.print.PrintActivity;
import ua.cashregister.storage.*;

import java.util.Random;

public class ReducingTravelCard extends Activity {

    private LinearLayout layoutQuantitySeat;
    private LinearLayout layoutListOfSeat;
    private LinearLayout layoutEnterNumberSeat;
    private LinearLayout layoutWay;

    private TextView quantitySeat;
    private TextView enterNumberSeat;
    private TextView selectFromListOfSeats;

    private RadioGroup way;

    private Integer seat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reducing_travel_card);

        layoutQuantitySeat = (LinearLayout) findViewById(R.id.layoutQuantitySeat);
        layoutWay = (LinearLayout) findViewById(R.id.layoutWay);
        layoutListOfSeat = (LinearLayout) findViewById(R.id.layoutListOfSeat);
        layoutEnterNumberSeat = (LinearLayout) findViewById(R.id.layoutEnterNumberSeat);

        quantitySeat = (TextView) findViewById(R.id.quantitySeat);
        selectFromListOfSeats = (TextView) findViewById(R.id.selectFromListOfSeats);
        enterNumberSeat = (TextView) findViewById(R.id.enterNumberSeat);

        way = (RadioGroup) findViewById(R.id.way);


        if (SettingRRO.checkModeSale(ReducingTravelCard.this, getString(R.string.mode_sale_regimes_without_seats))) {

            layoutListOfSeat.setVisibility(View.GONE);
            layoutEnterNumberSeat.setVisibility(View.GONE);

            // відобразити кількість вільних місць
            seat = countingNumberOfSeatsAvailable();
            quantitySeat.setText(seat.toString());

        } else if (SettingRRO.checkModeNDI(ReducingTravelCard.this)
                || (Receipt.checkTransition(ReducingTravelCard.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.posadchyk))
                && checkOperationInformation())) {

            layoutQuantitySeat.setVisibility(View.GONE);
            layoutWay.setVisibility(View.GONE);
            layoutEnterNumberSeat.setVisibility(View.GONE);

        } else {

            layoutQuantitySeat.setVisibility(View.GONE);
            layoutWay.setVisibility(View.GONE);
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
            // запис на картку
            dialogOfCardRecording();
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
    private boolean saveData(String key, TextView tv) {
        Receipt.putString(ReducingTravelCard.this, key, tv.getText().toString());
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
                        ReducingTravelCard.this,
                        getString(R.string.no_free_seat).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                return saveData(Receipt.TRANSFER, ((RadioButton) findViewById(way.getCheckedRadioButtonId())));
            }
        }
        // список місць
        else if (layoutListOfSeat.getVisibility() == View.VISIBLE) {
            if (selectFromListOfSeats.getText().length() == 0) {
                Toast.makeText(
                        ReducingTravelCard.this,
                        getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                return saveData(Receipt.SEAT, selectFromListOfSeats);
            }
        }
        // номер місця
        else if (layoutEnterNumberSeat.getVisibility() == View.VISIBLE) {
            if (enterNumberSeat.getText().length() == 0) {
                Toast.makeText(
                        ReducingTravelCard.this,
                        getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                return saveData(Receipt.SEAT, enterNumberSeat);
            }
        }
        return false;
    }

    // діалог запису даних
    private void dialogOfCardRecording() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ReducingTravelCard.this)
                .setTitle(getString(R.string.prepare_card_for_recording).concat("!"))
                .setMessage(getString(R.string.start_recording).concat("?"))
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (startRecording()) {
                            // запис вдалий - передати дані для друку
                            String report = new StringBuilder()
                                    .append(getString(R.string.zero_fiscal_receipt_sales))
                                    .append("\n")
                                    .append(getString(R.string.date_of_departure))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.DATE_OF_DEPARTURE))
                                    .append("\n")
                                    .append(getString(R.string.route))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.ROUTE))
                                    .append("\n")
                                    .append(getString(R.string.train))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.TRAIN))
                                    .append("\n")
                                    .append(getString(R.string.wagon))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.WAGON))
                                    .append("\n")
                                    .append(getString(R.string.types_class))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.TYPES_CLASS))
                                    .append("\n")
                                    .append(getString(R.string.carrier))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.CARRIER))
                                    .append("\n")
                                    .append(getString(R.string.transfer))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.TRANSFER))
                                    .append("\n")
                                    .append(getString(R.string.seat))
                                    .append("\n")
                                    .append(Receipt.getString(ReducingTravelCard.this, Receipt.SEAT))
                                    .toString();

                            Intent intent = new Intent(ReducingTravelCard.this, PrintActivity.class);
                            intent.putExtra(Print.PRINT, report);
                            startActivity(intent);
                            finish();
                        } else {
                            // запис не вдалий - повторити
                            dialogOfCardRecording();
                        }
                    }
                });
        dialog.create().show();
    }

    // метод запису даних на картку
    private boolean startRecording() {
        //TODO метод запису даних на картку
        return new Random().nextBoolean();
    }
}
