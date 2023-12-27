package com.botree.retailerssfa.scheme;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_SCHEME_UPSALE;

public class SchemeEngin extends SchemeCalc {
    private static final String TAG = SchemeEngin.class.getSimpleName();
    private static final String SCHEME_AMOUNT_BASE = "AB";
    private static final String SCHEME_WEIGHT_BASE = "WB";
    public static final String SCHEME_PAYOUT_DISCOUNT = "Discount %";
    public static final String SCHEME_PAYOUT_AMOUNT = "Amount";
    public static final String SCHEME_PAYOUT_FREE_PROD = "Free";
    private static final String GET = " get ";
    private static final String AND_ABOVE_GET = " and above get ";
    private static final String PURCHASE = "Purchase ";
    private static final String BUY = "Buy ";
    private final SFASharedPref sfaPref;
    private SFADatabase db;
    private Context context;
    private double configPercent = 60.0;

    private String batchCode;

    public SchemeEngin(Context activity) {
        this.context = activity;

        this.db = SFADatabase.getInstance(activity);
        this.sfaPref = SFASharedPref.getOurInstance();
        try {
            if(db.getConfigDataBasedOnName(CONFIG_SCHEME_UPSALE)!=null && !db.getConfigDataBasedOnName(CONFIG_SCHEME_UPSALE).isEmpty())
            configPercent = Double.parseDouble(db.getConfigDataBasedOnName(CONFIG_SCHEME_UPSALE));
            if (BuildConfig.DEBUG)
                Log.e(TAG, "SchemeEngin: " + configPercent);

        } catch (Exception e) {
            Log.e(TAG, "SchemeEngin: " + e.getMessage(), e);
        }
    }

    /**
     * This method is used to calculate single product scheme value based on the product code and scheme code
     *
     * @param orderValue             calculater order value for the product
     * @param conversionQty          base coversion qty of the product
     * @param orderBookingVO         Model object for the single product with details
     * @param productSchemeModelList List of scheme which is available for the product
     * @param freeProdSlabList       List of freeprod scheme slab list which is available for the product
     * @param schemeSlabList         List of scheme slab list for the product
     * @param uomIdSpinner           List of Uom for the single product
     * @return appliedSlablist return list of applied slab  List fot the product
     */
    public List<SchemeModel> calculateSchemes(double orderValue, double conversionQty, OrderBookingVO orderBookingVO, List<SchemeModel> productSchemeModelList,
                                              List<SchemeModel> freeProdSlabList, List<SchemeModel> schemeSlabList,
                                              List<String> uomIdSpinner) {
        List<SchemeModel> appliedSlablist = new ArrayList<>();
        appliedSlablist.clear();

        batchCode = orderBookingVO.getProdBatchCode();
        for (SchemeModel schemeCodelist : productSchemeModelList) {
            if (schemeCodelist.getPayoutType().equalsIgnoreCase(SCHEME_PAYOUT_FREE_PROD)) {
                freeProdPayoutScheme(orderValue, conversionQty, orderBookingVO, freeProdSlabList, uomIdSpinner, appliedSlablist, schemeCodelist);
            } else {
                if (schemeCodelist.getPayoutType().equalsIgnoreCase(SCHEME_PAYOUT_AMOUNT)) {
                    amountPayoutScheme(orderValue, conversionQty, orderBookingVO, schemeSlabList, uomIdSpinner, appliedSlablist, schemeCodelist);
                } else {
                    discountPayoutScheme(orderValue, conversionQty, orderBookingVO, schemeSlabList, uomIdSpinner, appliedSlablist, schemeCodelist);
                }
            }
//            schemeCodelist.setNoOfInvoice(orderBookingVO.getTotInvoiceQty());
        }

//        List<SchemeModel> slabsList = new ArrayList<>();
//        //Remove duplicate slabs of same scheme
//        for (SchemeModel schemeModel : appliedSlablist) {
//            if (!checkIfSlabIsDuplicated(slabsList, schemeModel)) {
//                slabsList.add(schemeModel);
//            }
//        }

        return appliedSlablist;
    }

