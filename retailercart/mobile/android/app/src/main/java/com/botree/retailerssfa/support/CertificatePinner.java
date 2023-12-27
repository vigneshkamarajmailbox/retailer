package com.botree.retailerssfa.support;

import android.content.Context;
import android.util.Log;

import com.botree.retailerssfa.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class CertificatePinner {
    private static final String TAG = CertificatePinner.class.getSimpleName();
    private static final String SSL = "SSL";
    private static CertificatePinner instCertificate = new CertificatePinner();
    private Picasso.Builder sPicasso;

    private CertificatePinner() {
    }

    public static CertificatePinner getInstCertificate() {
        if (instCertificate != null) {
            instCertificate = new CertificatePinner();
        }
        return instCertificate;
    }

    public SSLSocketFactory getSSlSocketFactory(Context context) {
        // loading CAs from an InputStream
        SSLContext sslContext;
        try {
            TrustManagerFactory tmf = getTrustManagerFactory(context);

            // creating an SSLSocketFactory that uses our TrustManager
            sslContext = SSLContext.getInstance(SSL);
            sslContext.init(null, tmf.getTrustManagers(), null);

            // creating an OkHttpClient that uses our SSLSocketFactory
            return sslContext.getSocketFactory();

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            Log.e(TAG, "createAdapter: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    private TrustManagerFactory getTrustManagerFactory(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        CertificateFactory cf;
        cf = CertificateFactory.getInstance("X.509");

        InputStream cert = context.getResources().openRawResource(R.raw.root);
        Certificate ca;
        try {
            ca = cf.generateCertificate(cert);
        } finally {
            cert.close();
        }

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        return tmf;
    }

    /**
     * get SSL Certifcate added OkHttp
     * @param context activity context
     * @return OKHTTP Client
     */
    public OkHttpClient getSSLAddedOkHttpClient(Context context) {
        // loading CAs from an InputStream
        SSLContext sslContext;
        OkHttpClient.Builder builder;
        try {
            TrustManagerFactory tmf = getTrustManagerFactory(context);

            // creating an SSLSocketFactory that uses our TrustManager
            sslContext = SSLContext.getInstance(SSL);
            sslContext.init(null, tmf.getTrustManagers(), null);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder = new OkHttpClient.Builder();
            builder.addInterceptor(interceptor);
            builder.readTimeout(120, TimeUnit.SECONDS);
            builder.connectTimeout(120, TimeUnit.SECONDS);

            TrustManager[] trustManagers = tmf.getTrustManagers();
            X509TrustManager x509TrustManager = (X509TrustManager) trustManagers[0];

            builder.sslSocketFactory(getSSlSocketFactory(context), x509TrustManager);
            return builder.build();

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            Log.e(TAG, "createAdapter: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }

    /**
     * get Picasso Instance with SSL Certificate added for TGBL Ony
     *
     * @param context activity Context
     * @return Picasso Instance
     */
    public Picasso getCustomPicasso(Context context) {
        if (sPicasso == null) {
            sPicasso = new Picasso.Builder(context);
            OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(getSSLAddedOkHttpClient(context));
            sPicasso.downloader(okHttp3Downloader);
        }
        /*if ("com.botree.productsfa.tgbl".equalsIgnoreCase(BuildConfig.APPLICATION_ID))
            return sPicasso.build();
        else*/
            return Picasso.get();
    }
}
