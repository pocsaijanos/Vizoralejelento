package com.example.vizoralejelento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 11;

    EditText emailEditT;
    EditText passwordEditT;
    ImageView imageView;

    private SharedPreferences preferences;
    private FirebaseAuth firebAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditT = findViewById(R.id.editTextEmail);
        passwordEditT = findViewById(R.id.editTextPassword);

        /* ANIMATION */
        if (findViewById(R.id.imageView) != null) {
            imageView = findViewById(R.id.imageView);
            Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            imageView.setAnimation(rotate);

            Animation swim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swim);
            emailEditT.setAnimation(swim);
            passwordEditT.setAnimation(swim);
        }
        /*----------------------*/

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        firebAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String email = emailEditT.getText().toString();
        String password = passwordEditT.getText().toString();

        firebAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    openProfile();
                } else {
                    Toast.makeText(MainActivity.this, "Hibás e-mail vagy jelszó", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void openProfile() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", emailEditT.getText().toString());
        editor.putString("password", passwordEditT.getText().toString());
        editor.apply();
    }
}