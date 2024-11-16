package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button emailButton = findViewById(R.id.button_email);
        Button phoneButton = findViewById(R.id.button_phone);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                emailIntent.setData(Uri.parse("mailto:"));

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@example.com"});  // Adresse e-mail
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sujet du message");  // Sujet du mail
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Contenu du message");  // Corps du mail
                startActivity(emailIntent);
            }

        });


        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                String phoneNumber = "tel:+1234567890";
                dialIntent.setData(Uri.parse(phoneNumber));
                    startActivity(dialIntent);

            }
        });
    }

}
