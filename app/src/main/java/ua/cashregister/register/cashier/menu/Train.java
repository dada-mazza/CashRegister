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
import ua.cashregister.storage.SettingRRO;

/**
 * перехід
 * з TrainOrRoute, DateOfDeparture
 * до TypeOfTicket
 * <p/>
 * список рейсів
 * <p/>
 * вибір зі списку рейсів
 * вибір зі списку вагонів
 * введення номеру вагонів
 * вибір зі списку класів місць
 * вибір зі спискуперевізників
 */
public class Train extends Activity {

    private TextView selectFromListOfTrain;
    private TextView titleListOfWagon;
    private TextView selectFromListOfWagon;
    private TextView titleNumberWagon;
    private EditText enterNumberWagon;
    private TextView titleListOfTypesClass;
    private TextView selectFromListOfTypesClass;
    private TextView titleListOfCarriers;
    private TextView selectFromListOfCarriers;

    private boolean modeSaleWithoutSeats;
    private boolean modeSaleAuto;
    private boolean modeUseNDI;
    private boolean transitionFromPosadchyk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        // перевірки
        // режим без місць
        modeSaleWithoutSeats = SettingRRO.checkModeSale(this, getString(R.string.mode_sale_regimes_without_seats));
        // режим авто
        modeSaleAuto = SettingRRO.checkModeSale(this, getString(R.string.mode_sale_regimes_auto));
        // перевірка режиму НДІ
        modeUseNDI = SettingRRO.checkModeNDI(this);
        // перехід від посадчика
        transitionFromPosadchyk = Receipt.checkTransition(
                this,
                Receipt.CASH_REGISTER_MENU_ITEM,
                getString(R.string.posadchyk));

        // звязок з полями
        // список рейсів
        selectFromListOfTrain = (TextView) findViewById(R.id.selectFromListOfTrain);
        // список вагонів
        titleListOfWagon = (TextView) findViewById(R.id.titleListOfWagon);
        selectFromListOfWagon = (TextView) findViewById(R.id.selectFromListOFWagon);
        // № Вагону
        titleNumberWagon = (TextView) findViewById(R.id.titleNumberWagon);
        enterNumberWagon = (EditText) findViewById(R.id.enterNumberWagon);
        // список класів місць
        titleListOfTypesClass = (TextView) findViewById(R.id.titleListOfTypesClass);
        selectFromListOfTypesClass = (TextView) findViewById(R.id.selectFromListOfTypesClass);
        // список перевізників
        titleListOfCarriers = (TextView) findViewById(R.id.titleListOfCarriers);
        selectFromListOfCarriers = (TextView) findViewById(R.id.selectFromListOfCarriers);

