package ua.cashregister.register.withdrawal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ua.cashregister.R;
import ua.cashregister.register.introduction.IntroductionAmount;
import ua.cashregister.storage.Print;


/**

 * Перехід 1 - (логін) - 5 - 2
 * <p/>
 * Службове вилучення
 * перехід з CashRegister
 * <p/>
 * Дані контрагента
 */
public class WithdrawalToCounterperty extends Activity {

    TextView title;
    EditText amountEditText;
    EditText textEditText;
    Integer numberCounterparty;
    String nameCounterparty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_and_text);

        title = (TextView) findViewById(R.id.textNumber);
        amountEditText = (EditText) findViewById(R.id.enterNumber);
        textEditText = (EditText) findViewById(R.id.enterText);

        title.setText(R.string.enter_data_of_counterparty);
        amountEditText.setHint(R.string.number_counterparty);
        textEditText.setHint(R.string.name_counterparty);
    }


    public void onClickNext(View view) {

        String report = new StringBuilder()
                .append(getIntent().getExtras().getString(Print.PRINT))
                .append("\n")
                .append(getResources().getString(R.string.number_counterparty))
                .append("\n")
                .append(amountEditText.getText().toString())
                .append("\n")
                .append(getResources().getString(R.string.name_counterparty))
                .append("\n")
                .append(textEditText.getText().toString())
                .toString();

        Intent intent = new Intent(this, IntroductionAmount.class);
        intent.putExtra(Print.PRINT, report);
        startActivity(intent);
        finish();
    }
}
