package com.example.whereismymoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Money.db";
    public static final int DATABASE_VERSION = 4;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USERNAME = "username";

    public static final String TABLE_MONEY = "money";
    public static final String MONEY_COLUMN_ID = "_id";
    public static final String MONEY_COLUMN_USER = "user_id";
    public static final String MONEY_COLUMN_STATUS = "status";
    public static final String MONEY_COLUMN_SUM = "sum";
    public static final String MONEY_COLUMN_DESC = "description";
    public static final String MONEY_COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_users = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_USERNAME + " TEXT) ";
        db.execSQL(create_users);
        String create_money = "CREATE TABLE " + TABLE_MONEY +
                " (" + MONEY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MONEY_COLUMN_USER + " INTEGER, " +
                MONEY_COLUMN_STATUS + " INTEGER, " +
                MONEY_COLUMN_SUM + " REAL, " +
                MONEY_COLUMN_DATE + " DATE, " +
                MONEY_COLUMN_DESC + " TEXT, FOREIGN KEY(" + MONEY_COLUMN_USER + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "));";
        db.execSQL(create_money);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);

        onCreate(db);
    }

    public void createUser(String email, String password, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_USERNAME, username);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }
    public void createMoney(int user_id, int status, float sum, String date, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MONEY_COLUMN_USER, user_id);
        cv.put(MONEY_COLUMN_STATUS, status);
        cv.put(MONEY_COLUMN_SUM, sum);
        cv.put(MONEY_COLUMN_DATE, date);
        cv.put(MONEY_COLUMN_DESC, description);

        long result = db.insert(TABLE_MONEY, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_EMAIL + "='" + email + "' AND " +
                COLUMN_PASSWORD + "='" + password + "';";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor getMoney(String date, int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MONEY + " WHERE "
                + MONEY_COLUMN_USER + "=" + user_id + " AND " +
                MONEY_COLUMN_DATE + "='" + date + "';";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
