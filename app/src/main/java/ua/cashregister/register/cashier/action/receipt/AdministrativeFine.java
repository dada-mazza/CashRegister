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

public class AdministrativeFine extends Activity {


    private TextView selectArticleOfAdministrativeFine;
    private TextView enterSizeOfAdministrativeFine;
    private TextView enterSurnameOfPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrative_fine);

        selectArticleOfAdministrativeFine = (TextView) findViewById(R.id.selectArticleOfAdministrativeFine);
        enterSizeOfAdministrativeFine = (TextView) findViewById(R.id.enterSizeOfAdministrativeFine);
        enterSurnameOfPassenger = (TextView) findViewById(R.id.enterSurnameOfPassenger);
    }


    // список штрафів
    public void onClickSelectArticleOfAdministrativeFine(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Lists.NAME, getString(R.string.list_of_article_of_administrative_fine));
        intent.putExtra(Lists.ID_ARRAY, R.array.array_list_of_article_of_administrative_fine);
        startActivityForResult(intent, RequestCode.ADMINISTRATIVE_FINE);
    }

    // обробка результату активностей
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.ADMINISTRATIVE_FINE) {
            // вибір зі списку товарів
            if (resultCode == RESULT_OK) {
                selectArticleOfAdministrativeFine.setText(data.getStringExtra(Lists.VALUE));
            } else {
                selectArticleOfAdministrativeFine.setText("");
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

        if (selectArticleOfAdministrativeFine.getText().length() == 0) {
            toast.append(getString(R.string.article_not_selected)).append("!");
        }

        if (enterSizeOfAdministrativeFine.getText().length() == 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.did_not_enter_size_of_fine)).append("!");
        }

        if (enterSurnameOfPassenger.getText().length() == 0) {
            if (toast.length() != 0) {
                toast.append("\n");
            }
            toast.append(getString(R.string.did_not_enter_passenger_data)).append("!");
        }

        if (toast.length() != 0) {
            Toast.makeText(AdministrativeFine.this, toast.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        Receipt.putString(AdministrativeFine.this, Receipt.ARTICLE_FINE, selectArticleOfAdministrativeFine.getText().toString());
        Receipt.putString(AdministrativeFine.this, Receipt.SIZE_OF_FINE, enterSizeOfAdministrativeFine.getText().toString());
        Receipt.putString(AdministrativeFine.this, Receipt.SURNAME_OF_PASSENGER, enterSurnameOfPassenger.getText().toString());

        Intent intent = new Intent(AdministrativeFine.this, AdditionalCharge.class);
        startActivity(intent);
        finish();
    }
}
