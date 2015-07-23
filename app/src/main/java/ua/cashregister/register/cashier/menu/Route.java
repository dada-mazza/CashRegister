package ua.cashregister.register.cashier.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ua.cashregister.R;
import ua.cashregister.list.ListActivity;
import ua.cashregister.register.cashier.action.menu.ActionOfReceipt;
import ua.cashregister.storage.Lists;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.RequestCode;

/**
 * перехід 1 - (логін) - (1,2) - (дата) - 2
 * <p/>
 * список маршрутів
 * № рейсу
 * <p/>
 * Вибір зі списку маршрутів та введення номеру рейсу
 */
public class Route extends Activity {

    TextView titleTrain;
    TextView route;
    EditText train;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        route = (TextView) findViewById(R.id.selectFromListOfRoute);

        titleTrain = (TextView) findViewById(R.id.titleNumberTrain);
        train = (EditText) findViewById(R.id.enterNumberTrain);

        trainGone();
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

    // обробка результату вибору маршруту
    // відображення чи приховування поля № рейсу
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.LIST_OF_ROUTE && resultCode == RESULT_OK) {
            trainVisible();
            route.setText(data.getStringExtra(Lists.VALUE));
        } else {
            trainGone();
            route.setText("");
            Toast.makeText(this,
                    getString(R.string.route_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickNext(View view) {

        if (route.getText().length() == 0) {
            Toast.makeText(Route.this, R.string.choice_route, Toast.LENGTH_SHORT).show();
        } else if (train.getText().length() == 0) {
            Toast.makeText(Route.this, R.string.select_from_list_of_train, Toast.LENGTH_SHORT).show();
        } else {
            Receipt.putString(this, Receipt.ROUTE, route.getText().toString());
            Receipt.putString(this, Receipt.TRAIN, train.getText().toString());

            Intent intent = new Intent(Route.this, ActionOfReceipt.class);
            startActivity(intent);
            finish();
        }
    }

    private void trainGone() {
        titleTrain.setVisibility(View.GONE);
        train.setVisibility(View.GONE);
    }

    private void trainVisible() {
        titleTrain.setVisibility(View.VISIBLE);
        train.setVisibility(View.VISIBLE);
    }

}
