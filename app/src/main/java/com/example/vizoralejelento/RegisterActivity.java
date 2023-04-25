package com.example.vizoralejelento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final String LOG_TAG = RegisterActivity.class.getName();
    EditText customerIDEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordReEditText;
    private SharedPreferences preferences;

    private FirebaseAuth firebAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);
        if (secret_key != 11) {
            finish();
        }
        customerIDEditText = findViewById(R.id.editTextCustomerID);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        passwordReEditText = findViewById(R.id.editTextRePassword);

        /* ANIMATION */
        Animation swim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swim);
        customerIDEditText.setAnimation(swim);
        emailEditText.setAnimation(swim);
        passwordEditText.setAnimation(swim);
        passwordReEditText.setAnimation(swim);
        /* ------------------- */

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        emailEditText.setText(email);
        passwordEditText.setText(password);
        passwordReEditText.setText(password);

        firebAuth = FirebaseAuth.getInstance();
    }

    public void registration(View view) {
        String customerID = customerIDEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordRe = passwordReEditText.getText().toString();

        if (!password.equals(passwordRe) || password.length() == 0) {
            passwordEditText.setBackgroundColor(Color.RED);
            passwordReEditText.setBackgroundColor(Color.RED);
            Toast.makeText(RegisterActivity.this, "Jelszó és megerősítése nem egyezik", Toast.LENGTH_LONG).show();
        } else {
            firebAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        openProfile();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Regisztráció sikertelen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void openProfile() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {
        finish();
    }
}