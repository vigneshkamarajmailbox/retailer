package com.botree.retailerssfa.controller.retrofit;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.arcprogressbar.Utils;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.db.query.ReportDownloadHelper;
import com.botree.retailerssfa.db.query.SchemeDownloadHelper;
import com.botree.retailerssfa.main.LoginActivity;
import com.botree.retailerssfa.models.DistrReportModel;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.models.OTPModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ReportDataModel;
import com.botree.retailerssfa.models.ReportZipModel;
import com.botree.retailerssfa.models.SalesHierarchy;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.SchemeZipModel;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.models.Sync;
import com.botree.retailerssfa.models.TimeCaptureModel;
import com.botree.retailerssfa.models.UserModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.botree.retailerssfa.service.JSONConstants.TAG_SCREEN_ACCESS;

/**
 * Created by vinothbaskaran on 7/2/18.
 */

public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();
    private static final String CONSTANT_UTF8 = "; charset=utf-8";
    private static DataManager ourInstance;
    private static WeakReference<Context> contextRef;
    private UserModel userdetails = new UserModel();
    private List<ScreenConfig> screenConfigArrayList = new ArrayList<>();
    private List<SalesHierarchy> salesHierarchyArrayList = new ArrayList<>();
    private List<SalesHierarchy> salesHierarchyValueList = new ArrayList<>();
    private Map<String, Object> imageOrderProductDetails = new HashMap<>();
    private List<OrderBookingVO> orderBookingVOList = new ArrayList<>();
    private List<SchemeModel> schemeProductDetail = new ArrayList<>();

    private List<OrderBookingVO> swipeOrderBookingVOList = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    public static void initDataManager(Context context) {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        contextRef = new WeakReference<>(context);
    }

    public List<SalesHierarchy> getSalesHierarchyArrayList() {
        return salesHierarchyArrayList;
    }

    private void setSalesHierarchyArrayList(List<SalesHierarchy> salesHierarchyArrayList) {
        this.salesHierarchyArrayList = salesHierarchyArrayList;
    }


    public UserModel getUserdetails() {
        return userdetails;
    }


    public void getReportDownloadOffline(String accessToken, String redirect, String loadPath, String[] keys, String[] values, final APICallBack apiCallBack) {
        try {
            RequestBody requestBody = getRequestBody(keys, values);
            Call<ReportDataModel> response = ApiClient.getApiClient().getApi().getOfflineReport(accessToken, redirect, loadPath, requestBody);
            response.enqueue(new Callback<ReportDataModel>() {
                @Override
                public void onResponse(Call<ReportDataModel> call, Response<ReportDataModel> response) {
                    try {
                        if (response != null) {
                            if (response.code() == 200) {
                                String responseMessage = response.body().toString();
                                ReportDataModel reportDataModel = response.body();
                                Sync sync = Sync.getInstance();
                                sync.setMtdFieldWorkingReport(reportDataModel.getMtdFieldWorkingList());
                                sync.setStockLedgerReportList(reportDataModel.getStockLedgerList());


                                apiCallBack.onAPICallback(responseMessage, true, AppConstant.RequestType.REPORT_DOWNLOAD);
                                Log.e(TAG, "responce 1: " + responseMessage + " : " + response.code());

                            } else {

                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.REPORT_DOWNLOAD);
                            }

                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.REPORT_DOWNLOAD);

                    }
                }

                @Override
                public void onFailure(Call<ReportDataModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.REPORT_DOWNLOAD);
                }
            });

        } catch (JSONException e) {
            Log.e(TAG, "getReportDownloadOffline: " + e.getMessage(), e);
        }


    }


    /**
     * method is used to fetch configuration data using retrofit post method with validation
     *
     * @param requestUrl  string value to append with the download api
     * @param keys        request params keys pairs
     * @param values      request params value for the keys
     * @param apiCallBack callback listerer to pass the data to the calling activity with status
     */
    public void getSalesHierarchyDownloadOffline(String accessToken, String redirect, String requestUrl, String[] keys, String[] values,
                                                 final APICallBack apiCallBack) {
        try {
            Log.e(TAG, "getSalesHierarchyDownloadOffline: " + "started");
            final RequestBody requestBody = getRequestBody(keys, values);
            Log.e(TAG, "getSalesHierarchyDownloadOffline:1 " + "started");
            Call<List<SalesHierarchy>> response = ApiClient.getApiClient().getApi().getSalesHierarchy(accessToken, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<List<SalesHierarchy>>() {
                @Override
                public void onResponse(Call<List<SalesHierarchy>> call, Response<List<SalesHierarchy>> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                Log.d(TAG, " getConfigurationDetails onResponse: " + response.body());
                                setSalesHierarchyArrayList(response.body());
                                String responseMessage = response.body().toString();
                                apiCallBack.onAPICallback(responseMessage, true, AppConstant.RequestType.SALES_HIERARCHY_DATA);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.SALES_HIERARCHY_DATA);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.SALES_HIERARCHY_DATA);
                    }
                }

                @Override
                public void onFailure(Call<List<SalesHierarchy>> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.SALES_HIERARCHY_DATA);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getSalesHierarchyDownloadOffline: " + e.getMessage(), e);
        }
    }

    /**
     * method is used to fetch configuration data using retrofit post method with validation
     *
     * @param requestUrl  string value to append with the download api
     * @param keys        request params keys pairs
     * @param values      request params value for the keys
     * @param apiCallBack callback listerer to pass the data to the calling activity with status
     */
    public void getSalesHierarchyValueDownloadOffline(String accessToken, String redirect, String requestUrl, String[] keys, String[] values,
                                                      final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<List<SalesHierarchy>> response = ApiClient.getApiClient().getApi().getSalesHierarchy(accessToken, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<List<SalesHierarchy>>() {
                @Override
                public void onResponse(Call<List<SalesHierarchy>> call, Response<List<SalesHierarchy>> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                setSalesHierarchyValueList(response.body());
                                String responseMessage = response.body().toString();
                                apiCallBack.onAPICallback(responseMessage, true, AppConstant.RequestType.SALES_HIERARCHY_VALUE);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.SALES_HIERARCHY_VALUE);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.SALES_HIERARCHY_VALUE);
                    }
                }

                @Override
                public void onFailure(Call<List<SalesHierarchy>> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.SALES_HIERARCHY_VALUE);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getSalesHierarchyValueDownloadOffline: " + e.getMessage(), e);
        }
    }


    /**
     * method is used to fetch login data using retrofit post method with validation
     *
     * @param requestUrl  string value to append with the download api
     * @param keys        request params keys pairs
     * @param values      request params value for the keys
     * @param apiCallBack callback listerer to pass the data to the calling activity with status
     */
    public void getLoginResponce(String requestUrl, String[] keys, String[] values,
                                 final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<LoginModel> response = ApiClient.getApiClient().getApi().getLogin(requestUrl, requestBody);
            response.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback(response.body().toString(), true, AppConstant.RequestType.LOGIN);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.LOGIN);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.LOGIN);
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.LOGIN);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getLoginResponce: " + e.getMessage(), e);
        }
    }

    /**
     * method is used to fetch login data using retrofit post method with validation
     *
     * @param requestUrl  string value to append with the download api
     * @param keys        request params keys pairs
     * @param values      request params value for the keys
     * @param apiCallBack callback listener to pass the data to the calling activity with status
     */
    public void getLoginResponse(String redirect, String requestUrl, String[] keys, String[] values,
                                 final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<LoginModel> response = ApiClient.getApiClient().getApi().performLogin(redirect, requestUrl, requestBody);
            response.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback(new Gson().toJson(response.body()), true, AppConstant.RequestType.LOGIN);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.LOGIN);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.LOGIN);
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.LOGIN);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getLoginResponse: " + e.getMessage(), e);
        }
    }

    /**
     * method is used to fetch configuration data using retrofit post method with validation
     *
     * @param requestUrl  string value to append with the download api
     * @param keys        request params keys pairs
     * @param values      request params value for the keys
     * @param apiCallBack callback listerer to pass the data to the calling activity with status
     */
    public void getConfigurationDetails(String token, String redirect, String requestUrl, String[] keys, String[] values,
                                        final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<List<ScreenConfig>> response = ApiClient.getApiClient().getApi().getScreenConfiguration(token, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<List<ScreenConfig>>() {
                @Override
                public void onResponse(Call<List<ScreenConfig>> call, Response<List<ScreenConfig>> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                setScreenConfigArrayList(response.body());
                                String responseMessage = response.body().toString();
                                apiCallBack.onAPICallback(responseMessage, true, AppConstant.RequestType.SCREEN_CONFIGURATION);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.SCREEN_CONFIGURATION);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.SCREEN_CONFIGURATION);
                    }
                }

                @Override
                public void onFailure(Call<List<ScreenConfig>> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.SCREEN_CONFIGURATION);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getConfigurationDetails: " + e.getMessage(), e);
        }
    }


    @NonNull
    private JSONObject convertJsonObject(String[] keys, String[] values) throws JSONException {
        JSONObject jsonBody;
        jsonBody = new JSONObject();
        for (int i = 0; i < keys.length; i++)
            jsonBody.put(keys[i], values[i]);
        Log.e(TAG, "getRequestBody: " + jsonBody.toString());
        return jsonBody;
    }

    @NonNull
    public RequestBody getRequestBody(String[] keys, String[] values) throws JSONException {
        JSONObject jsonBody;
        jsonBody = convertJsonObject(keys, values);
        return RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8),
                jsonBody.toString());
    }

    /**
     * method is used to fetch scheme retailer product details data using retrofit post method with validation
     *
     * @param requestUrl  string value to append with the download api
     * @param jsonBody    request params as json format
     * @param apiCallBack callback listerer to pass the data to the calling activity with status
     */
    public void getSchemeDetails(String accessToken,String loginCode, String redirect, String requestUrl, String jsonBody,
                                 final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8),
                    jsonBody);
            Call<SchemeZipModel> response = ApiClient.getApiClient().getApi().getRetailerSchemeDetails(accessToken,loginCode, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<SchemeZipModel>() {
                @Override
                public void onResponse(Call<SchemeZipModel> call, Response<SchemeZipModel> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                new SchemeDownloadHelper(contextRef.get(), response.body());
//                                new SchemeDownloadHelper(contextRef.get(), new Gson().fromJson(
//                                        Utils.getJsonFromRaw(contextRef.get(), R.raw.scheme_response),SchemeZipModel.class));

                                apiCallBack.onAPICallback("", true, AppConstant.RequestType.RETAILER_SCHEME_LIST);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.RETAILER_SCHEME_LIST);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getSchemeDetails onResponse: " + e.getMessage(), e);
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.RETAILER_SCHEME_LIST);
                    }
                }

                @Override
                public void onFailure(Call<SchemeZipModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.RETAILER_SCHEME_LIST);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getSchemeDetails: " + e.getMessage(), e);
        }
    }
    public void getBanner(String redirect, String requestUrl,final APICallBack apiCallBack) {
        try {
            Call<ResponseBody> response = ApiClient.getApiClient().getApi().getBanner(redirect+requestUrl);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback("", true, AppConstant.RequestType.GET_BANNER);
                            } else {
//                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.GET_BANNER);
                                apiCallBack.onAPICallback("", false, AppConstant.RequestType.GET_BANNER);
                            }
                        }
                    } catch (Exception e) {
//                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.GET_BANNER);
                        apiCallBack.onAPICallback("", false, AppConstant.RequestType.GET_BANNER);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.RETAILER_SCHEME_LIST);
                    apiCallBack.onAPICallback("", false, AppConstant.RequestType.GET_BANNER);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getSchemeDetails: " + e.getMessage(), e);
        }
    }

    public void downloadFile(String accessToken, String redirect, String getNotification, String filename,
                             final FileDownloadCallBack downloadCallBack) {

        final Call<ResponseBody> response = ApiClient.getApiClient().getApi().downloadMessageFile(accessToken, ApiClient.getApiClient().getFileUrl() + redirect + "/" + getNotification + "/" + filename);
        response.clone().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    if (response != null) {
                        if (response.isSuccessful()) {
                            downloadCallBack.onFileDownloadCallback("Success", response.body(), true, AppConstant.RequestType.DOWNLOAD_FILE);
                        } else {
                            errorResponse(response.code(), response.body(), downloadCallBack);
                        }

                    }
                } catch (Exception e) {
                    errorResponse(response.code(), response.body(), downloadCallBack);
                    Log.e(TAG, "Exception : " + e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    downloadCallBack.onFileDownloadCallback(contextRef.get().getString(R.string.unable_connect_server), response.execute().body(), false, AppConstant.RequestType.DOWNLOAD_FILE);
                } catch (IOException e) {
                    Log.e(TAG, "onFailure: " + e.getMessage(), e);
                }
            }
        });
    }


    public void errorResponse(int responseCode, APICallBack apiCallBack, AppConstant.RequestType type) {
        if (responseCode == 400) {
            apiCallBack.onAPICallback(contextRef.get().getString(R.string.invalid_request), false, type);
        } else if (responseCode == 401) {
            sessionTimeOutCall(responseCode);
        } else if (responseCode == 500) {
            apiCallBack.onAPICallback(contextRef.get().getString(R.string.internal_server_error), false, type);
        } else if (responseCode == 503) {
            apiCallBack.onAPICallback(contextRef.get().getString(R.string.service_unreachable), false, type);
        } else if (responseCode == 410) {
            sessionTimeOutCall(responseCode);
        } else {
            apiCallBack.onAPICallback(contextRef.get().getString(R.string.request_timeout) + responseCode, false, type);
        }
    }

    private void sessionTimeOutCall(int responseCode) {
        Intent intent = new Intent(contextRef.get(), LoginActivity.class);
        intent.putExtra("AuthError", responseCode);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        contextRef.get().startActivity(intent);
    }

    private void errorResponse(int responseCode, ResponseBody responseBody, FileDownloadCallBack downloadCallBack) {
        if (responseCode == 400) {
            downloadCallBack.onFileDownloadCallback(contextRef.get().getString(R.string.invalid_request), responseBody, false, AppConstant.RequestType.DOWNLOAD_FILE);
        } else if (responseCode == 401) {
            sessionTimeOutCall(responseCode);
        } else if (responseCode == 500) {
            downloadCallBack.onFileDownloadCallback(contextRef.get().getString(R.string.internal_server_error), responseBody, false, AppConstant.RequestType.DOWNLOAD_FILE);
        } else if (responseCode == 503) {
            downloadCallBack.onFileDownloadCallback(contextRef.get().getString(R.string.service_unreachable), responseBody, false, AppConstant.RequestType.DOWNLOAD_FILE);
        } else if (responseCode == 410) {
            sessionTimeOutCall(responseCode);
        } else {
            downloadCallBack.onFileDownloadCallback(contextRef.get().getString(R.string.request_timeout) + responseCode, responseBody, false, AppConstant.RequestType.DOWNLOAD_FILE);
        }
    }

    public List<ScreenConfig> getScreenConfigArrayList() {
        return screenConfigArrayList;
    }

    public void setScreenConfigArrayList(List<ScreenConfig> screenConfigArrayList) {
        this.screenConfigArrayList = screenConfigArrayList;
    }

    public List<SalesHierarchy> getSalesHierarchyValueList() {
        return salesHierarchyValueList;
    }

    private void setSalesHierarchyValueList(List<SalesHierarchy> salesHierarchyValueList) {
        this.salesHierarchyValueList = salesHierarchyValueList;
    }

    public Map<String, Object> getImageOrderProductDetails() {
        return imageOrderProductDetails;
    }

    public void setImageOrderProductDetails(Map<String, Object> imageOrderProductDetails) {
        this.imageOrderProductDetails = imageOrderProductDetails;
    }

    public List<OrderBookingVO> getOrderBookingVOList() {
        return orderBookingVOList;
    }

    public void setOrderBookingVOList(List<OrderBookingVO> orderBookingVOList, List<SchemeModel> schemeProductDetail) {

        this.orderBookingVOList = orderBookingVOList;
        this.schemeProductDetail = schemeProductDetail;
    }

    public List<SchemeModel> getSchemeProductDetail() {
        return schemeProductDetail;
    }

    public List<OrderBookingVO> getSwipeOrderBookingVOList() {
        return swipeOrderBookingVOList;
    }

    public void addSwipeOrderBookingVOList(List<OrderBookingVO> swipeOrderBookingVOList) {
        this.swipeOrderBookingVOList = swipeOrderBookingVOList;
    }

    public void getUploadProcessCaptureTime(String accessToken,String loginCode, String redirect, String loadPath, JSONObject jsonObject, final APICallBack apiCallBack) {

        try {
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8),
                    jsonObject.toString());
            Log.e(TAG, " getUploadProcessCaptureTime " + jsonObject.toString());
            Call<TimeCaptureModel> response = ApiClient.getApiClient().getApi().getProcessCaptureTime(accessToken,loginCode, redirect, loadPath, requestBody);
            response.enqueue(new Callback<TimeCaptureModel>() {
                @Override
                public void onResponse(Call<TimeCaptureModel> call, Response<TimeCaptureModel> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback(new Gson().toJson(response.body()), true, AppConstant.RequestType.SYNC_LOG);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.SYNC_LOG);
                            }

                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.SYNC_LOG);
                    }
                }

                @Override
                public void onFailure(Call<TimeCaptureModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.SYNC_LOG);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getUploadProcessCaptureTime: " + e.getMessage(), e);
        }

    }

    public void getUploadCodification(String accessToken, String redirect, String loadPath, String json, final APICallBack apiCallBack) {

        try {
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8), json);

            Call<ResponseBody> response = ApiClient.getApiClient().getApi().getCodificationResponse(accessToken, redirect, ApiClient.UPLOAD, requestBody);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response != null) {
                            if (response.code() == 200) {
                                String responseStr = response.body().string();
                                Log.e(TAG, "getUploadCodification response " + responseStr);

                                apiCallBack.onAPICallback(responseStr, true, AppConstant.RequestType.CODIFICATION);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.CODIFICATION);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getUploadCodification onResponse: " + e.getMessage(), e);
                        apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.CODIFICATION);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "getUploadCodification onResponse: " + t.getMessage(), t);
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.REPORT_DOWNLOAD);
                }
            });
        }catch (Exception e){
            Log.e(TAG,"getUploadCodification :  exp : "+e);
        }
    }

        public void performLogout(String redirect, String requestUrl, String[] keys, String[] values, final APICallBack apiCallBack) {
            try {
                final RequestBody requestBody = getRequestBody(keys, values);
                Call<LoginModel> response = ApiClient.getApiClient().getApi().performLogout(redirect, requestUrl, requestBody);
                response.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        try {
                            if (response != null) {
                                if (response.code() == 200) {
                                    apiCallBack.onAPICallback(new Gson().toJson(response.body()), true, AppConstant.RequestType.LOGIN);
                                } else {
                                    errorResponse(response.code(), apiCallBack, AppConstant.RequestType.LOGIN);
                                }
                            }
                        } catch (Exception e) {
                            apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.LOGIN);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.LOGIN);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "performLogout: " + e.getMessage(), e);
            }
        }

    public void getOTPResponse(String token, String redirect, String requestUrl, String[] keys, String[] values,
                               final APICallBack apiCallBack, final AppConstant.RequestType customerOtpRef) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<OTPModel> response = ApiClient.getApiClient().getApi().getOTP(token, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {

                                Globals.getOurInstance().setOtpModelData(response.body());

                                apiCallBack.onAPICallback(response.body().getRefCode(), true, customerOtpRef);
                            } else {
                                errorResponse(response.code(), apiCallBack, customerOtpRef);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, customerOtpRef);
                    }
                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, customerOtpRef);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getOTPResponse: " + e.getMessage(), e);
        }
    }

    public void getReportDetails(String accessToken, String redirect, String requestUrl, String jsonBody, final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8),
                    jsonBody);
            Call<ResponseBody> response = ApiClient.getApiClient().getApi().getDistrReportDetails(accessToken, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response != null) {
                            if (response.code() == 200) {
                                String responseStr = response.body().string();
                                Log.e(TAG, " response " + responseStr);

                                AppUtils appUtils = AppUtils.getOurInstance();
                                String responseString = appUtils.decompressGZIP(responseStr);
                                List<DistrReportModel> reportModelList;
                                Sync sync = Sync.getInstance();
                                JSONArray jsonArray = new JSONArray(responseString);
                                reportModelList = appUtils.getConvertedListFromJson(jsonArray, DistrReportModel.class);
                                sync.setDistrReportModelList(reportModelList);

                                apiCallBack.onAPICallback("", true, AppConstant.RequestType.REPORT_DOWNLOAD);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.REPORT_DOWNLOAD);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getReportDetails onResponse: " + e.getMessage(), e);
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.REPORT_DOWNLOAD);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "getReportDetails onResponse: " + t.getMessage(), t);
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.REPORT_DOWNLOAD);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getReportDetails: " + e.getMessage(), e);
        }
    }

    public void getScreenConfigurationDetails(String accessToken,String userCode, String redirect, String requestUrl, String[] keys, String[] values,
                                              final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<ResponseBody> response = ApiClient.getApiClient().getApi().getScreenConfigurationDetails(accessToken,userCode, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                String responseString = response.body().string();
                                JSONObject jsonObj = new JSONObject(responseString);
                                addScreenConfig(jsonObj, apiCallBack);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.SCREEN_CONFIGURATION);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.SCREEN_CONFIGURATION);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.SCREEN_CONFIGURATION);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getScreenConfigurationDetails: " + e.getMessage(), e);
        }


    }

    private void addScreenConfig(JSONObject jsonObj, APICallBack apiCallBack) throws JSONException {
        if (!isJsonNotNull(jsonObj, TAG_SCREEN_ACCESS))
            return;

        List<ScreenConfig> screenAccessConfig;
        if (jsonObj.get(TAG_SCREEN_ACCESS) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCREEN_ACCESS));
            JSONArray jsonArray = new JSONArray(responseString);
            AppUtils appUtils = AppUtils.getOurInstance();
            screenAccessConfig = appUtils.getConvertedListFromJson(jsonArray, ScreenConfig.class);

            setScreenConfigArrayList(screenAccessConfig);

            apiCallBack.onAPICallback("", true, AppConstant.RequestType.SCREEN_CONFIGURATION);
        }
    }

    public static boolean isJsonNotNull(JSONObject jsonObj, String key) {
        return jsonObj.has(key) && !(jsonObj.isNull(key));
    }

    public void getReportTemplateDetails(String accessToken, String redirect, String requestUrl, String jsonBody,
                                         final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8),
                    jsonBody);
            Call<ReportZipModel> response = ApiClient.getApiClient().getApi().getReportTemplateDetails(accessToken, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<ReportZipModel>() {
                @Override
                public void onResponse(Call<ReportZipModel> call, Response<ReportZipModel> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                Log.e(TAG, " getReportTemplateDetails "+ response.body());
                                new ReportDownloadHelper(contextRef.get(), response.body());

                                apiCallBack.onAPICallback("", true, AppConstant.RequestType.DOWNLOAD_REPORT_TEMPLATE);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.DOWNLOAD_REPORT_TEMPLATE);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getReportTemplateDetails onResponse: " + e.getMessage(), e);
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.DOWNLOAD_REPORT_TEMPLATE);
                    }
                }

                @Override
                public void onFailure(Call<ReportZipModel> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.DOWNLOAD_REPORT_TEMPLATE);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getReportTemplateDetails: " + e.getMessage(), e);
        }
    }

    public void getOnlineReportDetails(String accessToken, String redirect, String requestUrl, String jsonBody, final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(AppConstant.CONTENT_TYPE + CONSTANT_UTF8),
                    jsonBody);
            Call<ResponseBody> response = ApiClient.getApiClient().getApi().getOnlineReportDetails(accessToken, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response != null) {
                            if (response.code() == 200) {
                                String responseStr = response.body().string();
                                Log.e(TAG, " response " + responseStr);
                                AppUtils appUtils = AppUtils.getOurInstance();
                                String responseString = appUtils.decompressGZIP(responseStr);

                                apiCallBack.onAPICallback(responseString, true, AppConstant.RequestType.DOWNLOAD_ONLINE_REPORT);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.DOWNLOAD_ONLINE_REPORT);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getOnlineReportDetails onResponse: " + e.getMessage(), e);
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.DOWNLOAD_ONLINE_REPORT);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "getOnlineReportDetails onResponse: " + t.getMessage(), t);
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.DOWNLOAD_ONLINE_REPORT);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getOnlineReportDetails: " + e.getMessage(), e);
        }
    }

    public void sendFgtPwdOTP(String redirect, String requestUrl, String[] keys, String[] values, final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<ResponseBody> response = ApiClient.getApiClient().getApi().getSendFgtPwdOTP(redirect, requestUrl, requestBody);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                String responseStr = response.body().string();
                                AppUtils appUtils = AppUtils.getOurInstance();
                                String responseString = appUtils.decompressGZIP(responseStr);

                                apiCallBack.onAPICallback(responseString, true, AppConstant.RequestType.FGT_PWD_OTP);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.FGT_PWD_OTP);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.FGT_PWD_OTP);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.FGT_PWD_OTP);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "sendFgtPwdOTP : " + e.getMessage(), e);
        }
    }

    public void getForgotPasswordResponse(String redirect, String requestUrl, String[] keys, String[] values,
                                          final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<Void> response = ApiClient.getApiClient().getApi().performForgotPassword(redirect, requestUrl, requestBody);
            response.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call call, Response response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback("success", true, AppConstant.RequestType.CHANGE_PASSWORD);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.CHANGE_PASSWORD);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.CHANGE_PASSWORD);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.CHANGE_PASSWORD);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getForgotPasswordResponse: " + e.getMessage(), e);
        }
    }

    public void getGSTReport(String accessToken, String userCode, String redirect, String requestUrl, String[] keys, String[] values, final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<Void> response = ApiClient.getApiClient().getApi().getGSTReport(accessToken, userCode, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call call, Response response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback("success", true, AppConstant.RequestType.EXTRACT_REPORT);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.EXTRACT_REPORT);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.EXTRACT_REPORT);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.EXTRACT_REPORT);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getGSTReport: " + e.getMessage(), e);
        }
    }

    public interface APICallBack {
        void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType);
    }


    public interface FileDownloadCallBack {
        void onFileDownloadCallback(String message, ResponseBody responseBody, boolean isSuccess, AppConstant.RequestType requestType);
    }

    public void getChangePasswordResponse(String accessToken, String redirect, String requestUrl, String[] keys, String[] values,
                                          final APICallBack apiCallBack) {
        try {
            final RequestBody requestBody = getRequestBody(keys, values);
            Call<Void> response = ApiClient.getApiClient().getApi().performChangePassword(accessToken, redirect, requestUrl, requestBody);
            response.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call call, Response response) {
                    try {

                        if (response != null) {
                            if (response.code() == 200) {
                                apiCallBack.onAPICallback("success", true, AppConstant.RequestType.CHANGE_PASSWORD);
                            } else {
                                errorResponse(response.code(), apiCallBack, AppConstant.RequestType.CHANGE_PASSWORD);
                            }
                        }
                    } catch (Exception e) {
                        apiCallBack.onAPICallback(e.getMessage(), false, AppConstant.RequestType.CHANGE_PASSWORD);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    apiCallBack.onAPICallback(contextRef.get().getString(R.string.unable_connect_server), false, AppConstant.RequestType.CHANGE_PASSWORD);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getChangePasswordResponse: " + e.getMessage(), e);
        }
    }


}
