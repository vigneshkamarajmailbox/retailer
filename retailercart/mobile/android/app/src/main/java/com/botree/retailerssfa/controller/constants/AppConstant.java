package com.botree.retailerssfa.controller.constants;

public class AppConstant {
    public static final String CONTENT_TYPE = "application/json";
    public static final String USER_TYPE_DSR = "DSR";
    public static final String USER_TYPE_ISR = "ISR";
    public static final String USER_TYPE_DISTR = "DISTR";
    public static final String ORDER_SCREEN_TYPE_CUSTOM = "custom";
    public static final String ORDER_SCREEN_TYPE_QUICK = "quick";

    public static final Integer FILE_DOWNLOAD = 0;
    public static final Integer FILE_DOWNLOADING = 1;
    public static final Integer FILE_DOWNLOADED = 2;

    public static final String TAX_CGST = "CGST";
    public static final String TAX_SGST = "SGST";
    public static final String TAX_UTGST = "UTGST";
    public static final String TAX_IGST = "IGST";
    public static final String TAX_CESS = "CESS";

    public static final String REQ_MOBILE_NO = "mobileNo";


    public static final String TAR_VS_ACHI_MONTH_VALUE = "MONTH_VALUE";
    public static final String TAR_VS_ACHI_MONTH_VOLUME = "MONTH_VOLUME";
    public static final String TAR_VS_ACHI_YEAR_VALUE = "YEAR_VALUE";
    public static final String TAR_VS_ACHI_YEAR_VOLUME = "YEAR_VOLUME";


    public enum RequestType {
        LOGIN, SCREEN_CONFIGURATION, REPORT_DOWNLOAD, REPORT_MONTH_TRENDS, REPORT_PENDING_ORDER,
        SALES_HIERARCHY_DATA, SALES_HIERARCHY_VALUE, PRODUCT_MASTER, DOWNLOAD_MASTER_DATA, DOWNLOAD_FILE,
        REPORT_BILLED_OUTLETS, REPORT_UNBILLED_OUTLETS, REPORT_RETAILER_DASHBOARD, REPORT_MARKET_OUTSTANDING_BILLS, REPORT_AGING, REPORT_CHANNEL_PERFORMANCE,
        REPORT_CHANNEL_TREND_MONTH, REPORT_CHANNEL_TREND_YEAR, REPORT_BRAND_TREND_MONTH, REPORT_BRAND_TREND_YEAR, REPORT_DISTR_MONTH_TRENDS,
        REPORT_YEAR_MONTH_TRENDS, REPORT_STOCK_OUT_OF_STOCK, REPORT_PRODUCT_PERFORMANCE, REPORT_DISTR_SERVICE_LEVEL, REPORT_PRODUCT_INDEX,
        REPORT_TIME_SPEND, CHAT_RESULTS, SYNC_LOG, RETAILER_SCHEME_LIST, REPORT_SALESMAN_TRACKER, CUSTOMER_OTP_REF,GET_BANNER,
        CUSTOMER_OTP_VALIDATION_FROM_SERVER, CHANGE_PASSWORD, DOWNLOAD_REPORT_TEMPLATE, DOWNLOAD_ONLINE_REPORT, FGT_PWD_OTP, CODIFICATION, LOGOUT, EXTRACT_REPORT
    }

    public class Request {
        public static final String REQ_ID = "id";
        public static final String REQ_TYPE = "type";
        public static final String REQ_IS_LAST_LEVEL = "isLastLevel";
        public static final String REQ_HIER_LEVEL = "hierLevel";
        public static final String REQ_DISTR_CODE = "distrCode";
        public static final String REQ_CMPCODE_CODE = "cmpCode";
        public static final String REQ_USER_CODE = "userCode";
        public static final String REQ_PASS_STR = "password";
        public static final String REQ_FCM_KEY = "fireBaseKey";
        public static final String REQ_LOGIN_CODE = "loginCode";
        public static final String REQ_APP_VERSION = "appVersion";
        public static final String REQ_SYSTEM_DATE = "systemDate";
        public static final String REQ_DEVICE_BRAND = "deviceBrand";
        public static final String REQ_DEVICE_MODEL = "deviceModel";
        public static final String REQ_DEVICE_VERSION = "deviceVersion";
        public static final String REQ_ADV_ID = "advId";
        public static final String REQ_APPLICATION_TYEP = "applicationType";
        public static final String REQ_USER_NAME = "userName";
        public static final String REQ_MONTH = "month";
        public static final String REQ_YEAR = "year";
        public static final String REQ_TITLE = "title";
        public static final String REQ_EMAIL_ID = "emailId";
        private Request() {
        }
    }

