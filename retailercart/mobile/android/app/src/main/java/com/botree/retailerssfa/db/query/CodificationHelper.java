package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.AddressModel;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.BaseDB.checkString;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_APPROVAL_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CHANNEL_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CLASS_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CONTACT_PERSON;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_COVERAGE_SEQUENCE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CREDIT_BILLS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CREDIT_DAYS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CREDIT_LIMIT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_DEFAULT_SHIP_ADDR;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_DEFAULT_SHIP_GEOHIERPATH;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_ADDR1;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_ADDR2;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_ADDR3;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_CITY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_PHONENO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DRUG_LIC_EXPIRY_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DRUG_LIC_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_EMAIL_ID;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_FSSAI_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_GROUP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LATITUDE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LONGITUDE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MOBILE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PAN_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PARENT_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PHONE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PIN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_RETAILER_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_STORE_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SUB_CHANNEL_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TEMP_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TEMP_CUSTOMER_SHIP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TEMP_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.GSTNO;
import static com.botree.retailerssfa.db.query.IDBColumns.GST_STATE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_TEMP_CUSTOMER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_TEMP_CUSTOMER_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_TEMP_CUSTOMER_SHIP_ADDRESS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_TEMP_ROUTE;
/*
 *Created by Godlin Josheela Rani S on 04/11/19.
 */

public class CodificationHelper {

    public static CodificationHelper getInstance() {
        return ourInstance;
    }

    private CodificationHelper() {
    }

    String TAG = CodificationHelper.class.getSimpleName();
    private static final CodificationHelper ourInstance = new CodificationHelper();
    private static final String QUERY_GET_TEMP_ROUTE_COUNT = "SELECT * FROM " + TABLE_TEMP_ROUTE + "  where routeCode IS NULL OR routeCode = ''";
    private static final String QUERY_GET_TEMP_CUSTOMER_COUNT = "SELECT * FROM " + TABLE_TEMP_CUSTOMER + "  where customerCode IS NULL OR customerCode = ''";
    private static final String QUERY_GET_TEMP_CUSTOMER_ROUTE_COUNT = "SELECT * FROM " + TABLE_TEMP_CUSTOMER_ROUTE + "  where (routeCode IS NULL OR routeCode = '') or (customerCode IS NULL OR customerCode = '')";
    private static final String QUERY_GET_TEMP_CUSTOMER_SHIP_ADDR_COUNT = "SELECT * FROM " + TABLE_TEMP_CUSTOMER_SHIP_ADDRESS + "  where (customerCode IS NULL OR customerCode = '')";

    public boolean isCodificationAvailable(SFADatabase database) {
        int count = database.getTempRouteCount() + database.getTempCustomerCount() +
                database.getTempCustomerRouteCount() + database.getTempCustomerShipAddrCount();
        return count > 0;
    }

