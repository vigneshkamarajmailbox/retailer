package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.models.WDInfrastructure;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.BaseDB.checkString;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_FREEZER_CAPACITY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_FROZEN_SQFT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TOTAL_CAPACITY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UNIT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_WET_SQFT;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_WD_FREEZER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_WD_STORAGE;

public class WDInfrastructureHelper {


    private static final String TAG = WDInfrastructureHelper.class.getSimpleName();

    public WDInfrastructureHelper() {
    }


    public void insertStorage(BaseDB baseDB, String cmpCode, String distCode, WDInfrastructure infrastructure) {

        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CMP_CODE, cmpCode);
            values.put(COLUMN_DISTR_CODE, distCode);
            if (!infrastructure.getWetsqft().isEmpty()) {
                values.put(COLUMN_WET_SQFT, infrastructure.getWetsqft());
            } else {
                values.put(COLUMN_WET_SQFT, "0");
            }
            if (!infrastructure.getFrozensqft().isEmpty()) {
                values.put(COLUMN_FROZEN_SQFT, infrastructure.getFrozensqft());
            } else {
                values.put(COLUMN_FROZEN_SQFT, "0");
            }
            values.put(COLUMN_DATE, infrastructure.getModDt());
            values.put(COLUMN_UPLOAD, "N");

            String appendAnd = " = ? and ";
            String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + " = ?";
            String[] updateArgs = {cmpCode, distCode};

            int affected = db.update(TABLE_WD_STORAGE, values, update, updateArgs);
            if (affected == 0) {
                db.insert(TABLE_WD_STORAGE, null, values);
            }
        } finally {
            baseDB.closeDb();
        }
    }

    public void insertFreezer(BaseDB baseDB, String cmpCode, String distCode, WDInfrastructure infrastructure) {
        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CMP_CODE, cmpCode);
            values.put(COLUMN_DISTR_CODE, distCode);
            values.put(COLUMN_FREEZER_CAPACITY, infrastructure.getFreezerCapacity());
            if (!infrastructure.getUnit().isEmpty()) {
                values.put(COLUMN_UNIT, infrastructure.getUnit());
            } else {
                values.put(COLUMN_UNIT, "0");
            }
            if (!infrastructure.getTotalCapacity().isEmpty()) {
                values.put(COLUMN_TOTAL_CAPACITY, infrastructure.getTotalCapacity());
            } else {
                values.put(COLUMN_TOTAL_CAPACITY, "0");
            }

            values.put(COLUMN_DATE, infrastructure.getModDt());
            values.put(COLUMN_UPLOAD, "N");

            String appendAnd = " = ? and ";
            String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_FREEZER_CAPACITY + " = ?";
            String[] updateArgs = {cmpCode, distCode, infrastructure.getFreezerCapacity()};

            int affected = db.update(TABLE_WD_FREEZER, values, update, updateArgs);
            if (affected == 0) {
                db.insert(TABLE_WD_FREEZER, null, values);
            }
        } finally {
            baseDB.closeDb();
        }
    }

    public List<WDInfrastructure> getStorageDetail(BaseDB baseDB, String cmpCode, String distCode) {
        List<WDInfrastructure> storageList = new ArrayList<>();
        baseDB.openWritableDb();

        String query = "SELECT *\n" +
                "FROM t_WD_Storage\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='N'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, null);

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                WDInfrastructure wdInfrastructure = getStorageModel(cm);
                storageList.add(wdInfrastructure);
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getStorageDetail: " + e.getMessage(), e);
        }
        return storageList;
    }

    private WDInfrastructure getStorageModel(Cursor cm) {
        WDInfrastructure wdInfrastructure = new WDInfrastructure();
        wdInfrastructure.setWetsqft(cm.getString(cm.getColumnIndex(COLUMN_WET_SQFT)));
        wdInfrastructure.setFrozensqft(cm.getString(cm.getColumnIndex(COLUMN_FROZEN_SQFT)));
        return wdInfrastructure;
    }

    public List<WDInfrastructure> getFreezerDetail(BaseDB baseDB, String cmpCode, String distCode) {
        SQLiteDatabase db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_WD_Freezer\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='N'";

        List<WDInfrastructure> list = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    WDInfrastructure wdInfrastructure = new WDInfrastructure();
                    wdInfrastructure.setFreezerCapacity(checkString(c, COLUMN_FREEZER_CAPACITY));
                    wdInfrastructure.setUnit(checkString(c, COLUMN_UNIT));
                    wdInfrastructure.setTotalCapacity(checkString(c, COLUMN_TOTAL_CAPACITY));
                    list.add(wdInfrastructure);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getFreezerDetail: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return list;
    }

    public WDInfrastructure getWDInfrastructureStorage(BaseDB baseDB, String cmpCode, String distCode) {
        baseDB.openWritableDb();
        WDInfrastructure wdInfrastructure = new WDInfrastructure();
        String query = "SELECT *\n" +
                "FROM t_WD_Storage\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, null);

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                wdInfrastructure = getStorageModel(cm);
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getStorageDetail: " + e.getMessage(), e);
        }
        return wdInfrastructure;
    }

    public List<WDInfrastructure> getWDInfrastructureFreezer(BaseDB baseDB, String cmpCode, String distCode) {
        SQLiteDatabase db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_WD_Freezer\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'";

        List<WDInfrastructure> list = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    WDInfrastructure wdInfrastructure = new WDInfrastructure();
                    wdInfrastructure.setFreezerCapacity(checkString(c, COLUMN_FREEZER_CAPACITY));
                    wdInfrastructure.setUnit(checkString(c, COLUMN_UNIT));
                    wdInfrastructure.setTotalCapacity(checkString(c, COLUMN_TOTAL_CAPACITY));
                    list.add(wdInfrastructure);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getFreezerDetail: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return list;
    }
}


