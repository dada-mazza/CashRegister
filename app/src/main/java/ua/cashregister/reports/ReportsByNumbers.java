package ua.cashregister.reports;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ua.cashregister.R;
import ua.cashregister.storage.Print;
import ua.cashregister.print.PrintActivity;

/**
 * Перехід 1 - (логін) - 2 - (10,12)
 * Меню звітність вибір номерів звітів
 * Дозволяє ввести номери звітів
 * разом з назвою стартового звіту
 *
 * */

public class ReportsByNumbers extends Activity {


    private EditText startNumbersReports;
    private EditText finalNumbersReports;

    private int DIALOG_ALERT = 1;

    private int mStartNumber;
    private int mFinalNumber;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_by_numbers);

        startNumbersReports = (EditText) findViewById(R.id.enterStartNumber);
        finalNumbersReports = (EditText) findViewById(R.id.enterFinalNumber);

        mStartNumber = 1;
        mFinalNumber = 1;
    }


    public void onClickNext(View view) {

        mStartNumber = Integer.parseInt(startNumbersReports.getText().toString());
        mFinalNumber = Integer.parseInt(finalNumbersReports.getText().toString());
        // перевірка порядкових номерів звітів
        // кінцевий після стартового
        if (mFinalNumber < mStartNumber) {
            // виклик діалогу помилки невідповідність порядку номерів
            showDialog(DIALOG_ALERT);
        } else {
            Intent intent = new Intent(ReportsByNumbers.this, PrintActivity.class);
            String report = new StringBuilder()
                    .append(getIntent().getExtras().getString(Print.PRINT))
                    .append("\n")
                    .append(getResources().getString(R.string.start_numbers))
                    .append(" ")
                    .append(mStartNumber)
                    .append("\n")
                    .append(" ")
                    .append(getResources().getString(R.string.final_numbers))
                    .append(mFinalNumber)
                    .toString();

            intent.putExtra(Print.PRINT, report);
            startActivity(intent);
            finish();
        }
    }


    protected Dialog onCreateDialog(int id) {

        // створення повідомлення про помилку в датах
        if (id == DIALOG_ALERT) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // заголовок
            alertDialog.setTitle(R.string.attention);
            // повідомлення
            alertDialog.setMessage(R.string.discrepancy_numbers);
            // іконка
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            // кнопка відповіді
            alertDialog.setPositiveButton(R.string.yes, null);
            // створення діаллогу
            return alertDialog.create();
        }

        return super.onCreateDialog(id);
    }
}

