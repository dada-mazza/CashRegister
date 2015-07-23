package ua.cashregister.print;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ua.cashregister.R;
import ua.cashregister.storage.Print;
import ua.cashregister.storage.Receipt;

import java.util.Random;

/**
 * використовую для імітації друку
 * виводить на екран передані дані
 */

public class PrintActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        TextView textReports = (TextView) findViewById(R.id.textPrint);
        String nameReport = getIntent().getExtras().getString(Print.PRINT);

        textReports.setText(nameReport);
    }


    public void onClickScreen(View view) {

        if (Receipt.getString(PrintActivity.this, Receipt.TYPE_OF_SUBSCRIPTION_BLANK)
                .equals(getString(R.string.e_card))) {

            // запис на картку - діалог
            dialogOfCardRecording();

        } else {

            // TODO прописати відсилання звіту на друк
            // закрити активність
            finish();
        }
    }

    // діалог запису даних
    private void dialogOfCardRecording() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(PrintActivity.this)
                .setTitle(getString(R.string.prepare_card_for_recording).concat("!"))
                .setMessage(getString(R.string.start_recording).concat("?"))
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (startRecording()) {

                            // запис вдалий - закрити активність
                            Receipt.clear(PrintActivity.this);
                            finish();

                        } else {

                            // запис не вдалий - повторити
                            dialogOfCardRecording();

                        }
                    }
                });
        dialog.create().show();
    }


    // метод запису даних на картку
    private boolean startRecording() {
        //TODO метод запису даних на картку
        return new Random().nextBoolean();
    }
}


