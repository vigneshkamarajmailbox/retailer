package com.botree.retailerssfa.controller.retrofit;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.botree.retailerssfa.main.SFAApplication;
import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String LOGIN_REDIRECT = "login";
    public static final String LOGIN = "authenticate";
    public static final String CHANGE_PASS = "changepassword";
    public static final String SCREEN_CONFIGURATION = "fetchscreenaccess";
    public static final String REDIRECT = "controller";
    public static final String GET_NOTIFICATION = "getnotification";
    public static final String SALESFORCE_HIERLEVE = "salesforcehierlevel";
    public static final String SALESFORCE_HIERVALUE = "salesforcehiervalue";
    public static final String REPORTS_DOWNLOAD = "downloadreport";
    public static final String SALES_HIERERCHY_DOWNLOAD = "findAllDownLevel";
    public static final String PROCESS_TIME_CAPTURE = "savesynclog";
    public static final String DOWNLOAD_SCHEME_DETAILS = "downloadscheme";
    public static final String GET_BANNER_IMAGE = "/getbannerimage";
    public static final String CUSTOMER_OTP = "getsms";
    private static final String TAG = ApiClient.class.getSimpleName();
    private static ApiClient mApiClient = new ApiClient();
    private Retrofit mRetrofit;
    private RestApi mRestApi;
    private String fileUrl;
    public static final String CONTROLLER = "controller";
    public static final String UPLOAD = "upload";
    public static final String DOWNLOAD_REPORT_TEMPLATE = "downloadreporttemplate";
    public static final String DOWNLOAD_ONLINE_REPORT = "downloadonlinereport";
    public static final String SEND_OTP = "sendotp";
    public static final String LOGOUT = "logOut";
    public static final String EXTRACT_GST_REPORT = "extractreport";

    private ApiClient() {

    }

    public static ApiClient getApiClient() {
        return mApiClient;
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    public void init(SFAApplication sfaApplication) {
        final OkHttpClient okHttpClient;
        okHttpClient = getOkHttpClient();


        String baseUrl = getBaseUrl(sfaApplication);
        if (mRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .serializeNulls()
                    .create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();


            mRestApi = mRetrofit.create(RestApi.class);
        }
    }

    private String getBaseUrl(SFAApplication sfaApplication) {
        String baseUrl = sfaApplication.getResources().getString(R.string.BASE_URL_RELEASE);
        if (BuildConfig.DEBUG) {
            baseUrl = sfaApplication.getResources().getString(R.string.BASE_URL_DEBUG);
        }
        Log.e(TAG, "getBaseUrl: " + baseUrl);
        setFileUrl(baseUrl);
        return baseUrl;
    }

    public RestApi getApi() {
        return mRestApi;
    }

    String getFileUrl() {
        return fileUrl;
    }

    private void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
