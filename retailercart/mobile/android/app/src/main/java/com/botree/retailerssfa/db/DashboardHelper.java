/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.botree.retailerssfa.db.query.IDBColumns;
import com.botree.retailerssfa.models.GSTModel;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.models.ProductsVO;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.RouteModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_OUT_OF_STOCK_PRODUCTS;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_FOCUS_BRAND;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_MUST_SELL_PRODUCTS;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_PRODUCTS;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_SCHEME_PRODUCT;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_ALL_OTHER_BEAT_RETAILERS_COUNT;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_ALL_PRODUCTS;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_ALL_ROUTES_TODAY_BEAT;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_BANNER_LIST;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_FOCUS_BRANDS;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_IS_SCREEN_AVALABLE;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_MESSAGE_COUNT;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_OUT_OF_STOCK_PRODUCTS;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_RETAILERS_COUNT_FOR_ROUTE;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_ROUTE_NAME;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_SELECT_ACTIVE_QUICK_ACTION_MENU;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_SELECT_CONFIG_DATA;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_SELECT_GST_MASTER;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_SELECT_MENU_USE_COUNTS;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_STOCK_AVAILABLE_PRODUCTS;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_STOCK_MUST_SELL_PRODUCTS;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_STOCK_SCHEME_PARTICULAR_PRODUCT;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_STOCK_SCHEME_PRODUCT;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_TODAY_BEAT_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_UPDATE_DISTR_CODE_AND_MSG_CODE;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.getQueryOrderedRoute;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.getQuerySelectAllScreensUseageCount;
import static com.botree.retailerssfa.db.query.DashboardQueryHelper.getQuqeyMessageListWithDeleteStatus;
import static com.botree.retailerssfa.db.query.IDBColumns.ATTACH_FILE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CHECKED;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DESCRIPTION_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_FILE_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_KEY_SCREEN_COUNT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_KEY_SCREEN_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_KG_RATE_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MESSAGE_DESC;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ORG_FILE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_PRICE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALESMAN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.DOWNLOAD_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.GST_STATE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.GST_STATE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.ICON_IS_ACTIVE;
import static com.botree.retailerssfa.db.query.IDBColumns.KEY_ICON_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.MSG_BODY;
import static com.botree.retailerssfa.db.query.IDBColumns.MSG_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.MSG_DELETE_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.MSG_DETAIL;
import static com.botree.retailerssfa.db.query.IDBColumns.MSG_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.MSG_TITLE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_AUTO_QUICK_ACTION_MENUS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CONFIGURATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_MESSAGES;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_NEW_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_QUICK_ACTION_MENUS;


class DashboardHelper {
private final String TAG = DashboardHelper.class.getSimpleName();

    DashboardHelper() {
        //to get the object
    }

