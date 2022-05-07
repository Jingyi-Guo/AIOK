package com.AIOK.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputEditText textInputEditTextemail, textInputEditTextpassword;
    Button buttonLogin;
    TextView textViewsignupText;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextpassword = findViewById(R.id.password);

        buttonLogin = findViewById(R.id.buttonLogin);
        textViewsignupText = findViewById(R.id.signupText);
        progressBar = findViewById(R.id.progress);

        textViewsignupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                password = String.valueOf(textInputEditTextpassword.getText());
                email = String.valueOf(textInputEditTextemail.getText());

                if (email.isEmpty()) {
                    textInputEditTextemail.setError("Field cannot be empty");
                    textInputEditTextemail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textInputEditTextemail.setError("Invalid email");
                    textInputEditTextemail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    textInputEditTextemail.setError("Field cannot be empty");
                    textInputEditTextemail.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}
