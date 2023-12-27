package com.botree.retailerssfa.db.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.FreeProdModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_SCHEME_DETAIL_BY_PROD_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.CGST_PERCENT;
import static com.botree.retailerssfa.db.query.IDBColumns.CGST_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_AVAIL_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_BATCH_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_BUDGET;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_BUDGET_OS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CONVERSION_FACTOR;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_END_TIME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_GZIP_SCHEME_DATA;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ISMANDATORY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_IS_ACTIVE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_IS_SKU_LEVEL;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_IS_UNLIMITED;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LAST_ORD_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MRP_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ORDER_QTY_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ORDER_VALUE_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PRIM_DISC_ORDER_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_CODE_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_NAME_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_SHORT_NAME_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SELL_PRICE_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_START_TIME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UOM_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UOM_ID;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UTILIZED_AMOUNT;
import static com.botree.retailerssfa.db.query.IDBColumns.COMBI;
import static com.botree.retailerssfa.db.query.IDBColumns.DISCOUNT_AMOUNT;
import static com.botree.retailerssfa.db.query.IDBColumns.DISCOUNT_PERCENTAGE;
import static com.botree.retailerssfa.db.query.IDBColumns.FOR_EVERY;
import static com.botree.retailerssfa.db.query.IDBColumns.FREE_PROD_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.FREE_PROD_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.FREE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.IGST_PERCENT;
import static com.botree.retailerssfa.db.query.IDBColumns.IGST_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.MIN_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.NET_AMOUNT;
import static com.botree.retailerssfa.db.query.IDBColumns.ORDER_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_BASE;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_DESC;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_FROM_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_PAYOUT_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_SLAB_FROM;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_SLAB_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_SLAB_PAYOUT;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_SLAB_RANGE;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_SLAB_TO;
import static com.botree.retailerssfa.db.query.IDBColumns.SCHEME_TO_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.SGST_PERCENT;
import static com.botree.retailerssfa.db.query.IDBColumns.SGST_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING_APPLIED_SCHEME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_COMPANY_DETAIL;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CONFIRM_ORDER_APPLIED_SCHEME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_DISTRIBUTOR;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOADING_STOCK;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDERED_APPLIED_SCHEME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_CONFIRMATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_BATCH_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_DEFINITION_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_SLAB_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_SLAB_PRODUCT_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_ZIP_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_STOCK_ON_HAND;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TAX_PER_PRODUCT;
import static com.botree.retailerssfa.db.query.IDBColumns.UTGST_PERCENT;
import static com.botree.retailerssfa.db.query.IDBColumns.UTGST_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ISMANDATORY;

public class OrderBookingSchemeHelper {

    public OrderBookingSchemeHelper() {
    }

