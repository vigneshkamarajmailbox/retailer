package com.botree.retailerssfa.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.db.query.CodeGeneratorQueryHelper;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.support.ListOf;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.botree.retailerssfa.support.Globals.NAME_ACTIVITY_TRACKER;
import static com.botree.retailerssfa.support.Globals.NAME_FOCUS_BRAND_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_MUST_SELL_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.NAME_OUTLET_COLLECTION;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT_MASTER;
import static com.botree.retailerssfa.support.Globals.NAME_PURCHASE_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_SALES_RETURN;
import static com.botree.retailerssfa.support.Globals.NAME_SCHEME_PRODUCTS_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_STOCK_TAKE;
import static com.botree.retailerssfa.support.Globals.NAME_UPDATE_LOCATION;
import static com.botree.retailerssfa.support.Globals.NAME_VAN_SALES;
import static com.botree.retailerssfa.support.Globals.fromHtml;

public class AppUtils {


    public static final int PERCENTAGE_100_ABOVE_TYPE = 500;
    private static final int PERCENTAGE_100_TYPE = 501;
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    private static final NavigableMap<Long, String> suffixesforDistContribution = new TreeMap<>();
    private static final String TAG = AppUtils.class.getSimpleName();
    private static final String APPLICATION_PDF = "application/pdf";
    private static AppUtils ourInstance = null;
    private static WeakReference<Context> activityRef;

    private static final int[] DARK_COLORS = {
            Color.rgb(213, 74, 41)
            , Color.rgb(43, 137, 237)
            , Color.rgb(1, 159, 0)
            , Color.rgb(0, 149, 166)
            , Color.rgb(215, 81, 43)
            , Color.rgb(162, 0, 168)
            , Color.rgb(1, 159, 0)
            , Color.rgb(10, 87, 193)
    };

    static {

        suffixes.put(1_000L, "k");
        suffixes.put(1_000_00L, "Lk");
        suffixes.put(1_000_000_0L, "Cr");

    }

    static {
        suffixesforDistContribution.put(1_00L, "k");
        suffixesforDistContribution.put(1_000_0L, "LK");
        suffixesforDistContribution.put(1_000_00L, "CR");
    }

    private Paint p = new Paint();
    private SwipeListener swipeListener;
    private Dialog progressDialog;

    private AppUtils() {
    }

    public static void initAppUtils(Context context) {
        activityRef = new WeakReference<>(context);
        if (ourInstance == null) {
            ourInstance = new AppUtils();
        }
    }

    public static AppUtils getOurInstance() {
        return ourInstance;
    }

    public static double calPercentage(Double calculateValue, Double total, int percentageType) {
        try {
            if (total == 0 && calculateValue > 0) {
                return 100;
            } else if (total > 0) {
                if (((calculateValue / total) * 100) > 100 && percentageType == PERCENTAGE_100_TYPE)
                    return 100;
                else
                    return (calculateValue / total) * 100;
            } else {
                return 0;
            }

        } catch (Exception e) {
            return 0;
        }
    }


