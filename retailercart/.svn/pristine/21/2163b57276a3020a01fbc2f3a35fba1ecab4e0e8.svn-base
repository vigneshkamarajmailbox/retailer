package com.botree.retailerssfa.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.TaxMasterModel;
import com.botree.retailerssfa.models.TaxModel;
import com.botree.retailerssfa.scheme.SchemeEngin;
import com.botree.retailerssfa.support.Globals;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_PRIMARY_DISC;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_PRIORITY;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_WEIGHT_BASED_UOM;
import static com.botree.retailerssfa.controller.constants.AppConstant.ORDER_SCREEN_TYPE_CUSTOM;
import static com.botree.retailerssfa.controller.constants.AppConstant.ORDER_SCREEN_TYPE_QUICK;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_GM;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_KG;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_LT;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_ML;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_GST_STATE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.IS_UNION_TERRITORY;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_GST_STATE_MASTER;
import static com.botree.retailerssfa.support.Globals.ORDER_BOOKING_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ORDER_BOOKING_OPTIONS;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;

public class OrderSupportUtil {
    public static final String ALL_BRANDS = "All Brands";
    public static final String ALL_CATEGORY = "All Category";
    public static final String MUST_SELL = "Must Sell";
    public static final String PROMO = "Promo";
    public static final String OUT_OF_STOCK = "OutOfStock";
    public static final String TOP10_SKU_BRAND = "Top SKU";
    public static final String MUST_SELL_BILLED = "MustSellBilled";
    public static final String FOCUS_BRAND = "Focus";
    public static final String IS_QTY_DIALOG_SHOWING = "is_qty_dialog_showing";
    public static final String IS_SCHEME_DIALOG_SHOWING = "is_scheme_dialog_showing";
    public static final String IS_FILTER_DIALOG_SHOWING = "is_filter_dialog_showing";
    public static final String QTY_POSITION = "qty_position";
    public static final String SCHEME_POSITION = "scheme_position";
    public static final String ENTERED_QTY = "entered_qty";
    public static final String ENTERED_REMARKS = "entered_remarks";
    public static final String IS_REMARKS_FOCUSED = "is_remarks_focused";
    public static final String ENTERED_UOM = "entered_uom";
    public static final String QTY_LIST = "qtyList";
    public static final String ORDER_BOOKING_LIST = "order_booking_list";
    public static final String SEARCH_TEXT = "search_text";
    public static final String BRAND_TEXT = "brand_text";
    public static final String CATEGORY_TEXT = "category_text";
    public static final String SEARCH_LIST = "search_list";
    public static final String ALREADY_BOOKING_LIST = "already_booking_list";
    private static final String TAG = OrderSupportUtil.class.getSimpleName();
    private static OrderSupportUtil ourInstance;
    private static WeakReference<Context> activityRef;
    public SFADatabase db;
    private SFASharedPref preferences;

    private OrderSupportUtil() {
        preferences = SFASharedPref.getOurInstance();
        db = SFADatabase.getInstance(activityRef.get());
    }

    public static OrderSupportUtil getInstance() {
        return ourInstance;
    }

    public static void initOrderSupportUtil(Context context) {
        activityRef = new WeakReference<>(context);
        if (ourInstance == null) {
            ourInstance = new OrderSupportUtil();
        }
    }

    @NonNull
    public static ESFAFragTags getBillingFragBasedOnConfig(SFASharedPref preferences) {
        ESFAFragTags fragTags;


        String orderOption = preferences.readString(PREF_ORDER_BOOKING_OPTIONS);
        if (orderOption.equalsIgnoreCase(ORDER_SCREEN_TYPE_QUICK)) {
            fragTags = ESFAFragTags.QUICK_BILLING;
        } else if (orderOption.equalsIgnoreCase(ORDER_SCREEN_TYPE_CUSTOM)) {
            fragTags = ESFAFragTags.ORDER_BOOKING;
        } else {
            fragTags = ESFAFragTags.BILLING_ADD_PRODUCT;
        }

        return fragTags;
    }

    public static double getRoundedOffNetAmount(double actualNet) {
        return (double) Math.round(actualNet);
    }

    /**
     * used to Applied primary discount to PTR based on Configuration
     *
     * @param orderValue  line order Value
     * @param primaryDisc primary discount for  the product
     * @return primary discount applied value
     */
    public double getPrimDiscAppliedOrderValue(double orderValue, double primaryDisc) {
        if (db.getConfigDataBasedOnName(CONFIG_PRIMARY_DISC).equalsIgnoreCase("Y") && primaryDisc > 0)
            return (orderValue / (1 + (primaryDisc / 100)));
        else
            return orderValue;
    }

    /**
     * helps to calculate line order value for the quantity
     *
     * @param orderBookingVO ordered product details object
     * @param uom            selected uom for the product
     * @param currentQty     entered quantity for the product
     * @return lineOrderValue
     */
    public double calculateLineOrderValue(OrderBookingVO orderBookingVO, String uom, double currentQty) {
        if (isWeightUom(uom)) {
            double conversionQty = calcWeightToUomConversion(orderBookingVO, uom, currentQty);
            return conversionQty
                    * orderBookingVO.getConversionFactor().get(orderBookingVO.getDefaultUomid())
                    * orderBookingVO.getSellPrice()
                    .doubleValue();
        } else {
            return currentQty
                    * orderBookingVO.getConversionFactor().get(uom)
                    * orderBookingVO.getSellPrice()
                    .doubleValue();
        }
    }

    /**
     * helps to calculate line order value for the quantity
     *
     * @param orderBookingVO ordered product details object
     * @param uom            selected uom for the product
     * @param currentQty     entered quantity for the product
     * @return lineOrderValue
     */
    public BigDecimal calculateLineOrderValueBigDecimal(OrderBookingVO orderBookingVO, String uom, double currentQty) {
        if (isWeightUom(uom)) {
            BigDecimal conversionQty = calcWeightToUomConversionBigDecimal(orderBookingVO, uom, currentQty);

            return conversionQty.multiply(
                    BigDecimal.valueOf(orderBookingVO.getConversionFactor().get(uom)).multiply(orderBookingVO.getSellPrice()));

        } else {

            return BigDecimal.valueOf(currentQty).multiply(
                    BigDecimal.valueOf(orderBookingVO.getConversionFactor().get(uom)).multiply(orderBookingVO.getSellPrice()));
        }
    }

