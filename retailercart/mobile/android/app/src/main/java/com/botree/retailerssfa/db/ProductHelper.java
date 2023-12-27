package com.botree.retailerssfa.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.botree.retailerssfa.models.LogisticStockModel;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.ProdBatchModel;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.models.StockledgerReportModel;
import com.botree.retailerssfa.models.UomMasterModel;
import com.botree.retailerssfa.util.SFASharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ADJIN_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ADJIN_SALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ADJIN_UNSALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ADJOUT_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ADJOUT_SALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ADJOUT_UNSALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_AVAIL_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_BASE_UOM;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CESS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CGST;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CLOSINGSTK_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CLOSINGSTK_SALABLEQTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CLOSINGSTK_UNSALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CONVERSION_FACTOR;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DEFAULT_UOM;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_IGST;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MANF_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MATERIAL_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MATERIAL_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MRP_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_OPENING_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_OPENING_SALES_QUANTITY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_OPENING_UNSALABLE_QUANTITY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_HIER_LAST_VAL_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_HIER_LVL_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_HIER_PATH_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_LOB_DIVISTION_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_SHORT_NAME_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_OFFERQTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_PRICE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_RET_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_RET_SALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_RET_UNSALABLE_Qty;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_SALABLEQTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_UNSALABLEQTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_RECEIVED_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_RETURNED_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_RET_OFFER_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_RET_SALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_RET_UNSALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_SALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_UNSALABLE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SELL_PRICE_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SELL_RATE_WITH_TAX;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SGST;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_STOCK;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TRANS_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UOM_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UOM_DESCRIPTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOGISTIC_MATERIAL_STOCK;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_MUST_SELL_BILLING_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_STOCK_LEDGER_REPORT;
import static com.botree.retailerssfa.db.query.IDBColumns.UPLOAD_FLAG;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;

/**
 * Created by vinothbaskaran on 5/2/18.
 */

class ProductHelper {

    ProductHelper() {
//do your stuff
    }

