package com.botree.retailerssfa.db.query;

import android.database.Cursor;

import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.models.LobModel;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOB_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOB_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_DISTR_LOB;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOB;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.queryDeleteTable;

/*
 *Created by Godlin Josheela Rani S on 22/4/19.
 */
public class LobQueryHelper {
    final String TAG = LobQueryHelper.class.getSimpleName();

    /* getAllMasterLob */
    public static final String QUERY_GET_ALL_MASTER_LOB = "SELECT * From " + TABLE_LOB + " WHERE cmpCode=?";
    /* getAllDistributorLob */
    public static final String QUERY_GET_ALL_DISTRIBUTOR_LOB = "select t1.*, t2.lobName from t_distrLobMapping t1 left join m_lob t2 on t1.lobCode = t2.lobCode WHERE t1.cmpCode=? AND t1.distrCode = ?";

    public List<LobModel> getAllMasterLob(BaseDB baseDB, String companyCode) {

        List<LobModel> lobModelList = new ArrayList<>();
        Cursor cursor;
        cursor = baseDB.getDb().rawQuery(QUERY_GET_ALL_MASTER_LOB, new String[]{companyCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                LobModel lobModel = new LobModel();

                lobModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                lobModel.setLobCode(cursor.getString(cursor.getColumnIndex(COLUMN_LOB_CODE)));
                lobModel.setLobName(cursor.getString(cursor.getColumnIndex(COLUMN_LOB_NAME)));

                lobModelList.add(lobModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return lobModelList;
    }

    public List<LobModel> getAllDistributorLob(BaseDB baseDB, String companyCode, String distributorCode) {

        List<LobModel> lobModelList = new ArrayList<>();
        Cursor cursor;
        cursor = baseDB.getDb().rawQuery(QUERY_GET_ALL_DISTRIBUTOR_LOB, new String[]{companyCode, distributorCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                LobModel lobModel = new LobModel();

                lobModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                lobModel.setDistributorCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)));
                lobModel.setLobCode(cursor.getString(cursor.getColumnIndex(COLUMN_LOB_CODE)));
                lobModel.setLobName(cursor.getString(cursor.getColumnIndex(COLUMN_LOB_NAME)));

                lobModelList.add(lobModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return lobModelList;
    }

    public void resetAllDistributorLob(List<LobModel> distributorLobArrayList, BaseDB baseDB) {
        deleteDistributorLob(baseDB);
        new SyncDBHelper(baseDB).insertDistributorLobList(distributorLobArrayList);
    }

    private void deleteDistributorLob(BaseDB baseDB) {
        baseDB.openWritableDb();
        baseDB.getDb().execSQL(queryDeleteTable(TABLE_DISTR_LOB));
        baseDB.closeDb();
    }

}
