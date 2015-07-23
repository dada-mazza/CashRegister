package ua.cashregister.register.cashier.action.receipt;

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

public class TariffFine extends Activity {

    private TextView selectArticleOfTariffFine;
    private TextView enterSurnameOfPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariff_fine);

        selectArticleOfTariffFine = (TextView) findViewById(R.id.selectArticleOfTariffFine);
        enterSurnameOfPassenger = (TextView) findViewById(R.id.enterSurnameOfPassenger);
    }

    // список штрафів
    public void onClickSelectArticleOfTariffFine(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_article_of_tariff_fine));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_article_of_tariff_fine);
        startActivityForResult(intent, RequestCode.TARIFF_FINE);
    }

    // обробка результату активностей
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.TARIFF_FINE) {
            // вибір зі списку товарів
            if (resultCode == RESULT_OK) {
                selectArticleOfTariffFine.setText(data.getStringExtra(Lists.VALUE));
            } else {
                selectArticleOfTariffFine.setText("");
                Toast.makeText(this,
                        getResources().getString(R.string.article_not_selected).concat("!"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // далі
    public void onClickNext(View view) {

        // перевірка заповнення полів
        // виведення повідомлень
        // заповнення даних
        // перехід на наступну активність

        StringBuilder toast = new StringBuilder();

        if (selectArticleOfTariffFine.getText().length() == 0) {
            toast.append(getString(R.string.article_not_selected)).append("!");
        }

        if (enterSurnameOfPassenger.getText().length() == 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.did_not_enter_passenger_data)).append("!");
        }

        if (toast.length() != 0) {
            Toast.makeText(TariffFine.this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        Receipt.putString(TariffFine.this, Receipt.ARTICLE_FINE, selectArticleOfTariffFine.getText().toString());
        Receipt.putString(TariffFine.this, Receipt.SURNAME_OF_PASSENGER, enterSurnameOfPassenger.getText().toString());

        Intent intent = new Intent(TariffFine.this, AdditionalCharge.class);
        startActivity(intent);
        finish();
    }
}