    /**
     * Used to convert current to Slab uom weight
     *
     * @param orderBookingVO order single product details
     * @param weightUom      weight  uom for the scheme slab
     * @param enteredQty     current qty of order prod
     * @return converted qty in double type
     */
    private BigDecimal calcWeightToUomConversionBigDecimal(OrderBookingVO orderBookingVO, String weightUom, double enteredQty) {
        BigDecimal conversionQty = BigDecimal.valueOf(0.0);
        if (orderBookingVO.getWeightType().equalsIgnoreCase(weightUom)) {
            conversionQty = BigDecimal.valueOf(enteredQty).divide(BigDecimal.valueOf(orderBookingVO.getNetWeight()), BigDecimal.ROUND_UNNECESSARY);
        } else {
            if ((orderBookingVO.getWeightType().equalsIgnoreCase(UOM_KG) && weightUom.equalsIgnoreCase(UOM_GM))
                    || (orderBookingVO.getWeightType().equalsIgnoreCase(UOM_LT) && weightUom.equalsIgnoreCase(UOM_ML))) {

//                conversionQty = enteredQty / (orderBookingVO.getNetWeight() * 1000);

                conversionQty = BigDecimal.valueOf(enteredQty).divide(BigDecimal.valueOf(orderBookingVO.getNetWeight()).multiply(BigDecimal.valueOf(1000)));

            } else if ((orderBookingVO.getWeightType().equalsIgnoreCase(UOM_GM) && weightUom.equalsIgnoreCase(UOM_KG))
                    || (orderBookingVO.getWeightType().equalsIgnoreCase(UOM_ML) && weightUom.equalsIgnoreCase(UOM_LT))) {

//                conversionQty = ((enteredQty * 1000) / (orderBookingVO.getNetWeight()));

                conversionQty = BigDecimal.valueOf(enteredQty).multiply(BigDecimal.valueOf(1000).divide(BigDecimal.valueOf(orderBookingVO.getNetWeight()), BigDecimal.ROUND_UNNECESSARY));
            }
        }
        return conversionQty;

    }

    /**
     * helps to calculate line order value using purchase price for the quantity
     *
     * @param orderBookingVO ordered product details object
     * @param uom            selected uom for the product
     * @param currentQty     entered quantity for the product
     * @return lineOrderValue
     */
    public double calcLineOrderPurPriceValue(OrderBookingVO orderBookingVO, String uom, double currentQty) {
        if (isWeightUom(uom)) {
            double conversionQty = calcWeightToUomConversion(orderBookingVO, uom, currentQty);
            return conversionQty
                    * orderBookingVO.getConversionFactor().get(orderBookingVO.getDefaultUomid())
                    * orderBookingVO.getPurchasePrice();
        } else {
            return currentQty
                    * orderBookingVO.getConversionFactor().get(uom)
                    * orderBookingVO.getPurchasePrice();
        }
    }

    /**
     * helps to check selling price and then calculate line order value for the quantity
     *
     * @param orderBookingVO ordered product details object
     * @param uom            selected uom for the product
     * @param currentQty     entered quantity for the product
     * @return lineOrderValue
     */
    public double getOrderValueCheckSellingPrice(double currentQty, OrderBookingVO orderBookingVO, String uom) {
        double orderValue = 0.0;
        if (orderBookingVO.getSellPrice().doubleValue() > 0.0) {
            orderValue = calculateLineOrderValue(orderBookingVO, uom, currentQty);
        }
        return orderValue;
    }

    /**
     * this method helps to calculate tax scheme reduce applies order value
     *
     * @param schemeAmount scheme amount for the product
     * @param orderValue   order value for the product
     * @param schemeReduce scheme reduce for the retailer
     * @return order value after scheme reduce type applied
     */
    public double applySchemeReduceToOrderValue(double schemeAmount, double orderValue, String schemeReduce) {
        double calcualteOrderValue;
        if (schemeReduce.equalsIgnoreCase("R")) {
            calcualteOrderValue = orderValue - schemeAmount;
        } else if (schemeReduce.equalsIgnoreCase("A")) {
            calcualteOrderValue = orderValue + schemeAmount;
        } else {
            calcualteOrderValue = orderValue;
        }
        return calcualteOrderValue;
    }

    /**
     * used to fetch the product tax model from the db by checking the GST TYPE
     *
     * @param bookingVO         ordered product details object
     * @param distStateCode     current distrbutor state code
     * @param retailerStateCode reatiler state Code
     * @return Tax model list for the product
     */
    public List<TaxModel> fetchProductTaxList(OrderBookingVO bookingVO, String distStateCode, String retailerStateCode) {
        List<TaxModel> taxModelList = new ArrayList<>();
        if (!distStateCode.isEmpty() && !retailerStateCode.isEmpty()) {
            if (distStateCode.equalsIgnoreCase(retailerStateCode)) {
                taxModelList = getTaxModelsForUnionTeritory(bookingVO, distStateCode, retailerStateCode);
            } else {
                taxModelList = db.getTaxCalPercentStates(preferences.readString(PREF_DISTRCODE),
                        bookingVO.getProdCode(), retailerStateCode);
                if (!taxModelList.isEmpty() && taxModelList.get(0) != null) {
                    bookingVO.setTaxCode(taxModelList.get(0).getTaxCode());
                }
            }

        }
        return taxModelList;
    }

    /**
     * used to calculate the tax value
     *
     * @param orderValue    calculated Order value
     * @param outputTaxPerc percentage to calculate tax
     * @return taxValue
     */
    public double calculateTaxValue(double orderValue, double outputTaxPerc) {
        return (orderValue * (outputTaxPerc / 100.00));
    }

    /**
     * get non combi product scheme list
     *
     * @param prodCode            product hierarchy path
     * @param schemeProductDetail scheme product list
     * @return get Non combi scheme list
     */
    public List<SchemeModel> getNonCombiSchemeList(String prodCode, List<SchemeModel> schemeProductDetail) {
        List<SchemeModel> tempProductSchemeModelList = new ArrayList<>();
        for (SchemeModel schemeModel : schemeProductDetail) {
            if (prodCode.equalsIgnoreCase(schemeModel.getProductCode())
                    && schemeModel.getCombi().equalsIgnoreCase("N")) {
                tempProductSchemeModelList.add(schemeModel);
            }
        }
        return tempProductSchemeModelList;
    }

