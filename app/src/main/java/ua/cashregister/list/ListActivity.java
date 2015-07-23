package ua.cashregister.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ua.cashregister.R;
import ua.cashregister.storage.Lists;

/**
 * Клас призначено для відображення списків
 * Приймає від батьківського
 * Назву списку та
 * ID ресурсу - масив для формування елементів
 */
public class ListActivity extends Activity {

    ListView itemList;
    TextView nameList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Зв'язуємо с ListView
        // назва списку
        nameList = (TextView) findViewById(R.id.nameList);
        // елементи списку
        itemList = (ListView) findViewById(R.id.itemList);

        // витягуємо назву списку з Intent
        nameList.setText(getIntent().getStringExtra(Lists.NAME));

        // Створюємо адаптер, використовуючи масив з файлу ресурсів
        // ID масиву отримуємо з Intent
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                // ID масиву отримуємо з Intent (передано з батьківської активності)
                getIntent().getIntExtra(Lists.ID_ARRAY, -1),
                android.R.layout.simple_list_item_1);
        itemList.setAdapter(adapter);

        // встановлюємо адаптер списку
        itemList.setAdapter(adapter);

        // Обробка кліка по елементам списку
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),
                        parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();

                // передача вибраних даних (текст) як результат, до батьківської активності
                Intent intent = new Intent();
                intent.putExtra(Lists.VALUE, parent.getAdapter().getItem(position).toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}