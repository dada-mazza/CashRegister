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

public class AccountingForMissingPassengers extends Activity {


    private LinearLayout layoutListOfSeat;
    private LinearLayout layoutEnterNumberSeat;


    private TextView enterNumberSeat;
    private TextView selectFromListOfSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_for_missing_passengers);


        layoutListOfSeat = (LinearLayout) findViewById(R.id.layoutListOfSeat);
        layoutEnterNumberSeat = (LinearLayout) findViewById(R.id.layoutEnterNumberSeat);


        selectFromListOfSeats = (TextView) findViewById(R.id.selectFromListOfSeats);
        enterNumberSeat = (TextView) findViewById(R.id.enterNumberSeat);

        if (checkOperationInformation()) {

            layoutEnterNumberSeat.setVisibility(View.GONE);

        } else {

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

    // додати
    public void onClickAddPosition(View view) {

        // перевірити активне поле
        // заповнення поля
        // занести дані
       if(checkData()) {
           Toast.makeText(
                   AccountingForMissingPassengers.this,
                   getString(R.string.added),
                   Toast.LENGTH_LONG)
                   .show();
           // очистити поля
           enterNumberSeat.setText("");
           selectFromListOfSeats.setText("");
       }
    }

    // далі
    public void onClickNext(View view) {

        // перевірка заповнення полів
        if (checkData()) {
            // перехід
            finish();
        }
    }

    // перевірка оперативної інформації
    private boolean checkOperationInformation() {
        // TODO прописати метод перевірки стан Оперативної інформації
        return false;
    }

    // збереження даних
    private boolean saveData(String key, TextView tv) {
        Receipt.putString(AccountingForMissingPassengers.this, key, tv.getText().toString());
        return true;
    }

    // перевірити активне поле
    // заповнення поля
    // занести дані
    private boolean checkData() {

        if (layoutListOfSeat.getVisibility() == View.VISIBLE) {
            if (selectFromListOfSeats.getText().length() == 0) {
                Toast.makeText(
                        AccountingForMissingPassengers.this,
                        getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                return saveData(Receipt.SEAT, selectFromListOfSeats);
            }
        } else if (layoutEnterNumberSeat.getVisibility() == View.VISIBLE) {
            if (enterNumberSeat.getText().length() == 0) {
                Toast.makeText(
                        AccountingForMissingPassengers.this,
                        getString(R.string.seat_not_selected).concat("!"),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                return saveData(Receipt.SEAT, enterNumberSeat);
            }
        }
        return false;
    }

}