    /**
     * used to convert the quantity to selected uom qty
     *
     * @param orderBookingVO ordered product details object
     * @param uom            selected uom for the product
     * @param currentQty     current entered quantity
     * @return uom conversion quantity
     */
    public int calcUomConversionQty(OrderBookingVO orderBookingVO, String uom, double currentQty) {

        if (!uom.isEmpty())
            if (isWeightUom(uom)) {
                double conversionQty = calcWeightToUomConversion(orderBookingVO, uom, currentQty);
//            if (conversionQty % 1 == 0)
                return (int) conversionQty * orderBookingVO.getConversionFactor().get(orderBookingVO.getDefaultUomid());
//            return -1;
            } else {
                return (int) (currentQty * orderBookingVO.getConversionFactor().get(uom));
            }
        return (int) currentQty;
    }

    public boolean isWeightUom(String uom) {
        return UOM_KG.equalsIgnoreCase(uom) || UOM_LT.equalsIgnoreCase(uom) || UOM_GM.equalsIgnoreCase(uom) || UOM_ML.equalsIgnoreCase(uom);
    }

    /**
     * used to convert the quantity to selected uom qty
     *
     * @param orderBookingVO ordered product details object
     * @param uom            selected uom for the product
     * @param currentQty     current entered quantity
     * @return uom conversion quantity
     */
    public double calcWeightUomConversionQty(OrderBookingVO orderBookingVO, String uom, double currentQty) {

        if (isWeightUom(uom)) {
            double conversionQty = calcWeightToUomConversion(orderBookingVO, uom, currentQty);
            return conversionQty * orderBookingVO.getConversionFactor().get(orderBookingVO.getDefaultUomid());
        } else {
            return (currentQty * orderBookingVO.getConversionFactor().get(uom));
        }
    }

    /**
     * Used to convert current to Slab uom weight
     *
     * @param orderBookingVO order single product details
     * @param weightUom      weight  uom for the scheme slab
     * @param enteredQty     current qty of order prod
     * @return converted qty in double type
     */
    private double calcWeightToUomConversion(OrderBookingVO orderBookingVO, String weightUom, double enteredQty) {
        double conversionQty = 0d;
        if (orderBookingVO.getWeightType().equalsIgnoreCase(weightUom)) {
            conversionQty = enteredQty / orderBookingVO.getNetWeight();
        } else {
            if ((orderBookingVO.getWeightType().equalsIgnoreCase(UOM_KG) && weightUom.equalsIgnoreCase(UOM_GM))
                    || (orderBookingVO.getWeightType().equalsIgnoreCase(UOM_LT) && weightUom.equalsIgnoreCase(UOM_ML))) {
                conversionQty = enteredQty / (orderBookingVO.getNetWeight() * 1000);
            } else if ((orderBookingVO.getWeightType().equalsIgnoreCase(UOM_GM) && weightUom.equalsIgnoreCase(UOM_KG))
                    || (orderBookingVO.getWeightType().equalsIgnoreCase(UOM_ML) && weightUom.equalsIgnoreCase(UOM_LT))) {
                conversionQty = ((enteredQty * 1000) / (orderBookingVO.getNetWeight()));
            }
        }
        return conversionQty;

    }

    /**
     * helps to add the scheme amount with the order value
     *
     * @param prodCode           order product code
     * @param appliedCombiScheme applied scheme model list
     * @param normalSchemeAmount schemeAmount
     * @param j                  scheme model position
     * @return sum of normal scheme amount
     */
    public double getSchemeAmount(String prodCode, String batchCode, List<SchemeModel> appliedCombiScheme, double normalSchemeAmount, int j) {
        if (prodCode.equalsIgnoreCase(appliedCombiScheme.get(j).getProductCode())
                && batchCode.equalsIgnoreCase(appliedCombiScheme.get(j).getProdBatchCode())) {
            normalSchemeAmount = normalSchemeAmount + appliedCombiScheme.get(j).getFlatAmount();
        }
        return normalSchemeAmount;
    }

    public List<OrderBookingVO> filterQtyAvailOProdList(List<String> orderNos, List<OrderBookingVO> qtyUpdatedOrderBooking) {


//            String suffixYy = activity.db.getSuffixYyForScreen(ORDER_BOOKING_NAME);
        String code = DateUtil.getCurrentDateAndTimeForCodeGen() + AppUtils.generateCode(db, ORDER_BOOKING_NAME);
//            String prefix = sfaDatabase.getPrefixForScreen(SCREEN_NAME_ROUTE);
        List<OrderBookingVO> tempOrderBookingVOList = new ArrayList<>();

        if (orderNos.isEmpty()) {

            for (OrderBookingVO orderBookingVO : qtyUpdatedOrderBooking) {
                if (orderBookingVO.getQuantity() > 0 || (orderBookingVO.getQuantity() == 0 && orderBookingVO.getFreeQty() > 0)) {
                    String invoiceid = orderBookingVO.getRetailerCode() + code;
                    orderBookingVO.setOrderInvoiceNo(invoiceid);
                    orderBookingVO.setReadableInvNo(code);
                    orderBookingVO.setStartTime(Globals.getOurInstance().getRetailerStartTime());
                    orderBookingVO.setEndTime(""+System.currentTimeMillis());
                    orderBookingVO.setLatitude(String.valueOf(Globals.getOurInstance().getRetrlatitude()));
                    orderBookingVO.setLongitude(String.valueOf(Globals.getOurInstance().getRettlongitude()));
                    tempOrderBookingVOList.add(orderBookingVO);
                }
            }
        } else {

            for (OrderBookingVO orderBookingVO : qtyUpdatedOrderBooking) {
                if (orderBookingVO.getQuantity() > 0 || (orderBookingVO.getQuantity() == 0 && orderBookingVO.getFreeQty() > 0)) {
                    String invoiceid = orderBookingVO.getReadableInvNo();
                    if (invoiceid == null) {
                        invoiceid = orderNos.get(0);
                    }
                    orderBookingVO.setOrderInvoiceNo(orderBookingVO.getRetailerCode() + validateOrderDate(invoiceid));
                    orderBookingVO.setReadableInvNo(validateOrderDate(invoiceid));
                    orderBookingVO.setStartTime(Globals.getOurInstance().getRetailerStartTime());
                    orderBookingVO.setEndTime(DateUtil.getCurrentDateAndTime());
                    orderBookingVO.setLatitude(String.valueOf(Globals.getOurInstance().getRetrlatitude()));
                    orderBookingVO.setLongitude(String.valueOf(Globals.getOurInstance().getRettlongitude()));
                    tempOrderBookingVOList.add(orderBookingVO);
                }
            }
        }


        return tempOrderBookingVOList;
    }

