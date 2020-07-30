package com.example.letstalk.ui.login;


import android.annotation.SuppressLint;
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

import com.example.letstalk.R;
import com.example.letstalk.simplechat1.ClientConsole;

/**
 * @author Xiaoxi Jia
 * @version July 2020
 */

public class MainActivity extends AppCompatActivity {

    //LoginRepository loginRepository;

    //private static final String LOG_TAG = "MainTerminal";

    TextView textView; // Message window
    EditText windowEditText; // Edit window
    Button hideButton; // Hide the keyboard
    Button sendButton; // Send and update the message

    ClientConsole chat;

    final public static int DEFAULT_PORT = 5555;


    // private MyLog.MLog myLog = new MyLog.MLog();

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

        textView=(TextView)findViewById(R.id.windowText);
        windowEditText =(EditText) findViewById(R.id.editText);
        hideButton =(Button) findViewById(R.id.keyboard);
        sendButton =(Button) findViewById(R.id.send);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());


        try
        {
            //ClientConsole.main(new String[]{loginID, hostName, portNumber});

            chat = new ClientConsole(loginID, hostName, Integer.parseInt(portNumber));

            //chat.accept();

            //System.out.println("Victory(System)" + "\r\n");

/*
           Log.i(LOG_TAG, "Victory");

            MyLog.MLog.getLog();

            FileWriter output = new FileWriter("ChatLog.txt");
            BufferedWriter out = new BufferedWriter(output);
            out.write("Victory" + "/n");

            FileReader input = new FileReader("/com/example/letstalk/ui/login/ChatLog.txt");
            BufferedReader fromConsole = new BufferedReader(input);
            String message;

            BufferedReader fromConsole =
                    new BufferedReader(new InputStreamReader(System.in));
            String message;


                 while (true) {
                try
                {
                    message = fromConsole.readLine();

                    Log.i(LOG_TAG, message);

                    textView.setText(textView.getText() + message);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
*/

        }
        catch (Throwable e)
        {
            StringBuilder out = new StringBuilder("");

            for (int i = 0; i < e.getStackTrace().length; i++)
            {
                out.append(e.getStackTrace()[i]);
                out.append(" / ");
            }
            out.append("THE END.");

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), out.toString(), Toast.LENGTH_LONG).show();
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

                try
                {
                    String message;
                    message = windowEditText.getText().toString();


                        if (message.equals("#quit")) {
                            windowEditText.setText("");
                            chat.getChatClient().quit();
                            finish();
                        } else if (message.equals("#logoff")) {
                            chat.getChatClient().closeConnection();
                            windowEditText.setText("");
                        } else if (message.split(" ")[0].equals("#sethost")) {
                            if (chat.getChatClient().isConnected()) {
                                display("Client logged in, logoff to set the host.");
                            } else {
                                String newHost = message.split(" ")[1];
                                chat.getChatClient().setHost(newHost);
                                display("New host set to " + chat.getChatClient().getHost());
                            }
                        } else if (message.split(" ")[0].equals("#setport")) {
                            if (chat.getChatClient().isConnected()) {
                                display("Client logged in, logoff to set the port.");
                            } else {
                                int newPort = DEFAULT_PORT;
                                try {
                                    newPort = Integer.parseInt(message.split(" ")[1]);
                                    chat.getChatClient().setPort(newPort);
                                    display("New port set to " + chat.getChatClient().getPort());
                                } catch (NumberFormatException e) {
                                    display("Please enter port number in valid format.");
                                }
                            }
                        } else if (message.equals("#login")) {
                            if (chat.getChatClient().isConnected()) {
                                display("Already logged in.");
                            } else {
                                chat.getChatClient().openConnection();
                                windowEditText.setText("");
                                //chat.getChatClient().login();
                            }
                        } else if (message.equals("#gethost")) {
                            if (chat.getChatClient().isConnected()) {
                                display("current host name " + chat.getChatClient().getHost());
                            } else {
                                display("client logged off, host N/A.");
                            }
                        } else if (message.equals("#getport")) {
                            if (chat.getChatClient().isConnected()) {
                                display("current port number " + chat.getChatClient().getPort());
                            } else {
                                display("client logged off, port N/A.");
                            }
                        } else if (!chat.getChatClient().isConnected()) {
                            display("logged off from the server, login again to send messages.");
                        } else {

                            chat.getChatClient().handleMessageFromClientUI(message);
                            windowEditText.setText("");
                        }
                }
                catch (Exception ex)
                {
                    display
                            ("Unexpected error while reading from client console!");
                    ex.printStackTrace();
                }

                try {

                    String string;
                    string = chat.getChatClient().getMessage().toString();
                    textView.setText(string);
                }
                catch (Throwable e)
                {
                    StringBuilder out = new StringBuilder("");

                    for (int i = 0; i < e.getStackTrace().length; i++)
                    {
                        out.append(e.getStackTrace()[i]);
                        out.append(" / ");
                    }
                    out.append("THE END.");

                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), out.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String string;
                    string = chat.getChatClient().getMessage().toString();
                    textView.setText(string);
                }
                catch (Throwable e)
                {
                    StringBuilder out = new StringBuilder("");

                    for (int i = 0; i < e.getStackTrace().length; i++)
                    {
                        out.append(e.getStackTrace()[i]);
                        out.append(" / ");
                    }
                    out.append("THE END.");

                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), out.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


/*
        BufferedReader fromConsole =
                new BufferedReader(new InputStreamReader(System.in));
        String message;

        while (true) {
            try
            {
                message = fromConsole.readLine();
                textView.setText(textView.getText() + message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        public String ls(){
            Class<?> execClass = Class.forName("android.os.Exec");
            Method createSubprocess = null;
            try {
                createSubprocess = execClass.getMethod("createSubprocess", String.class, String.class, String.class, int[].class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            int[] pid = new int[1];
            FileDescriptor fd = (FileDescriptor)createSubprocess.invoke(null, "/system/bin/ls", "/", null, pid);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fd)));
            String output = "";
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    output += line + "n";
                }
            }
            catch (IOException e) {}
            return output;
        }
*/

/*
        try {
            Intent intentML = new Intent(MainActivity.this, MessageListener.class);
            startActivity(intentML);
        } catch (Throwable e) {
            e.printStackTrace();
        }
*/

    }

    public void display(String message)
    {
        windowEditText.setText(message);
    }

}
