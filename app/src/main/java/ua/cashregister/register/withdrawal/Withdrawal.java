package ua.cashregister.register.withdrawal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ua.cashregister.R;
import ua.cashregister.storage.Print;


/**
 * Перехід 1 - (логін) - 5
 * Службове вилучення
 * перехід з CashRegister
 */
public class Withdrawal extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_withdrawal);
    }

    private void gag(Class aClass, String report){

        Intent intent = new Intent(this, aClass);
        intent.putExtra(Print.PRINT, report);
        startActivity(intent);
        finish();
    }

    public void onClickOfficialWithdrawal(View view) {

        gag(WithdrawalAmount.class, getResources().getString(R.string.official_withdrawal));

    }

    public void onClickOfficialWithdrawalToCounterparty(View view) {

        gag(WithdrawalToCounterperty.class, getResources().getString(R.string.withdrawal_to_counterparty));

    }

    public void onClickOfficialWithdrawalToAnotherCashier(View view) {

        gag(WithdrawalToCashier.class, getResources().getString(R.string.withdrawal_to_another_cashier));

    }
}