    String validateOrderDate(String invoiceNo) {
        String suffix = invoiceNo.substring(invoiceNo.length() - 5);
        String prefix = invoiceNo.substring(0, invoiceNo.length() - 5);

        if (!prefix.equals(DateUtil.getCurrentDateAndTimeForCodeGen())) {
            return DateUtil.getCurrentDateAndTimeForCodeGen() + suffix;
        }
        return invoiceNo;
    }

    /**
     * used to fetch the tax model for the product
     *
     * @param bookingVO         product detail
     * @param distStateCode     distr stat code
     * @param retailerStateCode retailer state code
     * @return taxModelList
     */
    @NonNull
    private List<TaxModel> getTaxModelsForUnionTeritory(OrderBookingVO bookingVO, String distStateCode, String retailerStateCode) {
        List<TaxModel> taxModelList;
        if (db.isDistUnionTeritory(distStateCode)) {
            taxModelList = db.getTaxCalPercentForUT(preferences.readString(PREF_DISTRCODE),
                    bookingVO.getProdCode(), retailerStateCode, true);
            if (!taxModelList.isEmpty() && taxModelList.get(0) != null) {
                bookingVO.setTaxCode(taxModelList.get(0).getTaxCode());
            }
        } else {
            taxModelList = db.getTaxCalPercentForUT(preferences.readString(PREF_DISTRCODE),
                    bookingVO.getProdCode(), retailerStateCode, false);
            if (!taxModelList.isEmpty() && taxModelList.get(0) != null) {
                bookingVO.setTaxCode(taxModelList.get(0).getTaxCode());
            }
        }
        return taxModelList;
    }

    /**
     * used to map the products to mustSell ,Focus and Scheme Products.
     *
     * @param orderBookingVOList  all product list
     * @param mustSellProduct     must sell product list
     * @param focusBrandProduct   focus brand product list
     * @param schemeProductDetail scheme product list
     * @return product list with mapped filter items
     */
    public List<OrderBookingVO> mapFilterProducts(List<OrderBookingVO> orderBookingVOList, List<String> mustSellProduct,
                                                  List<String> focusBrandProduct, List<SchemeModel> schemeProductDetail) {
        int orderBookingVoListSize = orderBookingVOList.size();
        int mustSellProductSize = mustSellProduct.size();
        int focusBrandProductSize = focusBrandProduct.size();
        for (int i = 0; i < orderBookingVoListSize; i++) {
            for (int j = 0; j < mustSellProductSize; j++) {
                if (orderBookingVOList.get(i).getProductHierPath().contains(mustSellProduct.get(j))) {
                    orderBookingVOList.get(i).setMustcategory(MUST_SELL);
                    orderBookingVOList.get(i).setCategory(MUST_SELL);
                }
            }
            for (int j = 0; j < focusBrandProductSize; j++) {
                if (orderBookingVOList.get(i).getProductHierPath().contains(focusBrandProduct.get(j))) {
                    orderBookingVOList.get(i).setFocusCategory(FOCUS_BRAND);
                    orderBookingVOList.get(i).setCategory(FOCUS_BRAND);
                }
            }
            mapSchemeProductToList(orderBookingVOList, schemeProductDetail, i);
        }
        return orderBookingVOList;
    }

    /**
     * used to map schemes to  product list
     *
     * @param orderBookingVOList all product list
     * @param schemeProductList  scheme product list
     * @param i                  product list position
     */
    private void mapSchemeProductToList(List<OrderBookingVO> orderBookingVOList, List<SchemeModel> schemeProductList, int i) {
        int schemeProductDetailSize = schemeProductList.size();
        for (int j = 0; j < schemeProductDetailSize; j++) {
            if (orderBookingVOList.get(i).getProdCode().equalsIgnoreCase(schemeProductList.get(j).getProductCode())) {
                orderBookingVOList.get(i).setProductStatus(PROMO);
            }
        }
    }

    /**
     * Calculates the total items selected and item value to Display in Items
     * Layout
     *
     * @param orderBookingVOList calculate the total amt and count from this list
     * @param orderCountTxt      update the calculated count in this TextView
     * @param orderTotalTxt      update the calculated amount in this TextView
     * @param isPreviousOrder    true if it is previous order
     */
    public TextView calculateOrderTotal(List<OrderBookingVO> orderBookingVOList, TextView orderCountTxt, TextView orderTotalTxt, boolean isPreviousOrder) {
        int itemsCount = 0;
        double itemsValue = 0.0d;
        if (orderBookingVOList != null && !orderBookingVOList.isEmpty()) {
            for (OrderBookingVO orderBookingVO : orderBookingVOList) {
                if (orderBookingVO.getQuantity() > 0) {
                    try {
                        itemsValue = getItemsValue(itemsValue, orderBookingVO, isPreviousOrder);
                    } catch (Exception e) {
                        Log.e(TAG, "calculateOrderTotal: " + e.getMessage(), e);
                    }
                    itemsCount = itemsCount + 1;
                }
            }
        }
        if (orderCountTxt != null)
            orderCountTxt.setText(String.valueOf(itemsCount));


        if (orderTotalTxt != null)
            orderTotalTxt.setText(String.format(Locale.getDefault(), "%.2f", itemsValue));
        return orderTotalTxt;
    }

