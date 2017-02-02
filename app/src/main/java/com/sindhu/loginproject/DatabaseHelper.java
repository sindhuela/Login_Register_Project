package com.sindhu.loginproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sindhu on February 01 2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "user_db";
    public static final int databaseVersion = 1;
    public static final String tableName = "user_details";
    public static final String col_id = "id";                       // 0
    public static final String col_name = "user_name";              // 1
    public static final String col_mail = "mail_id";                // 2
    public static final String col_num = "phone_number";            // 3
    public static final String col_pass = "password";               // 4

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + tableName + "(" + col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_name + " TEXT, " + col_mail + " TEXT, " + col_num + " INTEGER, " + col_pass + " TEXT)";
        //  id,  name,  mail,  number,  password
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    //to add data into the database
    public int register(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count;
        SQLiteDatabase dbTemp = this.getReadableDatabase();
        Cursor dataTemp = dbTemp.rawQuery("SELECT * FROM " + tableName + " WHERE " + col_mail + "=?", new String[]{userInfo.getMailId()});
        count = dataTemp.getCount();
        if (count == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_name, userInfo.getName());
            contentValues.put(col_mail, userInfo.getMailId());
            contentValues.put(col_num, userInfo.getPhone());
            contentValues.put(col_pass, userInfo.getPassword());
            db.insert(tableName, null, contentValues);
            return count;
        }
        return count;
    }

    public int dataValidation(UserInfo userInfo) {

        String name = userInfo.getName();
        String email = userInfo.getMailId();
        String password = userInfo.getPassword();
        String contact = userInfo.getPhone();

        //Null check
        if (userInfo.getName().equals(""))
            return 1;
        if (userInfo.getMailId().equals(""))
            return 2;
        if (userInfo.getPassword().equals(""))
            return 3;
        if (userInfo.getPhone().equals(""))
            return 4;

        //Name Checking
        String lowerCase = "abcdefghijklmnopqrstuvwxyz ";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        int alphabetsCount = 0, numbersCount = 0;
        for (int i = 0; i < name.length(); i++) {
            for (int j = 0; j < lowerCase.length(); j++)
                if (name.charAt(i) == lowerCase.charAt(j))
                    alphabetsCount++;
            for (int j = 0; j < upperCase.length(); j++)
                if (name.charAt(i) == upperCase.charAt(j))
                    alphabetsCount++;
            for (int j = 0; j < numbers.length(); j++) {
                if (name.charAt(i) == numbers.charAt(j))
                    numbersCount++;
            }
        }
        if (!((alphabetsCount + numbersCount) == name.length() && alphabetsCount > 0 ))
            return 5;
        for (int j = 0; j < numbers.length(); j++) {
            if (name.charAt(0) == numbers.charAt(j)) {
                return 5;
            }
        }

        //Email check
        int at = -1, dot = 0;
        if (isCharacterAlphabet(email.charAt(0))) {
            at = email.indexOf('@');
            if (at != -1) {
                dot = email.indexOf('.');
                if (at < dot) {
                    for (int i = at + 1; i < dot; i++) {
                        if (!isCharacterAlphabet(email.charAt(i)))
                            return 6;
                    }
                    for (int i = dot + 1; i < email.length(); i++) {
                        if (!isCharacterAlphabet(email.charAt(i)))
                            return 6;
                    }
                }
            }
        } else
            return 6;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == ' ')
                return 6;
        }

        //Password Check
        String specialCharacters = "!@#$%^&*()_+-=~`\",./<>?;':[]{}\\| ";
        int lowerCount = 0, upperCount = 0, numberCount = 0, specialCount = 0;
        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < lowerCase.length(); j++) {
                if (password.charAt(i) == lowerCase.charAt(j)) {
                    lowerCount++;
                    break;
                }
            }
            for (int j = 0; j < upperCase.length(); j++) {
                if (password.charAt(i) == upperCase.charAt(j)) {
                    upperCount++;
                    break;
                }
            }
            for (int j = 0; j < numbers.length(); j++) {
                if (password.charAt(i) == numbers.charAt(j)) {
                    numberCount++;
                    break;
                }
            }
            for (int j = 0; j < specialCharacters.length(); j++) {
                if (password.charAt(i) == specialCharacters.charAt(j)) {
                    specialCount++;
                    break;
                }
            }
        }
        if (!((lowerCount > 0) && (upperCount > 0) && (numberCount > 0) && (specialCount > 0) && (password.length() >= 6)))
            return 7;

        //Contact Number check
        if (!(contact.length() == 10))
            return 8;

        return 0;
    }

    //To check whether the given character is an Alphabet
    public Boolean isCharacterAlphabet(char ch) {
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < lowerCase.length(); i++)
            if (ch == lowerCase.charAt(i))
                return true;
        for (int i = 0; i < upperCase.length(); i++)
            if (ch == upperCase.charAt(i))
                return true;
        return false;
    }

    //Returns the user information
    public UserInfo getData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserInfo userInfo = new UserInfo();
        Cursor data = db.query(tableName, new String[]{col_id, col_name, col_mail, col_num, col_pass}, col_mail + "=?", new String[]{email}, null, null, null);
        if (data.moveToFirst()) {
            userInfo.setName(data.getString(1));
            userInfo.setMailId(data.getString(2));
            userInfo.setPhone(data.getString(3));
            userInfo.setPassword(data.getString(4));
            return userInfo;
        } else {
            return null;
        }
    }
}
