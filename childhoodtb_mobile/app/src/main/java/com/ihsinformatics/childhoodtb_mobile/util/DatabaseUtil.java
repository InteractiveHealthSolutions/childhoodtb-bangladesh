/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 *
 */

package com.ihsinformatics.childhoodtb_mobile.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ihsinformatics.childhoodtb_mobile.R;
import com.ihsinformatics.childhoodtb_mobile.model.Percentile;
import com.ihsinformatics.childhoodtb_mobile.shared.Metadata;

import java.util.ArrayList;

/**
 * @author owais.hussain@irdinformatics.org
 */
public class DatabaseUtil extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseUtil";
    private static final String DB_NAME = "childhoodTb.db";
    private static final int DB_VERSION = 1;
    private Context context;

    public DatabaseUtil(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            return;
        }
        switch (oldVersion) {
            case 0: // Script to upgrade from version 0 to 1
                break;
            case 1: // Script to upgrade from version 1 to 2
                break;
        }
        onUpgrade(db, ++oldVersion, newVersion);
    }

    /**
     * Builds Sqlite database using schema queries defined in resources. If
     * createNew is true, then all existing table will be and recreated
     *
     * @param createNew
     */
    public void buildDatabase(boolean createNew) {
        if (createNew)
            context.deleteDatabase(DB_NAME);
        String[] tables = context.getResources().getStringArray(R.array.tables);
        SQLiteDatabase db = getWritableDatabase();
        for (String s : tables) {
            db.execSQL(s);
        }
        db.close();
        Log.i(TAG, "Database created/updated.");
    }


    /**
     * Insert records into local database
     *
     * @param table
     * @param values
     * @return
     */
    public synchronized boolean insert(String table, ContentValues values) {

        DatabaseUtil util = new DatabaseUtil(context);
        SQLiteDatabase writableDatabase = util.getWritableDatabase();
        long result = writableDatabase.insert(table, null, values);
        if (result == -1) {
            result = writableDatabase.insert(table, null, values);
        }
        writableDatabase.close();
        boolean check = (result != -1);
        if (check) {
            Log.i(TAG, "Record inserted in table: " + table);
        } else {
            Log.i(TAG, "Record not inserted in table: " + table
                    + ". Error code: " + result);
        }
        return check;
    }

    /**
     * Delete records from local database
     *
     * @param table
     * @param whereClause where = value1 = ? AND value2 = ? AND value3 = ?
     * @param whereArgs   whereArgs = {string1, string2, string3}
     * @return
     */
    public synchronized boolean delete(String table, String whereClause,
                                       String[] whereArgs) {
        DatabaseUtil util = new DatabaseUtil(context);
        SQLiteDatabase writableDatabase = util.getWritableDatabase();
        long result = writableDatabase.delete(table, whereClause, whereArgs);
        writableDatabase.close();
        boolean check = (result != -1);
        if (check) {
            Log.i(TAG, "Record deleted from table: " + table);
        } else {
            Log.i(TAG, "Record not deleted from table: " + table
                    + ". Error code: " + result);
        }
        return check;
    }

    /**
     * Update records in local database
     *
     * @param table
     * @param values
     * @param whereClause where = value1 = ? AND value2 = ? AND value3 = ?
     * @param whereArgs   whereArgs = {string1, string2, string3}
     * @return
     */
    public synchronized boolean update(String table, ContentValues values,
                                       String whereClause, String[] whereArgs) {
        DatabaseUtil util = new DatabaseUtil(context);
        SQLiteDatabase writableDatabase = util.getWritableDatabase();
        long result = writableDatabase.update(table, values, whereClause,
                whereArgs);
        writableDatabase.close();
        boolean check = (result != -1);
        if (check) {
            Log.i(TAG, "Record updated in table: " + table);
        } else {
            Log.i(TAG, "Record not updated in table: " + table
                    + ". Error code: " + result);
        }
        return check;
    }

    /**
     * Get an object in string from local database
     *
     * @param query
     * @return
     */
    public String getObject(String query) {
        String result = null;
        try {
            result = getColumnData(query)[0];
        } catch (Exception e) {
        }
        return result;
    }
    /**
     * Get an object in string from local database using given parameters
     *
     * @param table
     * @param column
     * @param whereClause
     * @return
     */
    public String getObject(String table, String column, String whereClause) {
        return getObject("SELECT " + column + " FROM " + table + " WHERE "
                + whereClause);
    }

    /**
     * Get a list of values in a column from local database using given
     * parameters
     *
     * @param table
     * @param column
     * @param whereClause
     * @param distinct
     * @return
     */
    public String[] getColumnData(String table, String column,
                                  String whereClause, boolean distinct) {
        String[] result = new String[0];
        try {
            String query = "SELECT " + (distinct ? "DISTINCT " : "") + column
                    + " FROM " + table + " WHERE " + whereClause;
            result = getColumnData(query);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Get a list of values in a column from local database using given query
     *
     * @param query
     * @return
     */
    public String[] getColumnData(String query) {
        DatabaseUtil util = new DatabaseUtil(context);
        SQLiteDatabase readableDatabase = util.getReadableDatabase();
        ArrayList<String> data = new ArrayList<String>();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    data.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        readableDatabase.close();
        return data.toArray(new String[]{});
    }


    /**
     * Get a single record local database using given query and return the
     * values in an array
     *
     * @param query
     * @return
     */
    public String[] getRecord(String query) {
        DatabaseUtil util = new DatabaseUtil(context);
        SQLiteDatabase readableDatabase = util.getReadableDatabase();
        ArrayList<String> data = new ArrayList<String>();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        if (cursor != null) {
            int columns = cursor.getColumnCount();
            if (cursor.moveToFirst()) {
                for (int i = 0; i < columns; i++)
                    data.add(cursor.getString(i));
            }
        }
        cursor.close();
        readableDatabase.close();
        return data.toArray(new String[]{});
    }

    /**
     * Get data in a 2-D table from local database using given parameters
     *
     * @param table
     * @param columns
     * @param whereClause
     * @return
     */
    public String[][] getTableData(String table, String columns,
                                   String whereClause) {
        String query = "SELECT " + columns + " FROM " + table + " WHERE "
                + whereClause;
        return getTableData(query);
    }

    /**
     * Get data in a 2-D table from local database using given query
     *
     * @param query
     * @return
     */
    public String[][] getTableData(String query) {
        DatabaseUtil util = new DatabaseUtil(context);
        SQLiteDatabase readableDatabase = util.getReadableDatabase();
        ArrayList<String[]> data = new ArrayList<String[]>();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        if (cursor != null) {
            int columns = cursor.getColumnCount();
            if (cursor.moveToFirst()) {
                do {
                    String[] record = new String[columns];
                    for (int i = 0; i < columns; i++)
                        record[i] = cursor.getString(i);
                    data.add(record);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        readableDatabase.close();
        return data.toArray(new String[][]{});
    }
}