    /**
     * calculate and add old item Values
     *
     * @param itemsValue      order Product item value
     * @param orderBookingVO  order product details
     * @param isPreviousOrder true if it is previous order
     * @return added item value
     */
    private double getItemsValue(double itemsValue, OrderBookingVO orderBookingVO, boolean isPreviousOrder) {
        if (isPreviousOrder) {
            itemsValue = itemsValue + orderBookingVO.getQuantity()
                    * orderBookingVO.getConversionFactor().get(orderBookingVO.getUomId())
                    * orderBookingVO.getSellPrice().doubleValue();
        } else {
            itemsValue = itemsValue + orderBookingVO.getOrderValue().doubleValue();
        }
        return itemsValue;
    }

    /**
     * Fetch the category data from database and added that into category list
     */
    public List<String> loadCategoryList() {
        List<String> categoryList = new ArrayList<>();
        categoryList.add(ALL_CATEGORY);
        categoryList.addAll(db.fetchCategory());
        return categoryList;
    }

    public void setUomSpinner(int enteredUOM, Spinner uomSpinner) {
        if (enteredUOM != -1)
            uomSpinner.setSelection(enteredUOM);
    }

    public void setEnteredQty(String enteredQty, EditText editQty) {
        if (enteredQty.length() > 0 && !"0".equals(enteredQty)) {
            editQty.setText(enteredQty);
        }
    }

    public String getDefaultUomId(String defaultUomId, String uomId) {
        if (uomId != null && uomId.length() > 0)
            defaultUomId = uomId;
        return defaultUomId;
    }

    public String getRemarks(String enteredRemarks, OrderBookingVO orderBookingVO) {
        if (!"".equals(enteredRemarks))
            return enteredRemarks;
        else
            return orderBookingVO.getRemarks();
    }

    public void setFocusToRemarks(boolean isRemarksFocused, EditText editRemarks) {
        if (isRemarksFocused) {
            editRemarks.setSelection(editRemarks.getText().toString().length());
            editRemarks.requestFocus();
        }
    }

    /**
     * get the current qunatity from edit text
     *
     * @param editQty editText to enter Quantity
     * @return current entered qty or 0
     */
    public double getCurrentQuantityWithError(EditText editQty) {
        double currentQty = 0;
        try {
            boolean isQtyEmpty = "".equals(editQty.getText().toString());
            if (!isQtyEmpty) {
                currentQty = Double.parseDouble(editQty.getText().toString());
            } else {
//                editQty.setError(activityRef.get().getString(R.string.error_quantity));
            }
        } catch (Exception e) {
            Log.e(TAG, "getCurrentQuantityWithError: " + e.getMessage(), e);
        }
        return currentQty;
    }

    /**
     * get the current qunatity from edit text
     *
     * @param editQty editText to enter Quantity
     * @return current entered qty or 0
     */
    public double getCurrentQty(EditText editQty) {
        double currentQty = 0;
        boolean isQtyEmpty = "".equals(editQty.getText().toString());
        if (!isQtyEmpty) {
            currentQty = Double.parseDouble(editQty.getText().toString());
        }
        return currentQty;
    }

    /**
     * used to calculate the order value with uom conversion
     *
     * @param orderBookingVO ordered product details
     * @param lineTot        text view to show total order value
     * @param editQty        qunatity of the product
     * @param uomSpinner     selected uom
     */
    public void setLineTotalVale(OrderBookingVO orderBookingVO, TextView lineTot, EditText editQty, Spinner uomSpinner) {

        try {
            boolean isQtyEmpty = "".equals(editQty.getText().toString());
            if (!isQtyEmpty) {
                double currentQty = Double.parseDouble(editQty.getText().toString());
                double orderValue = calculateLineOrderValue(orderBookingVO, uomSpinner.getSelectedItem().toString(), currentQty);
                lineTot.setText(String.format(Locale.getDefault(), "%.2f", orderValue));
            }
        } catch (Exception e) {
            Log.e(TAG, "setLineTotalVale: ", e);
        }
    }

    /**
     * Fetch the brand data from database and added that into brand list
     */
    public List<String> loadBrandsList(String tableName, String salesmanCode) {
        List<String> brandsList = new ArrayList<>();
        brandsList.add(ALL_BRANDS);
        List<OrderBookingVO> brandDataList = getBrandDataList(tableName, salesmanCode);
        for (OrderBookingVO brandData : brandDataList) {
            brandsList.add(brandData.getBrandName());
        }
        return brandsList;
    }

    public List<String> loadAutoStockBrandsList(List<OrderBookingVO> brandDataList) {
        List<String> brandsList = new ArrayList<>();
        brandsList.add(ALL_BRANDS);
        for (OrderBookingVO brandData : brandDataList) {
            brandsList.add(brandData.getBrandName());
        }
        return brandsList;
    }

    /**
     * Fetch the brand data from database and added that into brand list
     */
    public List<String> loadVanSalesBrandsList(String tableName) {
        List<String> brandsList = new ArrayList<>();
        brandsList.add(ALL_BRANDS);
        List<OrderBookingVO> brandDataList = getVanBrandDataList(tableName);
        for (OrderBookingVO brandData : brandDataList) {
            brandsList.add(brandData.getBrandName());
        }
        return brandsList;
    }

    /**
     * used to fetch the brand list
     *
     * @param tableName    tables to fetch the brand data
     * @param salesmanCode salesmanCode to fetch the brand data
     * @return BrandList
     */
    public List<OrderBookingVO> getBrandDataList(String tableName, String salesmanCode) {
        return db.fetchBrands(preferences.readString(PREF_DISTRCODE), salesmanCode, tableName);
    }

    /**
     * used to fetch the brand list
     *
     * @param tableName tables to fetch the brand data
     * @return BrandList
     */
    public List<OrderBookingVO> getVanBrandDataList(String tableName) {
        return db.fetchVanSalesBrands(preferences.readString(PREF_DISTRCODE),
                preferences.readString(PREF_SALESMANCODE), tableName);
    }

    /**
     * used to fethc the state code for the retailer
     *
     * @param newRetailer  to check whether it is new retailer or not
     * @param retailerCode selected retailer code
     * @return stateCode
     */
    public String getRetailerStateCode(int newRetailer, String retailerCode) {
        String retailerStateCode;
        if (newRetailer == 1) {
            retailerStateCode = db.getNewRetaielrStateCode(preferences.readString(PREF_DISTRCODE),
                    preferences.readString(PREF_SALESMANCODE), retailerCode);
        } else {
            retailerStateCode = db.getRetaielrStateCode(preferences.readString(PREF_CMP_CODE),
                    preferences.readString(PREF_DISTRCODE), retailerCode);
        }
        return retailerStateCode;
    }

