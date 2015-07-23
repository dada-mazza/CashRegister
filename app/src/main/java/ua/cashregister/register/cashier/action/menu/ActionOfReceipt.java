package ua.cashregister.register.cashier.action.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ua.cashregister.R;
import ua.cashregister.register.cashier.action.receipt.ProductsAndServices;
import ua.cashregister.register.cashier.action.receipt.TicketSeat;
import ua.cashregister.register.cashier.action.receipt.TypeOfReceipt;
import ua.cashregister.register.cashier.action.repay.Repay;
import ua.cashregister.print.PrintActivity;
import ua.cashregister.storage.Print;
import ua.cashregister.storage.Receipt;
import ua.cashregister.storage.SettingRRO;

/**
 * перехід (Т)
 * меню
 * новий
 * повтор
 * гашення
 * повернення
 * пробний квиток
 * відомість посадчика
 */
public class ActionOfReceipt extends Activity {

    private int DIALOG_RETRY = 2;
    private int DIALOG_REDEMPTION = 3;
    private int DIALOG_POSADCHYK_STATEMENT = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_of_receipt);

        // для режимів окрім ПОСАДЧИК, зробити невидимим
        if (!Receipt.checkTransition(
                this,
                Receipt.CASH_REGISTER_MENU_ITEM,
                getResources().getString(R.string.posadchyk))) {

            Button onClickPosadchykStatement = (Button) findViewById(R.id.posadchykStatement);
            onClickPosadchykStatement.setVisibility(View.GONE);
        }
    }

    // меню новий
    // в режимі "Товари/Послуги" одразу до (U)
    public void onClickNewTicket(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.ACTION_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string._new));

        // перевірити режим продажу
        if (SettingRRO.checkModeSale(ActionOfReceipt.this, getResources().getString(R.string.mode_sale_regimes_products_and_services))) {
            Intent intent = new Intent(ActionOfReceipt.this, ProductsAndServices.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(ActionOfReceipt.this, TypeOfReceipt.class);
            startActivity(intent);
        }
    }

    // меню повтор
    public void onClickRetry(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.ACTION_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.retry));

        showDialog(DIALOG_RETRY);
    }

    // меню гашення
    public void onClickRedemption(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.ACTION_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.redemption));

        showDialog(DIALOG_REDEMPTION);
    }

    // меню повернення
    public void onClickRepay(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.ACTION_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.repay));

        Intent intent = new Intent(ActionOfReceipt.this, Repay.class);
        startActivity(intent);
    }

    // меню пробний квиток`
    public void onClickTestTicket(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.ACTION_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.test_ticket));

        // Пробний квиток
        // TODO код друку
        Intent intent = new Intent(this, PrintActivity.class);
        intent.putExtra(Print.PRINT, getResources().getString(R.string.test_ticket));
        startActivity(intent);
    }

    // відомість посадчика
    public void onClickPosadchykStatement(View view) {

        // покласти в Storage меню перехода
        Receipt.putString(this, Receipt.ACTION_OF_RECEIPT_MENU_ITEM, getResources().getString(R.string.posadchyk_statement));

        // викликати наступну активність через діалог підтвердження
        showDialog(DIALOG_POSADCHYK_STATEMENT);
    }

    // створення діалогів
    @Override
    protected Dialog onCreateDialog(int id) {

        // Повтор
        // створення повідомлення
        if (id == DIALOG_RETRY) {

            return dialog(R.string.retry, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        // дія кнопки позитивної відповіді
                        case Dialog.BUTTON_POSITIVE:

                            if (SettingRRO.checkModeSale(ActionOfReceipt.this, getResources().getString(R.string.mode_sale_regimes_without_seats))
                                    || SettingRRO.checkModeSale(ActionOfReceipt.this, getResources().getString(R.string.mode_sale_regimes_products_and_services))) {

                                // перехід до (Р)
                                Intent intent = new Intent(ActionOfReceipt.this, Blank.class);
                                startActivity(intent);

                            } else if (SettingRRO.checkModeSale(ActionOfReceipt.this, getResources().getString(R.string.mode_sale_regimes_with_seats))
                                    || SettingRRO.checkModeSale(ActionOfReceipt.this, getResources().getString(R.string.mode_sale_regimes_auto))) {

                                // перехід до (M)
                                Intent intent = new Intent(ActionOfReceipt.this, TicketSeat.class);
                                startActivity(intent);
                            }

                            break;

                        // дія кнопки негативної відповіді
                        case Dialog.BUTTON_NEGATIVE:

                            break;
                    }
                }
            }).create();
        }

        // Гашення
        // створення повідомлення
        if (id == DIALOG_REDEMPTION) {
            return dialog(R.string.redemption, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        // дія кнопки позитивної відповіді
                        case Dialog.BUTTON_POSITIVE:
                            // Гашення
                            // TODO код друку
                            Intent intent = new Intent(ActionOfReceipt.this, PrintActivity.class);
                            intent.putExtra(Print.PRINT, getResources().getString(R.string.redemption_receipt));
                            startActivity(intent);
                            break;

                        // дія кнопки негативної відповіді
                        case Dialog.BUTTON_NEGATIVE:

                            break;
                    }
                }
            }).create();
        }

        // Відомість посадчика
        if (id == DIALOG_POSADCHYK_STATEMENT) {
            return dialog(R.string.print_posadchyk_statement, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        // дія кнопки позитивної відповіді
                        case Dialog.BUTTON_POSITIVE:
                            // Пробний квиток
                            // TODO код друку
                            Intent intent = new Intent(ActionOfReceipt.this, PrintActivity.class);
                            intent.putExtra(Print.PRINT, getResources().getString(R.string.posadchyk_statement).concat("\n 2 екземпляри"));
                            startActivity(intent);
                            break;

                        // дія кнопки негативної відповіді
                        case Dialog.BUTTON_NEGATIVE:

                            break;
                    }
                }
            }).create();
        }

        return super.onCreateDialog(id);
    }

    // створення діалогів
    private AlertDialog.Builder dialog(int messageId, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // заголовок
        dialog.setTitle(R.string.app_name);
        // повідомлення
        dialog.setMessage(messageId);
        // іконка
        dialog.setIcon(android.R.drawable.ic_menu_help);
        // кнопка позитивної відповіді
        dialog.setPositiveButton(R.string.yes, listener);
        // кнопка негативної відповіді
        dialog.setNegativeButton(R.string.cancel, listener);
        // створення діалогу
        return dialog;
    }

}
