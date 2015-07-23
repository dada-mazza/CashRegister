package ua.cashregister.register.introduction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ua.cashregister.R;
import ua.cashregister.storage.Print;
import ua.cashregister.print.PrintActivity;


/**
 * Перехід 1 - (логін) - 4 - 1,2
 *
 * Службове внесення
 * перехід з CashRegister
 */
public class IntroductionAmount extends Activity {

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


    public void onClickNext(View view) {

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

    }
}
