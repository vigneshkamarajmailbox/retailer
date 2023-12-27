package com.botree.retailerssfa.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.botree.retailerssfa.models.ScreenConfig;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CHECKED;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MODULE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MODULE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SCREEN_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SCREEN_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.SEQUENCE;
import static com.botree.retailerssfa.db.query.MTDDashboardQueryHelper.QUERY_OFFLINE_SCREEN_CONFIGURATION;


class MTDDashboardHelper  {
    private static final String TAG = MTDDashboardHelper.class.getSimpleName();

    List<ScreenConfig> getOfflineScreenConfig(SFADatabase sfaDatabase, String cmpCode) {
        List<ScreenConfig> screenConfigList = new ArrayList<>();

        try {

            SQLiteDatabase database = sfaDatabase.getReadableDatabase();
            Cursor c = database.rawQuery(QUERY_OFFLINE_SCREEN_CONFIGURATION, new String[]{});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    ScreenConfig screenConfig = new ScreenConfig();
                    screenConfig.setModuleName(c.getString(c.getColumnIndex(COLUMN_MODULE_NAME)));
                    screenConfig.setModuleNo(c.getInt(c.getColumnIndex(COLUMN_MODULE_NO)));
                    screenConfig.setScreenName(c.getString(c.getColumnIndex(COLUMN_SCREEN_NAME)));
                    screenConfig.setScreenNo(c.getInt(c.getColumnIndex(COLUMN_SCREEN_NO)));
                    screenConfig.setChecked(c.getString(c.getColumnIndex(COLUMN_CHECKED)));
                    screenConfig.setSequence(c.getInt(c.getColumnIndex(SEQUENCE)));
                    screenConfigList.add(screenConfig);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getOfflineScreenConfig: " + e.getMessage(),e);
        }

        return screenConfigList;
    }

}
