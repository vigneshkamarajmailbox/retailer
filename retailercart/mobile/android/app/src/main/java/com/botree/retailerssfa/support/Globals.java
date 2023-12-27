/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.support;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.async.AsyncLoadUsbSyncData;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.EditQtyModel;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.models.NewOutletImageList;
import com.botree.retailerssfa.models.OTPModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.util.SFASharedPref.PREF_APPNAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_URL;


public class Globals {

    public static final String TAG = Globals.class.getSimpleName();
    public static final int ORDER_BOOKING_QUANTITY_DIALOG = 81;
    public static final int ORDER_BOOKING_STOCK_DIALOG = 82;
    public static final int ORDER_BOOKING_FREE_DIALOG = 83;
    public static final int MTD_DASHBOARD_BARCHART = 108;
    public static final int ORDER_BOOKING = 92;
    public static final int VAN_SALES_BOOKING = 93;
    static final int MTD_TRENDS_LINECHART = 26;
    public static final int VOICE_SEARCH = 600;
    public static final int IMAGE_SCREEN_QTY = 700;
    public static final Integer SH_LEVEL = 201;
    public static final Integer SH_VALUE = 202;
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String MESSAGE_UNPROGRESS = "message_unprogress";
    public static final String NAME_INVOICE_SUMMARY = "Order Taking Summary";
    //Screen Names
    public static final String NAME_ADD_REMOVE = "Add/Remove";

    public static final String NAME_PRODUCT = "Product";
    public static final String NAME_FEEDBACK = "Feedback";
    public static final String NAME_PRODUCT_MASTER = "Product Master";

    public static final String NAME_SCHEME_PRODUCTS_REPORT = "Scheme Product Report";
    public static final String NAME_SCHEME_DETAILS_REPORT = "Scheme Report";
    public static final String NAME_FOCUS_BRAND_MASTER_REPORT = "FocusBrand Products";
    public static final String NAME_MUST_SELL_MASTER_REPORT = "MustSell Products";

    public static final String SCREEN_NAME = "ScreenName";

    // Approvals
    public static final String NAME_RETAILER_APPROVAL = "Retailer Approval";
    public static final String NAME_PO_APPROVAL = "PO Approval";
    public static final String NAME_APPROVALS_DASHBOARD = "Approvals";
    public static final String NAME_OPENING_STOCK_DASHBOARD = "Opening Stock";
    public static final String NAME_PHYSICAL_STOCK_SUMMARY = "Stock Summary";
    public static final String NAME_OTHER_REPORTS = "Report";
    public static final String NAME_STOCK_LEDGER_REPORT = "Stock Ledger Report";
    public static final String NAME_SCHEME_MASTER_REPORT = "Scheme Products";

    //Purchse
    public static final String NAME_PURCHASE_REPORT = "Purchase Report";

    //Add Outlets
    public static  String NAME_ADD_OUTLET = "Add Retailer";

    // purchase order  Screen
    public static final String NAME_DISTRIBUTOR_PURCHASE_ORDER = "Purchase Order";
    public static final String NAME_PREVIOUS_PURCHASE_ORDER = "PO Status";
    public static final String NAME_DISTRIBUTOR_PURCHASE_RECEIPT = "Purchase Receipt";
    public static final String NAME_DISTRIBUTOR_PURCHASE_RETURN = "Purchase Return";
    public static final String NAME_SUMMARY = "Cart";
    public static final String NAME_SALES_REPORT_DETAIL = "Sales Report";
    //Retailer Dashboard - Outlet Visit
    public static final String NAME_ORDER_BOOKING = "New Order";
    public static final String ORDER_BOOKING_NAME = "Order Booking";
    public static final String NAME_PREVIOUS_ORDERS_CONFIRMATION = "Order Confirmation";
    public static final String NAME_ORDER_DETAILS = "Order Details";
    public static final String NAME_PURCHASE_ORDER_BOOKING = "Purchase Order Booking";
    public static final String NAME_SALES_RETURN = "Sales Return";
    public static final String NAME_OUTLET_COLLECTION = "Outlet Collection";
    public static final String NAME_UPDATE_LOCATION = "Update Location";
    public static final String NAME_STOCK_TAKE = "Stock Take";
    public static final String NAME_VAN_SALES = "Van Sales";
    public static final String NAME_ACTIVITY_TRACKER = "Activity Tracker";
    public static final String NAME_SCHEME = "Scheme";
    public static final String NAME_DISTRIBUTOR_INFO = "Distributor Info";
    public static final String NAME_DAY_SUMMARY = "Day Summary";

