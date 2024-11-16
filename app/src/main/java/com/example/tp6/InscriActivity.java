package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriActivity extends AppCompatActivity {
    private EditText FirstNameEditText;
    private EditText LastNameEditText;
    private EditText EmailEditText;
    private EditText PasswordEditText;
    private MonOpenHelper monHelper = null;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscri);

        // Initialisation des champs EditText
        FirstNameEditText = findViewById(R.id.Txtfn);
        LastNameEditText = findViewById(R.id.Txtln);
        EmailEditText = findViewById(R.id.Txtm);
        PasswordEditText = findViewById(R.id.Txtp);

        // Initialisation du bouton Register
        Button registerButton = findViewById(R.id.Btnr);

        // Initialisation de MonOpenHelper et de la base de données
        monHelper = new MonOpenHelper(this);
        db = monHelper.getWritableDatabase();

        // Définition de l'action sur le bouton Register
        registerButton.setOnClickListener(view -> onRegisterClick());
    }

    // Méthode pour gérer l'inscription
    private void onRegisterClick() {
        // Récupérer le contenu des champs
        String email = EmailEditText.getText().toString().trim();
        String firstname = FirstNameEditText.getText().toString().trim();
        String lastname = LastNameEditText.getText().toString().trim();
        String password = PasswordEditText.getText().toString().trim();

        // Vérifier si les champs sont vides
        if (email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifier si l'utilisateur existe déjà
        User existingUser = MonOpenHelper.getUser(db, email);
        if (existingUser != null) {
            Toast.makeText(this, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show();
        } else {
            // Ajouter l'utilisateur si l'email est unique
            User newUser = new User(email, firstname, lastname, password);
            int result = MonOpenHelper.addUser(db, newUser);

            if (result == 1) {
                Toast.makeText(this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                // Rediriger vers l'interface d'authentification
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
