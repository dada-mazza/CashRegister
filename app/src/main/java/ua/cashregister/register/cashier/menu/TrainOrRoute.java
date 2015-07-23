package ua.cashregister.register.cashier.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ua.cashregister.R;

/**
 * перехід 1 - (логін) - (1,2) - (дата)
 * Меню переходу
 * список рейсів
 * список маршрутів
 */

public class TrainOrRoute extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_or_route);

    }

    // перехід Рейс
    public void onClickButtonTrain(View view) {

        Intent intent = new Intent(this, Train.class);
        startActivity(intent);
        finish();
    }

    // перехід Маршрут
    public void onClickButtonRoute(View view) {

        Intent intent = new Intent(this, Route.class);
        startActivity(intent);
        finish();
    }
}