    //UserNames
    public static final int SCHEME_DISCOUNT_PROD = 505;
    public static final int SCHEME_FREE_PROD = 506;
    public static final String NEW_IMG_LOCATION = "Android/data/" + BuildConfig.APPLICATION_ID + "/Image";
    public static final String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
    public static final String PUSH_NOTIFICATION_COUNT = "PUSH_NOTIFICATION_COUNT";
    private static final String[] ARRAY_RETURN_TYPE = new String[]{"Return Type", "Saleable Return", "Unsaleable Return", "Offer"};
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static Globals ourInstance;
    private static WeakReference<Context> contextRef;
    private double longitude = 0.0;
    private double latitude = 0.0;
    private String strimage = "";
    private String tempStrimage = "";
    private Date retailerEndTime;
    private double strRetrDistance = 0.0;
    private double retrlatitude = 0.0;
    private double rettlongitude = 0.0;
    private String retailerStartTime;
    private int categorPos = -1;
    private String strLatitude;
    private String strLongitude;
    private int brandPos = -1;
    private int prodLevel1Pos = -1;
    private int prodLevel2Pso = -1;
    private int otherFilterPso = 0;
    private Boolean isSubTitleShow = false;
    private long startTime;
    private long endTime;
    private OTPModel otpModelData = new OTPModel();

    private File pdfFile;

    public static String checkNull(String value) {
        if (value == null) {
            return "";
        } else if (value.equals("null")) {
            return "";
        }
        return value;
    }

    public static String checkNull(String value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value.equals("null")) {
            return defaultValue;
        }else if (value.equals("")) {
            return defaultValue;
        }
        return value;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    private List<NewOutletImageList> newOutletImageLists = new ArrayList<>();

    public static final String NAME_MASTER = "Master";
    public static final String NAME_MASTER_DISTR = "Distributor";
    public static final String NAME_MASTER_RETAILER = "Retailer";
    public static final String NAME_MASTER_SALESMAN = "Salesman";
    public static final String NAME_MASTER_ROUTE = "Route";
    public static final String NAME_MASTER_WD_INFRASTRUCTURE = "WD Infrastructure";
    public static final String NAME_NEW_SALESMANLIST_MASTER = "New Salesman List";
    public static final String NAME_MASTER_BEAT = "Beat Info";
    public static final String NAME_MASTER_BANK = "Bank Info";
    public static final String NAME_MASTER_SHIPPING = "Address Info";
    public static final String NAME_ROUTE_COVERAGE_PLAN = "Route Coverage Plan";
    public static final String NAME_ROUTE_COVERAGE = "Route Coverage";

    public static final String NAME_CUSTOMER_ROUTE_SEQUENCING = "Customer Route Sequencing";

    public static final String SCREEN_NAME_ROUTE = "Route";
    public static final String SCREEN_NAME_CUSTOMER = "Customer";
    public static final String SCREEN_NAME_CUTOMER_SHIP_ADDRESS = "Customer Ship Address";
    public static final String SCREEN_NAME_CUSTOMER_BANK = "Customer Bank";
    public static final String SCREEN_NAME_SALESMAN = "Salesman";
    public static final String SCREEN_NAME_PURCHASE_ORDER = "Purchase Order";
    public static final String SCREEN_NAME_BILLING = "Billing";
    public static final String SCREEN_NAME_COLLECTION = "Collection";

