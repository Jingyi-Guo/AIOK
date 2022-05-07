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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Signup extends AppCompatActivity{

    private FirebaseAuth mAuth;

    private TextInputEditText textInputEditTextfullname, textInputEditTextusername, textInputEditTextemail,
            textInputEditTextpassword, textInputEditTextconfirm_password;
    Button buttonSignUp;
    TextView textViewloginText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        textInputEditTextfullname = findViewById(R.id.fullname);
        textInputEditTextusername = findViewById(R.id.username);
        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextpassword = findViewById(R.id.password);
        textInputEditTextconfirm_password = findViewById(R.id.confirm_password);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewloginText = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewloginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String fullname, username, password, confirm_password, email;
                fullname = String.valueOf(textInputEditTextfullname.getText());
                username = String.valueOf(textInputEditTextusername.getText());
                password = String.valueOf(textInputEditTextpassword.getText());
                confirm_password = String.valueOf(textInputEditTextconfirm_password.getText());
                email = String.valueOf(textInputEditTextemail.getText());

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textInputEditTextemail.setError("Invalid email");
                    textInputEditTextemail.requestFocus();
                    return;
                }

                if (fullname.isEmpty()) {
                    textInputEditTextfullname.setError("Field cannot be empty");
                    textInputEditTextfullname.requestFocus();
                    return;
                }

                if (username.isEmpty()) {
                    textInputEditTextusername.setError("Field cannot be empty");
                    textInputEditTextusername.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    textInputEditTextpassword.setError("Field cannot be empty");
                    textInputEditTextpassword.requestFocus();
                    return;
                }

                if (confirm_password.isEmpty()) {
                    textInputEditTextconfirm_password.setError("Field cannot be empty");
                    textInputEditTextconfirm_password.requestFocus();
                    return;
                }

                if (password.length() <6 ) {
                    textInputEditTextpassword.setError("Password must be more than 6 characters");
                    textInputEditTextpassword.requestFocus();
                    return;
                }

                if (!password.equals(confirm_password)) {
                    textInputEditTextpassword.setError("Passwords must match");
                    textInputEditTextconfirm_password.setError("Passwords must match");
                    textInputEditTextconfirm_password.requestFocus();
                    textInputEditTextpassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(fullname, username, email);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Signup.this, "Sign up successful", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                            else {
                                                Toast.makeText(Signup.this, "Sign up failed", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }

}