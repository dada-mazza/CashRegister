package ua.cashregister.register.withdrawal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ua.cashregister.R;
import ua.cashregister.storage.Print;
import ua.cashregister.print.PrintActivity;

/**
 * Перехід 1 - (логін) - 5 - 1
 * або
 * Перехід 1 - (логін) - 5 - (2,3) - 1
 *
 * Сума вилучення
 *
 * Службове вилучення
 * перехід з CashRegister
 */
public class WithdrawalAmount extends Activity {

    TextView title;
    EditText amountEditText;
    Integer amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        title = (TextView) findViewById(R.id.textNumber);
        amountEditText = (EditText) findViewById(R.id.enterNumber);

        title.setText(R.string.enter_cost_of_products_and_services);
        amountEditText.setHint(R.string.amount);


    }

    private boolean checkBalanceOfRRO() {

        // TODO прописати метод перевірки залишку грошей
        return true;
    }

    public void onClickNext(View view) {

        if (checkBalanceOfRRO()) {

            Intent intent = new Intent(this, PrintActivity.class);

            String report = new StringBuilder()
                    .append(getIntent().getExtras().getString(Print.PRINT))
                    .append("\n")
                    .append(getResources().getString(R.string.amount))
                    .append(": ")
                    .append(amountEditText.getText().toString())
                    .toString();

            intent.putExtra(Print.PRINT, report);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.extract_not_available, Toast.LENGTH_SHORT).show();
        }

    }
}
