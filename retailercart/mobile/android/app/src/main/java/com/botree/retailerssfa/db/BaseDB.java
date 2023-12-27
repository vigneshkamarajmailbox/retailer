/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.botree.retailerssfa.db.query.ISFADB;

public abstract class BaseDB extends SQLiteOpenHelper implements ISFADB {


    private SQLiteDatabase db;

    BaseDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static String checkString(Cursor c, String col) {
        String str = "";
        if (c.getString(c.getColumnIndex(col)) != null && c.getString(c.getColumnIndex(col)).length() > 0) {
            str = c.getString(c.getColumnIndex(col));
        }
        return str;
    }

    public static Double checkDouble(Cursor c, String col) {
        double aDouble = 0d;
        if (c.getString(c.getColumnIndex(col)) != null && c.getDouble(c.getColumnIndex(col)) > 0) {
            aDouble = c.getDouble(c.getColumnIndex(col));
        }
        return aDouble;
    }

    public static Long checkLong(String longValue) {
        try {
            return Long.valueOf(longValue);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public synchronized void openWritableDb() {
        if (db == null) {
            db = this.getWritableDatabase();
        }
    }

    public synchronized void closeDb() {
        // use this to close base db
    }

    public synchronized SQLiteDatabase getDb() {

        if (db == null || !db.isOpen()) {
            openWritableDb();
        }
        return db;
    }
}