    public static Integer calcIntPercentage(Integer value, Integer total, int percentageType) {
        try {
            if (total == 0 && value > 0) {
                return 100;
            } else if (total > 0) {
                if (((value / total) * 100) > 100 && percentageType == PERCENTAGE_100_TYPE)
                    return 100;
                else
                    return (value / total) * 100;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String formatForDistContribution(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixesforDistContribution.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static boolean isUserOnline() {
        return SFASharedPref.getOurInstance().readBoolean(SFASharedPref.PREF_IS_ON_OFF_USER);
    }


    public static String generateCode(SFADatabase sfaDatabase, String prefix) {
        int code = CodeGeneratorQueryHelper.getCodeNumber(sfaDatabase, prefix);
        String numString;

        //Format code to 5 digits
        numString = AppUtils.formatCode(code);

        return numString;
    }

    public static int generateIntCode(SFADatabase sfaDatabase, String prefix) {
        return CodeGeneratorQueryHelper.getCodeNumber(sfaDatabase, prefix);
    }

    public static String formatCode(int code) {
        return String.format(Locale.getDefault(), "%05d", code);
    }

    /**
     * used to safe cast the object list in custom object list and vise versa
     *
     * @param lst list to be convert
     * @param cls custom object class name
     * @param <E> genrics for casting
     * @return list of custom class object
     */
    public <E> List<E> listSafeCasting(List<?> lst, Class<E> cls) {
        List<E> result = new ArrayList<>();
        for (Object obj : lst) {
            if (cls.isInstance(obj))
                result.add(cls.cast(obj));
        }
        return result;
    }

    /**
     * used to convert Json To Model Class by using GSON & Genrics
     *
     * @param <T>       Gloabal Model Declaration
     * @param jsonArray json array for conversion
     * @param type      selected Model Class to Covert Json
     * @return list of custom Object
     */
    @Nullable
    public <T> List<T> getConvertedListFromJson(JSONArray jsonArray, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(jsonArray.toString(), new ListOf<>(type));
    }

    /**
     * Method to decompress Data
     *
     * @param input Gzip compressed input string
     * @return decompressed Gzip output string
     */
    public String decompressGZIP(String input) {
        StringBuilder string = new StringBuilder();
        try {
            final int BUFFER_SIZE = 32;
            ByteArrayInputStream is = new ByteArrayInputStream(decodeBase64(input));
            GZIPInputStream gis = new GZIPInputStream(is);

            byte[] data = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = gis.read(data)) != -1) {
                string.append(new String(data, 0, bytesRead));
            }
            gis.close();
            is.close();
        } catch (Exception e) {
            Log.e(TAG, "decompressGZIP: " + e.getMessage(), e);
            return "";
        }
        return string.toString();
    }

    /**
     * Method  to compress data
     *
     * @param string json String
     * @return GZIP compressed byte[]
     * @throws IOException exception
     */
    public String compressGZIP(String string) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(string.getBytes());
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();
        return Base64.encodeToString(compressed, Base64.NO_WRAP);
    }

    /**
     * @param input Gzip compressed input string
     * @return GZIP byte[]
     */
    private byte[] decodeBase64(String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    public Intent openFile(File file, Context context) {

        if (Build.VERSION.SDK_INT >= 24) {
            Uri path = getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
            Intent openintent = new Intent(Intent.ACTION_VIEW);
            try {
                setFormatToFile(file, path, openintent);
                openintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                openintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "openFile: " + e.getLocalizedMessage());
            }
            return openintent;

        } else {
            Uri path = Uri.fromFile(file);
            Intent openintent = new Intent(Intent.ACTION_VIEW);
            try {
                setFormatToFile(file, path, openintent);
                openintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "openFile: " + e.getMessage(), e);
            }
            return openintent;
        }


    }

    private void setFormatToFile(File file, Uri path, Intent openintent) {
        if (isAnyFormatsTure(file, ".doc", ".docx")) {
            openintent.setDataAndType(path, "application/msword");// Word document
        } else if (isFileContains(file, ".pdf")) {
            openintent.setDataAndType(path, APPLICATION_PDF);// PDF file
        } else if (isAnyFormatsTure(file, ".ppt", ".pptx")) {
            openintent.setDataAndType(path, "application/vnd.ms-powerpoint");// Powerpoint file
        } else if (isAnyFormatsTure(file, ".xls", ".xlsx")) {
            openintent.setDataAndType(path, "application/vnd.ms-excel");// Excel file
        } else if (isAnyFormatsTure(file, ".zip", ".rar")) {
            openintent.setDataAndType(path, "application/x-wav"); // WAV audio file

        } else if (isFileContains(file, ".rtf")) {

            openintent.setDataAndType(path, "application/rtf"); // RTF file
        } else if (isAnyFormatsTure(file, ".wav", ".mp3")) {

            openintent.setDataAndType(path, "audio/x-wav");// WAV audio file
        } else if (isFileContains(file, ".gif")) {

            openintent.setDataAndType(path, "image/gif");// GIF file
        } else if (isAnyFormatsTure(file, ".jpg", ".jpeg", ".png")) {

            openintent.setDataAndType(path, "image/jpeg");            // JPG file
        } else if (isFileContains(file, ".txt")) {

            openintent.setDataAndType(path, "text/plain");// Text file
        } else if (isAnyFormatsTure(file, ".3gp", ".mpg", ".mpeg", ".mpe", ".mp4", ".avi")) {

            openintent.setDataAndType(path, "video/*"); // Video files
        } else {
            //if you want you can also define the openintent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            openintent.setDataAndType(path, "*/*");
        }
    }