    private boolean checkIfSlabIsDuplicated(List<SchemeModel> slabsList, SchemeModel schemeModel) {
        boolean check = false;
        for (SchemeModel slab : slabsList) {
            if (schemeModel.getSchemeCode().equals(slab.getSchemeCode())) {
                if (slab.getSlabTo() == 0) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    /**
     * @param orderValue      calculater order value for the product
     * @param conversionQty   base coversion qty of the product
     * @param orderBookingVO  Model object for the single product with details
     * @param schemeSlabList  List of scheme slab list for the product
     * @param uomIdSpinner    selected Uom for the product
     * @param appliedSlablist list of applied slab list
     * @param schemeCodelist  list of schemes applied for this product
     */
    private List<SchemeModel> discountPayoutScheme(double orderValue, double conversionQty, OrderBookingVO orderBookingVO, List<SchemeModel> schemeSlabList,
                                                   List<String> uomIdSpinner, List<SchemeModel> appliedSlablist, SchemeModel schemeCodelist) {
        SchemeModel appliedSlab = new SchemeModel();
        for (SchemeModel givenSlabList : schemeSlabList) {
            if (checkSchemeBase(schemeCodelist, givenSlabList, SCHEME_QUANTITY_BASE)) {
                if (isProductUomAvailable(uomIdSpinner, givenSlabList.getUom())) {
                    double slabUomQty = calcSchemeUomQty(orderBookingVO, givenSlabList.getUom(), conversionQty);
                    appliedSlab = getSchemeModel(schemeCodelist, appliedSlab, givenSlabList, slabUomQty, orderValue, SCHEME_PAYOUT_DISCOUNT);
                }
            } else if (checkSchemeBase(schemeCodelist, givenSlabList, SCHEME_AMOUNT_BASE)) {
                appliedSlab = getSchemeModel(schemeCodelist, appliedSlab, givenSlabList, orderValue, orderValue, SCHEME_PAYOUT_DISCOUNT);

            } else if (checkSchemeBase(schemeCodelist, givenSlabList, SCHEME_WEIGHT_BASE)) {
                double conversionWeight = calcWeightConversion(orderBookingVO, givenSlabList.getUom(), conversionQty);
                appliedSlab = getSchemeModel(schemeCodelist, appliedSlab, givenSlabList, conversionWeight, orderValue, SCHEME_PAYOUT_DISCOUNT);
            }
        }
        if (appliedSlab.getSchemeCode() != null && !appliedSlab.getSchemeCode().isEmpty()) {
            appliedSlab.setInvoiceNo(orderBookingVO.getOrderInvoiceNo());
            appliedSlab.setReadableInvNo(orderBookingVO.getReadableInvNo());
            appliedSlablist.add(appliedSlab);
        }
        return appliedSlablist;
    }

    /**
     * @param orderValue      calculater order value for the product
     * @param conversionQty   base coversion qty of the product
     * @param orderBookingVO  Model object for the single product with details
     * @param schemeSlabList  List of scheme slab list for the product
     * @param uomIdSpinner    selected Uom for the product
     * @param appliedSlablist list of applied slab list
     * @param schemeCodelist  list of schemes applied for this product
     */
    private List<SchemeModel> amountPayoutScheme(double orderValue, double conversionQty, OrderBookingVO orderBookingVO,
                                                 List<SchemeModel> schemeSlabList, List<String> uomIdSpinner,
                                                 List<SchemeModel> appliedSlablist, SchemeModel schemeCodelist) {
        SchemeModel appliedSlab = new SchemeModel();
        for (SchemeModel givenSlabList : schemeSlabList) {
            if (checkSchemeBase(schemeCodelist, givenSlabList, SCHEME_QUANTITY_BASE)) {
                if (isProductUomAvailable(uomIdSpinner, givenSlabList.getUom())) {
                    double slabUomQty = calcSchemeUomQty(orderBookingVO, givenSlabList.getUom(), conversionQty);
                    appliedSlab = getSchemeModel(schemeCodelist, appliedSlab, givenSlabList, slabUomQty, orderValue, SCHEME_PAYOUT_AMOUNT);
                }
            } else if (checkSchemeBase(schemeCodelist, givenSlabList, SCHEME_AMOUNT_BASE)) {
                appliedSlab = getSchemeModel(schemeCodelist, appliedSlab, givenSlabList, orderValue, orderValue, SCHEME_PAYOUT_AMOUNT);
            } else if (checkSchemeBase(schemeCodelist, givenSlabList, SCHEME_WEIGHT_BASE)) {
                double conversionWeight = calcWeightConversion(orderBookingVO, givenSlabList.getUom(), conversionQty);
                appliedSlab = getSchemeModel(schemeCodelist, appliedSlab, givenSlabList, conversionWeight, orderValue, SCHEME_PAYOUT_AMOUNT);
            }
        }
        if (appliedSlab.getSchemeCode() != null && !appliedSlab.getSchemeCode().isEmpty()) {
            appliedSlab.setInvoiceNo(orderBookingVO.getOrderInvoiceNo());
            appliedSlab.setReadableInvNo(orderBookingVO.getReadableInvNo());
            appliedSlablist.add(appliedSlab);
        }
        return appliedSlablist;
    }


    private List<SchemeModel> freeProdPayoutScheme(double orderValue, double conversionQty, OrderBookingVO orderBookingVO,
                                                   List<SchemeModel> freeProdSlabList, List<String> uomIdSpinner,
                                                   List<SchemeModel> appliedSlablist, SchemeModel schemeModel) {
        for (SchemeModel givenSlabList : freeProdSlabList) {
            SchemeModel appliedSlab = new SchemeModel();
            if (checkSchemeBase(schemeModel, givenSlabList, SCHEME_QUANTITY_BASE)) {
                if (isProductUomAvailable(uomIdSpinner, givenSlabList.getUom())) {
                    double slabUomQty = calcSchemeUomQty(orderBookingVO, givenSlabList.getUom(), conversionQty);
                    appliedSlab = getSchemeModel(schemeModel, appliedSlab, givenSlabList, slabUomQty, orderValue, SCHEME_PAYOUT_FREE_PROD);
                }
            } else if (checkSchemeBase(schemeModel, givenSlabList, SCHEME_WEIGHT_BASE)) {
                double conversionWeight = calcWeightConversion(orderBookingVO, givenSlabList.getUom(), conversionQty);
                appliedSlab = getSchemeModel(schemeModel, appliedSlab, givenSlabList, conversionWeight, orderValue, SCHEME_PAYOUT_FREE_PROD);

            } else if (checkSchemeBase(schemeModel, givenSlabList, SCHEME_AMOUNT_BASE)) {
                appliedSlab = getSchemeModel(schemeModel, appliedSlab, givenSlabList, orderValue, orderValue, SCHEME_PAYOUT_FREE_PROD);
            }
            appliedSlab.setInvoiceNo(orderBookingVO.getOrderInvoiceNo());
            appliedSlab.setReadableInvNo(orderBookingVO.getReadableInvNo());
            checkAlreadyAppliedSlab(appliedSlablist, appliedSlab);
        }
        return appliedSlablist;
    }

//    private void checkAlreadyAppliedSlab(List<SchemeModel> appliedSlablist, SchemeModel appliedSlab) {
//        boolean isAlreadyAdded = false;
//        for (SchemeModel schemeModel : appliedSlablist) {
//            if (schemeModel.getSchemeCode().equalsIgnoreCase(appliedSlab.getSchemeCode()) && schemeModel.getFreeProdCode().equalsIgnoreCase(appliedSlab.getFreeProdCode())) {
//                isAlreadyAdded = true;
//            }
//        }
//        if (!isAlreadyAdded && appliedSlab.getSchemeCode() != null && !appliedSlab.getSchemeCode().isEmpty())
//            appliedSlablist.add(appliedSlab);
//        else {
//            SchemeModel schemeModel = getAppliedSchemeObjectFromList(appliedSlablist, appliedSlab);
//            appliedSlablist.remove(schemeModel);
//            appliedSlablist.add(appliedSlab);
//        }
//    }

    private void checkAlreadyAppliedSlab(List<SchemeModel> appliedSlablist, SchemeModel appliedSlab) {
        boolean isAlreadyAdded = false;
        for (SchemeModel schemeModel : appliedSlablist) {
            if (schemeModel.getSchemeCode().equalsIgnoreCase(appliedSlab.getSchemeCode()) &&
                    schemeModel.getIsMandatory().equalsIgnoreCase("N") && !appliedSlab.getPayoutType().equalsIgnoreCase(SCHEME_PAYOUT_FREE_PROD)) {
                isAlreadyAdded = true;
            }
        }
        if (!isAlreadyAdded && appliedSlab.getSchemeCode() != null && !appliedSlab.getSchemeCode().isEmpty())

            appliedSlablist.add(appliedSlab);
        else {
            SchemeModel schemeModel = getAppliedSchemeObjectFromList(appliedSlablist, appliedSlab);
            appliedSlablist.remove(schemeModel);
            appliedSlablist.add(appliedSlab);
        }
    }


    /**
     * used to check the slab conditions for the scheme
     *
     * @param schemeCodelist         scheme definition data
     * @param givenSlabList          eligible slab data
     * @param slabUomQtyOrValueOrWgt Order value Or Quantity Or Order Weight
     * @param orderValue             Order value for the product
     * @param schemePayoutType       scheme Payout
     * @return applied slab datas
     */
    private SchemeModel getSchemeModel(SchemeModel schemeCodelist, SchemeModel appliedSlab, SchemeModel givenSlabList, double slabUomQtyOrValueOrWgt,
                                       double orderValue, String schemePayoutType) {
        if (isSlabEqualZero(givenSlabList)) {
            appliedSlab = getSlabAppliedScheme(slabUomQtyOrValueOrWgt, orderValue, schemeCodelist, appliedSlab,
                    givenSlabList, isSlabGreaterThanFrom(givenSlabList, slabUomQtyOrValueOrWgt), schemePayoutType);
        } else if (isSlabGreaterZero(givenSlabList)) {
            appliedSlab = getSlabAppliedScheme(slabUomQtyOrValueOrWgt, orderValue, schemeCodelist, appliedSlab,
                    givenSlabList, isSlabBetweenFromAndTo(givenSlabList, slabUomQtyOrValueOrWgt), schemePayoutType);
        } else {
            appliedSlab = getSlabAppliedScheme(slabUomQtyOrValueOrWgt, orderValue, schemeCodelist, appliedSlab,
                    givenSlabList, isSlabGreaterThanFrom(givenSlabList, slabUomQtyOrValueOrWgt), schemePayoutType);
        }
        return appliedSlab;
    }

    /**
     * check weather the quantity is not equals zero and greater than slab from
     *
     * @param slabmodel  scheme slab model
     * @param slabUomQty conversion quantity
     * @return boolean
     */
    private boolean isSlabGreaterThanFrom(SchemeModel slabmodel, double slabUomQty) {
        if (slabUomQty == 0)
            return false;
        return slabUomQty >= slabmodel.getSlabFrom();
    }

    private boolean isSlabBetweenFromAndTo(SchemeModel slabmodel, double slabUomQty) {
        return isSlabGreaterThanFrom(slabmodel, slabUomQty) &&
                slabUomQty <= slabmodel.getSlabTo();
    }

    private boolean checkSchemeBase(SchemeModel schemeCodelist, SchemeModel slabmodel, String baseType) {
        return schemeCodelist.getSchemeCode().equalsIgnoreCase(slabmodel.getSchemeCode()) &&
                schemeCodelist.getSchemeBase().trim().equalsIgnoreCase(baseType);
    }

    private SchemeModel getSlabAppliedScheme(double orderValueOrQtyOrWgt, double orderValue, SchemeModel schemeCodelist, SchemeModel appliedSlab,
                                             SchemeModel givenSlabList, boolean slabGreaterThanFrom, String schemePayoutType) {
        if (SCHEME_PAYOUT_DISCOUNT.equalsIgnoreCase(schemePayoutType) && slabGreaterThanFrom) {
//            if (schemeCodelist.getSchemeBase().equalsIgnoreCase(SCHEME_AMOUNT_BASE)) {
//                appliedSlab = getDiscPercAndValue(orderValueOrQty, schemeCodelist, givenSlabList);
//            } else {
            appliedSlab = getDiscPercAndValue(orderValue, schemeCodelist, givenSlabList);
//            }
        } else if (SCHEME_PAYOUT_AMOUNT.equalsIgnoreCase(schemePayoutType) && slabGreaterThanFrom) {
            appliedSlab = getAmountSlabDetails(orderValueOrQtyOrWgt, givenSlabList, schemeCodelist);
        } else if (SCHEME_PAYOUT_FREE_PROD.equalsIgnoreCase(schemePayoutType) && slabGreaterThanFrom) {
            appliedSlab = getFreeProductBasedOnMandatory(orderValueOrQtyOrWgt, schemeCodelist, givenSlabList);
        }
        return appliedSlab;
    }

    /**
     * used to get the free product details based on mandatory slab and set
     *
     * @param orderValueOrQtyOrWgt Ordervalue or OrderQty or Weight
     * @param schemeCodelist       scheme List
     * @param freeProdSlab         free product slab list
     * @return retrun applied prod list with all setter data.
     */
    @NonNull
    private SchemeModel getFreeProductBasedOnMandatory(Double orderValueOrQtyOrWgt, SchemeModel schemeCodelist, SchemeModel freeProdSlab) {
        SchemeModel appliedSlab;
        appliedSlab = getAppliedSlabDetails(freeProdSlab, schemeCodelist);
        appliedSlab.setProdName(freeProdSlab.getProdName());
        appliedSlab.setFreeProdName(freeProdSlab.getProdName());
        appliedSlab.setIsMandatory(freeProdSlab.getIsMandatory());
        appliedSlab.setFreeProdCode(freeProdSlab.getFreeProdCode());

        if (appliedSlab.getForEvery() > 0) {
            Integer payout = (int) (Math.floor(orderValueOrQtyOrWgt / appliedSlab.getForEvery()) * freeProdSlab.getFreeQty());
            appliedSlab.setFreeQty(payout);
        } else {
            appliedSlab.setFreeQty(freeProdSlab.getFreeQty());
        }
        return appliedSlab;
    }


    /**
     * calculate percentage and payout value based on Order value forevery condition
     *
     * @param orderValue     for every condition based on Order value
     * @param slabmodel      slab details list for the scheme
     * @param schemeCodelist scheme definition details list
     * @return SCheme model which is having all scheme detials of the scheme
     */
    @NonNull
    private SchemeModel getDiscPercAndValue(double orderValue, SchemeModel schemeCodelist, SchemeModel slabmodel) {
        SchemeModel appliedSlab;

        appliedSlab = getAppliedSlabDetails(slabmodel, schemeCodelist);

        double percentValue;
        percentValue = getDisPercentValuePrimaryCheck(db, orderValue, appliedSlab.getPayoutValue());

        appliedSlab.setFlatAmount(percentValue);
        appliedSlab.setPercentage(appliedSlab.getPayoutValue().intValue());
        return appliedSlab;
    }


    /**
     * used to get and set applied scheme details and calcualte payout value based on qty forevery  condition
     *
     * @param slabmodel      slab details list for the scheme
     * @param schemeCodelist scheme definition details list
     * @return SCheme model which is having all scheme detials of the scheme
     */
    private SchemeModel getAmountSlabDetails(Double orderValueOrQty, SchemeModel slabmodel, SchemeModel schemeCodelist) {
        SchemeModel appliedSlab = getAppliedSlabDetails(slabmodel, schemeCodelist);
        if (appliedSlab.getForEvery() > 0) {

            Double payout = Math.floor(orderValueOrQty / appliedSlab.getForEvery()) * appliedSlab.getPayoutValue();
            appliedSlab.setFlatAmount(payout);
        } else {
            appliedSlab.setFlatAmount(slabmodel.getPayoutValue());
        }

        return appliedSlab;
    }

    /**
     * Used to convert all applied scheme datas to change based on the message test.
     *
     * @param mAppliedSlablist final Applied slab List
     * @return String message which is showed in UI
     */
    public String showSchemeDetailInText(List<SchemeModel> mAppliedSlablist) {

        StringBuilder offerMsg = new StringBuilder();

        double flatAmount = 0;
        for (SchemeModel appliedScheme : mAppliedSlablist) {
            flatAmount = flatAmount + appliedScheme.getFlatAmount();
        }
        if (flatAmount > 0) {
            offerMsg = new StringBuilder("You have " + context.getResources().getString(R.string.Rs) + " " + AppUtils.getOurInstance().decimalFormatWithTwoDigit(flatAmount) + " off");
        }

        List<String> uniqueProdList = filterUniqueProdString(mAppliedSlablist);
        List<String> freeProdList = new ArrayList<>();
        for (String s : uniqueProdList) {
            int tempQty = 0;
            String tempprodName = "";
            for (SchemeModel appliedScheme : mAppliedSlablist) {
                if (appliedScheme.getFreeQty() != null && appliedScheme.getFreeQty() > 0 && s.equalsIgnoreCase(appliedScheme.getFreeProdCode())) {
                    tempQty = tempQty + appliedScheme.getFreeQty();
                    tempprodName = appliedScheme.getProdName();
                }
            }
            getAppliedText(offerMsg, freeProdList, tempQty, tempprodName);
        }
        for (String offerMsgString : freeProdList) {
            offerMsg.append(offerMsgString);
        }

        return offerMsg.toString();

    }

    private void getAppliedText(StringBuilder offerMsg, List<String> freeProdList, int tempQty, String tempprodName) {
        if (tempQty > 0) {
            if (freeProdList.size() == 0)
                freeProdList.add("You got " + tempQty + " " + tempprodName + " Free");
            else
                freeProdList.add("\n You got " + tempQty + " " + tempprodName + " Free");
        }
    }

    /**
     * Used to calculate and show all suggest Scheme Details to the User
     *
     * @param orderBookingVO    which is having all details about the single prod
     * @param currentQty        Current Qty without conversion
     *                          //     * @param orderValue        calcualter order value for the product
     * @param uomIdSpinner      List of UOM For the product
     * @param selectedUom       Selected Uom for the Product
     * @param className         calling class name
     * @return String message which is to be shown in UI
     */

    public String showSuggestedSchemeDetailInText(OrderBookingVO orderBookingVO, List<SchemeModel> schemeModelList,
                                                  double currentQty, double orderValue, List<String> uomIdSpinner,
                                                  String selectedUom, String className) {
        double conversionQty = OrderSupportUtil.getInstance().calcUomConversionQty(orderBookingVO, selectedUom, currentQty);
        StringBuilder offerMsg = new StringBuilder();
        List<SchemeModel> suggestedFreeList;
        SchemeModel suggestedschemeModel = new SchemeModel();
        List<String> offerMsgList = new ArrayList<>();
        for (SchemeModel schemeModel : schemeModelList) {

            String uomCodeForScheme = db.getUomCodeForScheme(schemeModel);
            if (isSchemeType(schemeModel.getSchemeBase(), SCHEME_AMOUNT_BASE, schemeModel.getPayoutType(), SCHEME_PAYOUT_AMOUNT)) {
                suggestedschemeModel = db.getSuggestedSchemeSlabDetail(schemeModel, orderValue);
                Double payout = getPayout(orderValue, suggestedschemeModel);
                double abAmt = suggestedschemeModel.getSlabFrom() / 100.0 * configPercent;
                offerMsgList = getSuggestMsgAbAmount(orderValue, suggestedschemeModel, offerMsgList, payout, abAmt);
            } else if (isSchemeType(schemeModel.getSchemeBase(), SCHEME_AMOUNT_BASE, schemeModel.getPayoutType(), SCHEME_PAYOUT_FREE_PROD)) {
                suggestedFreeList = db.fetchSuggestedFreeProdList(schemeModel, orderValue, className);
                for (SchemeModel suggFreeProd : suggestedFreeList) {
                    Integer freeqty = getFreeQty(orderValue, suggFreeProd);
                    double abAmt = suggFreeProd.getSlabFrom() / 100.0 * configPercent;
                    offerMsgList = getSuggestMsgABFree(orderValue, suggestedschemeModel, offerMsgList, suggFreeProd, freeqty, abAmt);
                }

            } else if (isSuggestionDiscountPayout(schemeModel, SCHEME_AMOUNT_BASE, SCHEME_PAYOUT_DISCOUNT)) {

                suggestedschemeModel = db.getSuggestedSchemeSlabDetail(schemeModel, orderValue);

                Double payout = suggestedschemeModel.getPayoutValue();
                double abAmt = suggestedschemeModel.getSlabFrom() / 100.0 * configPercent;
                offerMsgList = getSuggestMsgABDiscount(orderValue, suggestedschemeModel, offerMsgList, payout, abAmt);

            } else if (isSchemeType(schemeModel.getSchemeBase(), SCHEME_QUANTITY_BASE, schemeModel.getPayoutType(), SCHEME_PAYOUT_AMOUNT)) {
                if (isProductUomAvailable(uomIdSpinner, uomCodeForScheme)) {
                    double schemeUomQty = calcSchemeUomQty(orderBookingVO, uomCodeForScheme, conversionQty);
                    suggestedschemeModel = db.getSuggestedSchemeSlabDetail(schemeModel, schemeUomQty);

                    Double payout = getPayout(schemeUomQty, suggestedschemeModel);
                    double abAmt = suggestedschemeModel.getSlabFrom() / 100.0 * configPercent;
                    offerMsgList = getSuggestMsgQtyAmount(suggestedschemeModel, offerMsgList, schemeUomQty, payout, abAmt);
                }

            } else if (isSchemeType(schemeModel.getSchemeBase(), SCHEME_QUANTITY_BASE, schemeModel.getPayoutType(), SCHEME_PAYOUT_FREE_PROD)) {
                if (isProductUomAvailable(uomIdSpinner, uomCodeForScheme)) {
                    double schemeUomQty = calcSchemeUomQty(orderBookingVO, uomCodeForScheme, currentQty);
                    suggestedFreeList = db.fetchSuggestedFreeProdList(schemeModel, conversionQty, className);
                    for (SchemeModel suggFreeProd : suggestedFreeList) {
                        Integer freeqty = getFreeQty(schemeUomQty, suggFreeProd);
                        double abAmt = suggFreeProd.getSlabFrom() / 100.0 * configPercent;
                        offerMsgList = getSuggestMsgQtyFree(schemeUomQty, suggestedschemeModel, offerMsgList, suggFreeProd, freeqty, abAmt);
                    }
                }
            } else if (isSuggestionDiscountPayout(schemeModel, SCHEME_QUANTITY_BASE, SCHEME_PAYOUT_DISCOUNT) && isProductUomAvailable(uomIdSpinner, uomCodeForScheme)) {
                double schemeUomQty = calcSchemeUomQty(orderBookingVO, uomCodeForScheme, currentQty);
                suggestedschemeModel = db.getSuggestedSchemeSlabDetail(schemeModel, conversionQty);

                Double payout = suggestedschemeModel.getPayoutValue();
                double abAmt = suggestedschemeModel.getSlabFrom() / 100.0 * configPercent;
                if (isDiscountTrue(schemeUomQty, suggestedschemeModel, abAmt)) {
                    Integer reminingqty = (int) (suggestedschemeModel.getSlabFrom() - currentQty);
                    Double reminingValue = calculateLineOrderValue(orderBookingVO, selectedUom, reminingqty);
                    double dicountValue = getDisPercentValuePrimaryCheck(db, (orderValue + reminingValue), payout);
//                    double dicountValue = (orderValue + reminingValue) / 100.0 * payout;
                    offerMsgList = getSuggestMsgQtyDis(suggestedschemeModel, offerMsgList, payout, dicountValue);
                }
            } else if (isSuggestionDiscountPayout(schemeModel, SCHEME_WEIGHT_BASE, SCHEME_PAYOUT_DISCOUNT)) {
                double conversionWeight = calcWeightConversion(orderBookingVO, uomCodeForScheme, conversionQty);
                suggestedschemeModel = db.getSuggestedSchemeSlabDetail(schemeModel, conversionWeight);

                Double payout = suggestedschemeModel.getPayoutValue();
//                Double payout = getPayoutForWeight(conversionWeight, suggestedschemeModel);
                double slabConfigWeight = suggestedschemeModel.getSlabFrom() / 100.0 * configPercent;
                offerMsgList = getSuggestMsgWBDiscount(orderBookingVO, currentQty, orderValue, selectedUom, suggestedschemeModel, offerMsgList, payout, slabConfigWeight, conversionWeight);
            } else if (isSchemeType(schemeModel.getSchemeBase(), SCHEME_WEIGHT_BASE, schemeModel.getPayoutType(), SCHEME_PAYOUT_AMOUNT)) {

                double conversionWeight = calcWeightConversion(orderBookingVO, uomCodeForScheme, conversionQty);
                suggestedschemeModel = db.getSuggestedSchemeSlabDetail(schemeModel, conversionWeight);

                Double payout = getPayoutForWeight(conversionWeight, suggestedschemeModel);
                double slabConfigWeight = suggestedschemeModel.getSlabFrom() / 100.0 * configPercent;
                offerMsgList = getSuggesMsgWBAmount(orderBookingVO, orderValue, selectedUom, suggestedschemeModel, offerMsgList, conversionWeight, payout, slabConfigWeight);
            }
        }
        offerMsg = getOfferMessage(offerMsg, offerMsgList);

        return offerMsg.toString();

    }
    /**
     * used to check weather the suggestion in availabe for the scheme or Not
     *
     * @param appliedScheme applied scheme details
     * @param schemeBase    scheme base
     * @param payout        payout for  the scheme
     * @return boolean
     */
    private boolean isSuggestionDiscountPayout(SchemeModel appliedScheme, String schemeBase, String payout) {
        return appliedScheme.getSchemeBase().equalsIgnoreCase(schemeBase)
                && appliedScheme.getPayoutType().startsWith(payout);
    }

    private List<String> getSuggestMsgQtyDis(SchemeModel suggestedschemeModel, List<String> offerMsgList, Double payout, double dicountValue) {
        if (dicountValue > 0) {
            offerMsgList.add(BUY + suggestedschemeModel.getSlabFrom() + " " + suggestedschemeModel.getUom() + GET +
                    context.getResources().getString(R.string.Rs) + " " + AppUtils.getOurInstance().decimalFormatWithTwoDigit(dicountValue) + "(" + payout + "%) off ");
        }
        return offerMsgList;
    }

    private void getSuggestMsgWBDis(String uom, double enteredQty, int reminingQty, List<String> offerMsgList, Double payout, double dicountValue) {
        if (dicountValue > 0) {
            offerMsgList.add(BUY + (enteredQty + reminingQty) + " " + uom + GET +
                    context.getResources().getString(R.string.Rs) + " " + AppUtils.getOurInstance().decimalFormatWithTwoDigit(dicountValue) + "(" + payout + "%) off ");
        }
    }

    private List<String> getSuggestMsgQtyFree(double qty, SchemeModel suggestedschemeModel, List<String> offerMsgList, SchemeModel suggFreeProd, Integer freeqty, double abAmt) {
        if (isSuggestionAvailable(qty > abAmt, qty <= suggFreeProd.getSlabFrom(), freeqty > 0)) {
            offerMsgList.add(BUY + suggFreeProd.getSlabFrom() + " " + suggestedschemeModel.getUom() + GET + freeqty + " " + suggFreeProd.getProdName() + " Free ");
        }
        return offerMsgList;
    }

    private List<String> getSuggestMsgQtyAmount(SchemeModel suggestedschemeModel, List<String> offerMsgList, double schemeUomQty, Double payout, double abAmt) {
        if (isSuggestionAvailable(schemeUomQty >= abAmt, schemeUomQty <= suggestedschemeModel.getSlabFrom(), payout > 0)) {
            offerMsgList.add(BUY + suggestedschemeModel.getSlabFrom()
                    + " " + suggestedschemeModel.getUom() + GET + context.getResources().getString(R.string.Rs) + " " +
                    AppUtils.getOurInstance().decimalFormatWithTwoDigit(payout) + " off ");
        }
        return offerMsgList;
    }

    private List<String> getSuggestMsgABDiscount(double orderValue, SchemeModel suggestedschemeModel, List<String> offerMsgList, Double payout, double abAmt) {
        if (isDiscountTrue(orderValue, suggestedschemeModel, abAmt)) {
            Integer reminingValue = (int) (suggestedschemeModel.getSlabFrom() - orderValue);
            double dicountValue = getDisPercentValuePrimaryCheck(db, (orderValue + reminingValue), payout);
//            double dicountValue = (ordreValue + reminingValue) / 100.0 * payout;
            getSuggestMessage(suggestedschemeModel, offerMsgList, payout, dicountValue);
        }
        return offerMsgList;
    }

    private List<String> getSuggestMsgWBDiscount(OrderBookingVO orderBookingVO, double currentQty, double orderValue, String selectedUom, SchemeModel suggestedschemeModel,
                                                 List<String> offerMsgList, Double payout, double slabConfigWeight, double conversionWeight) {
        if (isDiscountWBTrue(conversionWeight, suggestedschemeModel, slabConfigWeight)) {
            double reminingWeight = (suggestedschemeModel.getSlabFrom() - conversionWeight);
            int reminingQty = (int) Math.ceil(reminingWeight / orderBookingVO.getNetWeight());
            Double reminingValue = calculateLineOrderValue(orderBookingVO, selectedUom, reminingQty);
            double dicountValue = getDisPercentValuePrimaryCheck(db, (orderValue + reminingValue), payout);
//            double dicountValue = (orderValue + reminingValue) / 100.0 * payout;
            getSuggestMsgWBDis(selectedUom, currentQty, reminingQty, offerMsgList, payout, dicountValue);
        }
        return offerMsgList;
    }

    private List<String> getSuggesMsgWBAmount(OrderBookingVO orderBookingVO, double orderValue, String selectedUom,
                                              SchemeModel suggestedschemeModel, List<String> offerMsgList, double conversionWeight,
                                              Double payout, double slabConfigWeight) {
        if (isDiscountWBTrue(conversionWeight, suggestedschemeModel, slabConfigWeight)) {
            double reminingWeight = (suggestedschemeModel.getSlabFrom() - conversionWeight);
            int reminingQty = (int) Math.ceil(reminingWeight / orderBookingVO.getNetWeight());
            double reminingValue = calculateLineOrderValue(orderBookingVO, selectedUom, reminingQty);
            double dicountValue = (orderValue + reminingValue);
            getSuggestMessageWBAmount(offerMsgList, payout, dicountValue);
        }
        return offerMsgList;
    }

    private void getSuggestMessageWBAmount(List<String> offerMsgList, Double payout, double dicountValue) {
        if (payout > 0)
            offerMsgList.add(PURCHASE + context.getResources().getString(R.string.Rs) +
                    dicountValue + AND_ABOVE_GET +
                    context.getResources().getString(R.string.Rs) + " " + AppUtils.getOurInstance().decimalFormatWithTwoDigit(payout) + " off");
    }

    private List<String> getSuggestMsgAbAmount(double ordreValue, SchemeModel suggestedschemeModel, List<String> offerMsgList, Double payout, double abAmt) {
        if (isSuggestionAvailable(ordreValue > abAmt, ordreValue <= suggestedschemeModel.getSlabFrom(), payout > 0)) {
            getSuggestMessageWBAmount(offerMsgList, payout, suggestedschemeModel.getSlabFrom());
        }
        return offerMsgList;
    }

    private List<String> getSuggestMsgABFree(double ordreValue, SchemeModel suggestedschemeModel, List<String> offerMsgList, SchemeModel suggFreeProd, Integer freeqty, double abAmt) {
        if (isSuggestionAvailable(ordreValue > abAmt, ordreValue <= suggestedschemeModel.getSlabFrom(), freeqty > 0)) {
            offerMsgList.add(PURCHASE + context.getResources().getString(R.string.Rs) + suggFreeProd.getSlabFrom() +
                    AND_ABOVE_GET + freeqty + " " + suggFreeProd.getProdName() + " Free ");
        }
        return offerMsgList;
    }

    private boolean isDiscountTrue(double ordreValue, SchemeModel suggestedschemeModel, double abAmt) {
        return ordreValue > abAmt && ordreValue <= suggestedschemeModel.getSlabFrom();
    }

    private boolean isDiscountWBTrue(double conversionWeight, SchemeModel suggestedschemeModel, double slabConfigWeight) {
        return conversionWeight > slabConfigWeight && slabConfigWeight <= suggestedschemeModel.getSlabFrom();
    }

    private List<String> getSuggestMessage(SchemeModel suggestedschemeModel, List<String> offerMsgList, Double payout, double dicountValue) {
        if (dicountValue > 0) {
            offerMsgList.add(PURCHASE + context.getResources().getString(R.string.Rs) + suggestedschemeModel.getSlabFrom() +
                    AND_ABOVE_GET + context.getResources().getString(R.string.Rs) + " " + AppUtils.getOurInstance().decimalFormatWithTwoDigit(dicountValue) + "(" + payout + "%) off");
        }
        return offerMsgList;
    }

    private StringBuilder getOfferMessage(StringBuilder offerMsg, List<String> offerMsgList) {
        for (int i = 0; i < offerMsgList.size(); i++) {
            if (i == 0) {
                offerMsg.append(offerMsgList.get(i));
            } else {
                offerMsg.append("\n").append(offerMsgList.get(i));
            }
        }
        return offerMsg;
    }

    private boolean isSuggestionAvailable(boolean b, boolean b2, boolean b3) {
        return b && b2 && b3;
    }

    private boolean isSchemeType(String schemeBase, String base, String payoutType, String payout) {
        return schemeBase.equalsIgnoreCase(base)
                && payoutType.equalsIgnoreCase(payout);
    }


    /**
     * used to fetch non combi schemes and pass the scheme to calculatescheme method to get the applied scheme  details of the product.
     *
     * @param qtyUpdatedOrderBooking List of ordered products
     * @param schemeProductDetail    list schemes available for the retailer
     * @param tableName              calling tableName
     * @return list of schemes which applied in all single products
     */
    public List<SchemeModel> fetchLineLevelSchemeList(List<OrderBookingVO> qtyUpdatedOrderBooking, List<SchemeModel> schemeProductDetail,
                                                      String tableName) {
        List<SchemeModel> lineLevelSchemeList = new ArrayList<>();
        for (OrderBookingVO orderProds : qtyUpdatedOrderBooking) {

            final List<SchemeModel> productSchemeModelList = new ArrayList<>();
            final List<SchemeModel> schemeSlabList = new ArrayList<>();
            final List<SchemeModel> freeProdSlabList = new ArrayList<>();
            try {
                for (SchemeModel schemeModel : schemeProductDetail) {
                    if (orderProds.getProdCode().equalsIgnoreCase(schemeModel.getProductCode()) &&
                            schemeModel.getCombi().equalsIgnoreCase("N")) {
                        productSchemeModelList.add(schemeModel);
                    }
                }
                schemeSlabList.addAll(db.getAllSchemeSlabDetail(productSchemeModelList));
                freeProdSlabList.addAll(db.getAllFreeProdList(productSchemeModelList));


            } catch (Exception e) {
                Log.e(TAG, "fetchLineLevelSchemeList: " + e.getMessage(), e);
            }

            List<String> prodUomList = db.getUOMForProd(orderProds.getProdCode(), tableName);
            if (orderProds.getQuantity() > 0) {
                lineLevelSchemeList.addAll(calculateSchemes(orderProds.getPrimDiscOrderValue().doubleValue(), orderProds.getTotQty(), orderProds,
                        productSchemeModelList, freeProdSlabList, schemeSlabList, prodUomList));
            }
        }


        List<SchemeModel> schemeModels = new ArrayList<>(lineLevelSchemeList);
        for (SchemeModel schemeModel : schemeProductDetail) {
            for (SchemeModel appliedSlab : schemeModels) {
                if(schemeModel.getSchemeCode().equalsIgnoreCase(appliedSlab.getSchemeCode())) {
                    appliedSlab.setCmpCode(schemeModel.getCmpCode());
                    appliedSlab.setDistrCode(schemeModel.getDistrCode());
                    appliedSlab.setCustomerCode(schemeModel.getCustomerCode());
                }
            }
        }

        lineLevelSchemeList.clear();
        lineLevelSchemeList.addAll(schemeModels);
        return lineLevelSchemeList;
    }

    /**
     * used to find combi available product and schemes and pass to calcualtion method.
     *
     * @param tableName              calling table name
     * @param qtyUpdatedOrderBooking List of ordered products
     * @param schemeProductDetail    list schemes available for the retailer
     * @return applied combi slab list
     */
    public List<SchemeModel> fetchCombiProdlist(List<OrderBookingVO> qtyUpdatedOrderBooking, List<SchemeModel> schemeProductDetail, String tableName) {

        List<SchemeModel> appliedCombiSlablist = new ArrayList<>();
        List<SchemeModel> combiSchemesList = filterUniqueCombiScheme(schemeProductDetail);
        for (SchemeModel combiSchemeModel : combiSchemesList) {
            List<SchemeModel> combiSchemeProductList = db.getCombiProductList(sfaPref.readString(SFASharedPref.PREF_DISTRCODE), combiSchemeModel.getSchemeCode());
            List<SchemeModel> slabs = db.fetchSchemeSlabList(combiSchemeModel);
            SchemeModel isCombiAvailable = isCombiSchemeAvailable(qtyUpdatedOrderBooking, combiSchemeProductList, slabs, tableName);
            if (!slabs.isEmpty() && isCombiAvailable != null) {
                List<OrderBookingVO> combiAvailableProd = filterCombiAvailableProd(qtyUpdatedOrderBooking, combiSchemeProductList);
                double totalCombiOrderQty = getSumOfOrderQtyforCombi(isCombiAvailable, combiAvailableProd, tableName);
                double totalcombiOrderValue = getSumOfOrderValueforCombi(combiAvailableProd);
                appliedCombiSlablist.addAll(applycombiSlab(isCombiAvailable, combiAvailableProd, totalCombiOrderQty, totalcombiOrderValue));
            }

            List<SchemeModel> schemeModels = new ArrayList<>(appliedCombiSlablist);
            for (SchemeModel appliedSlab : schemeModels) {
                appliedSlab.setCmpCode(combiSchemeModel.getCmpCode());
                appliedSlab.setDistrCode(combiSchemeModel.getDistrCode());
                appliedSlab.setCustomerCode(combiSchemeModel.getCustomerCode());
            }

            appliedCombiSlablist.clear();
            appliedCombiSlablist.addAll(schemeModels);
        }

        List<SchemeModel> schemeModels = new ArrayList<>(appliedCombiSlablist);

        for (SchemeModel appliedSlab : schemeModels) {
            if (appliedSlab.getPayoutType().equals(SCHEME_PAYOUT_FREE_PROD)) {
                appliedCombiSlablist.remove(appliedSlab);
                appliedCombiSlablist.addAll(db.fetchSchemeSlabProductList(appliedSlab));
            }
        }

        return appliedCombiSlablist;
    }

    public List<SchemeModel> checkSchemeBudget(List<SchemeModel> schemeModels, List<SchemeDistrBudgetModel> budgetList) {
        List<SchemeModel> budgetAvailableSchemes = new ArrayList<>();

        for (SchemeModel scheme : schemeModels) {
            if (scheme.getPayoutType().equals(SCHEME_PAYOUT_AMOUNT) || scheme.getPayoutType().equals(SCHEME_PAYOUT_DISCOUNT)) {
                for (SchemeDistrBudgetModel budget : budgetList) {
                    if (scheme.getSchemeCode().equals(budget.getSchemeCode())) {
                        if (budget.getBudget() > scheme.getFlatAmount() && scheme.getFlatAmount() > 0) {
                            budgetAvailableSchemes.add(scheme);
                        }
                    }
                }
            } else {
//                List<SchemeModel> schemeProducts = db.getSchemeFreeProducts()
                OrderBookingVO batchVO;
                if ("N".equalsIgnoreCase(scheme.getIsMandatory())) {
                    batchVO = db.getBatchCodeOfFreeProduct(scheme.getFreeProdCode(), scheme.getFreeQty(), false);
                } else {
                    batchVO = db.getBatchCodeOfFreeProduct(scheme.getFreeProdCode(), scheme.getFreeQty(), true);
                }

                if (batchVO != null && !batchVO.getProdBatchCode().isEmpty() && scheme.getFreeQty() > 0) {
                    scheme.setFreeProdBatchCode(batchVO.getProdBatchCode());
                    scheme.setSellPrice(batchVO.getSellPrice());
                    scheme.setMrp(batchVO.getMrp());
                    budgetAvailableSchemes.add(scheme);
                }
            }
        }

        return budgetAvailableSchemes;
    }

    /**
     * used to fetch multi prod schemes available list.
     *
     * @param qtyUpdatedOrderBooking List of ordered products
     * @param schemeProductDetail    list schemes available for the retailer
     * @param tableName              calling table name
     * @return multi prod applied scheme slab liat
     */
    public List<SchemeModel> fetchSchemeMultiProdlist(List<OrderBookingVO> qtyUpdatedOrderBooking, List<SchemeModel> schemeProductDetail, String tableName) {

        List<SchemeModel> appliedCombiSlablist = new ArrayList<>();
        List<SchemeModel> combiSchemesList = filterUniqueMultiProdScheme(schemeProductDetail);
        for (SchemeModel combiSchemeModel : combiSchemesList) {
            List<SchemeModel> schemeProdsList = getSingleSchemeProds(combiSchemeModel, schemeProductDetail);
            List<SchemeModel> slabs = db.fetchSchemeSlabList(combiSchemeModel);
            SchemeModel isMultipleSchemeModel = isMultiSchemeAvailable(qtyUpdatedOrderBooking, schemeProdsList, slabs, tableName);
            if (!slabs.isEmpty() && isMultipleSchemeModel != null) {
                List<OrderBookingVO> combiAvailableProd = filterCombiAvailableProd(qtyUpdatedOrderBooking, schemeProdsList);
                if (combiAvailableProd.size() > 1) {
                    double totalCombiOrderQty = getSumOfOrderQtyforCombi(isMultipleSchemeModel, combiAvailableProd, tableName);
                    double totalcombiOrderValue = getSumOfOrderValueforCombi(combiAvailableProd);
                    appliedCombiSlablist.addAll(applycombiSlab(isMultipleSchemeModel, combiAvailableProd, totalCombiOrderQty, totalcombiOrderValue));
                }
            }

            List<SchemeModel> schemeModels = new ArrayList<>(appliedCombiSlablist);
            for (SchemeModel appliedSlab : schemeModels) {
                appliedSlab.setCmpCode(combiSchemeModel.getCmpCode());
                appliedSlab.setDistrCode(combiSchemeModel.getDistrCode());
                appliedSlab.setCustomerCode(combiSchemeModel.getCustomerCode());
            }

            appliedCombiSlablist.clear();
            appliedCombiSlablist.addAll(schemeModels);
        }

        List<SchemeModel> schemeModels = new ArrayList<>(appliedCombiSlablist);

        for (SchemeModel appliedSlab : schemeModels) {
            if (appliedSlab.getPayoutType().equals(SCHEME_PAYOUT_FREE_PROD)) {
                appliedCombiSlablist.addAll(db.fetchSchemeSlabProductList(appliedSlab));
                appliedCombiSlablist.remove(appliedSlab);
            }
        }

        return appliedCombiSlablist;
    }

    private List<SchemeModel> getSingleSchemeProds(SchemeModel combiSchemeModel, List<SchemeModel> schemeProductDetail) {
        List<SchemeModel> singleSchemeProds = new ArrayList<>();
        for (SchemeModel schemeModel : schemeProductDetail) {
            if (schemeModel.getSchemeCode().equalsIgnoreCase(combiSchemeModel.getSchemeCode()))
                singleSchemeProds.add(schemeModel);
        }
        return singleSchemeProds;
    }

    /**
     * calculate sum of order Qty for combi schemes
     *
     * @param combiSchemeModel combi scheme model list
     * @param inputProducts    List ordered products
     * @param tableName        calling class name
     * @return sum of combi prod qty.
     */
    private double getSumOfOrderQtyforCombi(SchemeModel combiSchemeModel, List<OrderBookingVO> inputProducts, String tableName) {
        double sumOfcombiProd = 0;
        if (!inputProducts.isEmpty()) {
            for (OrderBookingVO inputOrderProduct : inputProducts) {
                if ("WB".equalsIgnoreCase(combiSchemeModel.getSchemeBase())) {
//                    sumOfcombiProd = sumOfcombiProd + calcSchemeUomQty(inputOrderProduct, inputOrderProduct.getBaseUOM(), inputOrderProduct.getTotQty());
//                    sumOfcombiProd = sumOfcombiProd + (inputOrderProduct.getNetWeight() * inputOrderProduct.getTotInvoiceQty());
                } else {
                    List<String> uomForProd = db.getUOMForProd(inputOrderProduct.getProdCode(), tableName);
                    if (isProductUomAvailable(uomForProd, combiSchemeModel.getUom())) {
                        sumOfcombiProd = sumOfcombiProd + calcSchemeUomQty(inputOrderProduct, combiSchemeModel.getUom(), inputOrderProduct.getTotQty());
                    }
                }
            }
        }
        return sumOfcombiProd;
    }

    /**
     * used to apply combi scheme slab list for all product
     *
     * @param combiSchemeModel     combi scheme model
     * @param combiAvailableProd   list of ordered products
     * @param totalCombiOrderQty   combi order qty to check combi scheme.
     * @param totalcombiOrderValue combi order value to check combi scheme.
     * @return applied combi scheme list
     */
    private List<SchemeModel> applycombiSlab(SchemeModel combiSchemeModel, List<OrderBookingVO> combiAvailableProd, double totalCombiOrderQty,
                                             double totalcombiOrderValue) {

        List<SchemeModel> appliedCombiSlablist = new ArrayList<>();
        SchemeModel schemeDefinition = db.getSchemeDefinition(sfaPref.readString(SFASharedPref.PREF_DISTRCODE), combiSchemeModel.getSchemeCode());
        for (OrderBookingVO combiaApplyProd : combiAvailableProd) {
            if (combiaApplyProd.getPrimDiscOrderValue().doubleValue() > 0.0 &&
                    schemeDefinition != null && !schemeDefinition.getPayoutType().equalsIgnoreCase(SCHEME_PAYOUT_FREE_PROD)) {
                applyCombiSchemeNonFreePayout(combiSchemeModel, totalCombiOrderQty, totalcombiOrderValue, appliedCombiSlablist, combiaApplyProd);

            } else {
                applyCombiSchemeFreePayout(combiSchemeModel, totalCombiOrderQty, totalcombiOrderValue, appliedCombiSlablist, schemeDefinition, combiaApplyProd);
            }
        }

        return appliedCombiSlablist;
    }

    private void applyCombiSchemeFreePayout(SchemeModel combiSchemeModel, double totalCombiOrderQty, double totalcombiOrderValue, List<SchemeModel> appliedCombiSlablist, SchemeModel schemeDefinition, OrderBookingVO combiaApplyProd) {
        if (combiaApplyProd.getPrimDiscOrderValue().doubleValue() > 0.0) {
            List<SchemeModel> freeProdCombiSchemeSlab = db.getFreeProdSingleScheme(combiSchemeModel.getSchemeCode());
//            List<SchemeModel> freeProdCombiSchemeSlab = new ArrayList<>();
//            freeProdCombiSchemeSlab.add(combiSchemeModel);
            SchemeModel appliedSlab = new SchemeModel();
            for (SchemeModel freeProdSlabModel : freeProdCombiSchemeSlab) {
                if (freeProdSlabModel.getSchemeBase().equalsIgnoreCase(SCHEME_QUANTITY_BASE)) {
                    appliedSlab = getCombiFreeProdScheme(totalCombiOrderQty, schemeDefinition, combiaApplyProd, appliedSlab, freeProdSlabModel);
                } else if (freeProdSlabModel.getSchemeBase().equalsIgnoreCase(SCHEME_WEIGHT_BASE)) {
                    appliedSlab = getCombiFreeProdScheme(totalCombiOrderQty, schemeDefinition, combiaApplyProd, appliedSlab, freeProdSlabModel);
                } else if (freeProdSlabModel.getSchemeBase().equalsIgnoreCase(SCHEME_AMOUNT_BASE)) {
                    appliedSlab = getCombiFreeProdScheme(totalcombiOrderValue, schemeDefinition, combiaApplyProd, appliedSlab, freeProdSlabModel);
                }
//                checkAlreadyAppliedSlab(tempSchemeSlabs, appliedSlab);
            }
            appliedSlab.setInvoiceNo(combiaApplyProd.getOrderInvoiceNo());
            appliedSlab.setReadableInvNo(combiaApplyProd.getReadableInvNo());
            appliedCombiSlablist.add(appliedSlab);

            for (int i = 0; i < appliedCombiSlablist.size(); i++) {
                for (int j = 1; j < appliedCombiSlablist.size(); j++) {
                    if (appliedCombiSlablist.get(i).equals(appliedCombiSlablist.get(j))) {
                        if (appliedCombiSlablist.get(i).getPayoutType().equals(SCHEME_PAYOUT_FREE_PROD)) {
                            if ((appliedCombiSlablist.get(i).getSlabNo().equals(appliedCombiSlablist.get(j).getSlabNo())) && (appliedCombiSlablist.get(i).getFreeProdCode().equals(appliedCombiSlablist.get(j).getFreeProdCode())) && (appliedCombiSlablist.get(i).getProductCode().equals(appliedCombiSlablist.get(j).getProductCode())) && (appliedCombiSlablist.get(i).getProdBatchCode().equals(appliedCombiSlablist.get(j).getProdBatchCode()))) {
                                appliedCombiSlablist.remove(i);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * used to find the combi applied free product list
     *
     * @param totalCombiOrderQtyOrValue total Combi order Qty or Value
     * @param schemeDefinition          scheme definition for the product
     * @param combiaApplyProd           all eligible combi products
     * @param appliedSlab               combi eligiible slab list
     * @param freeProdSlabModel         free product slab list
     * @return return applied slab details
     */
    private SchemeModel getCombiFreeProdScheme(double totalCombiOrderQtyOrValue, SchemeModel schemeDefinition, OrderBookingVO combiaApplyProd, SchemeModel appliedSlab, SchemeModel freeProdSlabModel) {
        if (isSlabEqualZero(freeProdSlabModel)) {
            if (isSlabGreaterThanFrom(freeProdSlabModel, totalCombiOrderQtyOrValue)) {
                appliedSlab = getFreeProductBasedOnMandatory(totalCombiOrderQtyOrValue, schemeDefinition, freeProdSlabModel);
                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
            }
        } else if (isSlabGreaterZero(freeProdSlabModel)) {
            if (isSlabBetweenFromAndTo(freeProdSlabModel, totalCombiOrderQtyOrValue)) {
                appliedSlab = getFreeProductBasedOnMandatory(totalCombiOrderQtyOrValue, schemeDefinition, freeProdSlabModel);
                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
            }

        } else {
            if (isSlabGreaterThanFrom(freeProdSlabModel, totalCombiOrderQtyOrValue)) {
                appliedSlab = getFreeProductBasedOnMandatory(totalCombiOrderQtyOrValue, schemeDefinition, freeProdSlabModel);
                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
            }
        }
        return appliedSlab;
    }

    /**
     * Used to calculate combi Non free  payout Scheme list
     *
     * @param combiSchemeModel     Combi scheme model list
     * @param totalCombiOrderQty   total Combi product order qty
     * @param totalcombiOrderValue total combi product order value
     * @param appliedCombiSlablist eligible combi slab list
     * @param combiaApplyProd      combi eligible product details
     */
    private void applyCombiSchemeNonFreePayout(SchemeModel combiSchemeModel, double totalCombiOrderQty, double totalcombiOrderValue,
                                               List<SchemeModel> appliedCombiSlablist, OrderBookingVO combiaApplyProd) {
//        List<SchemeModel> combiSchemeSlablist = db.getCombiSchemeSlabList(sfaPref.readString(SFASharedPref.PREF_DISTRCODE), combiSchemeModel.getSchemeCode());
        List<SchemeModel> combiSchemeSlablist = new ArrayList<>();
        combiSchemeSlablist.add(combiSchemeModel);
        SchemeModel appliedSlab = new SchemeModel();
        for (SchemeModel slabmodel : combiSchemeSlablist) {
            if (isSchemeType(slabmodel.getSchemeBase(), SCHEME_QUANTITY_BASE, slabmodel.getPayoutType(), SCHEME_PAYOUT_AMOUNT)) {
                appliedSlab = getCombiAmountScheme(totalCombiOrderQty, totalcombiOrderValue, combiaApplyProd, appliedSlab, slabmodel,
                        isSlabGreaterThanFrom(slabmodel, totalCombiOrderQty), isSlabBetweenFromAndTo(slabmodel, totalCombiOrderQty), SCHEME_QUANTITY_BASE);
            } else if (isSchemeType(slabmodel.getSchemeBase(), SCHEME_AMOUNT_BASE, slabmodel.getPayoutType(), SCHEME_PAYOUT_AMOUNT)) {
                appliedSlab = getCombiAmountScheme(totalCombiOrderQty, totalcombiOrderValue, combiaApplyProd, appliedSlab, slabmodel,
                        isSlabGreaterThanFrom(slabmodel, totalcombiOrderValue), isSlabBetweenFromAndTo(slabmodel, totalcombiOrderValue), SCHEME_AMOUNT_BASE);

            } else if (isSchemeType(slabmodel.getSchemeBase(), SCHEME_WEIGHT_BASE, slabmodel.getPayoutType(), SCHEME_PAYOUT_AMOUNT)) {
                appliedSlab = getCombiAmountScheme(totalCombiOrderQty, totalcombiOrderValue, combiaApplyProd, appliedSlab, slabmodel,
                        isSlabGreaterThanFrom(slabmodel, totalcombiOrderValue), isSlabBetweenFromAndTo(slabmodel, totalcombiOrderValue), SCHEME_WEIGHT_BASE);
            } else if (isSchemeType(slabmodel.getSchemeBase(), SCHEME_WEIGHT_BASE, slabmodel.getPayoutType(), SCHEME_PAYOUT_DISCOUNT)) {

                appliedSlab = getCombiDiscountScheme(combiaApplyProd, appliedSlab, slabmodel, isSlabGreaterThanFrom(slabmodel, totalCombiOrderQty),
                        isSlabBetweenFromAndTo(slabmodel, totalCombiOrderQty));

            } else if (isSuggestionDiscountPayout(slabmodel, SCHEME_QUANTITY_BASE, SCHEME_PAYOUT_DISCOUNT)) {
                appliedSlab = getCombiDiscountScheme(combiaApplyProd, appliedSlab, slabmodel, isSlabGreaterThanFrom(slabmodel, totalCombiOrderQty),
                        isSlabBetweenFromAndTo(slabmodel, totalCombiOrderQty));

            } else if (isSuggestionDiscountPayout(slabmodel, SCHEME_AMOUNT_BASE, SCHEME_PAYOUT_DISCOUNT)) {
                appliedSlab = getCombiDiscountScheme(combiaApplyProd, appliedSlab, slabmodel, isSlabGreaterThanFrom(slabmodel, totalcombiOrderValue),
                        isSlabBetweenFromAndTo(slabmodel, totalcombiOrderValue));
            }
        }
        if (appliedSlab.getSchemeCode() != null && !appliedSlab.getSchemeCode().isEmpty()) {
            appliedSlab.setInvoiceNo(combiaApplyProd.getOrderInvoiceNo());
            appliedSlab.setReadableInvNo(combiaApplyProd.getReadableInvNo());
            appliedCombiSlablist.add(appliedSlab);
        }
    }


    /**
     * Used to calculate combi discount schemes
     *
     * @param combiaApplyProd      Combi eligible product Details
     * @param appliedSlab          Combi slab details
     * @param slabmodel            all slab details fot the product
     * @param slabGreaterThanFrom  check qty greater than  slab from
     * @param slabBetweenFromAndTo check qty inbetween slabs
     * @return return applied Slab details
     */
    private SchemeModel getCombiDiscountScheme(OrderBookingVO combiaApplyProd, SchemeModel appliedSlab,
                                               SchemeModel slabmodel, boolean slabGreaterThanFrom, boolean slabBetweenFromAndTo) {
        if (isSlabEqualZero(slabmodel)) {
            if (slabGreaterThanFrom) {
//                appliedSlab = slabmodel;
                appliedSlab.setCombi(slabmodel.getCombi());
                appliedSlab.setIsActive(slabmodel.getIsActive());
                appliedSlab.setIsSkuLevel(slabmodel.getIsSkuLevel());
                appliedSlab.setPayoutType(slabmodel.getPayoutType());
                appliedSlab.setPayoutValue(slabmodel.getPayoutValue());
                appliedSlab.setSchemeBase(slabmodel.getSchemeBase());
                appliedSlab.setSchemeCode(slabmodel.getSchemeCode());
                appliedSlab.setSlabNo(slabmodel.getSlabNo());
                appliedSlab.setSlabRange(slabmodel.getSlabRange());
                appliedSlab.setSlabFrom(slabmodel.getSlabFrom());
                appliedSlab.setSlabTo(slabmodel.getSlabTo());
                appliedSlab.setUom(slabmodel.getUom());

                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
                double percentValue = getDisPercentValuePrimaryCheck(db, combiaApplyProd.getPrimDiscOrderValue().doubleValue(), appliedSlab.getPayoutValue());
//                double percentValue = combiaApplyProd.getPrimDiscOrderValue().doubleValue() * appliedSlab.getPayoutValue() / 100.00;
                appliedSlab.setFlatAmount(percentValue);
                appliedSlab.setPercentage(appliedSlab.getPayoutValue().intValue());
            }
        } else if (isSlabGreaterZero(slabmodel)) {
            if (slabBetweenFromAndTo) {
//                appliedSlab = slabmodel;
                appliedSlab.setCombi(slabmodel.getCombi());
                appliedSlab.setIsActive(slabmodel.getIsActive());
                appliedSlab.setIsSkuLevel(slabmodel.getIsSkuLevel());
                appliedSlab.setPayoutType(slabmodel.getPayoutType());
                appliedSlab.setPayoutValue(slabmodel.getPayoutValue());
                appliedSlab.setSchemeBase(slabmodel.getSchemeBase());
                appliedSlab.setSchemeCode(slabmodel.getSchemeCode());
                appliedSlab.setSlabNo(slabmodel.getSlabNo());
                appliedSlab.setSlabRange(slabmodel.getSlabRange());
                appliedSlab.setSlabFrom(slabmodel.getSlabFrom());
                appliedSlab.setSlabTo(slabmodel.getSlabTo());
                appliedSlab.setUom(slabmodel.getUom());

                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
                double percentValue = getDisPercentValuePrimaryCheck(db, combiaApplyProd.getPrimDiscOrderValue().doubleValue(), appliedSlab.getPayoutValue());
//                double percentValue = combiaApplyProd.getPrimDiscOrderValue().doubleValue() * appliedSlab.getPayoutValue() / 100.00;
                appliedSlab.setFlatAmount(percentValue);
                appliedSlab.setPercentage(appliedSlab.getPayoutValue().intValue());
            }
        } else {
            if (slabGreaterThanFrom) {
//                appliedSlab = slabmodel;
                appliedSlab.setCombi(slabmodel.getCombi());
                appliedSlab.setIsActive(slabmodel.getIsActive());
                appliedSlab.setIsSkuLevel(slabmodel.getIsSkuLevel());
                appliedSlab.setPayoutType(slabmodel.getPayoutType());
                appliedSlab.setPayoutValue(slabmodel.getPayoutValue());
                appliedSlab.setSchemeBase(slabmodel.getSchemeBase());
                appliedSlab.setSchemeCode(slabmodel.getSchemeCode());
                appliedSlab.setSlabNo(slabmodel.getSlabNo());
                appliedSlab.setSlabRange(slabmodel.getSlabRange());
                appliedSlab.setSlabFrom(slabmodel.getSlabFrom());
                appliedSlab.setSlabTo(slabmodel.getSlabTo());
                appliedSlab.setUom(slabmodel.getUom());

                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
                double percentValue = getDisPercentValuePrimaryCheck(db, combiaApplyProd.getPrimDiscOrderValue().doubleValue(), appliedSlab.getPayoutValue());
//                double percentValue = combiaApplyProd.getPrimDiscOrderValue().doubleValue() * appliedSlab.getPayoutValue() / 100.00;
                appliedSlab.setFlatAmount(percentValue);
                appliedSlab.setPercentage(appliedSlab.getPayoutValue().intValue());
            }
        }
        return appliedSlab;
    }

    /**
     * Used to calculate combi Amount schemes
     *
     * @param totalcombiOrderValue total combi order values
     * @param combiaApplyProd      Combi eligible product Details
     * @param appliedSlab          Combi slab details
     * @param slabmodel            all slab details fot the product
     * @param slabGreaterThanFrom  check qty greater than  slab from
     * @param slabBetweenFromAndTo check qty inbetween slabs
     * @param schemeBase           schemeBase for the product
     * @return return applied Slab details
     */
    private SchemeModel getCombiAmountScheme(double totalcombiOrderQty, double totalcombiOrderValue, OrderBookingVO combiaApplyProd,
                                             SchemeModel appliedSlab, SchemeModel slabmodel, boolean slabGreaterThanFrom,
                                             boolean slabBetweenFromAndTo, String schemeBase) {
        if (isSlabEqualZero(slabmodel)) {
            if (slabGreaterThanFrom) {
//                appliedSlab = slabmodel;
                appliedSlab.setCombi(slabmodel.getCombi());
                appliedSlab.setIsActive(slabmodel.getIsActive());
                appliedSlab.setIsSkuLevel(slabmodel.getIsSkuLevel());
                appliedSlab.setPayoutType(slabmodel.getPayoutType());
                appliedSlab.setPayoutValue(slabmodel.getPayoutValue());
                appliedSlab.setSchemeBase(slabmodel.getSchemeBase());
                appliedSlab.setSchemeCode(slabmodel.getSchemeCode());
                appliedSlab.setSlabNo(slabmodel.getSlabNo());
                appliedSlab.setSlabRange(slabmodel.getSlabRange());
                appliedSlab.setSlabFrom(slabmodel.getSlabFrom());
                appliedSlab.setSlabTo(slabmodel.getSlabTo());
                appliedSlab.setUom(slabmodel.getUom());

                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
                appliedSlab.setFlatAmount((calCombiPayoutValForEachProd(totalcombiOrderQty, totalcombiOrderValue, combiaApplyProd, slabmodel, schemeBase)).doubleValue());
            }
        } else if (isSlabGreaterZero(slabmodel)) {
            if (slabBetweenFromAndTo) {
//                appliedSlab = slabmodel;
                appliedSlab.setCombi(slabmodel.getCombi());
                appliedSlab.setIsActive(slabmodel.getIsActive());
                appliedSlab.setIsSkuLevel(slabmodel.getIsSkuLevel());
                appliedSlab.setPayoutType(slabmodel.getPayoutType());
                appliedSlab.setPayoutValue(slabmodel.getPayoutValue());
                appliedSlab.setSchemeBase(slabmodel.getSchemeBase());
                appliedSlab.setSchemeCode(slabmodel.getSchemeCode());
                appliedSlab.setSlabNo(slabmodel.getSlabNo());
                appliedSlab.setSlabRange(slabmodel.getSlabRange());
                appliedSlab.setSlabFrom(slabmodel.getSlabFrom());
                appliedSlab.setSlabTo(slabmodel.getSlabTo());
                appliedSlab.setUom(slabmodel.getUom());

                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
                appliedSlab.setFlatAmount((calCombiPayoutValForEachProd(totalcombiOrderQty, totalcombiOrderValue, combiaApplyProd, slabmodel, schemeBase)).doubleValue());
            }
        } else {
            if (slabGreaterThanFrom) {
//                appliedSlab = slabmodel;
                appliedSlab.setCombi(slabmodel.getCombi());
                appliedSlab.setIsActive(slabmodel.getIsActive());
                appliedSlab.setIsSkuLevel(slabmodel.getIsSkuLevel());
                appliedSlab.setPayoutType(slabmodel.getPayoutType());
                appliedSlab.setPayoutValue(slabmodel.getPayoutValue());
                appliedSlab.setSchemeBase(slabmodel.getSchemeBase());
                appliedSlab.setSchemeCode(slabmodel.getSchemeCode());
                appliedSlab.setSlabNo(slabmodel.getSlabNo());
                appliedSlab.setSlabRange(slabmodel.getSlabRange());
                appliedSlab.setSlabFrom(slabmodel.getSlabFrom());
                appliedSlab.setSlabTo(slabmodel.getSlabTo());
                appliedSlab.setUom(slabmodel.getUom());

                appliedSlab.setProductCode(combiaApplyProd.getProdCode());
                appliedSlab.setProdBatchCode(combiaApplyProd.getProdBatchCode());
                appliedSlab.setFlatAmount((calCombiPayoutValForEachProd(totalcombiOrderQty, totalcombiOrderValue, combiaApplyProd, slabmodel, schemeBase)).doubleValue());
            }
        }
        return appliedSlab;
    }

    /**
     * used to check slab range and slab to
     *
     * @param slabmodel current slab object
     * @return true if slab range YES and Slab To > Zero
     */
    private boolean isSlabGreaterZero(SchemeModel slabmodel) {
        return slabmodel.getSlabRange().equalsIgnoreCase("Y") && slabmodel.getSlabTo() > 0;
    }

    /**
     * used to check slab range and slab to
     *
     * @param slabmodel current slab object
     * @return true if slab range YES and Slab To == Zerro
     */
    private boolean isSlabEqualZero(SchemeModel slabmodel) {
        return slabmodel.getSlabRange().equalsIgnoreCase("Y") &&
                slabmodel.getSlabTo() == 0;
    }

    /**
     * used to filter combi products based on prod code
     *
     * @param orderBookingVOS       list of ordered products
     * @param combiSchemeDetailList list of available combi scheme list
     * @return filter combi available product list
     */
    private List<OrderBookingVO> filterCombiAvailableProd(List<OrderBookingVO> orderBookingVOS, List<SchemeModel> combiSchemeDetailList) {
        List<OrderBookingVO> filterCombiAvalProd = new ArrayList<>();
        for (SchemeModel combiProduct : combiSchemeDetailList) {
            for (OrderBookingVO inputProduct : orderBookingVOS) {
                if (inputProduct.getProdCode().equalsIgnoreCase(combiProduct.getProductCode()) && !filterCombiAvalProd.contains(inputProduct)) {
                    filterCombiAvalProd.add(inputProduct);
                }
            }

        }
        return filterCombiAvalProd;
    }


    /**
     * used to find combi available scheme based on the combi conditions
     *
     * @param inputProducts          list of combi available products
     * @param combiSchemeProductList list of combi scheme details
     * @param slabs                  lsit of slabs for the schemes
     * @param tableName              calling class name
     * @return scheme slab details
     */
    private SchemeModel isCombiSchemeAvailable(List<OrderBookingVO> inputProducts, List<SchemeModel> combiSchemeProductList,
                                               List<SchemeModel> slabs, String tableName) {

        SchemeModel flagSlab = null;
        boolean isCombiConditional = getIsCombiConditional(combiSchemeProductList);
        if (isCombiConditional) {
            flagSlab = getConditonalSchemeSlabs(inputProducts, combiSchemeProductList, slabs, tableName);
        } else {
            for (SchemeModel slabModel : slabs) {
                List<SchemeModel> combiProdList = new ArrayList<>();
                for (SchemeModel combiProd : combiSchemeProductList) {
                    if (slabModel.getSlabNo().equalsIgnoreCase(combiProd.getSlabNo())) {
                        combiProdList.add(combiProd);
                    }
                }
                List<SchemeModel> slabList = new ArrayList<>();
                slabList.add(slabModel);
                flagSlab = getCombiNonSchemeSlabs(inputProducts, combiProdList, slabList, tableName, flagSlab);
            }
        }
        return flagSlab;
    }

    /**
     * @param inputProducts          ordered produt list
     * @param combiSchemeProductList combi scheme product list
     * @param slabs                  combi scheme slab list
     * @param tableName              product table name ( Either Product table or VAN SALES product table)
     * @return slab
     */
    private SchemeModel getCombiNonSchemeSlabs(List<OrderBookingVO> inputProducts, List<SchemeModel> combiSchemeProductList, List<SchemeModel> slabs, String tableName, SchemeModel flagSlab) {
//        SchemeModel flagSlab = null;
        double uomConvQty = 0d;
        boolean mandatoryProd = false;
        for (SchemeModel slabModel : slabs) {
            for (SchemeModel combiProduct : combiSchemeProductList) {
                for (OrderBookingVO inputProduct : inputProducts) {
                    if (inputProduct.getProdCode().equalsIgnoreCase(combiProduct.getProductCode())) {
                        List<String> uomList = db.getUOMForCombiProduct(combiProduct.getProductCode(), tableName);
                        if (isCombiMandatory(combiProduct, inputProduct, combiProduct.getIsMandatory())) {
                            mandatoryProd = true;
                            if (combiProduct.getSchemeBase().equalsIgnoreCase(SCHEME_QUANTITY_BASE)) {
                                if (calcSchemeUomQty(inputProduct, slabModel.getUom(), inputProduct.getTotQty()) >= combiProduct.getMinValue()) {
                                    if (isProductUomAvailable(uomList, slabModel.getUom()))
                                        uomConvQty = uomConvQty + calcSchemeUomQty(inputProduct, slabModel.getUom(), inputProduct.getTotQty());
                                } else {
                                    return flagSlab;
                                }
                            } else if (combiProduct.getSchemeBase().equalsIgnoreCase(SCHEME_AMOUNT_BASE)) {
                                uomConvQty = uomConvQty + inputProduct.getOrderValue().doubleValue();
                            } else if (combiProduct.getSchemeBase().equalsIgnoreCase(SCHEME_WEIGHT_BASE)) {
                                uomConvQty = uomConvQty + (inputProduct.getNetWeight() * inputProduct.getTotQty());
                            }
                        } else {
                            if (combiProduct.getSchemeBase().equalsIgnoreCase(SCHEME_QUANTITY_BASE)) {
                                if (isProductUomAvailable(uomList, slabModel.getUom()))
                                    uomConvQty = uomConvQty + calcSchemeUomQty(inputProduct, slabModel.getUom(), inputProduct.getTotQty());
                            } else if (combiProduct.getSchemeBase().equalsIgnoreCase(SCHEME_AMOUNT_BASE)) {
                                uomConvQty = uomConvQty + inputProduct.getOrderValue().doubleValue();
                            } else if (combiProduct.getSchemeBase().equalsIgnoreCase(SCHEME_WEIGHT_BASE)) {
                                uomConvQty = uomConvQty + (inputProduct.getNetWeight() * inputProduct.getTotQty());
                            }
                        }

                    }
                }
            }
            if (mandatoryProd)
                flagSlab = getSlabDetails(flagSlab, uomConvQty, slabModel);
        }
        return flagSlab;
    }

    private SchemeModel getConditonalSchemeSlabs(List<OrderBookingVO> inputProducts, List<SchemeModel> combiSchemeProductList, List<SchemeModel> slabs, String tableName) {
        double uomConvQty = 0d;
        boolean conditonalProd = false;
        SchemeModel flagSlab = null;
        for (SchemeModel slabModel : slabs) {
            for (SchemeModel combiProduct : combiSchemeProductList) {
                for (OrderBookingVO inputProduct : inputProducts) {
                    if (inputProduct.getProdCode().equalsIgnoreCase(combiProduct.getProductCode())) {
                        List<String> uomList = db.getUOMForCombiProduct(combiProduct.getProductCode(), tableName);
                        if (isProductUomAvailable(uomList, slabModel.getUom()) && calcSchemeUomQty(inputProduct, slabModel.getUom(), inputProduct.getTotQty()) >= combiProduct.getMinValue()) {
                            conditonalProd = true;
                            uomConvQty = uomConvQty + calcSchemeUomQty(inputProduct, slabModel.getUom(), inputProduct.getTotQty());
                        } else {
                            return null;
                        }
                    }
                }
            }
            if (conditonalProd)
                flagSlab = getSlabDetails(flagSlab, uomConvQty, slabModel);
        }
        return flagSlab;
    }

    private boolean getIsCombiConditional(List<SchemeModel> combiSchemeProductList) {
        for (SchemeModel combiProduct : combiSchemeProductList)
            if (combiProduct.getIsMandatory().equalsIgnoreCase("Y")) {
                return false;
            }
        return true;
    }


    private boolean isCombiMandatory(SchemeModel combiProduct, OrderBookingVO inputProduct, String isMandatory) {
        return inputProduct.getProdCode().equalsIgnoreCase(combiProduct.getProductCode()) &&
                isMandatory.equalsIgnoreCase("Y");
    }

    private SchemeModel getSlabDetails(SchemeModel flagSlab, double uomConvQty, SchemeModel slabModel) {
        if (isSlabGreaterThanFrom(slabModel, uomConvQty)) {
            flagSlab = slabModel;
        }
        return flagSlab;
    }

    /**
     * used to find multi schemes available products based on the Multi scheme condition
     *
     * @param inputProducts   list of combi available products
     * @param schemeProdsList list of scheme products
     * @param slabs           lsit of slabs for the schemes
     * @param tableName       calling table name
     * @return scheme slab details
     */
    private SchemeModel isMultiSchemeAvailable(List<OrderBookingVO> inputProducts,
                                               List<SchemeModel> schemeProdsList, List<SchemeModel> slabs, String tableName) {
        SchemeModel flagSlab = null;
        Integer sumOfLineQty = 0;
        double uomConvQty = 0d;
        if (inputProducts.size() > 1) {
            for (SchemeModel slabModel : slabs) {
                for (SchemeModel combiProduct : schemeProdsList) {
                    for (int i = 0, inputProductsSize = inputProducts.size(); i < inputProductsSize; i++) {
                        OrderBookingVO inputProduct = inputProducts.get(i);

                        if (inputProduct.getProdCode().equalsIgnoreCase(combiProduct.getProductCode())) {
                            sumOfLineQty = sumOfLineQty + inputProduct.getTotQty();

                            if (combiProduct.getSchemeBase().equalsIgnoreCase("AB")) {
                                uomConvQty = uomConvQty + inputProduct.getOrderValue().doubleValue();
                            } else if ("WB".equalsIgnoreCase(combiProduct.getSchemeBase())) {
                                uomConvQty = uomConvQty + (inputProduct.getNetWeight() * inputProduct.getTotQty());
                            } else {
                                List<String> uomForProd = db.getUOMForProd(combiProduct.getProductCode(), tableName);
                                uomConvQty = getUomConvQty(sumOfLineQty, uomConvQty, slabModel, inputProduct, uomForProd);
                            }


                        }

                    }
                }

                flagSlab = getSlabDetails(flagSlab, uomConvQty, slabModel);
            }
        }

        if (flagSlab != null)
            for (SchemeModel combiProduct : schemeProdsList) {
                if (flagSlab.getSchemeCode().equals(combiProduct.getSchemeCode())) {
                    flagSlab.setSchemeBase(combiProduct.getSchemeBase());
                    break;
                }
            }

        return flagSlab;
    }


    /**
     * * used to filter unique Multi prod scheme list
     *
     * @param appliedSlablist scheme applied list
     * @return multi scheme list
     */
    private List<SchemeModel> filterUniqueMultiProdScheme(List<SchemeModel> appliedSlablist) {
        List<String> stringList = new ArrayList<>();
        List<SchemeModel> combiScheme = new ArrayList<>();
        for (SchemeModel schemeModel : appliedSlablist) {
            if (schemeModel.getCombi().equalsIgnoreCase("N") && !stringList.contains(schemeModel.getSchemeCode())) {
                stringList.add(schemeModel.getSchemeCode());
                combiScheme.add(schemeModel);
            }
        }
        return combiScheme;
    }

    /**
     * used to get  and set applied scheme details
     *
     * @param slabmodel      slab details list for the scheme
     * @param schemeCodelist scheme definition details list
     * @return SCheme model which is having all scheme detials of the scheme
     */
    @Override
    SchemeModel getAppliedSlabDetails(SchemeModel slabmodel, SchemeModel schemeCodelist) {
        SchemeModel schemeModel = new SchemeModel();
        schemeModel.setProductCode(schemeCodelist.getProductCode());
        schemeModel.setProdBatchCode(batchCode);
        schemeModel.setCmpCode(schemeCodelist.getCmpCode());
        schemeModel.setDistrCode(schemeCodelist.getDistrCode());
        schemeModel.setCustomerCode(schemeCodelist.getCustomerCode());
        schemeModel.setSchemeCode(schemeCodelist.getSchemeCode());
        schemeModel.setSchemeBase(schemeCodelist.getSchemeBase());
        schemeModel.setPayoutType(schemeCodelist.getPayoutType());
        schemeModel.setSchemeDescription(schemeCodelist.getSchemeDescription());
        schemeModel.setSlabNo(slabmodel.getSlabNo());
        schemeModel.setSlabFrom(slabmodel.getSlabFrom());
        schemeModel.setSlabTo(slabmodel.getSlabTo());
        schemeModel.setSlabRange(slabmodel.getSlabRange());
        schemeModel.setForEvery(slabmodel.getForEvery());
        schemeModel.setUom(slabmodel.getUom());
        schemeModel.setPayoutValue(slabmodel.getPayoutValue());

        return schemeModel;
    }

    public static void mergeFreeProductsForUiDisplay(List<OrderBookingVO> fullList) {
        List<OrderBookingVO> freeList = new ArrayList<>();
        for (OrderBookingVO orderBookingVO : fullList) {
            if (orderBookingVO.getTotQty() == 0) {
                freeList.add(orderBookingVO);
            }
        }

        for (OrderBookingVO orderBookingVO : freeList) {
            fullList.remove(orderBookingVO);
            if (fullList.contains(orderBookingVO)) {
                OrderBookingVO vo = fullList.get(fullList.indexOf(orderBookingVO));
                vo.setFreeQty(vo.getFreeQty() + orderBookingVO.getFreeQty());
            } else {
                fullList.add(orderBookingVO);
            }
        }
    }

    public boolean isCessTaxApplicable(String distrGstStateCode){
        String distrGstStateName = db.getStateNameByCode(distrGstStateCode);
        return distrGstStateName.equalsIgnoreCase("kerala");
    }

    public boolean isRegisteredRetailer(String customerCode){
        if(!customerCode.isEmpty()) {
            String customerType = db.isCustomerRegistered(customerCode);
            return customerType.equalsIgnoreCase("R");
        }else{
            return false;
        }
    }

}
