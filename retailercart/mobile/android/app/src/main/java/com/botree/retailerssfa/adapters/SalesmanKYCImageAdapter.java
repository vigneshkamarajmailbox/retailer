package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.KycImageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesmanKYCImageAdapter extends RecyclerView.Adapter<SalesmanKYCImageAdapter.MyViewHolder> {
    private HashMap<String, String> kycImage; // for original reference
    private ArrayList<KycImageModel> dataSet = new ArrayList<>();
    private Context con;
    private SFADatabase db;
    private String salesmanCode;

    private void refreshDataSet(HashMap<String, String> kycImageMap) {
        dataSet.clear();
        for (Object o : kycImageMap.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            if (!pair.getValue().toString().isEmpty()) {
                dataSet.add(new KycImageModel(pair.getKey().toString(), pair.getValue().toString()));
            }
        }
        notifyDataSetChanged();
    }

    public SalesmanKYCImageAdapter(HashMap<String, String> kycImageMap, Context context, String salesmanCode) {
        con = context;
        db = SFADatabase.getInstance(con);
        this.salesmanCode = salesmanCode;
        this.kycImage = kycImageMap;
        refreshDataSet(kycImage);
    }

    @NonNull
    @Override
    public SalesmanKYCImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.salesman_kyc_imagelist, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteSalesmanKycByCode(salesmanCode, dataSet.get(myViewHolder.getAdapterPosition()).getKey());
                kycImage.put(dataSet.get(myViewHolder.getAdapterPosition()).getKey(), "");
                refreshDataSet(kycImage);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SalesmanKYCImageAdapter.MyViewHolder myViewHolder, final int i) {
        final int adapterPos = myViewHolder.getAdapterPosition();
        myViewHolder.type.setText(dataSet.get(adapterPos).getKey());
        byte[] imageAsBytes = Base64.decode(dataSet.get(adapterPos).getImage().getBytes(), Base64.DEFAULT);
        myViewHolder.kycImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,
                0, imageAsBytes.length));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView type;
        private ImageView removeImg;
        private ImageView kycImage;

        private MyViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            kycImage = itemView.findViewById(R.id.kyc_img);
            removeImg = itemView.findViewById(R.id.remove_image);
        }
    }
}
