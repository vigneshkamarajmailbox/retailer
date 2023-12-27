package com.botree.retailerssfa.scheme;

import android.util.Log;

import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_PRIMARY_DISC;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_GM;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_KG;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_LT;
import static com.botree.retailerssfa.controller.constants.AppConstant.WeightUom.UOM_ML;

abstract class SchemeCalc {
    static final String SCHEME_QUANTITY_BASE = "QB";
    private static final String TAG = SchemeCalc.class.getSimpleName();
    private static final String SCHEME_WEIGHT_BASE = "WB";

    /**
     * used to calcualte combi product payout value
     *
     * @param totalcombiOrderQty total Order Quantity for the combi products
     * @param totalOrderValue    total order value for the combi products
     * @param combiaApplyProd    combi applied product
     * @param slabModel          slab details of the scheme
     * @param schemeBase         scheme base for the product
     * @return applied scheme value
     */
    BigDecimal calCombiPayoutValForEachProd(double totalcombiOrderQty, double totalOrderValue, OrderBookingVO combiaApplyProd, SchemeModel slabModel, String schemeBase) {
        BigDecimal prodSchemeValue = BigDecimal.valueOf(0.0);

        try {
            Double qtyOrValueForEvery;
            if (schemeBase.equalsIgnoreCase(SCHEME_QUANTITY_BASE) ||schemeBase.equalsIgnoreCase(SCHEME_WEIGHT_BASE) ) {
                qtyOrValueForEvery = totalcombiOrderQty;
            } else {
                qtyOrValueForEvery = totalOrderValue;
            }

            if (slabModel.getForEvery() > 0) {
                prodSchemeValue = ((combiaApplyProd.getPrimDiscOrderValue().divide(BigDecimal.valueOf(totalOrderValue),6,BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(qtyOrValueForEvery / slabModel.getForEvery()))).multiply(BigDecimal.valueOf(slabModel.getPayoutValue()));
            } else {
                prodSchemeValue = (combiaApplyProd.getPrimDiscOrderValue().divide(BigDecimal.valueOf(totalOrderValue),6,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(slabModel.getPayoutValue())));
            }
            return prodSchemeValue;
        } catch (Exception e) {
            Log.e(TAG, "calCombiPayoutValForEachProd: " + e.getMessage(), e);
        }
        return prodSchemeValue;
    }


    /**
     * used to filter unique combi scheme list
     *
     * @param appliedSlablist scheme applied list
     * @return combi scheme list
     */
    List<SchemeModel> filterUniqueCombiScheme(List<SchemeModel> appliedSlablist) {
        List<String> stringList = new ArrayList<>();
        List<SchemeModel> combiScheme = new ArrayList<>();
        for (SchemeModel schemeModel : appliedSlablist) {
            if (schemeModel.getCombi().equalsIgnoreCase("Y") && !stringList.contains(schemeModel.getSchemeCode())) {
                stringList.add(schemeModel.getSchemeCode());
                combiScheme.add(schemeModel);
            }
        }
        return combiScheme;
    }


    /**
     * Used to fetch already applied scheme from list using scheme Code
     *
     * @param appliedSlablist All applied scheme list
     * @param appliedSlab     new scheme model
     * @return already applied scheme Model
     */
    SchemeModel getAppliedSchemeObjectFromList(List<SchemeModel> appliedSlablist, SchemeModel appliedSlab) {
        SchemeModel appliedSchemeModel = null;
        for (SchemeModel schemeModel : appliedSlablist) {
            if (schemeModel.getSchemeCode().equalsIgnoreCase(appliedSlab.getSchemeCode())) {
                appliedSchemeModel = schemeModel;
            }
        }
        return appliedSchemeModel;
    }


    /**
     * Used to find whether the UOM is Available for the Product
     *
     * @param uomIdSpinner List of Uom for the single Product
     * @param slabuom      slab uom for the scheme
     * @return true or false based on avalability
     */
    boolean isProductUomAvailable(List<String> uomIdSpinner, String slabuom) {
        for (String uom : uomIdSpinner) {
            if (uom.equalsIgnoreCase(slabuom)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Used to filter all unique free Prod Code from the Applied list
     *
     * @param appliedSlablist All free prod applied slab list
     * @return return unique Free prod code List
     */
    List<String> filterUniqueProdString(List<SchemeModel> appliedSlablist) {
        List<String> stringList = new ArrayList<>();
        for (SchemeModel schemeModel : appliedSlablist) {
            if (!stringList.contains(schemeModel.getFreeProdCode())) {
                stringList.add(schemeModel.getFreeProdCode());
            }
        }
        return stringList;
    }

    /**
     * used to caluculate primary discount value with primary discount check
     *
     * @param db             sfadb instance
     * @param orderValue     order value for the product
     * @param payoutDiscount scheme discount payout
     * @return with or without primary check scheme discount value
     */
    double getDisPercentValuePrimaryCheck(SFADatabase db, double orderValue, double payoutDiscount) {
        double percentValue;
        if (db.getConfigDataBasedOnName(CONFIG_PRIMARY_DISC).equalsIgnoreCase("Y"))
            percentValue = orderValue - (orderValue / (1 + (payoutDiscount / 100.00)));
        else
            percentValue = orderValue * (payoutDiscount / 100.00);
        return percentValue;
    }

    double getPayout(double ordreValue, SchemeModel suggestedschemeModel) {
        double payout;
        if (suggestedschemeModel.getForEvery() > 0) {
            payout = Math.floor(ordreValue / suggestedschemeModel.getForEvery()) * suggestedschemeModel.getPayoutValue();
        } else {
            payout = suggestedschemeModel.getPayoutValue();
        }
        return payout;
    }

    double getPayoutForWeight(double conversionWeight, SchemeModel suggestedschemeModel) {
        double payout;
        if (suggestedschemeModel.getForEvery() > 0) {
            payout = (conversionWeight / suggestedschemeModel.getForEvery()) * suggestedschemeModel.getPayoutValue();
        } else {
            payout = suggestedschemeModel.getPayoutValue();
        }
        return payout;
    }

    Integer getFreeQty(double ordreValue, SchemeModel suggFreeProd) {
        Integer freeqty;
        if (suggFreeProd.getForEvery() > 0) {
            freeqty = (int) Math.floor(ordreValue / suggFreeProd.getForEvery()) * suggFreeProd.getFreeQty();
        } else {
            freeqty = suggFreeProd.getFreeQty();
        }
        return freeqty;
    }


    /**
     * Used to convert current to Slab uom Qty
     *
     * @param orderBookingVO order single product details
     * @param uom            uom for the scheme slab
     * @param currentQty     current qty of order prod
     * @return converted qty in double type
     */
    double calcSchemeUomQty(OrderBookingVO orderBookingVO, String uom, double currentQty) {
        try {
            return (int) (currentQty / orderBookingVO.getConversionFactor().get(uom));
        } catch (Exception e) {
            Log.e(TAG, "calcSchemeUomQty: " + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Used to convert current to Slab uom weight
     *
     * @param orderBookingVO order single product details
     * @param weightUom      weight  uom for the scheme slab
     * @param conversionQty  current qty of order prod
     * @return converted qty in double type
     */
    double calcWeightConversion(OrderBookingVO orderBookingVO, String weightUom, double conversionQty) {
        double schemeSlabConversionWeight = 0d;
        if (orderBookingVO.getWeightType().equalsIgnoreCase(weightUom)) {
            if (weightUom.equalsIgnoreCase(UOM_GM) || weightUom.equalsIgnoreCase(UOM_ML)) {
                schemeSlabConversionWeight = (orderBookingVO.getNetWeight() * conversionQty) / 1000;
            } else {
                schemeSlabConversionWeight = orderBookingVO.getNetWeight() * conversionQty;
            }
        } else {
            if ((orderBookingVO.getWeightType().equalsIgnoreCase(UOM_KG) && weightUom.equalsIgnoreCase(UOM_GM))
                    || (orderBookingVO.getWeightType().equalsIgnoreCase(UOM_LT) && weightUom.equalsIgnoreCase(UOM_ML))) {
                schemeSlabConversionWeight = (orderBookingVO.getNetWeight() * 1000) * conversionQty;
            } else if ((orderBookingVO.getWeightType().equalsIgnoreCase(UOM_GM) && weightUom.equalsIgnoreCase(UOM_KG))
                    || (orderBookingVO.getWeightType().equalsIgnoreCase(UOM_ML) && weightUom.equalsIgnoreCase(UOM_LT))) {
                schemeSlabConversionWeight = ((orderBookingVO.getNetWeight() / 1000) * conversionQty);
            }
        }
        return schemeSlabConversionWeight;
    }


    /**
     * Used to calculate line order value for the prod
     *
     * @param orderBookingVO ordered single product details
     * @param uom            uom for the scheme slab
     * @param currentQty     current qty of ordered prod
     * @return order value
     */
    double calculateLineOrderValue(OrderBookingVO orderBookingVO, String uom, int currentQty) {
        return currentQty
                * orderBookingVO.getConversionFactor().get(uom)
                * orderBookingVO.getSellPrice()
                .doubleValue();
    }

    /**
     * used to calcualate sum of combi order value for all Payout Type except free Payout Type
     *
     * @param inputProducts Ordered Product list
     * @return sum of combi order prod
     */
    Double getSumOfOrderValueforCombi(List<OrderBookingVO> inputProducts) {
        Double sumOfcombiProdOrder = 0d;
        if (!inputProducts.isEmpty()) {
            for (OrderBookingVO inputOrderProduct : inputProducts) {
                sumOfcombiProdOrder = sumOfcombiProdOrder + inputOrderProduct.getPrimDiscOrderValue().doubleValue();
            }
        }
        return sumOfcombiProdOrder;
    }


    double getUomConvQty(Integer sumOfLineQty, double uomConvQty, SchemeModel slabModel, OrderBookingVO inputProduct, List<String> uomForProd) {
        if (isProductUomAvailable(uomForProd, slabModel.getUom())) {
            uomConvQty = calcSchemeUomQty(inputProduct, slabModel.getUom(), sumOfLineQty);
        }
        return uomConvQty;
    }

    abstract SchemeModel getAppliedSlabDetails(SchemeModel slabmodel, SchemeModel schemeCodelist);
}