    public static final String NAME_TRANSACTION = "Order";
    public static final String NAME_TAXMASTER = "Tax Master";
    public static final String NAME_TRANSACTION_COLLECTION = "Collection";
    public static final String NAME_OUTLET_VISIT = "Retailer Visit";
    public static final String NAME_LOGISTIC_MATERIAL = "Logistic Material";
    public static final String NAME_PENDING_COLLECTION = "Pending Collection";
    public static final String NAME_TRANSACTION_BILLING = "Billing";
    public static final String NAME_BILLING_SUMMARY_CONFIRMATION = "Billing Confirmation";
    public static final String SCREEN_NAME_PURCHASE_RECEIPT = "Purchase Receipt";
    public static final String SCREEN_NAME_SALES_PANEL = "Sales Panel";
    public static final String SCREEN_NAME_SALES_RETURN = "Sales Return";
    public static final String NAME_ORDER_BOOKING_SUMMARY = "Order Booking";
    public static final String NAME_MANUAL_CLAIMS = "Manual Claim";
    public static final String NAME_STOCK_ADJUSTMENT = "Stock Adjustment";
    public static final String NAME_CODE_GENERATOR = "Counter Setting";
    public static final String NAME_STOCK = "Current Stock";
    public static final String NAME_REPORTS_MENU = "Reports";
    public static final String NAME_OPENING_STOCK = "Opening Stock";
    public static final String NAME_OPENING_STOCK_SUMMARY = "Opening Stock Summary";
    public static final String NAME_DAY_WEEK_WISE_REPORT_DETAIL = "Day Week Wise Sales";
    public static final String NAME_SALESMAN_ROUTE_WISE_REPORT = "MTD Salesman-Route";
    public static final String NAME_PRODUCT_WISE_REPORT = "MTD Product Sales";
    public static final String NAME_CHANNEL_WISE_REPORT_DETAIL = "Channel Wise Sales";
    public static final String NAME_YTD_REPORT_DETAIL = "YTD Report";
    public static final String NAME_CUSTOMER_WISE_REPORT_DETAIL = "Customer Wise Sales";
    public static final String NAME_PURCHASE_INVOICE_REPORT_DETAIL = "Purchase Invoice";
    public static final String NAME_ONLINE_REPORT = "Online Reports";
    public static final String NAME_CUSTOMER_ROUTE_ASSIGNING = "Customer Route Assigning";
    public static final String NAME_BATCH_TRANSFER = "Batch Transfer";
    public static final String NAME_BATCH_TRANSFER_SUMMARY = "Batch Transfer Summary";
    public static final String SCREEN_NAME_BATCH_TRANSFER = "Batch Transfer";
    public static final String NAME_GST_REPORT = "GST Report";

    HashMap<String, String> salesmanKycImageMap = new HashMap<>();

    public HashMap<String, String> getSalesmanKycImageMap() {
        return salesmanKycImageMap;
    }

    public void setSalesmanKycImageMap(HashMap<String, String> salesmanKycImageMap) {
        this.salesmanKycImageMap = salesmanKycImageMap;
    }

    public Globals() {
        //ignore default constructor
    }

    public static void initGlobals(Context context) {
        if (ourInstance == null) {
            ourInstance = new Globals();
        }
        contextRef = new WeakReference<>(context);
    }

    public static Globals getOurInstance() {
        return ourInstance;
    }

    private List<EditQtyModel> previousOrderQty = new ArrayList<>();

    public List<EditQtyModel> getPreviousOrderQty() {
        return previousOrderQty;
    }

    public void setPreviousOrderQty(List<EditQtyModel> previousOrderQty) {
        this.previousOrderQty = previousOrderQty;
    }

    /**
     * This method is used to check the connectivity.
     *
     * @param context context
     * @return boolean
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * This method is used to set the url in shared preference object
     */
    public static void setUrl(Context context) {
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        String baseUrl = context.getResources().getString(R.string.BASE_URL_RELEASE);
        if (BuildConfig.DEBUG) {
            baseUrl = context.getResources().getString(R.string.BASE_URL_DEBUG);
        }
        String url = baseUrl + sfaSharedPref.readString(PREF_APPNAME);
        sfaSharedPref.writeString(PREF_URL, url);
    }