    /**
     * used to get the uom spinner list
     *
     * @param orderBooking ordered product list
     * @return uom string list
     */
    public List<String> getUomIdSpinnerList(List<OrderBookingVO> orderBooking) {
        List<String> uomStringList = new ArrayList<>();
        int listSize = orderBooking.size();
        for (int i = 0; i < listSize; i++) {
            uomStringList.add(orderBooking.get(i).getUomGroupId());
        }
        return uomStringList;
    }

    /**
     * this method used to convert the object list to OrderBooking list with type safe check
     *
     * @param tempOrderBookingVOList object list to be convert
     * @return converted order booking list
     */
    public List<OrderBookingVO> typeSafeCheckCast(List<Object> tempOrderBookingVOList) {
        List<OrderBookingVO> tempList = new ArrayList<>();
        for (Object object : tempOrderBookingVOList) {
            if (object instanceof OrderBookingVO) {
                tempList.add((OrderBookingVO) object);
            }
        }
        return tempList;
    }

    public List<SchemeDistrBudgetModel> typeSafeCheckCastBudget(List<Object> tempOrderBookingVOList) {
        List<SchemeDistrBudgetModel> tempList = new ArrayList<>();
        for (Object object : tempOrderBookingVOList) {
            if (object instanceof OrderBookingVO) {
                tempList.add((SchemeDistrBudgetModel) object);
            }
        }
        return tempList;
    }

    public List<SchemeModel> typeSafeCheckCastScheme(List<Object> tempOrderBookingVOList) {
        List<SchemeModel> tempList = new ArrayList<>();
        for (Object object : tempOrderBookingVOList) {
            if (object instanceof OrderBookingVO) {
                tempList.add((SchemeModel) object);
            }
        }
        return tempList;
    }

    /**
     * @return return string screen  type for order booking
     */
    public ESFAFragTags getPrefOrderBookingOption() {
        ESFAFragTags fragTags;

        String orderOption = preferences.readString(PREF_ORDER_BOOKING_OPTIONS);
        if (orderOption.equalsIgnoreCase(ORDER_SCREEN_TYPE_QUICK)) {
            fragTags = ESFAFragTags.QUICK_ORDER_BOOKING;

        } else if (orderOption.equalsIgnoreCase(ORDER_SCREEN_TYPE_CUSTOM)) {
            fragTags = ESFAFragTags.ORDER_BOOKING;
        } else {
            fragTags = ESFAFragTags.ORDER_BOOKING;

        }

        return fragTags;
    }

