package com.botree.retailerssfa.controller.retrofit;

import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.models.OTPModel;
import com.botree.retailerssfa.models.ReportDataModel;
import com.botree.retailerssfa.models.ReportZipModel;
import com.botree.retailerssfa.models.SalesHierarchy;
import com.botree.retailerssfa.models.SchemeZipModel;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.models.TimeCaptureModel;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RestApi {


    @POST("{path}")
    Call<LoginModel> getLogin(@Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<LoginModel> performLogin(@Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ReportDataModel> getOfflineReport(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<List<ScreenConfig>> getScreenConfiguration(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<List<SalesHierarchy>> getSalesHierarchy(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @Headers({
            "Accept-Encoding: identity"
    })
    @GET
    Call<ResponseBody> downloadMessageFile(@Header("X-Auth-Token") String accessToken, @Url String filename);

    @POST("{redirect}/{path}")
    Call<TimeCaptureModel> getProcessCaptureTime(@Header("X-Auth-Token") String accessToken,@Header("X-Login-Code") String loginCode, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<SchemeZipModel> getRetailerSchemeDetails(@Header("X-Auth-Token") String accessToken,@Header("X-Login-Code") String loginCode, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @GET
    Call<ResponseBody> getBanner( @Url String filename);

    @POST("{redirect}/{path}")
    Call<OTPModel> getOTP(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<Distributor> getDistributor(@Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<Void> performChangePassword(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ResponseBody> getDistrReportDetails(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ResponseBody> getScreenConfigurationDetails(@Header("X-Auth-Token") String accessToken,@Header("X-Login-Code") String userCode, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ReportZipModel> getReportTemplateDetails(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ResponseBody> getOnlineReportDetails(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ResponseBody> getSendFgtPwdOTP(@Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<Void> performForgotPassword(@Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<ResponseBody> getCodificationResponse(@Header("X-Auth-Token") String accessToken, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<LoginModel> performLogout(@Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);

    @POST("{redirect}/{path}")
    Call<Void> getGSTReport(@Header("X-Auth-Token") String accessToken, @Header("UserCode") String userCode, @Path("redirect") String redirect, @Path("path") String path, @Body RequestBody requestBody);
}

