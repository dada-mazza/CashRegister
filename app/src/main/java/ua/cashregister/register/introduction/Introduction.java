package ua.cashregister.register.introduction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ua.cashregister.R;
import ua.cashregister.storage.Print;


/**
 * Перехід 1 - (логін) - 4
 *
 * Службове внесення
 * перехід з CashRegister
 */
public class Introduction extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_introduction);
    }


    private void gag(Class aClass, String report){

        Intent intent = new Intent(Introduction.this, aClass);
        intent.putExtra(Print.PRINT, report);
        startActivity(intent);
        finish();
    }

    public void onClickOfficialIntroduction(View view) {

       gag(IntroductionAmount.class, getResources().getString(R.string.official_introduction));

    }

    public void onClickOfficialIntroductionSurplus(View view) {

        gag(IntroductionAmount.class, getResources().getString(R.string.introduction_surplus));

    }

    public void onClickOfficialIntroductionFromAnotherCashier(View view) {

        gag(IntroductionFromCashier.class, getResources().getString(R.string.introduction_from_another_cashier));

    }
}