    public int getTempRouteCount(BaseDB baseDB) {
        int count = 0;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_GET_TEMP_ROUTE_COUNT, null);
            if (cm != null) {
                count = cm.getCount();
                cm.close();
            }
            baseDB.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getTempRouteCount: " + e.getMessage(), e);
            return count;
        }
        Log.d(TAG, "getTempRouteCount: " + count);

        return count;
    }

    public int getTempCustomerCount(BaseDB baseDB) {
        int count = 0;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_GET_TEMP_CUSTOMER_COUNT, null);
            if (cm != null) {
                count = cm.getCount();
                cm.close();
            }
            baseDB.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getTempCustomerCount: " + e.getMessage(), e);
            return count;
        }
        Log.d(TAG, "getTempCustomerCount: " + count);

        return count;
    }


    public int getTempCustomerShipAddrCount(BaseDB baseDB) {
        int count = 0;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_GET_TEMP_CUSTOMER_SHIP_ADDR_COUNT, null);
            if (cm != null) {
                count = cm.getCount();
                cm.close();
            }
            baseDB.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getTempCustomerShipAddrCount: " + e.getMessage(), e);
            return count;
        }
        Log.d(TAG, "getTempCustomerShipAddrCount: " + count);

        return count;
    }


    public int getTempCustomerRouteCount(BaseDB baseDB) {
        int count = 0;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_GET_TEMP_CUSTOMER_ROUTE_COUNT, null);
            if (cm != null) {
                count = cm.getCount();
                cm.close();
            }
            baseDB.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getTempCustomerRouteCount: " + e.getMessage(), e);
            return count;
        }
        Log.d(TAG, "getTempCustomerRouteCount: " + count);

        return count;
    }

    public List<RouteModel> getAllTempRoutes(BaseDB baseDB, String distCode) {

        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_TEMP_ROUTE + " WHERE distrCode=?  AND routeCode IS NULL OR routeCode = ''";
        cursor = baseDB.getDb().rawQuery(query, new String[]{distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeModel = new RouteModel();

                routeModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                routeModel.setDistributorCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                routeModel.setTempRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_TEMP_ROUTE_CODE)).trim());
                routeModel.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeModel.setRouteType(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_TYPE)).trim());
                routeModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeModel.setUploadFlag(cursor.getString(cursor.getColumnIndex(COLUMN_UPLOAD)).trim());

                routeList.add(routeModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<CustomerModel> getAllTempCustomers(BaseDB baseDB, String distCode) {

        List<CustomerModel> routeList = new ArrayList<>();
        Cursor c;
        String query = "SELECT * From " + TABLE_TEMP_CUSTOMER + " WHERE distrCode=? AND customerCode IS NULL OR customerCode = ''";
        c = baseDB.getDb().rawQuery(query, new String[]{distCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                CustomerModel retailerModel = new CustomerModel();
                retailerModel.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                retailerModel.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                retailerModel.setTempCustomerCode(checkString(c, COLUMN_TEMP_CUSTOMER_CODE));
                retailerModel.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                retailerModel.setPinCode(checkString(c, COLUMN_PIN_CODE));
                retailerModel.setPhoneNo(checkString(c, COLUMN_PHONE_NO));
                retailerModel.setMobileNo(checkString(c, COLUMN_MOBILE_NO));
                retailerModel.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                retailerModel.setEmailID(checkString(c, COLUMN_EMAIL_ID));
                retailerModel.setDayOff(c.getInt(c.getColumnIndex(COLUMN_RETAILER_STATUS)));
                retailerModel.setFssaiNo(checkString(c, COLUMN_FSSAI_NO));
                retailerModel.setDrugLicNo(checkString(c, COLUMN_DRUG_LIC_NO));
                retailerModel.setDrugLicExpiryDate(checkString(c, COLUMN_DRUG_LIC_EXPIRY_DATE));
                retailerModel.setCreditBills(c.getInt(c.getColumnIndex(COLUMN_CREDIT_BILLS)));
                retailerModel.setCreditDays(c.getInt(c.getColumnIndex(COLUMN_CREDIT_DAYS)));
                retailerModel.setCreditLimit(c.getInt(c.getColumnIndex(COLUMN_CREDIT_LIMIT)));
                retailerModel.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                retailerModel.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                retailerModel.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                retailerModel.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                retailerModel.setStoreType(checkString(c, COLUMN_STORE_TYPE));
                retailerModel.setParentCustomerCode(checkString(c, COLUMN_PARENT_CUSTOMER_CODE));
                retailerModel.setLatitude(checkString(c, COLUMN_LATITUDE));
                retailerModel.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                retailerModel.setLongitude(checkString(c, COLUMN_LONGITUDE));
                retailerModel.setCustomerType(checkString(c, COLUMN_CUSTOMER_TYPE));
                retailerModel.setGstTinNo(checkString(c, GSTNO));
                retailerModel.setPanNo(checkString(c, COLUMN_PAN_NO));
                retailerModel.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));
                retailerModel.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));

                routeList.add(retailerModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return routeList;
    }

    public List<AddressModel> getAllTempCustomerShipAddress(BaseDB baseDB, String cmpCode, String distCode) {
        String query;

        query = "SELECT *\n" +
                "FROM " + TABLE_TEMP_CUSTOMER_SHIP_ADDRESS +
                " WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "' AND " +
                "(customerCode IS NULL OR customerCode = '')";

        List<AddressModel> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = baseDB.getDb().rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    AddressModel retailerVO = new AddressModel();
                    retailerVO.setCmpCode(cmpCode);
                    retailerVO.setDistrCode(distCode);
                    retailerVO.setTempCustomerCode(checkString(c, COLUMN_TEMP_CUSTOMER_CODE));
                    retailerVO.setTempShipCode(checkString(c, COLUMN_TEMP_CUSTOMER_SHIP_CODE));
                    retailerVO.setShippingAddress1(checkString(c, COLUMN_CUSTOMER_SHIP_ADDR1));
                    retailerVO.setShippingAddress2(checkString(c, COLUMN_CUSTOMER_SHIP_ADDR2));
                    retailerVO.setShippingAddress3(checkString(c, COLUMN_CUSTOMER_SHIP_ADDR3));
                    retailerVO.setShipCityTown(checkString(c, COLUMN_CUSTOMER_SHIP_CITY));
                    retailerVO.setShippingGeoHierPath(checkString(c, COLUMN_CUSTOMER_DEFAULT_SHIP_GEOHIERPATH));
                    retailerVO.setShippingState(checkString(c, GST_STATE_CODE));
                    retailerVO.setShippingPhoneNumber(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                    retailerVO.setIsDefault(checkString(c, COLUMN_CUSTOMER_DEFAULT_SHIP_ADDR));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setShipCode(checkString(c, COLUMN_CUSTOMER_SHIP_CODE));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getAllTempCustomerShipAddress: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<RouteModel> getAllTempCustomerRoute(BaseDB baseDB, String cmpCode, String distCode) {
        String query;

        query = "SELECT *\n" +
                "FROM " + TABLE_TEMP_CUSTOMER_ROUTE +
                " WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'  AND " +
                "(routeCode IS NULL OR routeCode = '') or " +
                "(customerCode IS NULL OR customerCode = '')";

        List<RouteModel> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = baseDB.getDb().rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    RouteModel retailerVO = new RouteModel();
                    retailerVO.setCompanyCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistributorCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setTempCustomerCode(checkString(c, COLUMN_TEMP_CUSTOMER_CODE));
                    retailerVO.setTempRouteCode(checkString(c, COLUMN_TEMP_ROUTE_CODE));
                    retailerVO.setCoverageSeq(checkString(c, COLUMN_COVERAGE_SEQUENCE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setRouteCode(checkString(c, COLUMN_ROUTE_CODE));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getAllTempCustomerRoute: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public void updateTempCodificationTables(BaseDB baseDB, List<RouteModel> tempRoutes, List<CustomerModel> tempCustomers,
                                             List<AddressModel> tempCustomerAddress, List<RouteModel> tempCustomerRoutes) {

        String UPDATE_TEMP_ROUTE_QUERY = "distrCode = ? and tempRouteCode = ?";
        String UPDATE_TEMP_CUSTOMER_QUERY = "distrCode = ? and tempCustomerCode = ?";
        String UPDATE_TEMP_CUSTOMER_SHIP_ADDR_QUERY = "distrCode = ? and tempCustomerCode = ? and tempCustomerShipCode = ? ";
        String UPDATE_TEMP_CUSTOMER_ROUTE_QUERY = "distrCode = ? and tempRouteCode = ? and tempCustomerCode = ?";

        ContentValues values;
        for (RouteModel model : tempRoutes) {
            values = new ContentValues();
            values.put(COLUMN_ROUTE_CODE, model.getRouteCode());
            values.put(COLUMN_UPLOAD, "Y");

            String[] updateArgs = {model.getDistributorCode(), model.getTempRouteCode()};

            baseDB.getDb().update(TABLE_TEMP_ROUTE, values, UPDATE_TEMP_ROUTE_QUERY, updateArgs);
        }

        for (CustomerModel model : tempCustomers) {

            values = new ContentValues();
            values.put(COLUMN_CUSTOMER_CODE, model.getCustomerCode());
            values.put(COLUMN_UPLOAD, "Y");

            String[] updateArgs = {model.getDistrCode(), model.getTempCustomerCode()};

            baseDB.getDb().update(TABLE_TEMP_CUSTOMER, values, UPDATE_TEMP_CUSTOMER_QUERY, updateArgs);
        }

        for (RouteModel model : tempCustomerRoutes) {

            values = new ContentValues();
            values.put(COLUMN_CUSTOMER_CODE, model.getCustomerCode());
            values.put(COLUMN_ROUTE_CODE, model.getRouteCode());
            values.put(COLUMN_UPLOAD, "Y");

            String[] updateArgs = {model.getDistributorCode(), model.getTempRouteCode(), model.getTempCustomerCode()};

            baseDB.getDb().update(TABLE_TEMP_CUSTOMER_ROUTE, values, UPDATE_TEMP_CUSTOMER_ROUTE_QUERY, updateArgs);
        }

        for (AddressModel model : tempCustomerAddress) {

            values = new ContentValues();
            values.put(COLUMN_CUSTOMER_CODE, model.getCustomerCode());
            values.put(COLUMN_CUSTOMER_SHIP_CODE, model.getShipCode());
            values.put(COLUMN_UPLOAD, "Y");

            String[] updateArgs = {model.getDistrCode(), model.getTempCustomerCode(), model.getTempShipCode()};

            baseDB.getDb().update(TABLE_TEMP_CUSTOMER_SHIP_ADDRESS, values, UPDATE_TEMP_CUSTOMER_SHIP_ADDR_QUERY, updateArgs);
        }
        baseDB.closeDb();
    }

    public void insertCustomerDetail(SFADatabase sfaDatabase, List<CustomerModel> retailerList) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        for (CustomerModel model : retailerList) {
            SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, model.getCmpCode());
            statement.bindString(2, model.getDistrCode());
            statement.bindString(3, model.getCustomerCode());
            statement.bindString(4, model.getCustomerCode());
            statement.bindString(5, model.getCustomerName());
            statement.bindString(6, model.getPinCode());
            statement.bindString(7, model.getPhoneNo());
            statement.bindString(8, model.getMobileNo());
            statement.bindString(9, model.getContactPerson());
            statement.bindString(10, model.getEmailID());
            statement.bindString(11, String.valueOf(model.getDayOff()));
            statement.bindString(12, model.getRetailerStatus());
            statement.bindString(13, model.getFssaiNo());
            statement.bindString(14, model.getDrugLicNo());
            statement.bindString(15, model.getDrugLicExpiryDate());
            statement.bindString(16, String.valueOf(model.getCreditBills()));
            statement.bindString(17, String.valueOf(model.getCreditDays()));
            statement.bindString(18, String.valueOf(model.getCreditLimit()));
            statement.bindString(19, String.valueOf(model.getCashDiscPerc()));
            statement.bindString(20, model.getChannelCode());
            statement.bindString(21, model.getSubChannelCode());
            statement.bindString(22, model.getGroupCode());
            statement.bindString(23, model.getClassCode());
            statement.bindString(24, model.getStoreType());
            statement.bindString(25, model.getParentCustomerCode());
            statement.bindString(26, model.getLatitude());
            statement.bindString(27, model.getLongitude());
            statement.bindString(28, model.getCustomerType());
            statement.bindString(29, model.getGstTinNo());
            statement.bindString(30, model.getPanNo());
            statement.bindString(31, model.getApprovalStatus());
            statement.bindString(32, DateUtil.getCurrentYearMonthDate());
            statement.bindString(33, "N");
            statement.execute();
        }
    }
}
