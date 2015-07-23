package ua.cashregister.register.cashier.action.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ua.cashregister.R;
import ua.cashregister.storage.Receipt;

public class Blank extends Activity {

    TextView tvBlank;
    String blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        tvBlank = (TextView) findViewById(R.id.tvBlank);

        // Повернення
        if (Receipt.checkTransition(
                Blank.this,
                Receipt.ACTION_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.repay))) {

            blank = createFiscalCheckRepay();

        }
        // Квиток
        else if (Receipt.checkTransition(
                Blank.this,
                Receipt.TYPE_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.ticket))) {

            blank = createFiscalCheckSaleTicket();
        }
        // Багажна квитанція
        else if (Receipt.checkTransition(
                Blank.this,
                Receipt.TYPE_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.baggage_check))) {

            blank = createFiscalCheckSaleBaggage();
        }
        // Абонемент
        else if (Receipt.checkTransition(
                Blank.this,
                Receipt.TYPE_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.subscription))) {

            blank = createFiscalCheckSaleSubscription();
        }
        // Товари/Послуги
        else if (Receipt.checkTransition(
                Blank.this,
                Receipt.TYPE_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.products_and_services))) {

            blank = createFiscalCheckSaleForProductsAndServices();
        }
        // Тарифний штраф
        else if (Receipt.checkTransition(
                Blank.this,
                Receipt.TYPE_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.tariff_fine))) {

            blank = createFiscalCheckSaleTariffFine();
        }

        // Адміністративний штраф
        else if (Receipt.checkTransition(
                Blank.this,
                Receipt.TYPE_OF_RECEIPT_MENU_ITEM,
                getResources().getString(R.string.administrative_fine))) {

            blank = createFiscalCheckSaleAdministrativeFine();
        }


        tvBlank.setText(blank);
    }

    private String createFiscalCheckRepay() {

        return new StringBuilder()
                .append(getResources().getString(R.string.fiscal_check_return))
                .append("\n")
                .append(getResources().getString(R.string.number_rro))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.NUMBER_RRO))
                .append("\n")
                .append(getResources().getString(R.string.number_check))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.NUMBER_CHECK))
                .append("\n")
                .append(getResources().getString(R.string.date_of_sale))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.DATE_OF_SALE))
                .append("\n")
                .append(getResources().getString(R.string.goods))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.GOOD))
                .append("\n")
                .append(getResources().getString(R.string.quantity_of_goods))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.QUANTITY_OF_GOODS))
                .append("\n")
                .append(getResources().getString(R.string.unit_of_measurement))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.UNIT_OF_MEASUREMENT))
                .append("\n")
                .append(getResources().getString(R.string.total_amount_repay))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();

    }

    private String createFiscalCheckSaleForProductsAndServices() {

        return new StringBuilder()
                .append(getResources().getString(R.string.fiscal_check_sale))
                .append("\n")
                .append(getString(R.string.date_of_departure))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.DATE_OF_DEPARTURE))
                .append("\n")
                .append(getString(R.string.route))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ROUTE))
                .append("\n")
                .append(getString(R.string.train))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TRAIN))
                .append("\n")
                .append(getString(R.string.wagon))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.WAGON))
                .append("\n")
                .append(getString(R.string.types_class))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TYPES_CLASS))
                .append("\n")
                .append(getString(R.string.carrier))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.CARRIER))
                .append("\n")
                .append(getResources().getString(R.string.goods))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.GOOD))
                .append("\n")
                .append(getResources().getString(R.string.quantity_of_goods))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.QUANTITY_OF_GOODS))
                .append("\n")
                .append(getResources().getString(R.string.unit_of_measurement))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.UNIT_OF_MEASUREMENT))
                .append("\n")
                .append(getResources().getString(R.string.total_amount_repay))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();

    }

    private String createTicket() {

        return new StringBuilder()
                .append(getString(R.string.fiscal_check_sale))
                .append("\n")
                .append(getString(R.string.surname_of_passenger))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.SURNAME_OF_PASSENGER))
                .append("\n")
                .append(getString(R.string.date_of_departure))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.DATE_OF_DEPARTURE))
                .append("\n")
                .append(getString(R.string.route))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ROUTE))
                .append("\n")
                .append(getString(R.string.train))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TRAIN))
                .append("\n")
                .append(getString(R.string.wagon))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.WAGON))
                .append("\n")
                .append(getString(R.string.types_class))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TYPES_CLASS))
                .append("\n")
                .append(getString(R.string.carrier))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.CARRIER))
                .append("\n")
                .append(getString(R.string.from_station))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.FROM_STATION))
                .append("\n")
                .append(getString(R.string.to_station))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TO_STATION))
                .append("\n")
                .append(getString(R.string.transfer))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TRANSFER))
                .append("\n")
                .append(getString(R.string.station_of_transfer))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.STATION_OF_TRANSFER))
                .append("\n")
                .append(getString(R.string.way))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.WAY))
                .append("\n")
                .append(getString(R.string.benefit))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.BENEFIT))
                .append("\n")
                .append(getString(R.string.certificate))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.CERTIFICATE))
                .toString();
    }

    private String createFiscalCheckSaleTicket() {

        return new StringBuilder(createTicket())
                .append("\n")
                .append(getString(R.string.seat))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.SEAT))
                .append("\n")
                .append(getString(R.string.additional_charge))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ADDITIONAL_CHARGE))
                .append("\n")
                .append(getResources().getString(R.string.payable))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();
    }

    private String createFiscalCheckSaleBaggage() {

        return new StringBuilder(createTicket())
                .append("\n")
                .append(getString(R.string.quantity_baggage))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.BAGGAGE))
                .append("\n")
                .append(getString(R.string.additional_charge))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ADDITIONAL_CHARGE))
                .append("\n")
                .append(getResources().getString(R.string.payable))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();
    }

    private String createFiscalCheckSaleSubscription() {

        return new StringBuilder(createTicket())
                .append("\n")
                .append(getString(R.string.type_of_subscription))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TYPE_OF_SUBSCRIPTION))
                .append("\n")
                .append(getString(R.string.quantity_of_month_or_trip))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.QUANTITY_OF_MONTH_OR_TRIP))
                .append("\n")
                .append(getString(R.string.start_date))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.START_DATE))
                .append("\n")
                .append(getString(R.string.nominal))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.NOMINAL_SUBSCRIPTION))
                .append("\n")
                .append(getString(R.string.type_of_blank))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TYPE_OF_SUBSCRIPTION_BLANK))
                .append("\n")
                .append(getString(R.string.number_of_paper_blank))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.NUMBER_OF_PAPER_BLANK))
                .append("\n")
                .append(getString(R.string.additional_charge))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ADDITIONAL_CHARGE))
                .append("\n")
                .append(getResources().getString(R.string.payable))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();
    }

    private String createFiscalCheckSaleTariffFine() {

        return new StringBuilder(createTicket())
                .append("\n")
                .append(getString(R.string.list_of_article_of_tariff_fine))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ARTICLE_FINE))
                .append(getString(R.string.additional_charge))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ADDITIONAL_CHARGE))
                .append("\n")
                .append(getResources().getString(R.string.payable))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();
    }

    private String createFiscalCheckSaleAdministrativeFine() {

        return new StringBuilder()
                .append(getString(R.string.fiscal_check_sale))
                .append("\n")
                .append(getString(R.string.surname_of_passenger))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.SURNAME_OF_PASSENGER))
                .append("\n")
                .append(getString(R.string.date_of_departure))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.DATE_OF_DEPARTURE))
                .append("\n")
                .append(getString(R.string.route))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ROUTE))
                .append("\n")
                .append(getString(R.string.train))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TRAIN))
                .append("\n")
                .append(getString(R.string.wagon))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.WAGON))
                .append("\n")
                .append(getString(R.string.types_class))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.TYPES_CLASS))
                .append("\n")
                .append(getString(R.string.carrier))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.CARRIER))
                .append("\n")
                .append(getString(R.string.list_of_article_of_administrative_fine))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.ARTICLE_FINE))
                .append("\n")
                .append(getString(R.string.size_of_of_administrative_fine))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.SIZE_OF_FINE))
                .append("\n")
                .append(getResources().getString(R.string.payable))
                .append("\n")
                .append(Receipt.getString(Blank.this, Receipt.PAYABLE))
                .toString();
    }

    public void onClickScreen(View view) {

        //
        Receipt.putString(this, Receipt.BLANK, blank);

        Intent intent = new Intent(Blank.this, TypeOfPayment.class);
        startActivity(intent);
        finish();
    }

}