    public Dialog createDialogUsingProgressBar(Activity fragActivity, String tvMessage) {
        Dialog progressDialog = new Dialog(fragActivity, R.style.ThemeDialogCustom);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.layout_loading_spinner);
        TextView tvLoading = progressDialog.findViewById(R.id.tvLoading);
        RelativeLayout layoutLoding = progressDialog.findViewById(R.id.layoutLoading);
        layoutLoding.setVisibility(View.VISIBLE);
        layoutLoding.setBackgroundColor(ContextCompat.getColor(fragActivity, R.color.transparent));
        tvLoading.setText(tvMessage);
        return progressDialog;
    }

    /**
     * used to Prepare weight UOM for the product
     *
     * @param productObject current product object
     * @return list of Weight UOM
     */
    public List<String> getWeightUOMs(OrderBookingVO productObject) {
        List<String> weightUomList = new ArrayList<>();
        if (productObject.getWeightType() != null && (productObject.getWeightType().equalsIgnoreCase(UOM_GM) ||
                productObject.getWeightType().equalsIgnoreCase(UOM_KG))) {
            weightUomList.add(UOM_GM);
            weightUomList.add(UOM_KG);
        } else if (productObject.getWeightType() != null && (productObject.getWeightType().equalsIgnoreCase(UOM_ML) ||
                productObject.getWeightType().equalsIgnoreCase(UOM_LT))) {
            weightUomList.add(UOM_ML);
            weightUomList.add(UOM_LT);
        }
        if (db.getConfigDataBasedOnName(CONFIG_WEIGHT_BASED_UOM).equalsIgnoreCase("Y"))
            return weightUomList;
        else
            return new ArrayList<>();
    }

    /**
     * used to Prepare weight UOM list for the product
     *
     * @param productObject current product object
     * @return list of Weight UOM
     */
    public List<OrderBookingVO> getWeightUOMsList(OrderBookingVO productObject) {
        List<OrderBookingVO> weightUomList = new ArrayList<>();
        if (productObject.getWeightType() != null && (productObject.getWeightType().equalsIgnoreCase(UOM_GM) ||
                productObject.getWeightType().equalsIgnoreCase(UOM_KG))) {
            weightUomList.add(new OrderBookingVO(UOM_GM, UOM_GM));
            weightUomList.add(new OrderBookingVO(UOM_KG, UOM_KG));
        } else if (productObject.getWeightType() != null && (productObject.getWeightType().equalsIgnoreCase(UOM_ML) ||
                productObject.getWeightType().equalsIgnoreCase(UOM_LT))) {

            weightUomList.add(new OrderBookingVO(UOM_ML, UOM_ML));
            weightUomList.add(new OrderBookingVO(UOM_LT, UOM_LT));
        }
        if (db.getConfigDataBasedOnName(CONFIG_WEIGHT_BASED_UOM).equalsIgnoreCase("Y"))
            return weightUomList;
        else
            return new ArrayList<>();
    }

    /**
     * used to check wheather the qty is decimal or not
     *
     * @param quantity decimal Order Qty
     * @return converted String Value
     */
    public String getStringQty(double quantity) {
        if (quantity % 1 == 0) {
            return String.valueOf((int) quantity);
        } else {
            return String.valueOf(quantity);
        }
    }

    public List<TaxMasterModel> fetchProductTaxMasterList(OrderBookingVO orderBookingVO, String distStateCode, String supplierStateCode) {

        List<TaxMasterModel> taxModelList = new ArrayList<>();
        if (!distStateCode.isEmpty() && !supplierStateCode.isEmpty()) {

            taxModelList = db.getPOTaxCalPercentForStates(orderBookingVO.getCmpCode(),
                    orderBookingVO.getProdCode(), supplierStateCode);
            if (!taxModelList.isEmpty() && taxModelList.get(0) != null) {
                orderBookingVO.setTaxCode(taxModelList.get(0).getTaxStateCode());
            }
        }
        return taxModelList;

    }

    public String getGreaterUOMCode(OrderBookingVO orderBookingVO) {

        String maxUOM = "";
        if (orderBookingVO != null && !orderBookingVO.getConversionFactor().isEmpty()) {
            int maxValueInMap = (Collections.max(orderBookingVO.getConversionFactor().values()));  // This will return max value in the Hashmap
            for (Map.Entry<String, Integer> entry : orderBookingVO.getConversionFactor().entrySet()) {  // Itrate through hashmap
                if (entry.getValue() == maxValueInMap) {
                    // return the key with max value
                    maxUOM = entry.getKey();
                }
            }
        }
        return maxUOM;
    }

    public String getSmallerUOMCode(OrderBookingVO orderBookingVO) {

        String maxUOM = "";
        if (orderBookingVO != null && !orderBookingVO.getConversionFactor().isEmpty()) {
            int minValueInMap = (Collections.min(orderBookingVO.getConversionFactor().values()));  // This will return max value in the Hashmap
            for (Map.Entry<String, Integer> entry : orderBookingVO.getConversionFactor().entrySet()) {  // Itrate through hashmap
                if (entry.getValue() == minValueInMap) {
                    // return the key with max value
                    maxUOM = entry.getKey();
                }
            }
        }
        return maxUOM;
    }

    public String getGreaterUOMCode(MTDReportModel orderBookingVO) {

        String maxUOM = "";
        if (orderBookingVO != null && !orderBookingVO.getConversionFactor().isEmpty()) {
            int maxValueInMap = (Collections.max(orderBookingVO.getConversionFactor().values()));  // This will return max value in the Hashmap
            for (Map.Entry<String, Integer> entry : orderBookingVO.getConversionFactor().entrySet()) {  // Itrate through hashmap
                if (entry.getValue() == maxValueInMap) {
                    // return the key with max value
                    maxUOM = entry.getKey();
                }
            }
        }
        return maxUOM;
    }

    public String getSmallerUOMCode(MTDReportModel orderBookingVO) {

        String maxUOM = "";
        if (orderBookingVO != null && !orderBookingVO.getConversionFactor().isEmpty()) {
            int minValueInMap = (Collections.min(orderBookingVO.getConversionFactor().values()));  // This will return max value in the Hashmap
            for (Map.Entry<String, Integer> entry : orderBookingVO.getConversionFactor().entrySet()) {  // Itrate through hashmap
                if (entry.getValue() == minValueInMap) {
                    // return the key with max value
                    maxUOM = entry.getKey();
                }
            }
        }
        return maxUOM;
    }

    public List<SchemeModel> getAppliedSlabs(double currentQty, OrderBookingVO orderBookingVO, String uom, SchemeEngin schemeEngin, List<String> uomIdSpinner,
                                             List<SchemeDistrBudgetModel> schemeBudgetList, List<SchemeModel> schemeProductDetail) {
        BigDecimal orderValue;
        List<SchemeModel> mAppliedSlablist = new ArrayList<>();
        try {
            orderValue = calculateLineOrderValueBigDecimal(orderBookingVO, uom, currentQty);
            double primaryDiscOrderValue = getPrimDiscAppliedOrderValue(orderValue.doubleValue(), orderBookingVO.getPrimaryDisc());
            int conversionQty = calcUomConversionQty(orderBookingVO, uom, currentQty);

            List<SchemeModel> availableSlabList = new ArrayList<>();

            List<SchemeModel> productSchemeModelList = new ArrayList<>(getNonCombiSchemeList(orderBookingVO.getProdCode(), schemeProductDetail));
            List<SchemeModel> schemeSlabList = new ArrayList<>(db.getAllSchemeSlabDetail(productSchemeModelList));
            List<SchemeModel> freeProdSlabList = new ArrayList<>(db.getAllFreeProdList(productSchemeModelList));

            if (currentQty > 0) {
                availableSlabList.addAll(schemeEngin.calculateSchemes(primaryDiscOrderValue, conversionQty, orderBookingVO,
                        productSchemeModelList, freeProdSlabList, schemeSlabList, uomIdSpinner));
            }
            mAppliedSlablist = schemeEngin.checkSchemeBudget(availableSlabList, schemeBudgetList);
        } catch (Exception e) {
            Log.d(TAG, "getAppliedSlabs : exp : " + e);
        }
        return mAppliedSlablist;
    }

