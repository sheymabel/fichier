package com.example.tp6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class MonOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;

    // Nom de la table et les champs
    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_FIRSTNAME = "FIRSTNAME";
    public static final String COLUMN_LASTNAME = "LASTNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY, "
            + COLUMN_FIRSTNAME + " TEXT, "
            + COLUMN_LASTNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT);";

    public MonOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE); // Exécute la requête de création de la table
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprime la table existante si elle existe, puis la recrée
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db); // Crée à nouveau la base de données
    }
    public static int addUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("firstname", user.getFirstname());
        values.put("lastname", user.getLastname());
        values.put("password", user.getPassword());

        long result = db.insert("USERS", null, values);
        return (result == -1) ? 0 : 1;
    }

    public static User getUser(SQLiteDatabase db, String email) {
        Cursor cursor = db.query(TABLE_USERS,
                null,
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            String password = cursor.getString(3);
            cursor.close();
            return new User(email, firstname, lastname, password);
        }

        if (cursor != null) cursor.close();
        return null;
    }

}
