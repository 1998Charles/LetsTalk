package com.example.letstalk.ui.login;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.letstalk.R;
import com.example.letstalk.data.LoginRepository;
import com.example.letstalk.simplechat1.ClientConsole;
import com.example.letstalk.ui.login.LoginActivity;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    //LoginRepository loginRepository;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // LoginRepository loginRepository= new LoginActivity().getLoginRepository();

        Intent intent = getIntent();
        String loginID = intent.getStringExtra("loginID");
        String hostName = intent.getStringExtra("hostName");
        String portNumber = intent.getStringExtra("portNumber");

        TextView textView=(TextView)findViewById(R.id.windowText);
        final EditText windowEditText = findViewById(R.id.editText);
        final Button hideButton = findViewById(R.id.keyboard);
        final Button sendButton = findViewById(R.id.send);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        try
        {
            //ClientConsole.main(new String[]{loginID, hostName, portNumber});
            ClientConsole chat = new ClientConsole(loginID, hostName, Integer.parseInt(portNumber));
            chat.accept();

            System.out.println("Victory");

            BufferedReader fromConsole =
                    new BufferedReader(new InputStreamReader(System.in));
            String message;

          //  while (true) {
                try
                {
                    message = fromConsole.readLine();
                    textView.setText(textView.getText() + message);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
           // }

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

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        BufferedReader fromConsole =
//                new BufferedReader(new InputStreamReader(System.in));
//        String message;
//
//        while (true) {
//            try
//            {
//                message = fromConsole.readLine();
//                textView.setText(textView.getText() + message);
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


//        public String ls(){
//            Class<?> execClass = Class.forName("android.os.Exec");
//            Method createSubprocess = null;
//            try {
//                createSubprocess = execClass.getMethod("createSubprocess", String.class, String.class, String.class, int[].class);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            int[] pid = new int[1];
//            FileDescriptor fd = (FileDescriptor)createSubprocess.invoke(null, "/system/bin/ls", "/", null, pid);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fd)));
//            String output = "";
//            try {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output += line + "n";
//                }
//            }
//            catch (IOException e) {}
//            return output;
//        }

    }
}
