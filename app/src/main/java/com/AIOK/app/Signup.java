package com.AIOK.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Signup extends AppCompatActivity {

    TextInputEditText textInputEditTextfullname, textInputEditTextusername, textInputEditTextemail,
            textInputEditTextpassword, textInputEditTextconfirm_password;
    Button buttonSignUp;
    TextView textLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textInputEditTextfullname = findViewById(R.id.fullname);
        textInputEditTextusername = findViewById(R.id.username);
        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextpassword = findViewById(R.id.password);
        textInputEditTextconfirm_password = findViewById(R.id.confirm_password);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        textLoginText = findViewById(R.id.loginText);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname, username, password, confirm_password, email;
                fullname = String.valueOf(textInputEditTextfullname.getText());
                username = String.valueOf(textInputEditTextusername.getText());
                password = String.valueOf(textInputEditTextpassword.getText());
                confirm_password = String.valueOf(textInputEditTextconfirm_password.getText());
                email = String.valueOf(textInputEditTextemail.getText());

                if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("") && password.equals(confirm_password)) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "confirm_password";
                            field[4] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = "fullname";
                            data[1] = "username";
                            data[2] = "password";
                            data[3] = "email";
                            PutData putData = new PutData("http://192.168.12.16/majima/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required, and check passwords", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
