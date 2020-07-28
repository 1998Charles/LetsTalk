package com.example.letstalk.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letstalk.R;
import com.example.letstalk.data.LoginRepository;
import com.example.letstalk.ui.login.LoginViewModel;
import com.example.letstalk.ui.login.LoginViewModelFactory;

// From the simplechat1/ClientConsole.java
import java.io.*;
import com.example.letstalk.simplechat1.*;
import com.example.letstalk.ui.login.*;

// Scroll view
import android.text.method.ScrollingMovementMethod;

import org.w3c.dom.Text;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    //public LoginActivity loginActivity = this;
    public LoginRepository loginRepository;

    ClientConsole chat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText loginIdEditText = findViewById(R.id.loginid); // login id
        final EditText usernameEditText = findViewById(R.id.username); // host name
        final EditText passwordEditText = findViewById(R.id.password); // port number
        final Button loginButton = findViewById(R.id.login);
        final Button keyBoard = findViewById(R.id.keyboard);
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

        keyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




//********************
            //    setContentView(R.layout.activity_main);

            //    TextView textView=(TextView)findViewById(R.id.windowText);
            //    textView.setMovementMethod(ScrollingMovementMethod.getInstance());

                try
                {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("loginID", loginIdEditText.getText().toString());
                    intent.putExtra("hostName", usernameEditText.getText().toString());
                    intent.putExtra("portNumber", passwordEditText.getText().toString());
                    startActivity(intent);
                    //LoginActivity.this.finish();



                }

                catch (Throwable t)
                {
                    StringBuffer out = new StringBuffer("");

                    for (int i = 0; i < t.getStackTrace().length; i++)
                    {
                        out.append(t.getStackTrace()[i]);
                        out.append(" / ");
                    }
                    out.append("THE END.");

                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), out.toString(), Toast.LENGTH_LONG).show();
                }


//                ScrollView scrollView1=(ScrollView)findViewById(R.id.scrollView1);
//                scrollView1.setMovementMethod(ScrollingMovementMethod.getInstance());
//
//                TextView scrollView2=(TextView)findViewById(R.id.scrollView2);
//                scrollView2.setMovementMethod(ScrollingMovementMethod.getInstance());


//********************

//                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                if(imm != null) {
//                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
//                            0);
//                }


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
                //    ClientConsole chat = new ClientConsole(loginIdEditText.getText().toString(), usernameEditText.getText().toString(), Integer.parseInt(passwordEditText.getText().toString()));
                //    chat.accept();

                //    ClientConsole.main(new String[]{loginIdEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString()});

                    loginRepository = new LoginRepository(loginIdEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString());

                    //ClientConsole.main(new String[]{"OSFC", "192.168.137.35", "5555"});
                    //ServerConsole.main(new String[]{});
                    //HelloWorld.main(new String[]{});

                    //new RetrieveFeedTask().execute();

                    //Toast.makeText(getApplicationContext(), loginIdEditText.getText().toString() + usernameEditText.getText().toString() + passwordEditText.getText().toString(), Toast.LENGTH_LONG).show();

                    //setContentView(R.layout.activity_main);
                    //setContentView(R.layout.test2);

                //    TextView textView=(TextView)findViewById(R.id.windowText);
                //    textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                    Toast.makeText(getApplicationContext(), "Welcome to LetsTalk！", Toast.LENGTH_LONG).show();



                }
                catch (Throwable e)
                {
                    StringBuffer out = new StringBuffer("");

                    for (int i = 0; i < e.getStackTrace().length; i++)
                    {
                        out.append(e.getStackTrace()[i]);
                        out.append(" / ");
                    }
                    out.append("THE END.");

                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), out.toString(), Toast.LENGTH_LONG).show();
 //                   Toast.makeText(getApplicationContext(), "Error, please try again later.", Toast.LENGTH_LONG).show();
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

    public LoginRepository getLoginRepository()
    {
        return loginRepository;
    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        data.getExtras().getInt("data");
    }
*/
}