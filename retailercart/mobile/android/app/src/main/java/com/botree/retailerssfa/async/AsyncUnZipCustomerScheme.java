package com.botree.retailerssfa.async;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.SchemeModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static android.content.ContentValues.TAG;

public class AsyncUnZipCustomerScheme extends AsyncTask<Void, Void, Void> {
    private final WeakReference<Activity> activityRef;
    private String routeSchemeZipString;
    private UnZipCallBack unZipCallBack;
    private Dialog progressDialog;

    public AsyncUnZipCustomerScheme(Activity context, String routeSchemeZipString) {
        this.routeSchemeZipString = routeSchemeZipString;
        activityRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        activityRef.get().runOnUiThread(new Runnable() {
            public void run() {
                showProgressDialog(activityRef.get(), "Fetching Schemes");
            }
        });
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        unZipCallBack.unzipCallBackListener(true);
        cancelProgressDialog();
    }


    private byte[] decodeBase64(String input) {
        return Base64.decode(input, 0);
    }

    private void cancelProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

        } catch (Exception e) {
                progressDialog.dismiss();
            Log.e(TAG, "cancelProgressDialog: " + e.getMessage());
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.e(TAG, "doInBackground: Fetching Schemes");
            String responseString = decompress(decodeBase64(routeSchemeZipString));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<SchemeModel>>() {
            }.getType();
            List<SchemeModel> schemeModelList = gson.fromJson(responseString, listType);

            SFADatabase database = SFADatabase.getInstance(activityRef.get());
            database.insertCustomerSchemeProductDetails(schemeModelList);
            Log.e(TAG, "doInBackground: Fetching Schemes Completed");
        } catch (Exception e) {
            Log.e(TAG, "downloadSchemeDetails decompress : " + e.getMessage(), e);
        }
        return null;
    }

    private String decompress(byte[] compressed) {
        StringBuilder string = new StringBuilder();
        try {
            final int BUFFER_SIZE = 32;
            ByteArrayInputStream is = new ByteArrayInputStream(compressed);
            GZIPInputStream gis = new GZIPInputStream(is);

            byte[] data = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = gis.read(data)) != -1) {
                string.append(new String(data, 0, bytesRead));
            }
            gis.close();
            is.close();
        } catch (Exception e) {
            Log.e(TAG, "decompress: " + e.getMessage(), e);
        }
        return string.toString();
    }

    private void showProgressDialog(Activity fragActivity, String tvMessage) {
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

    }

    public void setUnZipCallBack(UnZipCallBack unZipCallBack) {
        this.unZipCallBack = unZipCallBack;
    }

    public interface UnZipCallBack {
        void unzipCallBackListener(Boolean aBoolean);
    }

}