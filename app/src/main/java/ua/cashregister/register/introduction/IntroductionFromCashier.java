package ua.cashregister.register.introduction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ua.cashregister.R;
import ua.cashregister.storage.Print;


/**
 * Перехід 1 - (логін) - 4 - 3
 *
 * Службове внесення
 * перехід з CashRegister
 *
 * Табельний номер касира
 */
public class IntroductionFromCashier extends Activity {

    TextView title;
    EditText amountEditText;
    Integer numberCashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        title = (TextView) findViewById(R.id.textNumber);
        amountEditText = (EditText) findViewById(R.id.enterNumber);

        title.setText(R.string.enter_number_of_cashier);
        amountEditText.setHint(R.string.number_cashier);
    }


    public void onClickNext(View view) {

        String report = new StringBuilder()
                .append(getIntent().getExtras().getString(Print.PRINT))
                .append("\n")
                .append(getResources().getString(R.string.number_cashier))
                .append("\n")
                .append(amountEditText.getText().toString())
                .toString();

        Intent intent = new Intent(this, IntroductionAmount.class);
        intent.putExtra(Print.PRINT, report);
        startActivity(intent);
        finish();
    }
}