    List<RouteModel> getAllRoutes(BaseDB baseDB, String distCode) {

        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        cursor = baseDB.getDb().rawQuery(QUERY_ALL_ROUTES_TODAY_BEAT, new String[]{distCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeVO = new RouteModel();

                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }



    List<RouteModel> getSalesmanAllRoutes(BaseDB baseDB, String cmpCode,String distCode,String salesmanCode) {

        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        cursor = baseDB.getDb().rawQuery(QUERY_ALL_ROUTES_TODAY_BEAT, new String[]{distCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeVO = new RouteModel();
                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }


    Map<String, String> getTodayBeatCount(SFADatabase sfaDatabase, String routeCode) {

        Map<String, String> map = new HashMap<>();

        String count;
        String routeName;

        Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_RETAILERS_COUNT_FOR_ROUTE, new String[]{routeCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            count = String.valueOf(cursor.getString(cursor.getColumnIndex("totcount")));
            routeName = getRouteName(sfaDatabase, routeCode);

            map.put("totCount", count);
            map.put("toDayRoute", routeName);
        }
        if (cursor != null) {
            cursor.close();
        }

        return map;
    }


    private String getRouteName(SFADatabase sfaDatabase, String routeCode) {

        String routeName = "";

        Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_ROUTE_NAME, new String[]{routeCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                routeName = cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME));

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeName;
    }

    String getTodayBeatRouteCode(SFADatabase sfaDatabase, String distrcode, String salesmancode, String state) {

        String currentRoute = "";

        Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_TODAY_BEAT_ROUTE_CODE, new String[]{distrcode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                currentRoute = cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE));

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return currentRoute;

    }


    List<MessageModel> getAllMessagesFromDB(SFADatabase sfaDatabase, String distrcode,
                                            String salesmanCode, Integer limit, boolean state) {

        List<MessageModel> messageModelList = new ArrayList<>();
        String query = getQuqeyMessageListWithDeleteStatus(limit);

        Cursor cursor;
        if (state) {
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distrcode, salesmanCode, "N"});
        } else {
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distrcode, salesmanCode, "Y"});
        }
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                MessageModel messageModel = new MessageModel();
                messageModel.setMsgCode(cursor.getString(cursor.getColumnIndex(IDBColumns.MSG_CODE)));
                messageModel.setMsgTitle(cursor.getString(cursor.getColumnIndex(IDBColumns.MSG_TITLE)).trim());
                messageModel.setMsgBody(cursor.getString(cursor.getColumnIndex(IDBColumns.MSG_BODY)).trim());
                messageModel.setMsgDate(cursor.getString(cursor.getColumnIndex(IDBColumns.COLUMN_DATE)));
                messageModel.setMsgStatus(cursor.getString(cursor.getColumnIndex(IDBColumns.MSG_STATUS)));
                messageModel.setDownloadStatus(cursor.getInt(cursor.getColumnIndex(IDBColumns.DOWNLOAD_STATUS)));
                messageModel.setMessageDetail(cursor.getString(cursor.getColumnIndex(IDBColumns.MSG_DETAIL)).trim());
                messageModel.setFileName(cursor.getString(cursor.getColumnIndex(IDBColumns.ATTACH_FILE_NAME)).trim());
                messageModelList.add(messageModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return messageModelList;
    }


    Integer getMessageCount(SFADatabase sfaDatabase) {

        int count = 0;
        Cursor cursor = sfaDatabase.getReadableDatabase().rawQuery(QUERY_MESSAGE_COUNT, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndex("count"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }

    void insertMessage(BaseDB baseDB, MessageModel messageModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, messageModel.getCmpCode());
        values.put(COLUMN_DISTR_CODE, messageModel.getDistCode());
        values.put(COLUMN_SALESMAN_CODE, messageModel.getSalesmanCode());
        values.put(MSG_CODE, messageModel.getMsgCode());
        values.put(MSG_TITLE, messageModel.getMsgTitle());
        values.put(MSG_BODY, messageModel.getMsgBody());
        values.put(MSG_DETAIL, messageModel.getMessageDetail());
        values.put(ATTACH_FILE_NAME, messageModel.getFileName());
        values.put(COLUMN_DATE, messageModel.getMsgDate());
        values.put(DOWNLOAD_STATUS, messageModel.getDownloadStatus());
        values.put(MSG_STATUS, messageModel.getMsgStatus());
        values.put(MSG_DELETE_STATUS, "N");
        // Inserting Row
        baseDB.getDb().insert(TABLE_MESSAGES, null, values);
    }

    void updateMessageStatusInDB(SFADatabase sfaDatabase, String distrcode, String msgCode) {

        ContentValues values = new ContentValues();
        values.put(MSG_STATUS, "0");

        String[] updateArgs = {distrcode, msgCode};

        sfaDatabase.getDb().update(TABLE_MESSAGES, values, QUERY_UPDATE_DISTR_CODE_AND_MSG_CODE, updateArgs);
        sfaDatabase.closeDb();

    }

    void updateMessageDownloadStatus(SFADatabase sfaDatabase, String distrcode, String msgCode, String downloadStatus) {

        ContentValues values = new ContentValues();
        values.put(DOWNLOAD_STATUS, downloadStatus);

        String[] updateArgs = {distrcode, msgCode};

        sfaDatabase.getDb().update(TABLE_MESSAGES, values, QUERY_UPDATE_DISTR_CODE_AND_MSG_CODE, updateArgs);
        sfaDatabase.closeDb();

    }

    void deleteMsgById(SFADatabase sfaDatabase, String distrcode, String msgCode) {

        String[] deleteArgs = {distrcode, msgCode};

        sfaDatabase.getDb().delete(TABLE_MESSAGES, QUERY_UPDATE_DISTR_CODE_AND_MSG_CODE, deleteArgs);
        sfaDatabase.closeDb();
    }

    List<ProductsVO> getAppStockProudcts(SFADatabase sfaDatabase, String distCode, int state) {
        List<ProductsVO> productsVOList = new ArrayList<>();

        String query;
        Cursor cursor;
        if (state == TYPE_STOCK_SCHEME_PRODUCT) {
            query = QUERY_STOCK_SCHEME_PRODUCT;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        } else if (state == TYPE_STOCK_MUST_SELL_PRODUCTS) {
            query = QUERY_STOCK_MUST_SELL_PRODUCTS;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        } else if (state == TYPE_STOCK_FOCUS_BRAND) {
            query = QUERY_FOCUS_BRANDS;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        } else if (state == TYPE_STOCK_PRODUCTS) {
            query = QUERY_STOCK_AVAILABLE_PRODUCTS;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        } else if (state == TYPE_OUT_OF_STOCK_PRODUCTS) {
            query = QUERY_OUT_OF_STOCK_PRODUCTS;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        } else {
            query = QUERY_ALL_PRODUCTS;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        }


        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                ProductsVO product = new ProductsVO();
                if (state == 1) {
                    product.setSchemeCodeCaps(cursor.getString(cursor.getColumnIndex("schemeCode")));
                } else {
                    product.setSchemeCodeCaps("");
                }
                product.setProdCodeCaps(cursor.getString(cursor.getColumnIndex("ProdCode")));
                product.setProdShortNameCaps(cursor.getString(cursor.getColumnIndex("ProdShortName")));
                product.setProdNameCaps(cursor.getString(cursor.getColumnIndex("ProdName")));
                product.setStockInHandCaps(Double.valueOf(cursor.getString(cursor.getColumnIndex("StockInHand"))));
                product.setProdHierValNameCaps(cursor.getString(cursor.getColumnIndex("ProdHierValName")));
                product.setProdHierValCodeCaps(cursor.getString(cursor.getColumnIndex("ProdHierValCode")));
                product.setmRPCaps(cursor.getDouble(cursor.getColumnIndex("MRP")));
                product.setDefaultUomidCaps(cursor.getString(cursor.getColumnIndex("DefaultUomid")));
                product.setSellPriceCaps(cursor.getDouble(cursor.getColumnIndex("SellPrice")));
                product.setPurchPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                product.setKgRate(cursor.getDouble(cursor.getColumnIndex(COLUMN_KG_RATE_CAPS)));
                productsVOList.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return productsVOList;
    }

    List<ProductsVO> getAppStockProudcts(SFADatabase sfaDatabase, String distCode, int state, String schemeCode) {
        List<ProductsVO> productsVOList = new ArrayList<>();

        String query;
        Cursor cursor;
//        if (state == TYPE_STOCK_SCHEME_PRODUCT) {
        if (schemeCode != null && !schemeCode.isEmpty()) {
            query = QUERY_STOCK_SCHEME_PARTICULAR_PRODUCT;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode, schemeCode});
        } else {
            query = QUERY_STOCK_SCHEME_PRODUCT;
            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
        }