    private boolean isAnyFormatsTure(File file, String... formatType) {
        for (String format : formatType) {
            if (isFileContains(file, format)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFileContains(File file, String format) {
        return file.toString().contains(format);
    }

    public String decimalFormatWithTwoDigit(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }

    public String decimalFormatWithOneDigit(double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }

    public List<QuickViewVo> getQuickMenuList() {
        Context activity = activityRef.get();
        int[] listofColors = activity.getResources().getIntArray(R.array.Color_navigationIcons);
        boolean isPurchaseReportAlreadyAdded = false;
        List<ScreenConfig> screenConfigList = DataManager.getInstance().getScreenConfigArrayList();
        List<QuickViewVo> quickViewVos = new ArrayList<>();
        for (ScreenConfig screenConfig : screenConfigList) {
            if (isMultipleCheckTrue(isPurchaseReportAlreadyAdded, screenConfig, 1, 35, 36)) {
                quickViewVos.add(new QuickViewVo(NAME_PURCHASE_REPORT, R.drawable.icon_purchase, false));
                isPurchaseReportAlreadyAdded = true;
            } else {
                getQuickActionMenuList2(quickViewVos, screenConfig);
            }
        }

        setColorToList(listofColors, quickViewVos);

        return quickViewVos;
    }

    private void setColorToList(int[] listofColors, List<QuickViewVo> quickViewVos) {
        for (int i = 0; i < quickViewVos.size(); i++) {
            quickViewVos.get(i).setColor(listofColors[i % (listofColors.length - 1)]);
        }
    }

    private void getQuickActionMenuList2(List<QuickViewVo> quickViewVos, ScreenConfig screenConfig) {
        if (isScreenVisibleTrue(screenConfig, 1, 20)) {
            quickViewVos.add(new QuickViewVo(NAME_PRODUCT, R.drawable.icon_product, false));
        } else if (isScreenVisibleTrue(screenConfig, 1, 21)) {
            quickViewVos.add(new QuickViewVo(NAME_PRODUCT_MASTER, R.drawable.icon_product, false));
        } else if (isScreenVisibleTrue(screenConfig, 1, 23)) {
            quickViewVos.add(new QuickViewVo(NAME_SCHEME_PRODUCTS_REPORT, R.drawable.ic_scheme_24dp, false));
        } else if (isScreenVisibleTrue(screenConfig, 1, 24)) {
            quickViewVos.add(new QuickViewVo(NAME_FOCUS_BRAND_MASTER_REPORT, R.drawable.ic_focus_product, false));
        } else if (isScreenVisibleTrue(screenConfig, 1, 25)) {
            quickViewVos.add(new QuickViewVo(NAME_MUST_SELL_MASTER_REPORT, R.drawable.ic_add_scheme, false));
        } else {
            getQuickActionMenuList3(quickViewVos, screenConfig);
        }
    }

    private void getQuickActionMenuList3(List<QuickViewVo> quickViewVos, ScreenConfig screenConfig) {
        if (isScreenVisibleTrue(screenConfig, 3, 2)) {
            quickViewVos.add(new QuickViewVo(NAME_ORDER_BOOKING, R.drawable.order_booking, false));
        } else if (isScreenVisibleTrue(screenConfig, 3, 3)) {
            quickViewVos.add(new QuickViewVo(NAME_SALES_RETURN, R.drawable.sales_return, false));
        } else if (isScreenVisibleTrue(screenConfig, 3, 4)) {
            quickViewVos.add(new QuickViewVo(NAME_OUTLET_COLLECTION, R.drawable.collection, false));
        } else if (isScreenVisibleTrue(screenConfig, 3, 8)) {
            quickViewVos.add(new QuickViewVo(NAME_UPDATE_LOCATION, R.drawable.update_location, false));
        } else {
            getQuickActionMenuList4(quickViewVos, screenConfig);
        }
    }

    private void getQuickActionMenuList4(List<QuickViewVo> quickViewVos, ScreenConfig screenConfig) {
        if (isScreenVisibleTrue(screenConfig, 3, 9)) {
            quickViewVos.add(new QuickViewVo(NAME_STOCK_TAKE, R.drawable.icon_stock, false));
        } else if (isScreenVisibleTrue(screenConfig, 3, 10)) {
            quickViewVos.add(new QuickViewVo(NAME_VAN_SALES, R.drawable.van, false));
        } else if (isScreenVisibleTrue(screenConfig, 3, 14)) {
            quickViewVos.add(new QuickViewVo(NAME_ACTIVITY_TRACKER, R.drawable.survy, false));
        }
    }

    private boolean isMultipleCheckTrue(Boolean isMarketOutstandingAlredyAdded, ScreenConfig screenConfig, int moduleNo, int scrnNo1, int scrnNo2) {
        return screenConfig.getModuleNo() == moduleNo && (screenConfig.getScreenNo() == scrnNo1 || screenConfig.getScreenNo() == scrnNo2)
                && screenConfig.getChecked().equalsIgnoreCase("true") && !isMarketOutstandingAlredyAdded;
    }

    private boolean isScreenVisibleTrue(ScreenConfig screenConfig, int moduleNo, int screenNo) {
        return screenConfig.getModuleNo() == moduleNo && screenConfig.getScreenNo() == screenNo && screenConfig.getChecked().equalsIgnoreCase("true");
    }

    public boolean isNetworkConnected() {
        Context activity = activityRef.get();
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    @NonNull
    public List<Integer> getColorsList() {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }

    public List<Integer> getDarkColors() {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : DARK_COLORS) {
            colors.add(c);
        }

        return colors;
    }


    @NonNull
    public List<Integer> getJoyfulAndColorfullcolors() {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        return colors;
    }

    /**
     * used to get drawable color for the initial
     *
     * @return circle Drawable with color
     */
    public Drawable getInitialCircleDrawable(int adapterPosition) {
        List<Integer> colors = AppUtils.getOurInstance().getColorsList();
        return getInitialDrawablesWithColors(adapterPosition, colors);
    }

    /**
     * used this method to get the color drawable with the passing color
     *
     * @param adapterPosition adapter position
     * @param colors          parameter colot for drawable
     * @return circle Drawable with color
     */
    private Drawable getInitialDrawablesWithColors(int adapterPosition, List<Integer> colors) {
        Drawable drawable = ContextCompat.getDrawable(activityRef.get(), R.drawable.blue_circle_btn);
        if (drawable != null) {
            if (adapterPosition < colors.size()) {
                drawable.setColorFilter(colors.get(adapterPosition), PorterDuff.Mode.SRC_ATOP);
            } else {
                Log.e(TAG, "getInitialCircleDrawable: " + (adapterPosition % colors.size()));
                drawable.setColorFilter(colors.get(adapterPosition % colors.size()), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return drawable;
    }

    public void getFixedHeightDrawable(int adapterPosition, View Layout) {
        List<Integer> colors = AppUtils.getOurInstance().getDarkColors();
        if (Layout != null) {
            if (adapterPosition < colors.size()) {
                Layout.setBackgroundColor(colors.get(adapterPosition));
            } else {
                Layout.setBackgroundColor(colors.get(adapterPosition % colors.size()));
            }
        }

    }

    public Drawable getDrawableColor(int adapterPosition) {
        List<Integer> colors = AppUtils.getOurInstance().getColorsList();
        Drawable drawable = ContextCompat.getDrawable(activityRef.get(), R.drawable.submit_bg);
        if (drawable != null) {
            if (adapterPosition < colors.size()) {
                drawable.setColorFilter(colors.get(adapterPosition), PorterDuff.Mode.SRC_ATOP);
            } else {
                drawable.setColorFilter(colors.get(adapterPosition % colors.size()), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return drawable;
    }

    /**
     * get the hard coded data from local
     *
     * @param assetFileName assest name for data fetching
     * @return return json string
     */
    public String readTextFileFromLocal(String assetFileName) {

        Context activity = activityRef.get();
        BufferedReader reader = null;
        StringBuilder responseBuilder = new StringBuilder();
        InputStreamReader stream = null;
        try {
            stream = new InputStreamReader(activity.getAssets().open(assetFileName));
            reader = new BufferedReader(stream);

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                responseBuilder.append(mLine);
            }
        } catch (Exception e) {
            //log the exception
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                //log the exception
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                //log the exception
            }

        }
        return responseBuilder.toString();
    }

    @NonNull
    public Animation loadBlinkAnim() {
        final Animation anim = new AlphaAnimation(0.2f, 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(40);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        return anim;
    }

    public void initSwipe(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT && swipeListener != null)
                    swipeListener.swipelistener(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        Context activity = activityRef.get();
                        icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_delete_white);
                        RectF iconDest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, iconDest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public void setInrSymToValue(TextView textView, Double value) {
        textView.setText(getRsFormat(value));
    }

    public String getRsFormat(Double value) {
        Context activity = activityRef.get();
        return String.format(activity.getResources().getString(R.string.rs_string), decimalFormatWithTwoDigit(value));
    }

    public Map<String, String> getConfigColorList(Context context, String[] colorProductNames) {
        return SFADatabase.getInstance(context).getConfigColors(colorProductNames);
    }

    @NonNull
    public List<String> covertArrayValuesToLowerCase(String[] yourArray) {
        ArrayList<String> lowercaseArray = new ArrayList<>();
        for (String s : yourArray) {
            lowercaseArray.add(s.toLowerCase(Locale.getDefault()));
        }
        return lowercaseArray;
    }

    /**
     * method is used to share the output file
     *
     * @param context    activity context
     * @param outputFile file to share
     * @param parentList orderbooking parent list
     */
    public void onShareClick(Context context, File outputFile, OrderBookingVO parentList) {
        try {
            Uri uri;
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                uri = getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", outputFile);
            } else {
                uri = Uri.fromFile(outputFile);
            }

            Intent share = new Intent();
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            share.setAction(Intent.ACTION_SEND);
            share.setType(APPLICATION_PDF);
            if (parentList != null) {
                share.putExtra(Intent.EXTRA_TEXT, fromHtml("Retailer Code : " + parentList.getRetailerName() + " , Date : " + parentList.getOrderDate() + " , No of Line Items : " + parentList.getNoOfItems() + ""));
                share.putExtra(Intent.EXTRA_SUBJECT, "Invoice Summary");
            } else {
                share.putExtra(Intent.EXTRA_TEXT, fromHtml("Total Orders"));
                share.putExtra(Intent.EXTRA_SUBJECT, "Orders Attached");
            }
            share.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(share, "Share PDF using"));
        } catch (Exception e) {
            Log.e(TAG, "onShareClick: " + e.getMessage(), e);
        }

    }

    public void showProgressDialog(Activity fragActivity, String tvMessage) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) return;
            progressDialog = new Dialog(fragActivity, R.style.ThemeDialogCustom);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.layout_loading_spinner);
            TextView tvLoading = progressDialog.findViewById(R.id.tvLoading);
            RelativeLayout layoutLoding = progressDialog.findViewById(R.id.layoutLoading);
            layoutLoding.setVisibility(View.VISIBLE);
            layoutLoding.setBackgroundColor(ContextCompat.getColor(fragActivity, R.color.transparent));
            tvLoading.setText(tvMessage);
            if (!progressDialog.isShowing())
                progressDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "showProgressDialog: " + e.getMessage(), e);
        }
    }

    public void cancleProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

        } catch (Exception e) {
            Log.e(TAG, "cancleProgressDialog: " + e.getMessage(), e);
        }
    }

    public interface SwipeListener {
        void swipelistener(int listPosition);
    }

    public String getMonthName(int monthInNo) {
        String monthsInWords = "";
        switch (monthInNo) {
            case 0:
                monthsInWords = "January";
                break;
            case 1:
                monthsInWords = "Febuary";
                break;
            case 2:
                monthsInWords = "March";
                break;
            case 3:
                monthsInWords = "April";
                break;
            case 4:
                monthsInWords = "May";
                break;
            case 5:
                monthsInWords = "June";
                break;
            case 6:
                monthsInWords = "July";
                break;
            case 7:
                monthsInWords = "Augest";
                break;
            case 8:
                monthsInWords = "September";
                break;
            case 9:
                monthsInWords = "October";
                break;
            case 10:
                monthsInWords = "November";
                break;
            case 11:
                monthsInWords = "December";
                break;
            default:
                getMonthName(Calendar.getInstance().get(Calendar.MONTH));
                break;
        }
        if (!monthsInWords.isEmpty()) {
            return monthsInWords.substring(0, 3).toUpperCase(Locale.getDefault());
        }
        return monthsInWords;
    }


    public String decimalFormatWithLakhs(double value) {
        DecimalFormat f = new DecimalFormat("#,##,##0");
        return f.format(value);
    }
}