//    private void qunatityDailogClose(double editQty, OrderBookingVO orderBookingVO, String uomSpinnerItem, int dialogType) {
//
//        if (dialogType == ORDER_BOOKING_QUANTITY_DIALOG) {
//
//            orderBookingVO.setQuantity(editQty);
//
//            orderBookingVO.setTotQty(calcUomConversionQtyQuick(orderBookingVO, uomSpinnerItem, editQty));
//
//            double orderValue = calculateLineOrderValueQuick(orderBookingVO, uomSpinnerItem, editQty);
//            orderBookingVO.setOrderValue(BigDecimal.valueOf(orderValue));
//            orderBookingVO.setlGrossAmt(BigDecimal.valueOf(orderValue));
//            orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(getPrimDiscAppliedOrderValue(orderValue, orderBookingVO.getPrimaryDisc())));
//            orderBookingVO.setUomId(uomSpinnerItem);
//
//        } else {
//
//            orderBookingVO.setStockConversionFactor(calcUomConversionQtyQuick(orderBookingVO, uomSpinnerItem, editQty));
//            orderBookingVO.setStockCheckQty(editQty);
//            orderBookingVO.setStockOrderValue(BigDecimal.valueOf(calculateLineOrderValueQuick(orderBookingVO, uomSpinnerItem, editQty)));
//            if (editQty > 0) {
//                orderBookingVO.setItemSelected(true);
//                orderBookingVO.setCheckBoxEnabled(false);
//            } else {
//                orderBookingVO.setItemSelected(false);
//                orderBookingVO.setCheckBoxEnabled(true);
//            }
//            orderBookingVO.setStockUomId(uomSpinnerItem);
//        }
//
//        if (qtyUpdatedOrderBooking != null && qtyUpdatedOrderBooking.contains(orderBookingVO)) {
//            qtyUpdatedOrderBooking.remove(orderBookingVO);
//        }
//
//        if (qtyUpdatedOrderBooking != null) {
//            qtyUpdatedOrderBooking.add(orderBookingVO);
//        }
//        calculateOrderTotal(qtyUpdatedOrderBooking, orderCountTxt, orderTotalTxt, isPreviousOrder);
//    }


    public boolean isDistrUnionTerritory(SFADatabase sfaDatabase, String distrStateCode) {
        String query = " SELECT  * FROM " + TABLE_GST_STATE_MASTER + " WHERE " + COLUMN_GST_STATE_CODE + " =? and " + IS_UNION_TERRITORY + " = ?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distrStateCode, "Y"});
        if (c != null && c.getCount() > 0) {
            return true;
        }
        if (c != null) {
            c.close();
        }
        return false;
    }

    /**
     * used to map the products to mustSell ,Focus and Scheme Products.
     *
     * @param orderBookingVOList     all product list
     * @param mustSellProduct        must sell product list
     * @param mustSellBilledProducts must sell billed product list
     * @param focusBrandProduct      focus brand product list
     * @param schemeProductDetail    scheme product list
     * @param top10Products          top skus in the list
     * @return product list with mapped filter items
     */

    public List<OrderBookingVO> mapFilteredProducts(List<OrderBookingVO> orderBookingVOList, List<String> mustSellProduct,
                                                    List<String> mustSellBilledProducts, List<String> focusBrandProduct, List<SchemeModel> schemeProductDetail, List<String> top10Products) {
        int orderBookingVoListSize = orderBookingVOList.size();
        int mustSellProductSize = mustSellProduct.size();
        int focusBrandProductSize = focusBrandProduct.size();
        for (int i = 0; i < orderBookingVoListSize; i++) {
            for (int j = 0; j < mustSellProductSize; j++) {
                if (orderBookingVOList.get(i).getProductHierPath().contains(mustSellProduct.get(j))) {
                    orderBookingVOList.get(i).setMustcategory(MUST_SELL);
                    orderBookingVOList.get(i).setCategory(MUST_SELL);
                    /* for must sell porduct qty added here*/
                    if (orderBookingVOList.get(i).getQuantity() == 0 && orderBookingVOList.get(i).getTempMustSellQty() > 0) {
                        orderBookingVOList.get(i).setMustSellQty(orderBookingVOList.get(i).getTempMustSellQty());
                    }
                }
            }
            for (int j = 0; j < focusBrandProductSize; j++) {
                if (orderBookingVOList.get(i).getProductHierPath().contains(focusBrandProduct.get(j))) {
                    orderBookingVOList.get(i).setFocusCategory(FOCUS_BRAND);
                    orderBookingVOList.get(i).setCategory(FOCUS_BRAND);
                }
            }
            setTop10Products(top10Products, orderBookingVOList, i);
            mapSchemeProductToList(orderBookingVOList, schemeProductDetail, i);

            setPriority(orderBookingVOList.get(i), mustSellProduct, focusBrandProduct);
            setMustSellBilledPriority(orderBookingVOList.get(i), mustSellBilledProducts);
        }
        return orderBookingVOList;
    }

    private void setTop10Products(List<String> top10Products, List<OrderBookingVO> orderBookingVOList, int i) {
        int top10ProductsSize = top10Products.size();
        for (int j = 0; j < top10ProductsSize; j++) {
            if (orderBookingVOList.get(i).getProductHierPath().contains(top10Products.get(j))) {
                orderBookingVOList.get(i).setTop10ProductCategory(TOP10_SKU_BRAND);
                orderBookingVOList.get(i).setCategory(TOP10_SKU_BRAND);
            }
        }
    }

    private void setPriority(OrderBookingVO orderBookingVO, List<String> mustSellProduct, List<String> focusBrandProduct) {
        String priorityString = db.getConfigDataBasedOnName(CONFIG_PRIORITY);
        if (!priorityString.isEmpty()) {
            String[] priorities = priorityString.trim().split(",");
            for (String priority : priorities) {
                //Focus Brand
                if (priority.trim().equalsIgnoreCase(FOCUS_BRAND) && orderBookingVO.getFocusCategory().equalsIgnoreCase(FOCUS_BRAND)) {
                    orderBookingVO.setPriority(priority);
                    break;
                }
                //Must Sell
                if (priority.trim().equalsIgnoreCase(MUST_SELL) && orderBookingVO.getMustcategory().equalsIgnoreCase(MUST_SELL)) {
                    orderBookingVO.setPriority(priority);
                    break;
                }
                //Promo - Scheme
                if (priority.trim().equalsIgnoreCase(PROMO) && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
                    orderBookingVO.setPriority(priority);
                    break;
                }
                // Top SKU products
                if (priority.trim().equalsIgnoreCase(TOP10_SKU_BRAND)
                        && TOP10_SKU_BRAND.equalsIgnoreCase(orderBookingVO.getTop10ProductCategory())) {
                    orderBookingVO.setPriority(priority);
                    break;
                }
                //Out of Stock Products
                if (priority.trim().equalsIgnoreCase(OUT_OF_STOCK) && orderBookingVO.getStockInHand() <= 0) {
                    orderBookingVO.setPriority(priority);
                    break;
                }


            }
        }
    }

    private void setMustSellBilledPriority(OrderBookingVO orderBookingVO, List<String> mustSellBilledProducts) {
        //Must Sell Billed
        if (mustSellBilledProducts.contains(orderBookingVO.getProdCode())) {
            orderBookingVO.setPriority(MUST_SELL_BILLED);
        }
    }


    public enum ZeroResult {
        ALL_PRODUCTS,
        ZERO_PRODUCTS,
        NON_ZERO_PRODUCTS
    }

}
