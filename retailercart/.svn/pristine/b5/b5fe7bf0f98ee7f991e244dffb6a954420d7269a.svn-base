package com.botree.retailerssfa.support;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.MyItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Created by vinothbaskaran on 13/10/17.
 */

public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {

    private final Context context;

    public MyClusterRenderer(Context context, GoogleMap map,
                             ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item,
                                               MarkerOptions markerOptions) {
        String[] seperated = item.getTitle().split("\\.");
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromBitmap(makeBitmap(context, seperated[0]));

        markerOptions.icon(markerDescriptor);
    }


    private Bitmap makeBitmap(Context context, String text) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_map_marker);
        bitmap = bitmap.copy(ARGB_8888, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE); // Text color
        paint.setTextSize(15 * scale); // Text size
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE); // Text shadow
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);


        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 3;
        canvas.drawText(text, x, y, paint);

        return bitmap;
    }

}