    public class Configurations {
        public static final String CONFIG_SCHEME_PRODUCT = "SchemeProduct";
        public static final String CONFIG_MUSTSELL_PRODUCT = "MustSellProduct";
        public static final String CONFIG_FOCUS_PRODUCT = "FocusProduct";
        public static final String CONFIG_OUT_OF_STOCK_PRODUCT = "OutofStockProduct";
        public static final String CONFIG_NEW_PRODUCT = "NewProduct";
        public static final String CONFIG_STOCK_TAKE = "StockTake";
        public static final String CONFIG_SCHEME_UPSALE = "SchemeUpsale";
        public static final String CONFIG_OUTLET_IMAGE = "OutletImage";
        public static final String CONFIG_ROUTE_SELECTION = "RouteSelection";
        public static final String CONFIG_SIH = "SIH";
        public static final String CONFIG_SOQ = "SOQ";
        public static final String CONFIG_PRIMARY_DISC = "PimaryDiscount";
        public static final String CONFIG_WEIGHT_BASED_UOM = "WeightBasedUOM";
        public static final String CONFIG_NEW_OUTLET_OTP_VALIDATE = "NewOutletOTPValidation";
        public static final String CONFIG_DISTR_STATE_CODE = "DistrStateCode";
        public static final String CONFIG_IS_AUTO_UPDATE = "APKAutoUpdate";
        public static final String CONFIG_NEW_OUTLET_IMG_MANDATORY = "NewOutletImageMandatory";
        public static final String CONFIG_ENABLE_QTY_2 = "EnableQty2";
        public static final String CONFIG_SOV = "SOV";

        public static final String CONFIG_IS_PTR_WITH_TAX = "PTR";
        public static final String CONFIG_NET_RETAILER_MARGIN = "netmargin";
        public static final String CONFIG_VAN_SALE_ZERO_STOCK = "VanSalesZeroStockLoad";
        public static final String CONFIG_PRODUCT_PRIORITY = "ProductPriority";
        public static final String CONFIG_ENABLE_OTHER_FILTER = "EnableOtherFilter";
        public static final String CONFIG_PRIORITY = "ColorCodingPriority";

        private Configurations() {
        }

    }

    public class ScreenConfigurations {
        public static final String SCREEN_CONFIG_ORDER_BOOKING = "Order Booking";

        private ScreenConfigurations() {
        }

    }

    public class ModuleConfigurations {
        public static final String MODULE_CONFIG_OUTLET_VISIT = "Outlet Visit";

        private ModuleConfigurations() {
        }

    }


    public class ScreenTitles {
        public static final String SCREEN_TITLE_TARGET_VS_ACHIEVEMENT = "Target Vs Achievement";
        public static final String SCREEN_TITLE_BILLING = "Billing";
        public static final String SCREEN_TITLE_ORDER_BOOKING = "New Order";

        private ScreenTitles() {
        }

    }

    public class IntentExtras {
        public static final String NAME_IS_VANSALES_ORDER = "isVanSalesOrder";

        private IntentExtras() {
        }
    }

    public class StockTypes {
        public static final int TYPE_PRODUCT = 0;
        public static final int TYPE_STOCK_SCHEME_PRODUCT = 1;
        public static final int TYPE_STOCK_MUST_SELL_PRODUCTS = 2;
        public static final int TYPE_STOCK_FOCUS_BRAND = 3;
        public static final int TYPE_STOCK_PRODUCTS = 4;
        public static final int TYPE_OUT_OF_STOCK_PRODUCTS = 5;

        private StockTypes() {
        }
    }

    public class WeightUom {
        public static final String UOM_GM = "GM";
        public static final String UOM_KG = "KG";
        public static final String UOM_ML = "ML";
        public static final String UOM_LT = "LT";

        private WeightUom() {
        }
    }
}
