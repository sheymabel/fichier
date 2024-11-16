package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText EmailEditText;
    private EditText PasswordEditText;
    private MonOpenHelper monHelper;
    private SQLiteDatabase db;
    private TextView register;
    private TextView contactLink;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        register = findViewById(R.id.register_now);
        contactLink = findViewById(R.id.contact_link);
        EmailEditText = findViewById(R.id.Txtm);
        PasswordEditText = findViewById(R.id.Txtp);
        loginButton = findViewById(R.id.loginButton);

        monHelper = new MonOpenHelper(this);
        db = monHelper.getReadableDatabase();

        // Redirection vers l'activité d'inscription
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InscriActivity.class);
                startActivity(intent);
            }
        });

        contactLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("email") && intent.hasExtra("password")) {
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            EmailEditText.setText(email);
            PasswordEditText.setText(password);
        }

        // Définition de l'action du bouton de connexion
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });
    }

    private void onLoginClick() {
        String email = EmailEditText.getText().toString().trim();
        String password = PasswordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = MonOpenHelper.getUser(db, email);
        if (user != null && user.getPassword().equals(password)) {
            Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstname", user.getFirstname());
            editor.putString("lastname", user.getLastname());
            editor.apply();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Adresse mail ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
    }

}