    public static boolean isSpeechAvail() {
        Context mContext = contextRef.get();
        PackageManager pm = mContext.getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return !activities.isEmpty();
    }


    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    /**
     * @param serviceClass used to check the running service class
     * @return true if {@link Class<> service class} is running else false
     */
    public static boolean isMyServiceRunning(Class<?> serviceClass) {
        Context mContext = contextRef.get();
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            String className = serviceClass.getName();
            if (className.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFileExists() {

        File filePath = new File(AsyncLoadUsbSyncData.PATH + AsyncLoadUsbSyncData.FILENAME);
        return filePath.exists();
    }

    public List<NewOutletImageList> getNewOutletImageLists() {
        return newOutletImageLists;
    }

    public void setNewOutletImageLists(List<NewOutletImageList> newOutletImageLists) {
        this.newOutletImageLists = newOutletImageLists;
    }

    public OTPModel getOtpModelData() {
        return otpModelData;
    }

    public void setOtpModelData(OTPModel otpModelData) {
        this.otpModelData = otpModelData;
    }

    public int getOtherFilterPso() {
        return otherFilterPso;
    }

    public void setOtherFilterPso(int otherFilterPso) {
        this.otherFilterPso = otherFilterPso;
    }

    public int getProdLevel1Pos() {
        return prodLevel1Pos;
    }

    public void setProdLevel1Pos(int prodLevel1Pos) {
        this.prodLevel1Pos = prodLevel1Pos;
    }

    public int getProdLevel2Pso() {
        return prodLevel2Pso;
    }

    public void setProdLevel2Pso(int prodLevel2Pso) {
        this.prodLevel2Pso = prodLevel2Pso;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String[] getArrayReturnType() {
        return ARRAY_RETURN_TYPE;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStrimage() {
        return strimage;
    }

    public void setStrimage(String strimage) {
        this.strimage = strimage;
    }

    public void setTempStrimage(String tempStrimage) {
        this.tempStrimage = tempStrimage;
    }

    public Date getRetailerEndTime() {
        return retailerEndTime;
    }

    public void setRetailerEndTime(Date retailerEndTime) {
        this.retailerEndTime = retailerEndTime;
    }

    public double getStrRetrDistance() {
        return strRetrDistance;
    }

    public void setStrRetrDistance(double strRetrDistance) {
        this.strRetrDistance = strRetrDistance;
    }

    public double getRetrlatitude() {
        return retrlatitude;
    }

    public void setRetrlatitude(double retrlatitude) {
        this.retrlatitude = retrlatitude;
    }

    public double getRettlongitude() {
        return rettlongitude;
    }

    public void setRettlongitude(double rettlongitude) {
        this.rettlongitude = rettlongitude;
    }

    public String getRetailerStartTime() {
        return retailerStartTime;
    }

    public void setRetailerStartTime(String retailerStartTime) {
        this.retailerStartTime = retailerStartTime;
    }

    public int getCategorPos() {
        return categorPos;
    }

    public void setCategorPos(int categorPos) {
        this.categorPos = categorPos;
    }

    public String getStrLatitude() {
        return strLatitude;
    }

    public void setStrLatitude(String strLatitude) {
        this.strLatitude = strLatitude;
    }

    public String getStrLongitude() {
        return strLongitude;
    }

    public void setStrLongitude(String strLongitude) {
        this.strLongitude = strLongitude;
    }

    public int getBrandPos() {
        return brandPos;
    }

    public void setBrandPos(int brandPos) {
        this.brandPos = brandPos;
    }

    public Boolean getIsSubTitleShow() {
        return isSubTitleShow;
    }

    public void setIsSubTitleShow(Boolean isSubTitleShow) {
        this.isSubTitleShow = isSubTitleShow;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * check whether camera hardware is available
     *
     * @param context params
     * @return true is available otherwise false
     */
    public boolean isCameraAvailable(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    public File getOutputMediaFile(int type) {

        Log.d(TAG, "openCameraOnly : 2");
        File sdcard = FileAccessUtil.getInstance().getExternalStorageDirectory();
        File mediaStorageDir = new File(sdcard, NEW_IMG_LOCATION);
        // Create the storage directory if it does not exist
        Log.d(TAG, "openCameraOnly : 3");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("", "Oops! Failed create "
                    + "" + " directory");
            Log.d(TAG, "openCameraOnly : 4");
            return null;
        }
        Log.d(TAG, "openCameraOnly : 5");
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        Log.d(TAG, "openCameraOnly : 6");
        String imageFileName = "IMG_" + timeStamp + ".png";

        if (type == MEDIA_TYPE_IMAGE) {
            Log.d(TAG, "openCameraOnly : 7");
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + imageFileName);
            Log.d(TAG, "openCameraOnly : 8");
        } else {
            Log.d(TAG, "openCameraOnly : 9");
            return null;
        }

        Log.e(TAG, "create file: " + mediaFile.toString());
        Log.d(TAG, "openCameraOnly : 10");
        return mediaFile;
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public void getProfileImagePath(Context context, Uri selectedImage, File photofile, boolean state) {
        Log.d(TAG, "getProfileImagePath : 1 : " + selectedImage);
        int rotate = 0;
        Bitmap bmp = null;
        String picturePath = null;
        Log.d(TAG, "getProfileImagePath : 2 : " + photofile);
        if (!state) {
            Log.d(TAG, "getProfileImagePath : 3");
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            Log.d(TAG, "getProfileImagePath : 4");
            if (cursor != null && cursor.getCount() > 0) {
                Log.d(TAG, "getProfileImagePath : 5");
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
            }
            Log.d(TAG, "getProfileImagePath : 6");
            closeCursor(cursor);
            Log.d(TAG, "getProfileImagePath : 7");
        }

        try {
            Log.d(TAG, "getProfileImagePath : 8");
            ExifInterface exif = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            Log.d(TAG, "getProfileImagePath : 9");

            options.inSampleSize = 8;
//            Log.e(TAG, "intent path: " + selectedImage.getPath());
            Log.d(TAG, "getProfileImagePath : 10");
            if (state) {
                Log.d(TAG, "getProfileImagePath : 11");
                if (Build.VERSION.SDK_INT >= 24) {
                    if (Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27) {
                        options.inSampleSize = 16;
                    } else {
                        options.inSampleSize = 8;
                    }
                    Log.d(TAG, "getProfileImagePath : 12");
//                    bmp = BitmapFactory.decodeFile(photofile.getAbsolutePath(), options);
//                    exif = new ExifInterface(photofile.getAbsolutePath());
                    bmp = BitmapFactory.decodeFile(photofile.getPath(), options);
                    exif = new ExifInterface(photofile.getPath());
                } else {
                    Log.d(TAG, "getProfileImagePath : 13");
                    bmp = BitmapFactory.decodeFile(selectedImage.getPath(), options);
                    exif = new ExifInterface(selectedImage.getPath());
                }
                Log.d(TAG, "getProfileImagePath : 14");
            } else {
                options.inSampleSize = 8;
                Log.d(TAG, "getProfileImagePath : 15");
                if (picturePath != null) {
                    bmp = BitmapFactory.decodeFile(picturePath, options);
                    exif = new ExifInterface(picturePath);
                }
                Log.d(TAG, "getProfileImagePath : 16");
            }
            rotate = getRotate(rotate, exif);
            Log.d(TAG, "getProfileImagePath : 17");
            Matrix matrix = getMatrix(rotate);
            Log.d(TAG, "getProfileImagePath : 18");

            if (bmp != null) {
                Log.d(TAG, "getProfileImagePath : 19");
                Bitmap rotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                        bmp.getHeight(), matrix, false);
                Log.d(TAG, "getProfileImagePath : 20");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotated.compress(Bitmap.CompressFormat.PNG, 100, baos);
                Log.d(TAG, "getProfileImagePath : 21");
                byte[] b = baos.toByteArray();
                String base64img = Base64.encodeToString(b, Base64.NO_WRAP);
                Log.d(TAG, "getProfileImagePath : 22");
                Globals.getOurInstance().setStrimage(base64img);
                bmp.recycle();
                Log.d(TAG, "getProfileImagePath : 23");
            }

            //deleteSavedImages(selectedImage, photofile, state);

        } catch (NullPointerException | IOException e) {
            Log.e(TAG, "getProfileImagePath: " + e.getMessage(), e);
            Log.d(TAG, "getProfileImagePath : 24");
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = (float) width * (float) height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2f;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public Bitmap getRotatedImage(Bitmap bmp, ByteArrayInputStream inputStream) {
        int rotate = 0;
        Bitmap rotated = null;
        try {
            ExifInterface exif = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            exif = new ExifInterface(inputStream);
            rotate = getRotate(rotate, exif);
            Matrix matrix = getMatrix(rotate);

            if (bmp != null) {
                rotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                        bmp.getHeight(), matrix, false);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] b = baos.toByteArray();
//                String base64img = Base64.encodeToString(b, Base64.DEFAULT);
//                Globals.getOurInstance().setStrimage(base64img);
                bmp.recycle();
            }

        } catch (NullPointerException | IOException e) {
            Log.e(TAG, "getProfileImagePath: " + e.getMessage(), e);
        }
        return rotated;
    }

    /**
     * used to delete the last saved images for folder
     *
     * @param selectedImage image uri for below API 24
     * @param photofile     Photot File for above API 24
     * @param state         if true delete the image
     */
    private void deleteSavedImages(Uri selectedImage, File photofile, boolean state) {
        if (state) {
            if (Build.VERSION.SDK_INT >= 24) {
                if (photofile.exists())
                    deleteLastPicture(photofile.getPath());
            } else {
                deleteLastPicture(selectedImage.getPath());
            }
//            deleteLastFromDCIM();
        }
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    @NonNull
    private Matrix getMatrix(int rotate) {
        Log.d(TAG, "getMatrix : 1");
        Matrix matrix = new Matrix();
        if (rotate != 0) {
            Log.d(TAG, "getMatrix : 2");
            matrix.postRotate(rotate);
            Log.d(TAG, "getMatrix : 3");
        } else {
            Log.d(TAG, "getMatrix : 4");
            matrix.postRotate(0);
            Log.d(TAG, "getMatrix : 5");
        }
        Log.d(TAG, "getMatrix : 6");
        return matrix;
    }

    private int getRotate(int rotate, ExifInterface exif) {
        Log.d(TAG, "getRotate : 1");
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotate = 270;
                Log.d(TAG, "getRotate : 2");

            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotate = 180;
                Log.d(TAG, "getRotate : 3");

            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotate = 90;
                Log.d(TAG, "getRotate : 4");

            } else if (orientation == ExifInterface.ORIENTATION_NORMAL) {
                rotate = 0;
                Log.d(TAG, "getRotate : 5");

            }
        }
        Log.d(TAG, "getRotate : 6");
        return rotate;
    }

    private void deleteLastFromDCIM() {
        try {
            File[] images = new File(FileAccessUtil.getInstance().getExternalStorageDirectory()
                    + File.separator + "DCIM/Camera").listFiles();
            File latestSavedImage = images[0];
            Log.i(TAG, "last image path" + latestSavedImage.toString());

            for (int i = 1; i < images.length; ++i) {
                if (images[i].lastModified() > latestSavedImage.lastModified()) {
                    latestSavedImage = images[i];
                }
            }
            boolean isDeleted = latestSavedImage.getAbsoluteFile().delete();
            if (isDeleted) {
                Log.i(TAG, "deleteLastFromDCIM: File Deleted");
            }

        } catch (Exception e) {
            Log.e(TAG, "deleteLastFromDCIM: " + e.getMessage(), e);
        }
    }

    private void deleteLastPicture(String path) {

        try {
            File fdelete = new File(path);
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    //file deleted
                    Log.i(TAG, "deleteLastPicture: file Deleted :" + path);
                } else {
                    Log.i(TAG, "deleteLastPicture: file not Deleted :" + path);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "deleteLastPicture: " + e.getMessage(), e);
        }
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int radius = Math.min(h / 2, w / 2);
        Bitmap output = Bitmap.createBitmap(w + 8, h + 8, Bitmap.Config.ARGB_8888);
        Paint p = new Paint();
        p.setAntiAlias(true);
        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);
        c.drawCircle((w / 2f) + 4, (h / 2f) + 4, radius, p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        c.drawBitmap(bitmap, 4, 4, p);
        p.setXfermode(null);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        c.drawCircle((w / 2f) + 4, (h / 2f) + 4, radius, p);
        return output;
    }

    public void isDateChanged(Context context) {
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        SFADatabase db = SFADatabase.getInstance(context);
        String distrCode = preferences.readString(PREF_DISTRCODE);
        String today = DateUtil.getCurrentDate();
        boolean isDayStartPerformed = db.checkDayStartPerformed(distrCode, today);
        if (!isDayStartPerformed) {
            NotifyUtil.showAutoSyncDialog(context, "Sync", "Please sync to get the current date masters and reports data");
        }
    }

    public void setAutoScreenCount(String screenName) {
        try {
            Context mContext = contextRef.get();
            SFASharedPref preferences = SFASharedPref.getOurInstance();
            SFADatabase db = SFADatabase.getInstance(mContext);
            assert preferences != null;
            int screenCount = db.getAutoQuickMenuCount(screenName);
            db.insertOrUpdateAutoQuickActions(screenName, screenCount);
        } catch (Exception e) {
            Log.e(TAG, "setAutoScreenCount: " + e.getMessage(), e);
        }
    }


    /**
     * @param activity is used to disable the screenshot functionality for the respective activity
     */
    public void disableScreenShot(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }


    public static void clearFilter() {
        Globals.getOurInstance().setProdLevel1Pos(-1);
        Globals.getOurInstance().setProdLevel2Pso(-1);
        Globals.getOurInstance().setCategorPos(-1);
        Globals.getOurInstance().setBrandPos(-1);
        Globals.getOurInstance().setOtherFilterPso(0);
        NotifyUtil.getOurInstance().setCurrentPos(0);
        NotifyUtil.getOurInstance().setStrBrandCode("");
        NotifyUtil.getOurInstance().setStrBrandName("");
    }

    private static boolean isStrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isCategoryValidationSuccess(ProductMasterModel bookingVO, String categoryValue) {
        if (isStrEmpty(categoryValue)) {

            if (categoryValue.toLowerCase().contains("all")) {
                return true;
            }

            if (bookingVO.getLobDivisionCode() != null && bookingVO.getLobDivisionCode().equalsIgnoreCase(categoryValue)) {
                return true;
            }

            Log.d(TAG, "isCategoryValidationSuccess: " + bookingVO.getLobDivisionCode() + " | " + categoryValue);
            return false;
        }

        return true;
    }

    public static boolean isBrandValidationSuccess(ProductMasterModel bookingVO, String brandCode, String brandValue) {
        return (isStrEmpty(brandValue) && isStrEmpty(brandCode) /*&& !brandValue.equals(ALL_BRANDS)*/
                && !"".equals(bookingVO.getProductHierPathCode())
                && bookingVO.getProductHierPathCode().contains(brandCode)) ||
                (isStrEmpty(brandCode) && brandCode.toLowerCase().contains("all")) || checkStringEmpty(brandCode);
    }

    private static boolean checkStringEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public static boolean isBrandValidationSuccess(OrderBookingVO bookingVO, String brandCode, String brandValue) {
        return (isStrEmpty(brandValue) && isStrEmpty(brandCode) /*&& !brandValue.equals(ALL_BRANDS)*/
                && !"".equals(bookingVO.getProductHierPath())
                && bookingVO.getProductHierPath().contains(brandCode)) ||
                (isStrEmpty(brandCode) && brandCode.toLowerCase().contains("all")) || checkStringEmpty(brandCode);
    }

    public static boolean checkFilterText(String filterText, String productName, String productCode) {
        return filterText.length() == 0 ||
                (productName.toLowerCase(Locale.getDefault()).contains(filterText.toLowerCase())) ||
                (productCode.toLowerCase(Locale.getDefault()).contains(filterText.toLowerCase()));
    }

    public static String getCodeBasedOnLevel(int level, MTDReportModel model){
        String brandCode = "";
        if (level == 0) {
            brandCode = model.getLevelCode1();
        } else if (level == 1) {
            brandCode = model.getLevelCode2();
        } else if (level == 2) {
            brandCode = model.getLevelCode3();
        } else if (level == 3) {
            brandCode = model.getLevelCode4();
        } else if (level == 4) {
            brandCode = model.getLevelCode5();
        } else if (level == 5) {
            brandCode = model.getLevelCode6();
        } else if (level == 6) {
            brandCode = model.getLevelCode7();
        } else if (level == 7) {
            brandCode = model.getLevelCode8();
        } else if (level == 8) {
            brandCode = model.getLevelCode9();
        } else if (level == 9) {
            brandCode = model.getLevelCode10();
        } else if (level == 10) {
            brandCode = model.getLevelCode11();
        } else if (level == 11) {
            brandCode = model.getLevelCode12();
        } else if (level == 12) {
            brandCode = model.getLevelCode13();
        } else if (level == 13) {
            brandCode = model.getLevelCode14();
        } else if (level == 14) {
            brandCode = model.getLevelCode15();
        }
        return brandCode;
    }

    public static String getNameBasedOnLevel(int level, MTDReportModel model){
        String brandName = "";
        if (level == 0) {
            brandName = model.getLevelName1();
        } else if (level == 1) {
            brandName = model.getLevelName2();
        } else if (level == 2) {
            brandName = model.getLevelName3();
        } else if (level == 3) {
            brandName = model.getLevelName4();
        } else if (level == 4) {
            brandName = model.getLevelName5();
        } else if (level == 5) {
            brandName = model.getLevelName6();
        } else if (level == 6) {
            brandName = model.getLevelName7();
        } else if (level == 7) {
            brandName = model.getLevelName8();
        } else if (level == 8) {
            brandName = model.getLevelName9();
        } else if (level == 9) {
            brandName = model.getLevelName10();
        } else if (level == 10) {
            brandName = model.getLevelName11();
        } else if (level == 11) {
            brandName = model.getLevelName12();
        } else if (level == 12) {
            brandName = model.getLevelName13();
        } else if (level == 13) {
            brandName = model.getLevelName14();
        } else if (level == 14) {
            brandName = model.getLevelName15();
        }
        return brandName;
    }
}