        // зробити невидимими
        setVisibilityGone(titleNumberWagon, enterNumberWagon);
        setVisibilityGone(titleListOfWagon, selectFromListOfWagon);
        setVisibilityGone(titleListOfTypesClass, selectFromListOfTypesClass);
        setVisibilityGone(titleListOfCarriers, selectFromListOfCarriers);
    }

    // встановлення параметру (View.GONE) для заданих елементів (невидимий)
    private void setVisibilityGone(TextView title, TextView list) {
        title.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
    }

    // встановлення параметру (View.VISIBLE) для заданих елементів (видимий)
    private void setVisibilityVisible(TextView title, TextView list) {
        title.setVisibility(View.VISIBLE);
        list.setVisibility(View.VISIBLE);
    }

    // список рейсів
    // виклик активності зі списком рейсів
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOfTrain(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_train));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_trains);
        startActivityForResult(intent, RequestCode.LIST_OF_TRAINS);
    }

    // список вагонів
    // виклик активності зі списком рейсів
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOFWagon(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_wagon));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_wagons);
        startActivityForResult(intent, RequestCode.LIST_OF_WAGONS);
    }

    // список класів місць
    // виклик активності зі списком рейсів
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOfTypesClass(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_types_class));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_types_class);
        startActivityForResult(intent, RequestCode.LIST_OF_TYPES_CLASS);
    }

    // список перевізників
    // виклик активності зі списком рейсів
    // старт з очікуванням результату вибору
    public void onClickSelectFromListOfCarriers(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_carriers));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_carriers);
        startActivityForResult(intent, RequestCode.LIST_OF_CARRIERS);
    }

    // обробка результату вибору маршруту
    // відображення чи приховування поля № рейсу
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.LIST_OF_TRAINS) {
            // обробка результатів вибору зі списку рейсів
            resultSelectFromListOfTrain(resultCode, data);

        } else if (requestCode == RequestCode.LIST_OF_WAGONS) {
            // обробка результатів вибору зі списку вагонів
            resultSelectFromListOFWagon(resultCode, data);

        } else if (requestCode == RequestCode.LIST_OF_TYPES_CLASS) {
            // обробка результатів вибору зі списку класів місць
            resultSelectFromListOfTypesClass(resultCode, data);

        } else if (requestCode == RequestCode.LIST_OF_CARRIERS) {
            // обробка результатів вибору зі списку перевізників
            resultSelectFromListOfCarriers(resultCode, data);
        }
    }

    // обробка результатів вибору зі списку рейсів
    private void resultSelectFromListOfTrain(int resultCode, Intent data) {

        // якщо вибирали зі списку рейсів
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfTrain.setText(data.getStringExtra(Lists.VALUE));

            // для режимів продажу окрім "без місць"
            if (!modeSaleWithoutSeats) {
                // якщо НДІ використовується
                // відобразити список вагонів
                // для режимів продажу окрім "авто"
                if (modeUseNDI && !modeSaleAuto) {
                    // список вагонів - відобразити на екрані
                    setVisibilityVisible(titleListOfWagon, selectFromListOfWagon);
                } else {
                    // перевірити батьківський виклик (Посадчик)
                    // перевірка - Опереативна інформація залита
                    if (transitionFromPosadchyk && checkOperationInformation()) {
                        // для режимів продажу окрім "авто"
                        if (!modeSaleAuto) {
                            // список вагонів - відобразити на екрані
                            setVisibilityVisible(titleListOfWagon, selectFromListOfWagon);
                        }
                    } else {
                        // якщо режим не авто
                        if (!modeSaleAuto) {
                            // № вагону
                            setVisibilityVisible(titleNumberWagon, enterNumberWagon);
                        }
                        // список класів місць
                        setVisibilityVisible(titleListOfTypesClass, selectFromListOfTypesClass);
                        // список перевізників
                        setVisibilityVisible(titleListOfCarriers, selectFromListOfCarriers);
                    }
                }
            }
        } else {
            // очистити значення вибору зі списку рейсів
            selectFromListOfTrain.setText("");
            // вивести повідомлення
            Toast.makeText(this,
                    getString(R.string.train_not_selected) + "!",
                    Toast.LENGTH_SHORT).show();
            // приховати наступні пукти вибору
            if (!modeSaleWithoutSeats) {
                setVisibilityGone(titleListOfWagon, selectFromListOfWagon);
                setVisibilityGone(titleNumberWagon, enterNumberWagon);
                setVisibilityGone(titleListOfTypesClass, selectFromListOfTypesClass);
                setVisibilityGone(titleListOfCarriers, selectFromListOfCarriers);
            }
        }

    }

    // обробка результатів вибору зі списку вагонів
    private void resultSelectFromListOFWagon(int resultCode, Intent data) {
        // якщо вибирали зі списку вагонів
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfWagon.setText(data.getStringExtra(Lists.VALUE));
        } else {
            // очистити значення вибору зі списку
            selectFromListOfTrain.setText("");
            // вивести повідомлення
            Toast.makeText(this,
                    getString(R.string.wagon_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // обробка результатів вибору зі списку класів місць
    private void resultSelectFromListOfTypesClass(int resultCode, Intent data) {
        // якщо вибирали зі списку вагонів
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfTypesClass.setText(data.getStringExtra(Lists.VALUE));
        } else {
            // очистити значення вибору зі списку
            selectFromListOfTypesClass.setText("");
            // вивести повідомлення
            Toast.makeText(this,
                   getString(R.string.types_class_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // обробка результатів вибору зі списку перевізників
    private void resultSelectFromListOfCarriers(int resultCode, Intent data) {

        // якщо вибирали зі списку вагонів
        if (resultCode == RESULT_OK) {

            // встановити значення вибране зі списку
            selectFromListOfCarriers.setText(data.getStringExtra(Lists.VALUE));
        } else {
            // очистити значення вибору зі списку
            selectFromListOfCarriers.setText("");
            // вивести повідомлення
            Toast.makeText(this,
                   getString(R.string.carriers_not_selected).concat("!"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickNext(View view) {

        // якщо не вибрано рейс
        if (selectFromListOfTrain.getText().length() == 0) {
            // вивести повідомлення
            Toast.makeText(this, getString(R.string.select_from_list_of_train).concat("!"), Toast.LENGTH_SHORT).show();
            return;
        } else {
            // занести значення поля
            Receipt.putString(this, Receipt.TRAIN, selectFromListOfTrain.getText().toString());
        }

        // для режимів продажу окрім "Без Місць"
        if (!modeSaleWithoutSeats) {
            // якщо не вибрано вагон
            // для режимів продажу окрім "Авто"
            // при використанні НДІ
            // чи старті з посадчика та залитій оперативній інформації
            if (selectFromListOfWagon.getText().length() == 0
                    && !modeSaleAuto
                    && (modeUseNDI || transitionFromPosadchyk && checkOperationInformation())) {
                // вивести повідомлення
                Toast.makeText(this, getString(R.string.select_from_list_of_wagon).concat("!"), Toast.LENGTH_SHORT).show();
                return;
            } else if (selectFromListOfWagon.getText().length() != 0) {
                // занести значення поля
                Receipt.putString(this, Receipt.WAGON, selectFromListOfWagon.getText().toString());
            } else {
                // для тексту повідомлення
                StringBuilder toast = new StringBuilder();

                // якщо не введено вагон
                // для режимів продажу окрім "Авто"
                if (enterNumberWagon.getText().length() == 0 && !modeSaleAuto) {
                    // додати в текст повідомлення
                    toast.append(getString(R.string.enter_number_wagon));
                    toast.append("!");
                }
                // якщо не вибрано клас місця
                if (selectFromListOfTypesClass.getText().length() == 0) {
                    // додати в текст повідомлення
                    if (toast.length() != 0) {
                        toast.append("\n");
                    }
                    toast.append(getString(R.string.select_from_list_of_types_class));
                    toast.append("!");
                }
                // якщо не вибрано перевізника
                if (selectFromListOfCarriers.getText().length() == 0) {
                    // додати в текст повідомлення
                    if (toast.length() != 0) {
                        toast.append("\n");
                    }
                    toast.append(getString(R.string.select_from_list_of_carriers));
                    toast.append("!");
                }

                // якщо повідомлення не пусте - вивести повідомлення
                if (toast.length() != 0) {
                    Toast.makeText(this, toast.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // занести значення полів
                if (!modeSaleAuto) {
                    Receipt.putString(this, Receipt.WAGON, selectFromListOfWagon.getText().toString());
                }
                Receipt.putString(this, Receipt.TYPES_CLASS, selectFromListOfTypesClass.getText().toString());
                Receipt.putString(this, Receipt.CARRIER, selectFromListOfCarriers.getText().toString());
            }
        }

        Intent intent = new Intent(this, ActionOfReceipt.class);
        startActivity(intent);
        finish();
    }

    // перевірка оперативної інформації
    private boolean checkOperationInformation() {
        // TODO прописати метод перевірки стан Оперативної інформації
        return false;
    }

}
