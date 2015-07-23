package ua.cashregister.menu;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ua.cashregister.R;

/**
 * Перехід 1-(1,2,3)
 * Перевірка авторизації з запуском відповідного екрану заданого попереднім меню
 */

public class Login extends Activity {

    private final static String FOR_LOGIN = "ua.cashregister.FOR_LOGIN";

    private EditText mLogin;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (EditText) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);
    }

    // перевірка логіна та паролю користувача
    // замінити на вичитку з бази користувачів
    private boolean authorization(EditText login, EditText password) {
        // TODO прописати  метод авторизації

        return (login.getText().toString().equals("q") && password.getText().toString().equals("q"));
    }

    // Клік Авторизація
    public void onClickSignIn(View v) {

       ProgressDialog dialog =
                ProgressDialog.show(this, "", getResources().getString(R.string.checking), true);

        // Відображення результатів перевірки
        // невведені дані або невірний логін чи пароль
        TextView unavailableLogin = (TextView) findViewById(R.id.textViewUnavailableLogin);

        if (mLogin.getText().length() == 0 && mPassword.getText().length() != 0) {
            unavailableLogin.setText("\n" + getResources().getString(R.string.enter_login));
        } else if (mLogin.getText().length() != 0 && mPassword.getText().length() == 0) {
            unavailableLogin.setText("\n" + getResources().getString(R.string.enter_password));
        } else if (mLogin.getText().length() == 0 && mPassword.getText().length() == 0) {
            unavailableLogin.setText("\n" + getResources().getString(R.string.enter_login_and_password));
        }
        // Запустити перевірку введених логіна і пароля
        else if (authorization(mLogin, mPassword)) {
            Class aClass = (Class) getIntent().getExtras()
                    .get(FOR_LOGIN);
            Intent intent = new Intent(this, aClass);
            startActivity(intent);
            finish();
        } else {
            unavailableLogin.setText("\n" + getResources().getString(R.string.incorrect_login_or_password));
        }

        dialog.cancel();
    }

}
