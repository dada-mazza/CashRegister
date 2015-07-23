package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ua.cashregister.R;
import ua.cashregister.list.ListActivity;
import ua.cashregister.register.cashier.action.menu.Blank;
import ua.cashregister.register.cashier.action.repay.Good;
import ua.cashregister.storage.Lists;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.RequestCode;

public class ProductsAndServices extends Activity {

    private TextView selectGoods;
    private TextView selectUnitOfMeasurement;
    private TextView enterQuantityOfGoods;
    private TextView enterСostOfProductsAndServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_and_services);

        selectGoods = (TextView) findViewById(R.id.selectGoods);
        selectUnitOfMeasurement = (TextView) findViewById(R.id.selectUnitOfMeasurement);
        enterQuantityOfGoods = (TextView) findViewById(R.id.enterQuantityOfGoods);
        enterСostOfProductsAndServices = (TextView) findViewById(R.id.enterСostOfProductsAndServices);
    }

    public void onClickSelectGood(View view) {

        Intent intent = new Intent(this, Good.class);
        startActivityForResult(intent, RequestCode.PRODUCTS_AND_SERVICES);
    }

    public void onClickSelectFromListOfUnitOfMeasurement(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getResources().getString(R.string.unit_of_measurement));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_unit_of_measurement);
        startActivityForResult(intent, RequestCode.UNIT_OF_MEASUREMENT);
    }

    public void onClickAddPosition(View view) {
        StringBuilder toast = checkField();
        // якщо повідомлення не пусте - вивести повідомлення
        if (toast.length() != 0) {
            Toast.makeText(this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        // занести значення полів
        saveDataProductsAndServices();

        // очистити поля ддя введення
        selectGoods.setText("");
        selectUnitOfMeasurement.setText("");
        enterQuantityOfGoods.setText("");
        enterСostOfProductsAndServices.setText("");


    }

    public void onClickNext(View view) {

        StringBuilder toast = checkField();
        // якщо повідомлення не пусте - вивести повідомлення
        if (toast.length() != 0) {
            Toast.makeText(this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        // занести значення полів
        saveDataProductsAndServices();

        // Intent intent = new Intent(Repay.this, BlankRepay.class);
        Intent intent = new Intent(ProductsAndServices.this, Blank.class);
        startActivity(intent);
        finish();

    }

    // перевірити заповненність полів, сформувати повідомлення про незаповнені
    private StringBuilder checkField() {
        // формування тексту повідомлення
        StringBuilder toast = new StringBuilder();

        // якщо не введено найменування товару
        if (selectGoods.getText().length() == 0) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.select_name_goods));
            toast.append("!");
        }

        // якщо не введено одиницю виміру
        if (selectUnitOfMeasurement.getText().length() == 0) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.select_unit_of_measurement));
            toast.append("!");
        }

        // якщо не введено кількість товару
        if (enterQuantityOfGoods.getText().length() == 0) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.enter_quantity_of_goods));
            toast.append("!");
        }

        // якщо не введено вартість товару
        if (enterСostOfProductsAndServices.getText().length() == 0) {
            // додати в текст повідомлення
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getResources().getString(R.string.enter_cost_of_products_and_services));
            toast.append("!");
        }

        return toast;
    }

    // збереження даних
    private void saveDataProductsAndServices() {

        //TODO занесення введених даних

        Receipt.putString(this, Receipt.GOOD, selectGoods.getText().toString());
        Receipt.putString(this, Receipt.UNIT_OF_MEASUREMENT, selectUnitOfMeasurement.getText().toString());
        Receipt.putString(this, Receipt.QUANTITY_OF_GOODS, enterQuantityOfGoods.getText().toString());
        Receipt.putString(this, Receipt.PAYABLE, enterСostOfProductsAndServices.getText().toString());
    }

    // обробка результату активностей
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.PRODUCTS_AND_SERVICES) {
            // обробка результатів вибору
            resultSelectGood(resultCode, data);
        } else if (requestCode == RequestCode.UNIT_OF_MEASUREMENT) {
            // обробка результатів вибору
            resultUnitOfMeasurement(resultCode, data);
        }
    }

    // обробка результатів вибору найменування товару
    private void resultSelectGood(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            selectGoods.setText(data.getStringExtra(Receipt.GOOD));
        } else {
            selectGoods.setText("");
            Toast.makeText(this,
                    getResources().getString(R.string.goods_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // обробка результатів вибору одиниці виміру
    private void resultUnitOfMeasurement(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            selectUnitOfMeasurement.setText(data.getStringExtra(Lists.VALUE));
        } else {
            selectUnitOfMeasurement.setText("");
            Toast.makeText(this,
                    getResources().getString(R.string.unit_of_measurement_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }


}
