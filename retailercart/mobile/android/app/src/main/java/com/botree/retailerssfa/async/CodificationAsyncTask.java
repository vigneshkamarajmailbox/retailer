package com.botree.retailerssfa.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.db.query.CodeGeneratorQueryHelper;
import com.botree.retailerssfa.models.AddressModel;
import com.botree.retailerssfa.models.CodeGeneratorModel;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_SHIP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER_SHIP_ADDRESS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_CUSTOMER;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_CUTOMER_SHIP_ADDRESS;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_ROUTE;
/*
 *Created by Godlin Josheela Rani S on 04/11/19.
 */

public class CodificationAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    private final WeakReference<Activity> contextRef;
    private SFADatabase sfaDatabase;
    private String distrCode;
    private String cmpCode;
    CodificationCompleteListener listener;

    public CodificationAsyncTask(Activity context, CodificationCompleteListener listener) {
        contextRef = new WeakReference<>(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        sfaDatabase = SFADatabase.getInstance(context);
        distrCode = preferences.readString(SFASharedPref.PREF_DISTRCODE);
        cmpCode = preferences.readString(SFASharedPref.PREF_CMP_CODE);
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        List<RouteModel> routesList;
        List<CustomerModel> customersList;
        List<AddressModel> customerShipAddrList;
        List<RouteModel> customerRouteList;
        int tempRouteCode;
        int tempCustomerCode;
        int tempCustomerShipCode;

        String suffixYy;
        int codeNo;
        String prefix;

        try {
            List<RouteModel> tempRoutes = sfaDatabase.getAllTempRoutes(distrCode);
            List<CustomerModel> tempCustomers = sfaDatabase.getAllTempCustomers(distrCode);
            List<AddressModel> tempCustomerShipAddr = sfaDatabase.getAllTempCustomerShipAddress(cmpCode, distrCode);
            List<RouteModel> tempCustomerRoutes = sfaDatabase.getAllTempCustomerRoute(cmpCode, distrCode);

            routesList = new ArrayList<>(tempRoutes);
            prefix = sfaDatabase.getPrefixForScreen(SCREEN_NAME_ROUTE);
            suffixYy = sfaDatabase.getSuffixYyForScreen(SCREEN_NAME_ROUTE);

            codeNo = getCorrectCode(TABLE_ROUTE, COLUMN_ROUTE_CODE, SCREEN_NAME_ROUTE);
            for (RouteModel routeModel : routesList) {
                routeModel.setRouteCode(prefix + suffixYy + AppUtils.formatCode(codeNo));
                codeNo = codeNo + 1;
            }
            tempRouteCode = codeNo;

            customersList = new ArrayList<>(tempCustomers);
            prefix = sfaDatabase.getPrefixForScreen(SCREEN_NAME_CUSTOMER);
            suffixYy = sfaDatabase.getSuffixYyForScreen(SCREEN_NAME_CUSTOMER);

            codeNo = getCorrectCode(TABLE_CUSTOMER, COLUMN_CUSTOMER_CODE, SCREEN_NAME_CUSTOMER);
            for (CustomerModel model : customersList) {
                model.setCustomerCode(distrCode + prefix + suffixYy + AppUtils.formatCode(codeNo));
                codeNo = codeNo + 1;
            }
            tempCustomerCode = codeNo;

            customerShipAddrList = new ArrayList<>(tempCustomerShipAddr);
            prefix = sfaDatabase.getPrefixForScreen(SCREEN_NAME_CUTOMER_SHIP_ADDRESS);
            suffixYy = sfaDatabase.getSuffixYyForScreen(SCREEN_NAME_CUTOMER_SHIP_ADDRESS);

            codeNo = getCorrectCode(TABLE_CUSTOMER_SHIP_ADDRESS, COLUMN_CUSTOMER_SHIP_CODE, SCREEN_NAME_CUTOMER_SHIP_ADDRESS);
            for (AddressModel model : customerShipAddrList) {
                model.setShipCode(prefix + suffixYy + AppUtils.formatCode(codeNo));
                model.setCustomerCode(getCustomerCode(model.getTempCustomerCode(), customersList));
                codeNo = codeNo + 1;
            }
            tempCustomerShipCode = codeNo;

            customerRouteList = new ArrayList<>(tempCustomerRoutes);
            for (RouteModel model : customerRouteList) {
                String customerCode = getCustomerCode(model.getTempCustomerCode(), customersList);
//                if (customerCode.isEmpty()) {
//                    customerCode = sfaDatabase.getCustomerCodeFromTempCustomerCode(model.getTempCustomerCode());
//                }
                String routeCode = getRouteCode(model.getTempRouteCode(), routesList);
//                if (routeCode.isEmpty()) {
//                    routeCode = sfaDatabase.getRouteCodeFromTempRouteCode(model.getTempRouteCode());
//                }
                model.setCustomerCode(customerCode);
                model.setRouteCode(routeCode);
            }

            sfaDatabase.insertRoutes(routesList);
            sfaDatabase.insertCustomerDetail(customersList);
            sfaDatabase.insertCustomerRoute(cmpCode, distrCode, customerRouteList, "");
            sfaDatabase.insertCustomerShipAddressDetail(customerShipAddrList, cmpCode, distrCode, "");
            sfaDatabase.updateTempCodificationTables(routesList, customersList, customerShipAddrList, customerRouteList);

            CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel(cmpCode, distrCode,
                    SCREEN_NAME_ROUTE, SCREEN_NAME_ROUTE, suffixYy, tempRouteCode);
            CodeGeneratorQueryHelper.updateCode(sfaDatabase, codeGeneratorModel);

            codeGeneratorModel = new CodeGeneratorModel(cmpCode, distrCode,
                    SCREEN_NAME_CUSTOMER, SCREEN_NAME_CUSTOMER, suffixYy, tempCustomerCode);
            CodeGeneratorQueryHelper.updateCode(sfaDatabase, codeGeneratorModel);

            codeGeneratorModel = new CodeGeneratorModel(cmpCode, distrCode,
                    SCREEN_NAME_CUTOMER_SHIP_ADDRESS, SCREEN_NAME_CUTOMER_SHIP_ADDRESS, suffixYy, tempCustomerShipCode);
            CodeGeneratorQueryHelper.updateCode(sfaDatabase, codeGeneratorModel);
            return true;
        } catch (Exception e) {
            Log.e("CODIFICATION", e.toString());
            return false;
        }
    }

    private String getCustomerCode(String tempCustomerCode, List<CustomerModel> customerModels) {
        for (CustomerModel customerModel : customerModels) {
            if (customerModel.getTempCustomerCode().equalsIgnoreCase(tempCustomerCode)) {
                return customerModel.getCustomerCode();
            }
        }
        return sfaDatabase.getCustomerCodeFromTempCustomerCode(tempCustomerCode);
    }

    private String getRouteCode(String tempRouteCode, List<RouteModel> routeModels) {
        for (RouteModel routeModel : routeModels) {
            if (routeModel.getTempRouteCode().equalsIgnoreCase(tempRouteCode)) {
                return routeModel.getRouteCode();
            }
        }
        return sfaDatabase.getRouteCodeFromTempRouteCode(tempRouteCode);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        AppUtils.getOurInstance().showProgressDialog(contextRef.get(), "Codification in progress...");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        AppUtils.getOurInstance().cancleProgressDialog();
        listener.onCodificationCompleted(aBoolean);
    }

    public interface CodificationCompleteListener {
        void onCodificationCompleted(boolean success);
    }

    private int getCorrectCode(String tableName, String columnName, String codeType) {
//        String code = sfaDatabase.getMaxCodeValueUsed(tableName, columnName);
        int actualCode = AppUtils.generateIntCode(sfaDatabase, codeType);
//        try {
//            int maxVal = 0;
//            if (code.length() > 5) {
//                maxVal = Integer.parseInt(code.substring(code.length() - 5));
//            }
//            if (actualCode > maxVal) {
                return actualCode;
//            } else {
//                return maxVal + 1;
//            }
//        } catch (Exception e) {
//            return actualCode;
//        }
    }
}
