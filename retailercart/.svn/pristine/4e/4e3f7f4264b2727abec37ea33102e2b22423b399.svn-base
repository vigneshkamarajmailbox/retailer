package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.CodeGeneratorModel;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PREFIX;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SCREEN_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SUFFIX_NN;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SUFFIX_YY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CODE_GENERATOR;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CODE_GENERATOR_HISTORY;

/*
 *Created by Godlin Josheela Rani S on 17/4/19.
 */

public class CodeGeneratorQueryHelper {
    private static final String TAG = CodeGeneratorQueryHelper.class.getSimpleName();
    private static CodeGeneratorQueryHelper codeGeneratorQueryHelper;

    public static CodeGeneratorQueryHelper getInstance(){
        if(codeGeneratorQueryHelper == null){
            codeGeneratorQueryHelper = new CodeGeneratorQueryHelper();
        }
        return codeGeneratorQueryHelper;
    }

    public static int getCodeNumber(SFADatabase sfaDatabase, String prefix) {
        String code = "0";
        String query = "SELECT " + COLUMN_SUFFIX_NN + " FROM " + TABLE_CODE_GENERATOR +
                " WHERE " + COLUMN_SCREEN_NAME + " = ? LIMIT 1";

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{prefix});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            code = c.getString(c.getColumnIndex(COLUMN_SUFFIX_NN));
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return Integer.parseInt(code);
    }

    public static void insertCode(SFADatabase sfaDatabase, CodeGeneratorModel codeGeneratorModel) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "insertCode : model : " + codeGeneratorModel.toString());

        String sql = "INSERT INTO " + TABLE_CODE_GENERATOR + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        try {
            statement.clearBindings();
            statement.bindString(1, codeGeneratorModel.getCompanyCode());
            statement.bindString(2, codeGeneratorModel.getDistributorCode());
            statement.bindString(3, codeGeneratorModel.getScreenName());
            statement.bindString(4, codeGeneratorModel.getPrefix());
            statement.bindString(5, codeGeneratorModel.getSuffixYy());
            statement.bindString(6, String.valueOf(codeGeneratorModel.getSuffixNn()));
            statement.bindString(7, "N");
            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(TAG, e);
        }
    }

    public static void updateCode(SFADatabase sfaDatabase, CodeGeneratorModel codeGeneratorModel) {
        String query = COLUMN_SCREEN_NAME + " = ?";

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUFFIX_NN, codeGeneratorModel.getSuffixNn());
        values.put(COLUMN_UPLOAD, "N");

        String[] updateArgs = {codeGeneratorModel.getScreenName()};

        sfaDatabase.getDb().update(TABLE_CODE_GENERATOR, values, query, updateArgs);
        sfaDatabase.closeDb();
    }



    public List<CodeGeneratorModel> getAllGeneratedCodesForUpload(BaseDB baseDB, String distCode, String tableName) {

        List<CodeGeneratorModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + tableName + " WHERE upload = 'N'";
        cursor = baseDB.getDb().rawQuery(query, new String[]{});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel();

                codeGeneratorModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                codeGeneratorModel.setDistributorCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                codeGeneratorModel.setScreenName(cursor.getString(cursor.getColumnIndex(COLUMN_SCREEN_NAME)).trim());
                codeGeneratorModel.setPrefix(cursor.getString(cursor.getColumnIndex(COLUMN_PREFIX)).trim());
                codeGeneratorModel.setSuffixYy(cursor.getString(cursor.getColumnIndex(COLUMN_SUFFIX_YY)).trim());
                codeGeneratorModel.setSuffixNn(cursor.getInt(cursor.getColumnIndex(COLUMN_SUFFIX_NN)));
                codeGeneratorModel.setUpload(cursor.getString(cursor.getColumnIndex(COLUMN_UPLOAD)));

                routeList.add(codeGeneratorModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<CodeGeneratorModel> getAllGeneratedCodes(BaseDB baseDB, String distCode, String tableName) {

        List<CodeGeneratorModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + tableName + " WHERE distrCode=?";
        cursor = baseDB.getDb().rawQuery(query, new String[]{distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel();

                codeGeneratorModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                codeGeneratorModel.setDistributorCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                codeGeneratorModel.setScreenName(cursor.getString(cursor.getColumnIndex(COLUMN_SCREEN_NAME)).trim());
                codeGeneratorModel.setPrefix(cursor.getString(cursor.getColumnIndex(COLUMN_PREFIX)).trim());
                codeGeneratorModel.setSuffixYy(cursor.getString(cursor.getColumnIndex(COLUMN_SUFFIX_YY)).trim());
                codeGeneratorModel.setSuffixNn(cursor.getInt(cursor.getColumnIndex(COLUMN_SUFFIX_NN)));
                codeGeneratorModel.setUpload(cursor.getString(cursor.getColumnIndex(COLUMN_UPLOAD)));

                routeList.add(codeGeneratorModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public void insertCodeGenerator(SFADatabase sfaDatabase, List<CodeGeneratorModel> codeGeneratorModels, String tableName) {

        String sql = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        for(CodeGeneratorModel codeGeneratorModel : codeGeneratorModels) {
            try {
                statement.clearBindings();
                statement.bindString(1, codeGeneratorModel.getCompanyCode());
                statement.bindString(2, codeGeneratorModel.getDistributorCode());
                statement.bindString(3, codeGeneratorModel.getScreenName());
                statement.bindString(4, codeGeneratorModel.getPrefix());
                statement.bindString(5, codeGeneratorModel.getSuffixYy());
                statement.bindString(6, String.valueOf(codeGeneratorModel.getSuffixNn()));
                statement.bindString(7, "N");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(TAG, e);
            }
        }
    }

    public void updateCodeGeneratorHistoryStatus(BaseDB baseDB, String distrCode, String screenName, String prefix, String suffixYy) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, "Y");

        String update = "distrCode = ? and screenName = ?  and prefix = ? and suffixYy = ?";

        String[] updateArgs = {distrCode, screenName, prefix, suffixYy};

        baseDB.getDb().update(TABLE_CODE_GENERATOR_HISTORY, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void updateCodeGenerator(BaseDB baseDB, String cmpCode, String distCode, List<CodeGeneratorModel> codeGeneratorModels) {
        String query = COLUMN_CMP_CODE + " = ? and " + COLUMN_DISTR_CODE + " = ? and " + COLUMN_SCREEN_NAME +" = ?";

        for(CodeGeneratorModel codeGeneratorModel : codeGeneratorModels) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PREFIX, codeGeneratorModel.getPrefix());
            values.put(COLUMN_SUFFIX_YY, codeGeneratorModel.getSuffixYy());
            values.put(COLUMN_SUFFIX_NN, codeGeneratorModel.getSuffixNn());
            values.put(COLUMN_UPLOAD, "N");

            String[] updateArgs = {codeGeneratorModel.getCompanyCode(), codeGeneratorModel.getDistributorCode(),
                    codeGeneratorModel.getScreenName()};

            baseDB.getDb().update(TABLE_CODE_GENERATOR, values, query, updateArgs);
        }
        baseDB.closeDb();
    }
}
