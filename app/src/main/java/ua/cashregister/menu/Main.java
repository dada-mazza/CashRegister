package ua.cashregister.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ua.cashregister.R;
import ua.cashregister.storage.SettingRRO;
import ua.cashregister.setting.SettingRROActivity;

/**
 * Старт програми
 * Екран з емблемою
 * створення файлу налаштувань при першому запуску
 */
public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // створення файлу налаштувань при першому запуску додатка
        firstStart();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // створення файлу налаштувань при першому запуску
    // перевіряє наявність файлу налаштувань
    // запускає клас для створення налаштувань
    // використовується внутрішній файл додатку
    private void firstStart() {
       /* if (getSharedPreferences(SettingRRO.STORAGE_NAME, MODE_PRIVATE)
                .getBoolean(SettingRRO.FIRST_START, true)) {*/

        if (!SettingRRO.getBoolean(this, SettingRRO.FIRST_START)) {

            Intent start = new Intent(this, SettingRROActivity.class);
            startActivity(start);
        }
    }

    // дотик до екрану - дія запуск наступного Activity  та закриття поточного
    public void onClickMainScreen(View v) {

        Intent intent = new Intent(this, WorkingRegimes.class);
        startActivity(intent);

        finish();
    }
}