    public List<SchemeModel> getCombiProductList(SFADatabase sfaDatabase, String distrCode, String schemeCode) {

        List<SchemeModel> schemeModels = new ArrayList<>();

        String query = "select sc.schemeCode, sc.slabNo, sc.prodCode, sc.minValue, sc.ismandatory,sd.schemeBase,sd.payoutType, sd.isSkuLevel, sd.isActive, sd.combi\n" +
                " from m_schemeCombiProducts sc left join m_schemeDefinitionList sd on sc.schemeCode = sd.schemeCode where sc.schemeCode = ? and " +
                "sd.schemeToDt >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{schemeCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SchemeModel schemeModelsModel = new SchemeModel();
                schemeModelsModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)).trim());
                schemeModelsModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)).trim());
                schemeModelsModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                schemeModelsModel.setMinValue(c.getInt(c.getColumnIndex(MIN_VALUE)));
                schemeModelsModel.setIsMandatory((c.getString(c.getColumnIndex(COLUMN_ISMANDATORY)).trim()));
                schemeModelsModel.setSchemeBase((c.getString(c.getColumnIndex(SCHEME_BASE)).trim()));
                schemeModelsModel.setPayoutType((c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)).trim()));
                schemeModelsModel.setIsSkuLevel((c.getString(c.getColumnIndex(COLUMN_IS_SKU_LEVEL)).trim()));
                schemeModelsModel.setIsActive((c.getString(c.getColumnIndex(COLUMN_IS_ACTIVE)).trim()));
                schemeModelsModel.setCombi((c.getString(c.getColumnIndex(COMBI)).trim()));

                schemeModels.add(schemeModelsModel);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return schemeModels;
    }


    public List<SchemeModel> getSingleSchemeProdList(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode,
                                                     String customerCode, OrderBookingVO bookingVO, String tableType) {

        List<SchemeModel> schemeModels = new ArrayList<>();
//        if(bookingVO.getPrimDiscOrderValue().doubleValue()>0) {
//            double primaryDiscValue = bookingVO.getOrderValue().doubleValue() - bookingVO.getPrimDiscOrderValue().doubleValue();
//            if (Math.ceil(primaryDiscValue) > 0) {
//                schemeModels.add(new SchemeModel("Primary Discount", "", primaryDiscValue));
//            }
//        }

        if (bookingVO.getTotQty() > 0) {
            String query;
            if (tableType.equalsIgnoreCase(TABLE_BILLING)) {
                query = " SELECT  os.distrCode,os.customerCode,os.schemeCode,sd.schemeDescription,os.prodCode,os.slabNo," +
                        "os.freeProdCode,os.freeProdName,os.freeQty,os.discountAmount," +
                        "os.discountPercen FROM   " + TABLE_BILLING_APPLIED_SCHEME + " os  \n" +
                        "INNER JOIN " + TABLE_SCHEME_DEFINITION_LIST + " sd ON os.schemeCode=sd.schemeCode WHERE    os.distrCode=?\n" +
                        "AND    os.salesmanCode= ?\n" +
                        "AND    os.routeCode= ?\n" +
                        "AND    os.customerCode= ?\n" +
                        "AND    os.prodCode= ?\n" +
                        "AND    os.BatchCode= ?\n" +
                        "AND    os.orderNo= ?\n" +
                        "AND    os.upload = 'N' AND os.discountAmount > 0.0";
            } else {
                query = "SELECT  os.distrCode,os.customerCode,os.schemeCode,sd.schemeDescription,os.prodCode,os.slabNo,os.freeProdCode,\n" +
                        "os.freeProdName,os.freeQty,os.discountAmount,os.discountPercen FROM   t_orderAppliedSchemes os  \n" +
                        "    INNER JOIN m_schemeDefinitionList sd ON os.schemeCode=sd.schemeCode WHERE    os.distrCode=?\n" +
                        "    AND    os.cmpCode= ?\n" +
                        "    AND    os.customerCode= ?\n" +
                        "    AND    os.prodCode= ?\n" +
                        "    AND    os.BatchCode= ?\n" +
                        "    AND    os.orderNo= ?\n" +
                        "    AND    os.upload = 'N' AND os.discountAmount > 0.0";
            }

            Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{bookingVO.getDistrCode(), bookingVO.getCmpCode(), bookingVO.getRetailerCode(),
                    bookingVO.getProdCode(), bookingVO.getProdBatchCode(), bookingVO.getOrderNo()});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    SchemeModel schemeModelsModel = new SchemeModel();
                    schemeModelsModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                    schemeModelsModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                    schemeModelsModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                    schemeModelsModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)).trim());
                    schemeModelsModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)).trim());
                    schemeModelsModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)).trim());
                    schemeModelsModel.setFreeProdCode(c.getString(c.getColumnIndex(FREE_PROD_CODE)).trim());
                    schemeModelsModel.setFreeProdName(c.getString(c.getColumnIndex(FREE_PROD_NAME)).trim());
                    schemeModelsModel.setFreeQty(Integer.valueOf(c.getString(c.getColumnIndex(FREE_QTY)).trim()));
                    schemeModelsModel.setFlatAmount(Double.parseDouble(c.getString(c.getColumnIndex(DISCOUNT_AMOUNT)).trim()));
                    if (!c.getString(c.getColumnIndex(DISCOUNT_PERCENTAGE)).isEmpty())
                        schemeModelsModel.setPercentage(Double.valueOf(c.getString(c.getColumnIndex(DISCOUNT_PERCENTAGE))).intValue());

                    schemeModels.add(schemeModelsModel);

                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
        }
        return schemeModels;
    }


    public List<OrderBookingVO> getOrderedFreeProductFromDB(SFADatabase sfaDatabase,
                                                            String prefDistrcode,
                                                            String prefSalesmancode,
                                                            String prefRoutecode,
                                                            String retrCode, String tableName) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        String query;
        Cursor c;
        if (tableName != null && tableName.equalsIgnoreCase(TABLE_BILLING)) {

            query = " SELECT o.ProdCode,o.BatchCode,\n"
                    + "       p.ProdName,\n" +
                    "       p.ProdShortName,\n" +
                    "       p.SellPrice,\n" +
                    "       p.MRP,\n" +
                    "       o.freeProdCode,\n" +
                    "       o.freeProdName,\n" +
                    "       o.freeQty \n" +
                    "FROM   " + TABLE_BILLING_APPLIED_SCHEME + " o,\n" +
                    "       " + TABLE_VANSALES_PRODUCTS + " p \n" +
                    "WHERE  o.prodCode=p.ProdCode\n" +
                    "AND    o.BatchCode=p.BatchCode \n" +
                    "AND    o.distrCode=p.DistrCode \n" +
                    "AND    p.salesmanCode=o.salesmanCode\n" +
                    "AND    o.distrCode=?\n" +
                    "AND    o.salesmanCode= ?\n" +
                    "AND    o.routeCode= ?\n" +
                    "AND    o.customerCode= ?\n" +
                    "AND    o.freeQty > 0";
//                    "AND    upload = 'N'";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{prefDistrcode, prefSalesmancode,
                    prefRoutecode, retrCode});

        } else if (tableName != null && tableName.equalsIgnoreCase(TABLE_ORDER_CONFIRMATION)) {
            query = " SELECT o.ProdCode,o.BatchCode,\n" +
                    "       p.ProdName,\n" +
                    "       p.ProdShortName,\n" +
                    "       p.SellPrice,\n" +
                    "       p.MRP,\n" +
                    "       o.freeProdCode,\n" +
                    "       o.freeProdName,\n" +
                    "       o.freeQty \n" +
                    "FROM   " + TABLE_CONFIRM_ORDER_APPLIED_SCHEME + " o,\n" +
                    "       " + TABLE_PRODUCTS + " p \n" +
                    "WHERE  o.prodCode=p.ProdCode\n" +
                    "AND    o.BatchCode=p.BatchCode \n" +
                    "AND    o.distrCode=p.DistrCode \n" +
                    "AND    p.salesmanCode=o.salesmanCode\n" +
                    "AND    o.distrCode=?\n" +
                    "AND    o.salesmanCode= ?\n" +
                    "AND    o.routeCode= ?\n" +
                    "AND    o.customerCode= ?\n" +
                    "AND    o.freeQty > 0";
//                    "AND    upload = 'N'";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{prefDistrcode, prefSalesmancode,
                    prefRoutecode, retrCode});
        } else {
            query = "SELECT o.ProdCode,\n" +
                    "        o.BatchCode,\n" +
                    "         p.ProdName,\n" +
                    "         p.ProdShortName,\n" +
                    "         pbm.SellPrice,\n" +
                    "         pbm.MRP,\n" +
                    "         o.freeProdCode,\n" +
                    "         o.freeProdName,\n" +
                    "         o.freeQty\n" +
                    "FROM t_orderAppliedSchemes o, m_product_master p\n" +
                    "LEFT JOIN m_product_batch_master pbm\n" +
                    "    ON pbm.prodCode = p.prodCode\n" +
                    "        AND pbm.cmpCode = p.cmpCode\n" +
                    "WHERE o.prodCode=p.ProdCode\n" +
                    "        AND o.BatchCode=pbm.ProdBatchCode\n" +
                    "        AND o.distrCode=pbm.DistrCode\n" +
                    "        AND o.freeQty > 0 and o.orderNo like '%" + retrCode + "'";
//                    "AND    upload = 'N'";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{});
        }

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex("prodCode")).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex("BatchCode")).trim());
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex("ProdShortName")).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex("ProdName")).trim());
                orderBookingVO.setFreeProdCode(c.getString(c.getColumnIndex("freeProdCode")).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex("freeProdName")).trim());
                orderBookingVO.setSellPrice(BigDecimal.valueOf(c.getDouble(c.getColumnIndex("SellPrice"))));

                orderBookingVO.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex("MRP"))));
                orderBookingVO.setOrderFreeQty(c.getInt(c.getColumnIndex("freeQty")));
                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }


    public List<SchemeModel> fetchSuggestedFreeProdList(SFADatabase sfaDatabase, SchemeModel schemeModelList, double orderValue, String className) {

        List<SchemeModel> freeSlabProductList = new ArrayList<>();
        String productTable = TABLE_PRODUCTS;
        if (className.equalsIgnoreCase(TABLE_VANSALES_PRODUCTS)) {
            productTable = TABLE_VANSALES_PRODUCTS;
        }
        String brandQuery = "select  sl.schemeCode as schemeCode,sl.slabNo as slabNo,sl. slabFrom as slabFrom,sl.slabTo as slabTo,"
                + "sl.payout as payout,sl.slabRange as slabRange," +
                "sp.qty as qty,sp.prodCode as prodCode ,p.ProdShortName as ProdShortName,sp.isMandatory as isMandatory from\n" +
                "" + TABLE_SCHEME_SLAB_LIST + " sl \n" +
                "inner join " + TABLE_SCHEME_SLAB_PRODUCT_LIST + " sp ON sp.cmpCode=sl.cmpCode AND sp.distrCode=sl.distrCode  \n" +
                "AND sp.schemeCode=sl.schemeCode AND sp.slabNo=sl.slabNo\n" +
                "inner join " + productTable + " p ON p.cmpCode=sl.cmpCode AND p.distrCode=sl.distrCode  \n" +
                "AND  p.productHierPath like ( '%/' || sp.prodCode ||  '/%' )" +
                "Where sl.schemeCode=? AND slabFrom > ? ORDER BY sp.isMandatory DESC";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeModelList.getSchemeCode(), String.valueOf(orderValue)});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setSlabTo(c.getDouble(c.getColumnIndex(SCHEME_SLAB_TO)));
                schemeModel.setSlabFrom(Double.valueOf(c.getString(c.getColumnIndex(SCHEME_SLAB_FROM))));
                schemeModel.setSlabRange(c.getString(c.getColumnIndex(SCHEME_SLAB_RANGE)));
                schemeModel.setPayoutValue(c.getDouble(c.getColumnIndex(SCHEME_SLAB_PAYOUT)));
                schemeModel.setFreeProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                schemeModel.setFreeQty(Integer.valueOf(c.getString(c.getColumnIndex(SCHEME_QTY))));
                schemeModel.setIsMandatory(c.getString(c.getColumnIndex(COLUMN_ISMANDATORY)));
                freeSlabProductList.add(schemeModel);
            }
            c.close();
        }

        return freeSlabProductList;

    }


    public List<SchemeModel> getAllFreeProdList(SFADatabase sfaDatabase, List<SchemeModel> schemeModelList) {

        List<SchemeModel> freeSlabProductList = new ArrayList<>();

        for (int i = 0; i < schemeModelList.size(); i++) {

            freeSlabProductList.addAll(getFreeProdSingleScheme(sfaDatabase, schemeModelList.get(i).getSchemeCode()));
        }

        return freeSlabProductList;

    }

    public List<SchemeModel> getFreeProdSingleScheme(SFADatabase sfaDatabase, String schemeCode) {
        List<SchemeModel> schemeFreeProdSlabs = new ArrayList<>();
        String brandQuery = "select  sl.schemeCode as schemeCode,sl.slabNo as slabNo,sl. slabFrom as slabFrom,sl.slabTo as slabTo,sl.payout as payout,sl.slabRange \n" +
                "as slabRange,sl.forEvery as forEvery,sl.uomCode as uomCode,sp.qty as qty,sp.prodCode as prodCode , p.prodName as prodName, p.ProdShortName as ProdShortName,\n" +
                "sp.isMandatory as isMandatory,sd.schemeBase as schemeBase,sd.payOutType as payOutType,sd.combi as combi from\n" +
                " m_schemeSlabList sl \n" +
                "inner join m_schemeDefinitionList sd ON sd.cmpCode=sl.cmpCode AND sd.schemeCode=sl.schemeCode  \n" +
                "inner join m_schemeSlabProductList sp ON sp.cmpCode=sl.cmpCode \n" +
                "AND sp.schemeCode=sl.schemeCode AND sp.slabNo=sl.slabNo\n" +
                "inner join m_product_master p ON p.cmpCode=sl.cmpCode\n" +
                "AND  p.prodCode  = sp.prodCode Where sl.schemeCode=? and sd.schemeToDt >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy") + " ORDER BY sp.isMandatory DESC";
