package com.example.letstalk.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letstalk.R;
import com.example.letstalk.ui.login.LoginViewModel;
import com.example.letstalk.ui.login.LoginViewModelFactory;

// From the simplechat1/ClientConsole.java
import java.io.*;
import com.example.letstalk.simplechat1.*;
import com.example.letstalk.ui.login.*;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText loginIdEditText = findViewById(R.id.loginid);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());



                try
                {
                    ClientConsole chat = new ClientConsole(loginIdEditText.getText().toString(), usernameEditText.getText().toString(), Integer.parseInt(passwordEditText.getText().toString()));
                    chat.accept();

                    //ClientConsole.main(new String[]{loginIdEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString()});
                    //ClientConsole.main(new String[]{"OSFC", "192.168.137.35", "5555"});
                    //ServerConsole.main(new String[]{});
                    //HelloWorld.main(new String[]{});

                    //new RetrieveFeedTask().execute();

                    //Toast.makeText(getApplicationContext(), loginIdEditText.getText().toString() + usernameEditText.getText().toString() + passwordEditText.getText().toString(), Toast.LENGTH_LONG).show();

                    setContentView(R.layout.activity_main);
                    //Toast.makeText(getApplicationContext(), "Welcome to LetsTalk！", Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    StringBuilder out = new StringBuilder();

                    for (int i = 0; i < e.getStackTrace().length; i++)
                    {
                        out.append(e.getStackTrace()[i]);
                        out.append(" ");
                    }
                    out.append("THE END.");

                    Toast.makeText(getApplicationContext(), e.fillInStackTrace().toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), out.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Error, please try again later.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        // String welcome = getString(R.string.welcome) + model.getDisplayName();
        String login = new String("Confirmed");
        // TODO : initiate successful logged in experience
        // Toast.makeText(getApplicationContext(), login, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}