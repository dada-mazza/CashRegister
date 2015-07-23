package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import ua.cashregister.R;
import ua.cashregister.register.cashier.action.menu.Blank;
import ua.cashregister.storage.Receipt;

import java.util.Random;

public class AdditionalCharge extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_charge);
    }

    public void onClickNext(View view) {

        // зберегти дані
        saveData();

        Intent intent = new Intent(AdditionalCharge.this, Blank.class);
        startActivity(intent);
        finish();
    }

    // розрахунок вартості квитка
    private Integer invoicing() {
        // TODO розрахунок вартості квитка
        return new Random().nextInt(500) + 500;
    }

    private void saveData() {

        RadioGroup additionalCharge = (RadioGroup) findViewById(R.id.additionalCharge);

        Receipt.putString(AdditionalCharge.this,
                Receipt.ADDITIONAL_CHARGE,
                ((RadioButton) findViewById(additionalCharge.getCheckedRadioButtonId())).getText().toString());


        Receipt.putString(AdditionalCharge.this,
                Receipt.PAYABLE,
                invoicing().toString());
    }
}
