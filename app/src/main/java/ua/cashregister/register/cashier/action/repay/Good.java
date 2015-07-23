package ua.cashregister.register.cashier.action.repay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ua.cashregister.R;
import ua.cashregister.list.ListActivity;
import ua.cashregister.storage.Lists;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.RequestCode;

/**
 * Клас призначено для введення продукта/послуги
 * <p/>
 * Перехід
 * з Repay
 * до Repay
 * повертає назву чи код товару вибраного користувачем
 */

public class Good extends Activity {

    TextView enterNumberGood;
    TextView enterNameGood;
    TextView selectFromListOfGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);

        enterNumberGood = (TextView) findViewById(R.id.enterNumberGood);
        enterNameGood = (TextView) findViewById(R.id.enterNameGood);
        selectFromListOfGoods = (TextView) findViewById(R.id.selectFromListOfGoods);

    }

    // виклик списку товарів
    public void onClickSelectFromListOfGoods(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.goods));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_goods);
        startActivityForResult(intent, RequestCode.GOOD);
    }

    // обробка результату активностей
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.GOOD) {
            // вибір зі списку товарів
            if (resultCode == RESULT_OK) {
                selectFromListOfGoods.setText(data.getStringExtra(Lists.VALUE));
            } else {
                selectFromListOfGoods.setText("");
                Toast.makeText(this,
                        getResources().getString(R.string.goods_not_selected).concat("!"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void onClickNext(View view) {

        String nameGood = "";

        // код товару
        if (enterNumberGood.getText().length() != 0) {
            nameGood = enterNumberGood.getText().toString();
        }
        // назва товару
        if (enterNameGood.getText().length() != 0) {
            nameGood = enterNameGood.getText().toString();
        }
        // товар зі списку
        if (selectFromListOfGoods.getText().length() != 0) {
            nameGood = selectFromListOfGoods.getText().toString();
        }

        Intent intent = new Intent();
        intent.putExtra(Receipt.GOOD, nameGood);
        setResult(RESULT_OK, intent);
        finish();
    }
}
