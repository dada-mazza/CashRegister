package ua.cashregister.register.cashier.action.receipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ua.cashregister.R;
import ua.cashregister.print.PrintActivity;
import ua.cashregister.storage.Print;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.SettingRRO;

import java.util.Random;

public class TypeOfReceipt extends Activity {

    private Button buttonTicket;
    private Button buttonBaggageCheck;
    private Button buttonSubscription;
    private Button buttonProductsOrServices;
    private Button buttonETicket;
    private Button buttonTariffFine;
    private Button buttonAdministrativeFine;
    private Button buttonReducingTravelCard;
    private Button buttonAccountingForMissingPassengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_receipt);

        buttonTicket = (Button) findViewById(R.id.buttonTicket);
        buttonBaggageCheck = (Button) findViewById(R.id.buttonBaggageCheck);
        buttonSubscription = (Button) findViewById(R.id.buttonSubscription);
        buttonProductsOrServices = (Button) findViewById(R.id.buttonProductsOrServices);
        buttonETicket = (Button) findViewById(R.id.buttonETicket);
        buttonTariffFine = (Button) findViewById(R.id.buttonTariffFine);
        buttonAdministrativeFine = (Button) findViewById(R.id.buttonAdministrativeFine);
        buttonReducingTravelCard = (Button) findViewById(R.id.buttonReducingTravelCard);
        buttonAccountingForMissingPassengers = (Button) findViewById(R.id.buttonAccountingForMissingPassengers);

        // не відображати в режимі Посадчик
        if (!Receipt.checkTransition(TypeOfReceipt.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.posadchyk))) {
            buttonSubscription.setVisibility(View.GONE);
        }

        // не відображати для режиму продажу Без Місць
        if (SettingRRO.checkModeSale(TypeOfReceipt.this, getString(R.string.mode_sale_regimes_without_seats))) {
            buttonProductsOrServices.setVisibility(View.GONE);
            buttonETicket.setVisibility(View.GONE);
            buttonReducingTravelCard.setVisibility(View.GONE);
        }

        // не відображати для режиму продажу Без Місць чи Ревізор
        if (SettingRRO.checkModeSale(TypeOfReceipt.this, getString(R.string.mode_sale_regimes_without_seats))
                || !Receipt.checkTransition(TypeOfReceipt.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.auditor))) {
            buttonTariffFine.setVisibility(View.GONE);
            buttonAdministrativeFine.setVisibility(View.GONE);

        }

        // не відображати для режиму продажу Без Місць чи Посадчик
        if (SettingRRO.checkModeSale(TypeOfReceipt.this, getString(R.string.mode_sale_regimes_without_seats))
                || !Receipt.checkTransition(TypeOfReceipt.this, Receipt.CASH_REGISTER_MENU_ITEM, getString(R.string.posadchyk))) {
            buttonAccountingForMissingPassengers.setVisibility(View.GONE);
        }

    }

    // Квиток
    public void onClickTicket(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.ticket));

        Intent intent = new Intent(TypeOfReceipt.this, Ticket.class);
        startActivity(intent);
    }

    // Багажна квитанція
    public void onClickBaggageCheck(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.baggage_check));

        Intent intent = new Intent(TypeOfReceipt.this, Ticket.class);
        startActivity(intent);
    }

    // абнемент
    public void onClickSubscription(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.subscription));

        Intent intent = new Intent(TypeOfReceipt.this, Ticket.class);
        startActivity(intent);

    }

    // Товари/Послуги
    public void onClickProductsOrServices(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.products_and_services));

        Intent intent = new Intent(TypeOfReceipt.this, ProductsAndServices.class);
        startActivity(intent);
    }

    // Електронний квиток
    public void onClickETicket(View view) {
        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.e_ticket));

        readETicket();
    }

    // Тарифний штраф
    public void onClickTariffFine(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.tariff_fine));

        Intent intent = new Intent(TypeOfReceipt.this, Ticket.class);
        startActivity(intent);
    }

    //Адміністративний штраф
    public void onClickAdministrativeFine(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.administrative_fine));

        Intent intent = new Intent(TypeOfReceipt.this, AdministrativeFine.class);
        startActivity(intent);
    }

    // Зменшення поїздок на картці
    public void onClickReducingTravelCard(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.reducing_travel_card));

        Intent intent = new Intent(TypeOfReceipt.this, ReducingTravelCard.class);
        startActivity(intent);
    }

    // облік відсутніх пасажирів
    public void onClickAccountingForMissingPassengers(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.TYPE_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.accounting_for_missing_passengers));

        Intent intent = new Intent(TypeOfReceipt.this, AccountingForMissingPassengers.class);
        startActivity(intent);
    }

    // зчитування штрих-коду
    private boolean readBarcode() {
        // TODO зчитування штрих-коду
        return new Random().nextBoolean();
    }

    private void readETicket() {

        if (readBarcode()) {
            // штрих-код зчитався вдало
            // TODO друк фіскального чеку
            String reports = new StringBuilder()
                    .append(getString(R.string.fiscal_check_sale))
                    .append("\n")
                    .append(getString(R.string.e_ticket))
                    .toString();

            Intent intent = new Intent(TypeOfReceipt.this, PrintActivity.class);
            intent.putExtra(Print.PRINT, reports);
            startActivity(intent);
        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(TypeOfReceipt.this)
                    .setTitle(R.string.barcode_not_read_off)
                    .setMessage(getString(R.string.read_barcode_again).concat("?"))
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            readETicket();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            buttonSubscription.setVisibility(View.VISIBLE);
                            buttonProductsOrServices.setVisibility(View.GONE);
                            buttonETicket.setVisibility(View.GONE);
                            buttonTariffFine.setVisibility(View.GONE);
                            buttonAdministrativeFine.setVisibility(View.GONE);
                            buttonReducingTravelCard.setVisibility(View.GONE);
                            buttonAccountingForMissingPassengers.setVisibility(View.GONE);
                        }
                    });

            dialog.create().show();
        }
    }
}