    List<StockledgerReportModel> getStockLedgerReportDetails(SFADatabase sfaDatabase) {
        List<StockledgerReportModel> stockLedgerlist = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_STOCK_LEDGER_REPORT;

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                StockledgerReportModel stockLedgerModel = new StockledgerReportModel();
                stockLedgerModel.setDistName(c.getString(c.getColumnIndex(COLUMN_DISTR_NAME)));
                stockLedgerModel.setDistCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                stockLedgerModel.setTransDate(c.getString(c.getColumnIndex(COLUMN_TRANS_DATE)));
                stockLedgerModel.setProductName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                stockLedgerModel.setProductCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                stockLedgerModel.setOpeningSalableQty(c.getString(c.getColumnIndex(COLUMN_OPENING_SALES_QUANTITY)));
                stockLedgerModel.setOpeningUnSalableQty(c.getString(c.getColumnIndex(COLUMN_OPENING_UNSALABLE_QUANTITY)));
                stockLedgerModel.setOpeningOfferQty(c.getString(c.getColumnIndex(COLUMN_OPENING_OFFER_QTY)));
                stockLedgerModel.setPurchaseSalableQty(c.getString(c.getColumnIndex(COLUMN_PURCHASE_SALABLEQTY)));
                stockLedgerModel.setPurchaseUnSalableQty(c.getString(c.getColumnIndex(COLUMN_PURCHASE_UNSALABLEQTY)));
                stockLedgerModel.setPurchaseOfferQty(c.getString(c.getColumnIndex(COLUMN_PURCHASE_OFFERQTY)));
                stockLedgerModel.setPurchaseRetSalableQty(c.getString(c.getColumnIndex(COLUMN_PURCHASE_RET_SALABLE_QTY)));
                stockLedgerModel.setPurchaseRetUnSalableQty(c.getString(c.getColumnIndex(COLUMN_PURCHASE_RET_UNSALABLE_Qty)));
                stockLedgerModel.setPurchaseRetOfferQty(c.getString(c.getColumnIndex(COLUMN_PURCHASE_RET_OFFER_QTY)));
                stockLedgerModel.setSalesSalableQty(c.getString(c.getColumnIndex(COLUMN_SALES_SALABLE_QTY)));
                stockLedgerModel.setSalesUnSalableQty(c.getString(c.getColumnIndex(COLUMN_SALES_UNSALABLE_QTY)));
                stockLedgerModel.setSalesOfferQty(c.getString(c.getColumnIndex(COLUMN_SALES_OFFER_QTY)));
                stockLedgerModel.setSalesRetSalableQty(c.getString(c.getColumnIndex(COLUMN_SALES_RET_SALABLE_QTY)));
                stockLedgerModel.setSalesRetUnSalableQty(c.getString(c.getColumnIndex(COLUMN_SALES_RET_UNSALABLE_QTY)));
                stockLedgerModel.setSalesRetOfferQty(c.getString(c.getColumnIndex(COLUMN_SALES_RET_OFFER_QTY)));
                stockLedgerModel.setAdjInSalableQty(c.getString(c.getColumnIndex(COLUMN_ADJIN_SALABLE_QTY)));
                stockLedgerModel.setAdjInUnSalableQty(c.getString(c.getColumnIndex(COLUMN_ADJIN_UNSALABLE_QTY)));
                stockLedgerModel.setAdjInOfferQty(c.getString(c.getColumnIndex(COLUMN_ADJIN_OFFER_QTY)));
                stockLedgerModel.setAdjOutSalableQty(c.getString(c.getColumnIndex(COLUMN_ADJOUT_SALABLE_QTY)));
                stockLedgerModel.setAdjOutUnSalableQty(c.getString(c.getColumnIndex(COLUMN_ADJOUT_UNSALABLE_QTY)));
                stockLedgerModel.setAdjOutOfferQty(c.getString(c.getColumnIndex(COLUMN_ADJOUT_OFFER_QTY)));
                stockLedgerModel.setClosingStkSalableQty(c.getString(c.getColumnIndex(COLUMN_CLOSINGSTK_SALABLEQTY)));
                stockLedgerModel.setClosingStkUnSalableQty(c.getString(c.getColumnIndex(COLUMN_CLOSINGSTK_UNSALABLE_QTY)));
                stockLedgerModel.setClosingStkOfferQty(c.getString(c.getColumnIndex(COLUMN_CLOSINGSTK_OFFER_QTY)));
                stockLedgerlist.add(stockLedgerModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return stockLedgerlist;
    }


    List<ProFilterModel> getFirstHierLevel(SFADatabase sfaDatabase, String tableName,
                                           String levelCode, String nextLevelName, String lob) {

        List<ProFilterModel> proFilterModels = new ArrayList<>();


        String query;
        if (lob.isEmpty() || lob.toLowerCase().contains("all")) {
            query = "Select * from " + tableName + " Group by " + levelCode + " Order by hierLevelName1 Asc";
        } else {
            query = "Select * from " + tableName + " where lobCode = '" + lob + "' Group by " + levelCode + " Order by hierLevelName1 Asc";
        }

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                ProFilterModel channelModel = new ProFilterModel();
                channelModel.setHierLelevelCode1(c.getString(c.getColumnIndex(levelCode)));
                channelModel.setHierLelevelName1(c.getString(c.getColumnIndex(nextLevelName)));

                proFilterModels.add(channelModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return proFilterModels;
    }


    ProFilterModel getHierLevelByName(SFADatabase sfaDatabase, String levelName) {

        ProFilterModel channelModel = null;
        String query = "Select * from m_product_hier_level where prodHierLvlName = ?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{levelName});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                channelModel = new ProFilterModel();
                channelModel.setHierLelevelCode1(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LVL_CODE)));

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return channelModel;
    }