//                "AND  p.productHierPathCode like ( '%/' || sp.prodCode ||  '/%' )Where sl.schemeCode=? ORDER BY sp.isMandatory DESC";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeCode});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setSlabTo(c.getDouble(c.getColumnIndex(SCHEME_SLAB_TO)));
                schemeModel.setSlabFrom(Double.valueOf(c.getString(c.getColumnIndex(SCHEME_SLAB_FROM))));
                schemeModel.setSlabRange(c.getString(c.getColumnIndex(SCHEME_SLAB_RANGE)));
                schemeModel.setPayoutValue(c.getDouble(c.getColumnIndex(SCHEME_SLAB_PAYOUT)));
                schemeModel.setFreeProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                schemeModel.setFreeQty(Integer.valueOf(c.getString(c.getColumnIndex(SCHEME_QTY))));
                schemeModel.setIsMandatory(c.getString(c.getColumnIndex(COLUMN_ISMANDATORY)));
                schemeModel.setForEvery(c.getDouble(c.getColumnIndex(FOR_EVERY)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeModel.setUom(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                schemeFreeProdSlabs.add(schemeModel);
            }
            c.close();
        }
        return schemeFreeProdSlabs;
    }


    public String getUomCodeForScheme(SFADatabase sfaDatabase, SchemeModel schemeModelList) {

        String uom = "";

        String brandQuery = "Select * from " + TABLE_SCHEME_SLAB_LIST + " where schemeCode=? limit 1";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeModelList.getSchemeCode()});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                uom = (c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
            }
            c.close();
        }

        return uom;

    }


    public SchemeModel fetchSuggestedSchemeSlabDetail(SFADatabase sfaDatabase, SchemeModel schemeModelList, double orderValue) {

        SchemeModel schemeModel = new SchemeModel();

        String brandQuery = "Select * from " + TABLE_SCHEME_SLAB_LIST + " where schemeCode=?  AND slabFrom > ? limit 1";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeModelList.getSchemeCode(), String.valueOf(orderValue)});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                schemeModel.setSlabTo(c.getDouble(c.getColumnIndex(SCHEME_SLAB_TO)));
                schemeModel.setSlabFrom(Double.valueOf(c.getString(c.getColumnIndex(SCHEME_SLAB_FROM))));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setForEvery(c.getDouble(c.getColumnIndex(FOR_EVERY)));
                schemeModel.setUom(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                schemeModel.setSlabRange(c.getString(c.getColumnIndex(SCHEME_SLAB_RANGE)));
                schemeModel.setPayoutValue(c.getDouble(c.getColumnIndex(SCHEME_SLAB_PAYOUT)));

            }
            c.close();
        }

        return schemeModel;

    }

    public List<SchemeModel> getSchemaSlabDetails(SFADatabase sfaDatabase, String schemeCode) {

        List<SchemeModel> schemeSlabList = new ArrayList<>();

        String brandQuery = "Select * from " + TABLE_SCHEME_SLAB_LIST + " where schemeCode=? ";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeCode});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setSlabTo(c.getDouble(c.getColumnIndex(SCHEME_SLAB_TO)));
                schemeModel.setSlabFrom(Double.valueOf(c.getString(c.getColumnIndex(SCHEME_SLAB_FROM))));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setForEvery(c.getDouble(c.getColumnIndex(FOR_EVERY)));
                schemeModel.setPayoutValue(c.getDouble(c.getColumnIndex(SCHEME_SLAB_PAYOUT)));
                schemeSlabList.add(schemeModel);
            }
            c.close();
        }

        return schemeSlabList;
    }


    public SchemeModel getSchemeDefinition(SFADatabase sfaDatabase, String distCode, String schemeCode) {
        SchemeModel schemeModel = null;
        String brandQuery = "select * from " + TABLE_SCHEME_DEFINITION_LIST + "  where schemeCode=? and schemeToDt >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeCode});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                schemeModel = new SchemeModel();
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
            }
            c.close();
        }

        return schemeModel;
    }


    public List<SchemeModel> getAllSchemeDefinition(SFADatabase sfaDatabase, String cmpCode) {
        SchemeModel schemeModel;
        String brandQuery = "select * from " + TABLE_SCHEME_DEFINITION_LIST + "  where cmpCode = ? and schemeToDt >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{cmpCode});

        List<SchemeModel> list = new ArrayList<>();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                schemeModel = new SchemeModel();
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeModel.setSchemeFromDt(c.getString(c.getColumnIndex(SCHEME_FROM_DT)));
                schemeModel.setSchemeToDt(c.getString(c.getColumnIndex(SCHEME_TO_DT)));
                list.add(schemeModel);
            }
            c.close();
        }

        return list;
    }


    public List<SchemeModel> getCombiSchemeSlabList(SFADatabase sfaDatabase, String distCode, String schemeCode) {

        List<SchemeModel> schemeSlabList = new ArrayList<>();


        String brandQuery = "Select sl.schemeCode,sl.slabNo,sl.slabFrom,sl.slabTo,sl.payout,sl.slabrange,sl.uomCode,sl.forEvery,sd.schemeDescription,\n"
                + "sd.schemeBase,sd.schemeFromDt,sd.schemeToDt,sd.payOutType,sd.combi\n" +
                " from " + TABLE_SCHEME_SLAB_LIST + " sl \n" +
                " INNER JOIN " + TABLE_SCHEME_DEFINITION_LIST + " sd ON sd.schemeCode=sl.schemeCode \n" +
                " where sl.schemeCode=? and sd.schemeToDt >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeCode});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setSlabTo(c.getDouble(c.getColumnIndex(SCHEME_SLAB_TO)));
                schemeModel.setSlabFrom(Double.valueOf(c.getString(c.getColumnIndex(SCHEME_SLAB_FROM))));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setSlabRange(c.getString(c.getColumnIndex(SCHEME_SLAB_RANGE)));
                schemeModel.setPayoutValue(c.getDouble(c.getColumnIndex(SCHEME_SLAB_PAYOUT)));
                schemeModel.setForEvery(c.getDouble(c.getColumnIndex(FOR_EVERY)));
                schemeModel.setUom(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeSlabList.add(schemeModel);
            }
            c.close();
        }

        return schemeSlabList;
    }


    public List<FreeProdModel> fetchSingleProdFreeProds(SFADatabase sfaDatabase, String distrCode, String salesmanCode,
                                                        String routeCode, String retlrCode, OrderBookingVO bookingVO, String tableName) {

        List<FreeProdModel> freeProdModels = new ArrayList<>();

        String brandQuery = "";
        Cursor c;
        if (tableName.equalsIgnoreCase(TABLE_ORDER_BOOKING)) {


            brandQuery = "Select distrCode,cmpCode,readableInvNo,customerCode,orderNo,prodCode,\n"
                    + "schemeCode,slabNo,freeProdCode,freeProdName,sum(freeQty) as freeQty,discountAmount,discountPercen,\n" +
                    "upload from " + TABLE_ORDERED_APPLIED_SCHEME + " WHERE  freeProdCode IS NOT NULL AND freeQty>0 group by freeProdCode ";
            c = sfaDatabase.getDb().rawQuery(brandQuery, new String[]{});
        } else {
            brandQuery = "Select distrCode,salesmanCode,routeCode,customerCode,orderNo,prodCode,\n" +
                    "schemeCode,slabNo,freeProdCode,freeProdName,sum(freeQty) as freeQty,discountAmount,discountPercen,\n" +
                    "upload from " + TABLE_CONFIRM_ORDER_APPLIED_SCHEME + " WHERE  distrCode=? \n" +
                    "AND    salesmanCode=? \n" +
                    "AND    routeCode=? \n" +
                    "AND    customerCode=?\n" +
                    "AND    prodCode =? AND BatchCode=? AND freeProdCode IS NOT NULL AND freeQty>0 group by freeProdCode ";
            c = sfaDatabase.getDb().rawQuery(brandQuery, new String[]{distrCode, salesmanCode, routeCode, retlrCode, bookingVO.getProdCode(), bookingVO.getProdBatchCode()});

        }

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                FreeProdModel freeProdModel = new FreeProdModel();
                freeProdModel.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                freeProdModel.setFreeProdCode(c.getString(c.getColumnIndex(FREE_PROD_CODE)));
                freeProdModel.setFreeQty(c.getString(c.getColumnIndex(FREE_QTY)));
                freeProdModel.setFreeProdName(c.getString(c.getColumnIndex(FREE_PROD_NAME)));
                freeProdModels.add(freeProdModel);
            }
            c.close();
        }
        return freeProdModels;
    }


    public List<SchemeModel> fetchSchemeSlabDetail(SFADatabase sfaDatabase, List<SchemeModel> schemeModelList) {

        List<SchemeModel> schemeSlabModel = new ArrayList<>();

        for (int i = 0; i < schemeModelList.size(); i++) {
            schemeSlabModel.addAll(fetchSchemeSlabList(sfaDatabase, schemeModelList.get(i)));
        }
        return schemeSlabModel;
    }

    public List<SchemeModel> fetchSchemeSlabList(SFADatabase sfaDatabase, SchemeModel schemeModelList) {

        List<SchemeModel> schemeSlabs = new ArrayList<>();
        String brandQuery = "SELECT ss.schemeCode,\n" +
                "       ss.slabNo,\n" +
                "       ss.slabFrom,\n" +
                "       ss.slabTo,\n" +
                "       ss.payout,\n" +
                "       ss.slabRange,\n" +
                "       ss.uomCode,\n" +
                "       ss.forevery,\n" +
                "       sd.schemeBase,\n" +
                "       sd.payoutType,\n" +
                "       sd.isSkuLevel,\n" +
                "       sd.isActive,\n" +
                "       sd.combi\n" +
                "FROM m_schemeSlabList ss\n" +
                "LEFT JOIN m_schemeDefinitionList sd ON ss.schemeCode = sd.schemeCode\n" +
                "WHERE ss.schemeCode=?\n" +
                "  AND sd.schemeToDt >=" + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeModelList.getSchemeCode()});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setSlabTo(c.getDouble(c.getColumnIndex(SCHEME_SLAB_TO)));
                schemeModel.setSlabFrom(Double.valueOf(c.getString(c.getColumnIndex(SCHEME_SLAB_FROM))));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setSlabRange(c.getString(c.getColumnIndex(SCHEME_SLAB_RANGE)));
                schemeModel.setPayoutValue(c.getDouble(c.getColumnIndex(SCHEME_SLAB_PAYOUT)));
                schemeModel.setUom(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                schemeModel.setForEvery(c.getDouble(c.getColumnIndex(FOR_EVERY)));
                schemeModel.setSchemeBase((c.getString(c.getColumnIndex(SCHEME_BASE)).trim()));
                schemeModel.setPayoutType((c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)).trim()));
                schemeModel.setIsSkuLevel((c.getString(c.getColumnIndex(COLUMN_IS_SKU_LEVEL)).trim()));
                schemeModel.setIsActive((c.getString(c.getColumnIndex(COLUMN_IS_ACTIVE)).trim()));
                schemeModel.setCombi((c.getString(c.getColumnIndex(COMBI)).trim()));
                schemeSlabs.add(schemeModel);
            }
            c.close();
        }
        return schemeSlabs;
    }

    public List<SchemeModel> fetchSchemeSlabProductList(SFADatabase sfaDatabase, SchemeModel schemeModelList) {

        List<SchemeModel> schemeSlabs = new ArrayList<>();
        String brandQuery = "Select * from " + TABLE_SCHEME_SLAB_PRODUCT_LIST + " where schemeCode=? and slabNo = ?";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{schemeModelList.getSchemeCode(), schemeModelList.getSlabNo()});

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                schemeModel.setFreeProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                schemeModel.setFreeQty(c.getInt(c.getColumnIndex(SCHEME_QTY)));
                schemeModel.setIsMandatory(c.getString(c.getColumnIndex(TAG_ISMANDATORY)));
                schemeModel.setPayoutType(schemeModelList.getPayoutType());
                schemeModel.setProductCode(schemeModelList.getProductCode());
                schemeModel.setProdBatchCode(schemeModelList.getProdBatchCode());
                schemeSlabs.add(schemeModel);
            }
            c.close();
        }
        return schemeSlabs;
    }


    public List<OrderBookingVO> getBillingProductAndFreeProdFromDB(SFADatabase sfaDatabase,
                                                                   String distrcode,
                                                                   String salesmancode,
                                                                   String retrCode, String className) {
        String productTable = TABLE_PRODUCTS;
        if (className.equalsIgnoreCase(TABLE_VANSALES_PRODUCTS)) {
            productTable = TABLE_VANSALES_PRODUCTS;
        }

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        /*String query = "SELECT DISTINCT * FROM\n" +
                "   (SELECT p.prodcode as ProdCode ,p.ProdName as ProdName ,p.ProdShortName as ProdShorName,p.SellPrice as SellPrice,p.MRP as MRP,"
                + " o.orderQty as OrderQty,o.OrderValue as OrderValue ,o.conversionFactor as conversionFactor ,o.CGSTValue,o.CGSTPerc,o.SGSTValue," +
                "o.SGSTPerc,o.UTGSTValue,o.UTGSTPerc,o.IGSTValue,o.IGSTPerc,o.netAmount, o.BatchCode as BatchCode, " +
                "o.taxPerProduct as taxPerProdct,o.discountAmount as discountAmount,o.uomId,o.orderNo,ls.availQty as availQty,ls.lastOrdQty as lastOrdQty," +
                " (SELECT sum(bs.freeQty) \n" +
                "FROM " + TABLE_BILLING_APPLIED_SCHEME + " bs WHERE \n" +
                "       p.prodcode =bs.freeProdCode\n" +
                "       AND p.BatchCode = bs.BatchCode\n" +
                "       AND p.distrcode = bs.distrcode\n" +
                "       AND p.salesmancode = bs.salesmancode AND bs.upload = 'N' AND bs.customerCode = '" + retrCode + "') AS freeQty \n" +
                "       FROM " + productTable + " p \n" +
                "\tLEFT JOIN " + TABLE_BILLING + " o ON p.prodcode = o.prodcode\n" +
                "       AND p.BatchCode = o.BatchCode\n" +
                "       AND p.distrcode = o.distrcode\n" +
                "       AND p.salesmancode = o.salesmancode AND o.upload = 'N' AND o.retlrcode = '" + retrCode + "'\n" +
                "\tLEFT JOIN " + TABLE_LOADING_STOCK + " ls ON p.prodcode = ls.prodcode\n" +
                "       AND p.BatchCode = ls.BatchCode\n" +
                "       AND p.distrcode = ls.distrcode\n" +
                "       AND p.salesmancode = ls.salesmancode AND o.upload = 'N' \n" +
                "WHERE p.distrcode = '" + distrcode + "'\n" +
                "       AND p.salesmancode = '" + salesmancode + "') temp\n" +
                "       WHERE freeqty>0 OR OrderQty>0;";*/

        String query = "SELECT DISTINCT *\n" +
                "FROM   (SELECT p.prodcode AS ProdCode,\n" +
                "               p.prodname AS ProdName,\n" +
                "               p.prodshortname AS ProdShortName,\n" +
                "               p.sellprice AS SellPrice,\n" +
                "               p.mrp AS MRP,\n" +
                "               o.orderqty AS OrderQty,\n" +
                "               o.ordervalue AS OrderValue,\n" +
                "               o.primaryDiscOrderValue AS primaryDiscOrderValue,\n" +
                "               o.conversionfactor AS conversionFactor,\n" +
                "               o.CGSTValue,\n" +
                "               o.CGSTPerc,\n" +
                "               o.SGSTValue,\n" +
                "               o.SGSTPerc,\n" +
                "               o.UTGSTValue,\n" +
                "               o.UTGSTPerc,\n" +
                "               o.IGSTValue,\n" +
                "               o.IGSTPerc,\n" +
                "               o.netAmount,\n" +
                "               o.batchcode AS BatchCode,\n" +
                "               o.taxperproduct AS taxPerProduct,\n" +
                "               o.discountamount AS discountAmount,\n" +
                "               o.uomId,\n" +
                "               o.orderNo,\n" +
                "               ls.availqty AS availQty,\n" +
                "               ls.lastordqty AS lastOrdQty,\n" +
                "               (SELECT Sum(bs.freeqty)\n" +
                "                FROM   " + TABLE_BILLING_APPLIED_SCHEME + " bs\n" +
                "                WHERE  p.prodcode = bs.freeprodcode\n" +
                "                       AND p.batchcode = bs.batchcode\n" +
                "                       AND p.distrcode = bs.distrcode\n" +
                "                       AND p.salesmancode = bs.salesmancode\n" +
                "                       AND bs.upload = 'N'\n" +
                "                       AND bs.customercode = '" + retrCode + "') AS freeQty\n" +
                "        FROM   " + productTable + " p\n" +
                "               LEFT JOIN " + TABLE_BILLING + " o\n" +
                "                      ON p.prodcode = o.prodcode\n" +
                "                         AND p.batchcode = o.batchcode\n" +
                "                         AND p.distrcode = o.distrcode\n" +
                "                         AND p.salesmancode = o.salesmancode\n" +
                "                         AND o.upload = 'N'\n" +
                "                         AND o.retlrcode = '" + retrCode + "'\n" +
                "               LEFT JOIN " + TABLE_LOADING_STOCK + " ls\n" +
                "                      ON p.prodcode = ls.prodcode\n" +
                "                         AND p.batchcode = ls.batchcode\n" +
                "                         AND p.distrcode = ls.distrcode\n" +
                "                         AND p.salesmancode = ls.salesmancode\n" +
                "                         AND o.upload = 'N'\n" +
                "        WHERE  p.distrcode = '" + distrcode + "'\n" +
                "               AND p.salesmancode = '" + salesmancode + "') temp\n" +
                "WHERE  freeqty > 0\n" +
                "        OR orderqty > 0;";

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setOrderInvoiceNo(c.getString(c.getColumnIndex(ORDER_NO)));
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)));
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)));
                orderBookingVO.setSellPrice(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                orderBookingVO.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                orderBookingVO.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderBookingVO.setlGrossAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
                orderBookingVO.setOrderFreeQty(c.getInt(c.getColumnIndex("freeQty")));
                orderBookingVO.setTax(c.getDouble(c.getColumnIndex(TAX_PER_PRODUCT)));
                orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                orderBookingVO.setLastOrdQty(c.getInt(c.getColumnIndex(COLUMN_LAST_ORD_QTY)));
                orderBookingVO.setDicountAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(DISCOUNT_AMOUNT))));
                orderBookingVO.setCgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(CGST_VALUE))));
                orderBookingVO.setSgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SGST_VALUE))));
                orderBookingVO.setIgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(IGST_VALUE))));
                orderBookingVO.setUtgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(UTGST_VALUE))));

                orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));

                orderBookingVO.setNetAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(NET_AMOUNT))));
                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }


    public boolean isCustomerSchemeExist(SFADatabase sfaDatabase, String cmpCode, String customerCode) {
        String query = " SELECT  * FROM " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " WHERE " + COLUMN_CMP_CODE + "=? AND "
                + COLUMN_CUSTOMER_CODE + " =?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, customerCode});
        if (c != null && c.getCount() > 0) {
            return true;
        }
        if (c != null) {
            c.close();
        }
        return false;
    }


    public String getCustomerZipScheme(SFADatabase sfaDatabase, String distrCode, String customerCode) {
        String result = "";
        String query = "Select * from " + TABLE_SCHEME_ZIP_LIST + " Where " + COLUMN_DISTR_CODE + " =? AND " +
                COLUMN_CUSTOMER_CODE + " =? ";
        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, customerCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(COLUMN_GZIP_SCHEME_DATA));
        }
        if (cursor != null) {
            cursor.close();
        }
        return result;
    }

    public List<SchemeModel> getRetailerSchemeProducts(SFADatabase sfaDatabase) {
        List<SchemeModel> schemeModelList = new ArrayList<>();
        String brandQuery = "Select * from " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST;

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                schemeModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                schemeModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                schemeModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeModel.setStartDate(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                schemeModel.setEndDate(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                schemeModelList.add(schemeModel);
            }
            c.close();
        }

        return schemeModelList;
    }


    public List<SchemeModel> getSchemeDetailByProdCode(SFADatabase sfaDatabase, String cmpCode, String prodCodeCaps) {

        List<SchemeModel> productsVOList = new ArrayList<>();

        Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_SCHEME_DETAIL_BY_PROD_CODE, new String[]{cmpCode, prodCodeCaps});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                SchemeModel product = new SchemeModel();

                product.setProductCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_CODE)));
                product.setProdName(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
                product.setSchemeCode(cursor.getString(cursor.getColumnIndex(SCHEME_CODE)));
                product.setSchemeBase(cursor.getString(cursor.getColumnIndex(SCHEME_BASE)));
                product.setSchemeDescription(cursor.getString(cursor.getColumnIndex(SCHEME_DESC)));
                product.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_START_TIME)));
                product.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_END_TIME)));
                product.setPayoutType(cursor.getString(cursor.getColumnIndex(SCHEME_PAYOUT_TYPE)));

                productsVOList.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return productsVOList;
    }

    public List<OrderBookingVO> getBillingProductListAndFreeProdFromDB(SFADatabase sfaDatabase,
                                                                       String distrcode,
                                                                       String salesmancode,
                                                                       String retrCode, String className) {
        /*String productTable = TABLE_PRODUCTS;
        if (className.equalsIgnoreCase(TABLE_VANSALES_PRODUCTS)) {
            productTable = TABLE_VANSALES_PRODUCTS;
        }*/
        String productTable = TABLE_PRODUCT_MASTER;
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        String query = "SELECT DISTINCT *\n" +
                "FROM   (SELECT p.prodcode AS ProdCode,\n" +
                "               p.prodname AS ProdName,\n" +
                "               p.prodshortname AS ProdShortName,\n" +
                "               pbm.sellprice AS SellPrice,\n" +
                "               pbm.mrp AS MRP,\n" +
                "               o.orderqty AS OrderQty,\n" +
                "               o.ordervalue AS OrderValue,\n" +
                "               o.primaryDiscOrderValue AS primaryDiscOrderValue,\n" +
                "               o.conversionfactor AS conversionFactor,\n" +
                "               o.CGSTValue,\n" +
                "               o.CGSTPerc,\n" +
                "               o.SGSTValue,\n" +
                "               o.SGSTPerc,\n" +
                "               o.UTGSTValue,\n" +
                "               o.UTGSTPerc,\n" +
                "               o.IGSTValue,\n" +
                "               o.IGSTPerc,\n" +
                "               o.netAmount,\n" +
                "               o.batchcode AS BatchCode,\n" +
                "               o.taxperproduct AS taxPerProduct,\n" +
                "               o.discountamount AS discountAmount,\n" +
                "               o.uomId,\n" +
                "               o.orderNo,\n" +
                "               ls.availqty AS availQty,\n" +
                "               ls.lastordqty AS lastOrdQty,\n" +
                "               (SELECT Sum(bs.freeqty)\n" +
                "                FROM   " + TABLE_BILLING_APPLIED_SCHEME + " bs\n" +
                "                WHERE  p.prodcode = bs.freeprodcode\n" +
                "                       AND o.batchcode = bs.batchcode\n" +
                "                       AND o.distrcode = bs.distrcode\n" +
                "                       AND o.salesmancode = bs.salesmancode\n" +
                "                       AND bs.upload = 'N'\n" +
                "                       AND bs.customercode = '" + retrCode + "') AS freeQty\n" +
                "        FROM   " + productTable + " p\n" +
                "               INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " pbm\n" +
                "                       ON pbm.cmpcode = p.cmpcode\n" +
                "               LEFT JOIN " + TABLE_BILLING + " o\n" +
                "                      ON p.prodcode = o.prodcode\n" +
                "                         AND pbm.prodbatchcode = o.batchcode\n" +
                "                         AND pbm.distrcode = o.distrcode\n" +
                "                         AND o.upload = 'N'\n" +
                "                         AND o.retlrcode = '" + retrCode + "'\n" +
                "               LEFT JOIN " + TABLE_STOCK_ON_HAND + " ls\n" +
                "                      ON p.prodcode = ls.prodcode\n" +
                "                         AND pbm.prodbatchcode = ls.ProdBatchcode\n" +
                "                         AND pbm.distrcode = ls.distrcode\n" +
                "                         AND o.upload = 'N'\n" +
                "        WHERE  pbm.distrcode = '" + distrcode + "'\n" +
                "               AND o.salesmancode = '" + salesmancode + "') temp\n" +
                "WHERE  freeqty > 0\n" +
                "        OR orderqty > 0;";

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setOrderInvoiceNo(c.getString(c.getColumnIndex(ORDER_NO)));
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)));
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)));
                orderBookingVO.setSellPrice(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                orderBookingVO.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                orderBookingVO.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderBookingVO.setlGrossAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
                orderBookingVO.setOrderFreeQty(c.getInt(c.getColumnIndex("freeQty")));
                orderBookingVO.setTax(c.getDouble(c.getColumnIndex(TAX_PER_PRODUCT)));
                orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                orderBookingVO.setLastOrdQty(c.getInt(c.getColumnIndex(COLUMN_LAST_ORD_QTY)));
                orderBookingVO.setDicountAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(DISCOUNT_AMOUNT))));
                orderBookingVO.setCgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(CGST_VALUE))));
                orderBookingVO.setSgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SGST_VALUE))));
                orderBookingVO.setIgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(IGST_VALUE))));
                orderBookingVO.setUtgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(UTGST_VALUE))));

                orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));

                orderBookingVO.setNetAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(NET_AMOUNT))));
                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }

    public List<SchemeModel> getCustomerSchemeProducts(SFADatabase sfaDatabase, String customerCode) {
        List<SchemeModel> schemeModelList = new ArrayList<>();
//        String brandQuery = "Select * from " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " csp left join " + TABLE_SCHEME_DEFINITION_LIST + " sdl on csp.schemeCode = sdl.schemeCode Where csp." + COLUMN_CUSTOMER_CODE + " =? and sdl.schemeToDt >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");
        String brandQuery = "Select * from " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " Where " + COLUMN_CUSTOMER_CODE + " =? and endTime >= " + DateUtil.convertStringDateToTimestamp(DateUtil.getCurrentDate(), "dd-MM-yyyy");

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{customerCode});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setCustomerCode(customerCode);
                schemeModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeModel.setStartDate(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                schemeModel.setEndDate(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                schemeModelList.add(schemeModel);
            }
            c.close();
        }

        return schemeModelList;
    }

    public List<SchemeDistrBudgetModel> getSchemeBudgetList(SFADatabase sfaDatabase, String distrCode, String customerCode) {
        List<SchemeDistrBudgetModel> schemeModelList = new ArrayList<>();
        String brandQuery = "SELECT DISTINCT (sdb.schemeCode) , sdb.isUnlimited,\n" +
                "                                   sdb.budget,\n" +
                "                                   sdb.isActive,\n" +
                "                                   sdb.utilizedAmount,\n" +
                "                                   sdb.budgetOs\n" +
                "FROM m_schemeDistrBudget sdb\n" +
                "LEFT JOIN m_customerSchemeProductList csp ON sdb.schemeCode = csp.schemeCode\n" +
                "WHERE csp.customerCode = ?\n" +
                "  AND sdb.distrCode = ?\n" +
                "  AND ((sdb.budget - sdb.utilizedAmount) > 0)";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{customerCode, distrCode});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeDistrBudgetModel schemeModel = new SchemeDistrBudgetModel();

                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setIsUnlimited(c.getString(c.getColumnIndex(COLUMN_IS_UNLIMITED)));
                schemeModel.setBudget(Double.valueOf(c.getString(c.getColumnIndex(COLUMN_BUDGET))));
                schemeModel.setIsActive(c.getString(c.getColumnIndex(COLUMN_IS_ACTIVE)));
                schemeModel.setUtilizedAmount(Double.valueOf(c.getString(c.getColumnIndex(COLUMN_UTILIZED_AMOUNT))));
                schemeModel.setBudgetOs(Double.valueOf(c.getString(c.getColumnIndex(COLUMN_BUDGET_OS))));

                schemeModelList.add(schemeModel);
            }
            c.close();
        }

        return schemeModelList;
    }

    //Scheme new
    public ArrayList<SchemeModel> getCustomerSchemeProductsList(SFADatabase sfaDatabase) {
        ArrayList<SchemeModel> schemeModelList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " GROUP BY " + COLUMN_DISTR_CODE + ", " + COLUMN_CMP_CODE;
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(query, null);

//        Cursor c = database.query(true, TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST, new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_CUSTOMER_CODE, COLUMN_PROD_CODE, SCHEME_CODE}, null, null, COLUMN_DISTR_CODE, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                schemeModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                schemeModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                schemeModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeModel.setStartDate(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                schemeModel.setEndDate(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                schemeModelList.add(schemeModel);
            }
            c.close();
        }

        return schemeModelList;
    }

    //Scheme new
    public List<SchemeModel> getRetailerSchemeProductsList(SFADatabase sfaDatabase, String distr_code, String cmp_code) {
        List<SchemeModel> schemeModelList = new ArrayList<>();
        String qery = "SELECT * FROM '" + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + "' WHERE " + COLUMN_DISTR_CODE + " = ? AND " + COLUMN_CMP_CODE + " = ?";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(qery, new String[]{distr_code, cmp_code});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                schemeModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                schemeModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                schemeModel.setSchemeBase(c.getString(c.getColumnIndex(SCHEME_BASE)));
                schemeModel.setPayoutType(c.getString(c.getColumnIndex(SCHEME_PAYOUT_TYPE)));
                schemeModel.setSchemeDescription(c.getString(c.getColumnIndex(SCHEME_DESC)));
                schemeModel.setCombi(c.getString(c.getColumnIndex(COMBI)));
                schemeModel.setStartDate(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                schemeModel.setEndDate(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                schemeModelList.add(schemeModel);
            }
            c.close();
        }

        return schemeModelList;
    }

    public ArrayList<Distributor> getSchemeDistrList(SFADatabase sfaDatabase) {

        ArrayList<Distributor> distributorArrayList = new ArrayList<>();

        String query = " SELECT cs.cmpCode AS cmpCode,\n" +

                "       cs.distrCode AS distrCode,\n" +

                "       dis.distrName AS distrName,\n" +

                "       com.cmpName AS cmpName\n" +

                "FROM   " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " cs\n" +

                "       INNER JOIN " + TABLE_DISTRIBUTOR + " dis\n" +

                "               ON cs.cmpCode = dis.cmpCode\n" +

                "                  AND cs.distrCode = dis.distrCode\n" +

                "       INNER JOIN " + TABLE_COMPANY_DETAIL + " com\n" +

                "               ON cs.cmpCode = com.cmpCode\n" +

                "GROUP  BY cs.cmpCode ,cs.distrCode";


        SQLiteDatabase database = sfaDatabase.getReadableDatabase();

        Cursor c = database.rawQuery(query, null);


        if (c != null && c.getCount() > 0) {

            while (c.moveToNext()) {


                Distributor distributor = new Distributor();

                distributor.setCompanyCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));

                distributor.setDistributorCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));

                distributor.setCompanyName(c.getString(c.getColumnIndex(COLUMN_CMP_NAME)));

                distributor.setDistributorName(c.getString(c.getColumnIndex(COLUMN_DISTR_NAME)));

                distributorArrayList.add(distributor);

            }

            c.close();

        }


        return distributorArrayList;

    }


    public int getSchemeDistrListCount(BaseDB baseDB) {

        int count = 0;

        baseDB.openWritableDb();

        String query = "SELECT * FROM " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " GROUP BY " + COLUMN_DISTR_CODE + ", " + COLUMN_CMP_CODE;

        Cursor cm = baseDB.getDb().rawQuery(query, null);

        count = cm.getCount();


        if (cm != null) {

            count = cm.getCount();


        }

        if (cm != null) {

            cm.close();

        }

        baseDB.closeDb();


        return count;

    }
}
