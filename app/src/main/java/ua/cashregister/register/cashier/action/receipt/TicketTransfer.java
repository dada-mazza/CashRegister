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

import java.util.Random;

public class TicketTransfer extends Activity {

    private Spinner trainOrRoute;
    private LinearLayout layoutSelectFromListOfTrain;
    private LinearLayout layoutSelectFromListOfRoute;
    private LinearLayout layoutSelectFromListOfStation;

    private TextView selectFromListOfTrain;
    private TextView selectFromListOfRoute;
    private TextView selectFromListOfStation;

    private final int MAX_DISTANCE = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_transfer);

        layoutSelectFromListOfTrain = (LinearLayout) findViewById(R.id.layoutSelectFromListOfTrain);
        layoutSelectFromListOfRoute = (LinearLayout) findViewById(R.id.layoutSelectFromListOfRoute);
        layoutSelectFromListOfStation = (LinearLayout) findViewById(R.id.layoutSelectFromListOfStation);

        selectFromListOfTrain = (TextView) findViewById(R.id.selectFromListOfTrain);
        selectFromListOfRoute = (TextView) findViewById(R.id.selectFromListOfRoute);
        selectFromListOfStation = (TextView) findViewById(R.id.selectFromListOfStation);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.list_of_train_or_route,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        trainOrRoute = (Spinner) findViewById(R.id.trainOrRoute);
        // адаптер
        trainOrRoute.setAdapter(adapter);
        // елемент по замовчуванню
        trainOrRoute.setSelection(0);
        // слухач
        trainOrRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                // дії в залежності від вибраного елемента

                if (position == 0) {
                    // рейс
                    layoutSelectFromListOfTrain.setVisibility(View.VISIBLE);
                    layoutSelectFromListOfRoute.setVisibility(View.GONE);
                    layoutSelectFromListOfStation.setEnabled(false);

                } else if (position == 1) {
                    // маршрут
                    layoutSelectFromListOfTrain.setVisibility(View.GONE);
                    layoutSelectFromListOfRoute.setVisibility(View.VISIBLE);
                    layoutSelectFromListOfStation.setEnabled(false);
                }


                Toast.makeText(TicketTransfer.this, trainOrRoute.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }

    // список рейсів
    // виклик активності зі списком рейсів
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOfTrain(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.list_of_train));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_trains);
        startActivityForResult(intent, RequestCode.LIST_OF_TRAINS);
    }

    // список маршрутів
    // виклик активності зі списком маршрутів
    // очікування результату вибору
    public void onClickSelectFromListOfRoute(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.list_of_routes));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_routes);
        startActivityForResult(intent, RequestCode.LIST_OF_ROUTE);
    }

    // список станцій
    // виклик активності зі списком рейсів
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOfStation(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.list_of_station));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_station);
        startActivityForResult(intent, RequestCode.LIST_OF_STATION);
    }

    // обробка результату вибору списків
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.LIST_OF_TRAINS) {
            // обробка результатів вибору зі списку рейсів
            resultSelectFromListOfTrain(resultCode, data);

        } else if (requestCode == RequestCode.LIST_OF_ROUTE) {
            // обробка результатів вибору зі списку маршрутів
            resultSelectFromListOFRoute(resultCode, data);

        } else if (requestCode == RequestCode.LIST_OF_STATION) {
            // обробка результатів вибору зі списку станцій
            resultSelectFromListOfStation(resultCode, data);
        }


    }

    // обробка результатів вибору зі списку рейсів
    private void resultSelectFromListOfTrain(int resultCode, Intent data) {

        // якщо вибирали зі списку рейсів
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfTrain.setText(data.getStringExtra(Lists.VALUE));
            // список станцій
            layoutSelectFromListOfStation.setEnabled(true);

        } else {

            // очистити значення вибору зі списку рейсів
            selectFromListOfTrain.setText("");
            // список станцій
            layoutSelectFromListOfStation.setEnabled(false);
            // вивести повідомлення
            Toast.makeText(this,
                    getResources().getString(R.string.train_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();

        }

    }

    // обробка результатів вибору зі списку маршрутів
    private void resultSelectFromListOFRoute(int resultCode, Intent data) {

        // якщо вибирали зі списку маршрутів
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfRoute.setText(data.getStringExtra(Lists.VALUE));
            // список станцій
            layoutSelectFromListOfStation.setEnabled(true);

        } else {

            // очистити значення вибору зі списку рейсів
            selectFromListOfRoute.setText("");
            // список станцій
            layoutSelectFromListOfStation.setEnabled(false);
            // вивести повідомлення
            Toast.makeText(this,
                    getResources().getString(R.string.route_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();

        }
    }

    // обробка результатів вибору зі списку станцій
    private void resultSelectFromListOfStation(int resultCode, Intent data) {

        // якщо вибирали зі списку станцій
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfStation.setText(data.getStringExtra(Lists.VALUE));

        } else {

            // очистити значення вибору зі списку рейсів
            selectFromListOfStation.setText("");

            // вивести повідомлення
            Toast.makeText(this,
                    getResources().getString(R.string.station_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void onClickNext(View view) {

        StringBuilder toast = new StringBuilder();

        if (layoutSelectFromListOfTrain.getVisibility() == View.VISIBLE
                && selectFromListOfTrain.getText().length() == 0) {
            toast.append(getString(R.string.train_not_selected)).append("!");
        }

        if (layoutSelectFromListOfRoute.getVisibility() == View.VISIBLE
                && selectFromListOfRoute.getText().length() == 0) {
            toast.append(getString(R.string.route_not_selected)).append("!");
        }

        if (selectFromListOfStation.getText().length() == 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.station_not_selected)).append("!");
        }


        if (toast.length() != 0) {
            Toast.makeText(
                    TicketTransfer.this,
                    toast.toString(),
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // підрахувати дистанцію
        Integer distance = totalDistance();

        if (distance > MAX_DISTANCE) {

            // вивести повідомлення про перевищення
            Toast.makeText(
                    TicketTransfer.this,
                    new StringBuilder()
                            .append(getString(R.string.exceeding_maximum_permissible_length_of_route))
                            .append("\n")
                            .append(MAX_DISTANCE).append(" ").append(getString(R.string.km)).append("!")
                            .toString(),
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            // занести дані про дистанцію до чека
            Receipt.putString(TicketTransfer.this, Receipt.DISTANCE, distance.toString());
           // занести дані про пересадку
            Receipt.putString(TicketTransfer.this, Receipt.STATION_OF_TRANSFER, selectFromListOfStation.getText().toString());
            // закрити активність
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    // метод підрахунку дистанції
    private int totalDistance() {
        //TODO прописати реалізацію підрахунку дистанції
        return new Random().nextInt(800);
    }
}