    List<ProFilterModel> getNextLevelData(SFADatabase sfaDatabase, String tableName,
                                          String brandVal, String brandColumn,
                                          String levelCode, String levelName, String lobCode) {

        List<ProFilterModel> proFilterModels = new ArrayList<>();
        String query;
        Cursor c;
        if (lobCode.isEmpty() || lobCode.toLowerCase().contains("all")) {
            query = "Select * from " + tableName + " where " + brandColumn + "=?" + " group by " + levelCode + " Order by " + levelName + " Asc";
            c = sfaDatabase.getDb().rawQuery(query, new String[]{brandVal});
        } else {
            query = "Select * from " + tableName + " where " + brandColumn + "=? and lobCode = ? " + " group by " + levelCode + " Order by " + levelName + " Asc";
            c = sfaDatabase.getDb().rawQuery(query, new String[]{brandVal, lobCode});
        }

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                ProFilterModel channelModel = new ProFilterModel();
                channelModel.setHierLelevelCode1(c.getString(c.getColumnIndex(levelCode)));
                channelModel.setHierLelevelName1(c.getString(c.getColumnIndex(levelName)));
                proFilterModels.add(channelModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return proFilterModels;
    }


    List<ProductMasterModel> fetchAllBatchProducts(SFADatabase sfaDatabase) {
        List<ProductMasterModel> productMasterModels = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT DISTINCT( p.prodcode ), \n" +
                "               p.prodname, \n" +
                "               p.prodshortname, \n" +
                "               p.lobDivisionCode, \n" +
                "               p.productHierPathCode, \n" +
                "               p.prodHierLastValCode, \n" +
                "               ds.saleableQty as stock, \n" +
                "               um.uomCode, \n" +
                "               ds.saleableQty as availQty \n" +
                "FROM   m_product_master p \n" +
                "       LEFT JOIN m_product_uom_master um \n" +
                "              ON um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       LEFT JOIN m_distributorStock ds \n" +
                "              ON ds.prodcode = p.prodcode \n" +
                "              AND ds.cmpCode = p.cmpCode \n" +
                " COLLATE nocase ";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{});
        ProductMasterModel product;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                product = new ProductMasterModel();
                product.setProdCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_CODE)));
                product.setProdShortName(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                product.setProdName(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
                product.setBranchStock(cursor.getString(cursor.getColumnIndex(COLUMN_STOCK)));
                product.setLobDivisionCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_LOB_DIVISTION_CODE)));
                product.setDefaultUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM_CODE)));
                product.setSaleableQty(cursor.getInt(cursor.getColumnIndex(COLUMN_AVAIL_QTY)));
                product.setProdHierLastValCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                product.setProductHierPathCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));

                productMasterModels.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return productMasterModels;
    }


    List<LogisticStockModel> fetchLogisticMaterialStockBasedOnInvoice(SFADatabase sfaDatabase, String cmpCode, String invoiceCode) {
        List<LogisticStockModel> logisticStockModels = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT invoiceNo,materialCode,materialName, receivedQty, returnedQty from " +
                TABLE_LOGISTIC_MATERIAL_STOCK + " WHERE  cmpCode = ? AND invoiceNo = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, invoiceCode});
        LogisticStockModel product;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                product = new LogisticStockModel();
                product.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO)));
                product.setMaterialCode(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIAL_CODE)));
                product.setMaterialName(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIAL_NAME)));
                product.setReceivedQty(cursor.getString(cursor.getColumnIndex(COLUMN_RECEIVED_QTY)));
                product.setReturnedQty(cursor.getString(cursor.getColumnIndex(COLUMN_RETURNED_QTY)));

                if ((Integer.parseInt(product.getReceivedQty()) - Integer.parseInt(product.getReturnedQty())) > 0) {
                    logisticStockModels.add(product);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return logisticStockModels;
    }

    List<LogisticStockModel> fetchAllLogisticMaterialStock(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        List<LogisticStockModel> logisticStockModels = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT cmpCode, distrCode, invoiceNo, materialCode,materialName, receivedQty, returnedQty from " +
                TABLE_LOGISTIC_MATERIAL_STOCK + " WHERE  cmpCode = ? COLLATE NOCASE AND distrCode = ? " +
                " order by returnedQty desc";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode});
        LogisticStockModel product;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                product = new LogisticStockModel();
                product.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO)));
                product.setMaterialCode(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIAL_CODE)));
                product.setMaterialName(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIAL_NAME)));
                product.setReceivedQty(cursor.getString(cursor.getColumnIndex(COLUMN_RECEIVED_QTY)));
                product.setReturnedQty(cursor.getString(cursor.getColumnIndex(COLUMN_RETURNED_QTY)));
                product.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                product.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)));

                logisticStockModels.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return logisticStockModels;
    }

    void updateLogisticMaterialStock(SFADatabase sfaDatabase, List<LogisticStockModel> purchaseReceiptList) {
        String query = "UPDATE\n" +
                "   t_logisticMaterialStock \n" +
                "SET\n" +
                "   returnedQty = ? " +
                ", uploadFlag = 'N' \n" +
                "WHERE\n" +
                "   materialCode = ?" +
                "AND invoiceNo = ? AND cmpCode = ? AND distrCode = ?";

        Cursor c = null;
        for (LogisticStockModel vo : purchaseReceiptList) {
            c = sfaDatabase.getDb().rawQuery(query, new String[]{vo.getReturnedQty(), vo.getMaterialCode(),
                    vo.getInvoiceNo(), vo.getCmpCode(), vo.getDistrCode()});
            c.moveToFirst();
            c.close();
        }
        if (c != null) {
            c.close();
        }
    }


    List<ProdBatchModel> fetchAllBatchesOfProduct(SFADatabase sfaDatabase, String productId) {
        List<ProdBatchModel> prodBatchModels = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT DISTINCT pbm.mrp, \n" +
                "                pbm.sellprice, \n" +
                "                pbm.purchaseprice, \n" +
                "                pbm.manfDate, \n" +
                "                pbm.sellRateWithTax, \n" +
                "               0 as availQty \n" +
                "FROM   m_product_batch_master pbm \n" +
                "WHERE  pbm.prodcode = ? ";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{productId});
        ProdBatchModel batchModel;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                batchModel = new ProdBatchModel();
                batchModel.setPurchaseRate(cursor.getDouble(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                batchModel.setMrp(cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP_CAPS)));
                batchModel.setSellRate(cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_PRICE_CAPS)));
                batchModel.setAvailQty(cursor.getString(cursor.getColumnIndex(COLUMN_AVAIL_QTY)));
                batchModel.setManfDate(cursor.getLong(cursor.getColumnIndex(COLUMN_MANF_DATE)));
                batchModel.setSellRateWithTax(cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_RATE_WITH_TAX)));

                prodBatchModels.add(batchModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return prodBatchModels;
    }


    List<UomMasterModel> fetchAllUomsOfProduct(SFADatabase sfaDatabase, String productId) {
        List<UomMasterModel> uomMasterModels = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT * FROM  m_product_uom_master WHERE  prodcode = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{productId});
        UomMasterModel uomMasterModel;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                uomMasterModel = new UomMasterModel();
                uomMasterModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                uomMasterModel.setProdCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_CODE)));
                uomMasterModel.setUomCode(cursor.getString(cursor.getColumnIndex(COLUMN_UOM_CODE)));
                uomMasterModel.setUomDescription(cursor.getString(cursor.getColumnIndex(COLUMN_UOM_DESCRIPTION)));
                uomMasterModel.setUomConvFactor(cursor.getString(cursor.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                uomMasterModel.setBaseUOM(cursor.getString(cursor.getColumnIndex(COLUMN_BASE_UOM)));
                uomMasterModel.setDefaultUOM(cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULT_UOM)));

                uomMasterModels.add(uomMasterModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return uomMasterModels;
    }


    public List<LogisticStockModel> getLogisticStockForUpload(BaseDB baseDB) {
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distCode = preferences.readString(PREF_DISTRCODE);

        List<LogisticStockModel> logisticStockModels = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_LOGISTIC_MATERIAL_STOCK + " WHERE cmpCode = ? COLLATE NOCASE AND distrCode=? " +
                "AND uploadFlag = 'N' ";
        cursor = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                LogisticStockModel logisticStockModel = new LogisticStockModel();

                logisticStockModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                logisticStockModel.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                logisticStockModel.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO)).trim());
                logisticStockModel.setMaterialCode(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIAL_CODE)).trim());
                logisticStockModel.setMaterialName(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIAL_NAME)).trim());
                logisticStockModel.setReceivedQty(cursor.getString(cursor.getColumnIndex(COLUMN_RECEIVED_QTY)).trim());
                logisticStockModel.setReturnedQty(cursor.getString(cursor.getColumnIndex(COLUMN_RETURNED_QTY)).trim());
                logisticStockModel.setModDt(String.valueOf(System.currentTimeMillis()));
                logisticStockModel.setUploadFlag(cursor.getString(cursor.getColumnIndex(UPLOAD_FLAG)).trim());

                logisticStockModels.add(logisticStockModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return logisticStockModels;
    }


    List<ProdBatchModel> fetchAllOtherBatchesOfProduct(SFADatabase sfaDatabase, String productId) {
        List<ProdBatchModel> prodBatchModels = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT DISTINCT pbm.mrp, \n" +
                "                pbm.sellprice, \n" +
                "                pbm.purchaseprice, \n" +
                "               (select soh.availQty from m_stockOnHand soh where soh.prodCode = pbm.prodCode and soh.prodBatchCode = pbm.prodBatchCode) as availQty \n" +
                "FROM   m_product_batch_master pbm \n" +
                "WHERE  pbm.prodcode = ? ";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{productId});
        ProdBatchModel batchModel;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                batchModel = new ProdBatchModel();
                batchModel.setPurchaseRate(cursor.getDouble(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                batchModel.setMrp(cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP_CAPS)));
                batchModel.setSellRate(cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_PRICE_CAPS)));
                batchModel.setAvailQty(cursor.getString(cursor.getColumnIndex(COLUMN_AVAIL_QTY)));

                prodBatchModels.add(batchModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return prodBatchModels;
    }

    List<OrderBookingVO> fetchAllTaxofProduct(SFADatabase sfaDatabase, String prodCode) {
        List<OrderBookingVO> orderBookingVOList = new ArrayList<>();

        String query;
        Cursor cursor;
        query = "SELECT * FROM  m_product_tax_master WHERE  prodCode = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{prodCode});
        OrderBookingVO orderBookingVO;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                orderBookingVO = new OrderBookingVO();
                orderBookingVO.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                orderBookingVO.setProdCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_CODE)));
                orderBookingVO.setCgstperc(cursor.getDouble(cursor.getColumnIndex(COLUMN_CGST)));
                orderBookingVO.setSgstPerc(cursor.getDouble(cursor.getColumnIndex(COLUMN_SGST)));
                orderBookingVO.setIgstPerc(cursor.getDouble(cursor.getColumnIndex(COLUMN_IGST)));
                orderBookingVO.setUtgstPerc(cursor.getDouble(cursor.getColumnIndex(COLUMN_CESS)));

                orderBookingVOList.add(orderBookingVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return orderBookingVOList;
    }

    public boolean isLogisticAvailable(SFADatabase sfaDatabase, String invoiceNo) {
        sfaDatabase.openWritableDb();
        String query = "SELECT * FROM " + TABLE_LOGISTIC_MATERIAL_STOCK + " WHERE invoiceNo = '" + invoiceNo + "'";
        Cursor cm = sfaDatabase.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();

        return false;
    }

    List<MTDReportModel> getUniqueProductLevels(SFADatabase sfaDatabase, String[] whereArgs, String codeColumn, String nameColumn, String groupBy, String where) {

        List<MTDReportModel> channels = new ArrayList<>();
        MTDReportModel channelModel = null;
        String query;
        Cursor c;
        if (whereArgs == null) {
            query =  "select " + codeColumn + " , " + nameColumn + " from r_sales_prodhier_product_wise_report_detail group by " + groupBy;
            c = sfaDatabase.getDb().rawQuery(query, null);
        } else {
            query = "select " + codeColumn + " , " + nameColumn + " from r_sales_prodhier_product_wise_report_detail where " + where+ " group by " + groupBy;
            c = sfaDatabase.getDb().rawQuery(query, whereArgs);
        }
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                channelModel = new MTDReportModel();
                channelModel.setLevelCode1(c.getString(c.getColumnIndex(codeColumn)));
                channelModel.setLevelName1(c.getString(c.getColumnIndex(nameColumn)));

                if(!channelModel.getLevelCode1().isEmpty()) {
                    channels.add(channelModel);
                }
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return channels;
    }

    public List<String> getAllMustSellBilledProducts(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode) {
        List<String> list = new ArrayList<>();
        String query = "select prodCode from " + TABLE_MUST_SELL_BILLING_STATUS + " where cmpCode = ?  and distrCode = ? " +
                "and customerCode = ? and billedStatus = 'Y'";

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(query, new String[]{cmpCode, distrCode, customerCode});
        if (isCursorTrue(c)) {
            do {
                String prodCode = c.getString(c.getColumnIndex(COLUMN_PROD_CODE));
                List<String> arrList = Arrays.asList(prodCode.split(","));
                list.addAll(arrList);
            } while (c.moveToNext());
        }
        closeCursor(c);
        return list;
    }

    private boolean isCursorTrue(Cursor cursorPending) {
        return cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst();
    }

    private void closeCursor(Cursor c) {
        if (c != null) {
            c.close();
        }
    }
}