//        } else if (state == TYPE_STOCK_MUST_SELL_PRODUCTS) {
//            query = QUERY_STOCK_MUST_SELL_PRODUCTS;
//            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
//        } else if (state == TYPE_STOCK_FOCUS_BRAND) {
//            query = QUERY_FOCUS_BRANDS;
//            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
//        } else if (state == TYPE_STOCK_PRODUCTS) {
//            query = QUERY_STOCK_AVAILABLE_PRODUCTS;
//            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
//        } else if (state == TYPE_OUT_OF_STOCK_PRODUCTS) {
//            query = QUERY_OUT_OF_STOCK_PRODUCTS;
//            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
//        } else {
//            query = QUERY_ALL_PRODUCTS;
//            cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distCode});
//        }


        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                ProductsVO product = new ProductsVO();
//                if (state == 1) {
                    product.setSchemeCodeCaps(cursor.getString(cursor.getColumnIndex("schemeCode")));
//                } else {
//                    product.setSchemeCodeCaps("");
//                }
                product.setProdCodeCaps(cursor.getString(cursor.getColumnIndex("prodCode")));
                product.setProdShortNameCaps(cursor.getString(cursor.getColumnIndex("ProdShortName")));
                product.setProdNameCaps(cursor.getString(cursor.getColumnIndex("prodName")));
                String soh = cursor.getString(cursor.getColumnIndex("availQty"));
                if(!isEmpty(soh)) {
                    product.setStockInHandCaps(Double.valueOf(soh));
                }
                product.setProdHierValNameCaps(cursor.getString(cursor.getColumnIndex("productHierPathName")));
                product.setProdHierValCodeCaps(cursor.getString(cursor.getColumnIndex("productHierPathCode")));
                product.setmRPCaps(cursor.getDouble(cursor.getColumnIndex("MRP")));
                product.setDefaultUomidCaps(cursor.getString(cursor.getColumnIndex("defaultUOM")));
                product.setSellPriceCaps(cursor.getDouble(cursor.getColumnIndex("SellPrice")));
                product.setPurchPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                productsVOList.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return productsVOList;
    }

    boolean isEmpty(String value){
        return  value == null || value.isEmpty();
    }

    void deleteQuickActions(SFADatabase sfaDatabase, String menuName) {

        String deleteMenu = KEY_ICON_NAME + "=?";
        String[] deleteArgs = {menuName};
        sfaDatabase.getDb().delete(TABLE_QUICK_ACTION_MENUS, deleteMenu, deleteArgs);
    }

    Boolean insertQuickActions(SFADatabase sfaDatabase, String menuName, boolean isAcive) {
        ContentValues values = new ContentValues();
        values.put(KEY_ICON_NAME, menuName);
        values.put(ICON_IS_ACTIVE, String.valueOf(isAcive));
        // Inserting Row
        sfaDatabase.getDb().insertWithOnConflict(TABLE_QUICK_ACTION_MENUS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }

    List<QuickViewVo> getActiveQuickMenus(SFADatabase sfaDatabase) {
        ArrayList<QuickViewVo> arrayList = new ArrayList<>();

        String query;

        query = "SELECT * FROM " + TABLE_QUICK_ACTION_MENUS;

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                QuickViewVo quickViewVo = new QuickViewVo();
                quickViewVo.setName(cursor.getString(cursor.getColumnIndex(KEY_ICON_NAME)));
                quickViewVo.setActiveIcon(cursor.getString(cursor.getColumnIndex(ICON_IS_ACTIVE)).equals(Boolean.TRUE.toString()));
                arrayList.add(quickViewVo);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return arrayList;
    }

    boolean getIsFavoriteMenu(SFADatabase sfaDatabase, String screenName) {

        boolean state = false;

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ACTIVE_QUICK_ACTION_MENU, new String[]{screenName, Boolean.TRUE.toString()});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            state = true;
        }
        if (c != null) {
            c.close();
        }
        return state;
    }

    void insertOrUpdateAutoQuickActions(SFADatabase sfaDatabase, String screenName, int screenCount) {

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_MENU_USE_COUNTS, new String[]{screenName});
        if (c != null && c.getCount() == 1) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY_SCREEN_COUNT, screenCount + 1);
            sfaDatabase.getDb().update(TABLE_AUTO_QUICK_ACTION_MENUS, values, COLUMN_KEY_SCREEN_NAME + "=?", new String[]{String.valueOf(screenName)});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY_SCREEN_NAME, screenName);
            values.put(COLUMN_KEY_SCREEN_COUNT, screenCount);
            sfaDatabase.getDb().insertWithOnConflict(TABLE_AUTO_QUICK_ACTION_MENUS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        if (c != null) {
            c.close();
        }
    }

    int getAutoQuickMenuCount(SFADatabase sfaDatabase, String screenName) {
        int count = 0;
        Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_SELECT_MENU_USE_COUNTS, new String[]{screenName});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndex(COLUMN_KEY_SCREEN_COUNT));
        }
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }

    List<QuickViewVo> getAllAutoQuickMenus(SFADatabase sfaDatabase, int menucount) {


        List<QuickViewVo> arrayList = new ArrayList<>();

        Cursor cursor = sfaDatabase.getDb().rawQuery(getQuerySelectAllScreensUseageCount(menucount), new String[]{});

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    QuickViewVo quickViewVo = new QuickViewVo();
                    quickViewVo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_KEY_SCREEN_NAME)));
                    quickViewVo.setIcons(cursor.getInt(cursor.getColumnIndex(COLUMN_KEY_SCREEN_COUNT)));
                    arrayList.add(quickViewVo);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        return arrayList;
    }

    List<GSTModel> getStateDataFromDB(SFADatabase sfaDatabase, String cmpCode) {

        List<GSTModel> gstModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_GST_MASTER, new String[]{cmpCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                GSTModel gstModel = new GSTModel();
                gstModel.setGstStateCode(c.getString(c.getColumnIndex(GST_STATE_CODE)).trim());
                gstModel.setGstStateName(c.getString(c.getColumnIndex(GST_STATE_NAME)).trim());

                gstModelList.add(gstModel);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return gstModelList;
    }

    String getConfigData(SFADatabase sfaDatabase, String keyFiled) {
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CONFIG_DATA, new String[]{keyFiled});

        if (c != null && c.getCount() > 0 && c.moveToFirst())
            return c.getString(c.getColumnIndex(COLUMN_CODE));
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return "";


    }

    String getConfigDescription(SFADatabase sfaDatabase, String keyFiled) {
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CONFIG_DATA, new String[]{keyFiled});

        if (c != null && c.getCount() > 0 && c.moveToFirst())
            return c.getString(c.getColumnIndex(COLUMN_DESCRIPTION_CAPS));
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return "";


    }

    String getNotifDescriptionBasedRetailer(SFADatabase sfaDatabase, String cmpCode,String  channelCode,String  subChannelCode, String groupCode, String classCode) {
        String query = "Select * from m_bulletin_board where cmpCode = ? and channelCode = ? and subChannelCode = ? and groupCode = ? and classCode = ? order by expiryDate desc limit 1 ";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, channelCode, subChannelCode, groupCode, classCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst())
            return c.getString(c.getColumnIndex(COLUMN_MESSAGE_DESC));
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return "";
    }

    Map<String, String> getConfigColors(SFADatabase sfaDatabase, String[] tableNames) {

        HashMap<String, String> hashMap = new HashMap<>();
        StringBuilder fields = new StringBuilder();
        for (int i = 0; i < tableNames.length; i++) {
            if (i == 0)
                fields.append(TABLE_NAME + " =? ");
            else
                fields.append(" OR " + TABLE_NAME + " =?");
        }
        String query = "SELECT * from " + TABLE_CONFIGURATION + " WHERE " + fields;

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(query, tableNames);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) do {
            hashMap.put(c.getString(c.getColumnIndex(TABLE_NAME)), c.getString(c.getColumnIndex(COLUMN_CODE)));
        } while (c.moveToNext());
        if (c != null) {
            c.close();
        }
        return hashMap;


    }

    ArrayList<String> fetchAllBannerList(SFADatabase sfaDatabase) {

        ArrayList<String> imgResources = new ArrayList<>();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_BANNER_LIST, null);
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                String fileName = c.getString(c.getColumnIndex(COLUMN_ORG_FILE_NAME));
                String fileType = c.getString(c.getColumnIndex(COLUMN_FILE_TYPE));
                String urlName = "redirect/getbannerimage/" + fileName + "/" + fileType;
                imgResources.add(urlName);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return imgResources;
    }

    Integer fetchNewOutletTotalCount(SFADatabase sfaDatabase, String strDistCode,
                                     String strSalesmanCode, String tableName) {

        int itemCount = 0;
        String query = "SELECT COUNT(*) as count from " + tableName + " where distrCode=? and salesmanCode=? and Upload=?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{strDistCode, strSalesmanCode, "N"});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {

            if (tableName.equalsIgnoreCase(TABLE_NEW_RETAILER)) {
                itemCount = c.getInt(c.getColumnIndex("count"));
            } else {
                itemCount = c.getCount();
            }
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return itemCount;
    }

    Integer getMappedCount(SFADatabase sfaDatabase, String tableName) {

        int itemCount = 0;
        String query = "SELECT COUNT(*) as count from " + tableName;

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {

                itemCount = c.getInt(c.getColumnIndex("count"));
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return itemCount;
    }


    String getDistrBalance(SFADatabase sfaDatabase, String tableName, String columnName, String whereColumnName, String distrCode) {

        String strBalnce = "0.0";
        try {
            String query = "SELECT " + columnName + " from " + tableName + " where " + whereColumnName + "=?";
            Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                strBalnce = c.getString(c.getColumnIndex(columnName));
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            Log.e("getDistrBalance", "getDistrBalance: " + e.getMessage(), e);
        }

        sfaDatabase.closeDb();
        return strBalnce;
    }


    /**
     * Get all the route detail based on below params
     *
     * @param baseDB       db instance
     * @param distCode     device configure distributor code
     * @param salesmanCode distributor mapped salesman code
     * @return all the retailer
     */
    List<RetailerVO> getOrderedRoute(BaseDB baseDB, String distCode, String salesmanCode, String screenName) {

        List<RetailerVO> routeList = new ArrayList<>();
        Cursor cursor;
        String query = getQueryOrderedRoute(screenName);

        cursor = baseDB.getDb().rawQuery(query, new String[]{distCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RetailerVO routeVO = new RetailerVO();

                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)));
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)));
                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    List<Map<String, String>> getOtherBeatCount(SFADatabase sfaDatabase, String distCode) {

        List<Map<String, String>> mapList = new ArrayList<>();

        Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_ALL_OTHER_BEAT_RETAILERS_COUNT, new String[]{distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {

            do {

                String count;
                String routeName;
                Map<String, String> map = new HashMap<>();
                count = String.valueOf(cursor.getString(cursor.getColumnIndex("totcount")));
                routeName = getRouteName(sfaDatabase, cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)));
                map.put("totCount", count);
                map.put("toDayRoute", routeName);

                mapList.add(map);
            } while (cursor.moveToNext());

        }
        if (cursor != null) {
            cursor.close();
        }

        return mapList;
    }

    boolean isScreenAvailable(SFADatabase sfaDatabase, String moduleName, String screenName) {
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_IS_SCREEN_AVALABLE, new String[]{moduleName, screenName});
        boolean isScreenAvail = false;
        if (c != null && c.getCount() > 0 && c.moveToFirst())
            isScreenAvail = Boolean.parseBoolean(c.getString(c.getColumnIndex(COLUMN_CHECKED)));
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return isScreenAvail;
    }

    public String[] fetchSalesReturnSalableUnsalableCount(SFADatabase sfaDatabase, String tableSalesReturn, String distCode, String salesmanCode) {


        String salableCount = "0";
        String unSalableCount = "0";
        Cursor c = null;
        try {

            String strQuery = "Select SUM(SalQty) as SalQtyCount, SUM(UnSalQty) as UnSalQtyCount from " + tableSalesReturn + " WHERE distrCode=? AND salesmanCode=?";
            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                salableCount = c.getString(c.getColumnIndex("SalQtyCount"));
                unSalableCount = c.getString(c.getColumnIndex("UnSalQtyCount"));
                if (salableCount == null) {
                    salableCount = "0";
                }
                if (unSalableCount == null) {
                    unSalableCount = "0";
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "fetchSalesReturnSalableUnsalableCount: " + e.getMessage(), e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            sfaDatabase.closeDb();
        }
        return new String[]{salableCount, unSalableCount};
    }

    public String fetchNewOutletCount(SFADatabase sfaDatabase, String strDistCode,
                                      String strSalesmanCode, String tableName) {

        String itemCount = "0";
        String query;
        if (tableName.equalsIgnoreCase(TABLE_NEW_RETAILER)) {
            query = "SELECT COUNT(*) as count from " + tableName + " where distrCode=? and salesmanCode=? and customerType='N'";
        } else {
            query = "SELECT * from " + tableName + " where distrCode=? and salesmanCode=? group By invoiceNo";
        }
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{strDistCode, strSalesmanCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {

            if (tableName.equalsIgnoreCase(TABLE_NEW_RETAILER)) {
                itemCount = c.getString(c.getColumnIndex("count"));
            } else {
                itemCount = String.valueOf(c.getCount());
            }
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return itemCount;
    }


}
