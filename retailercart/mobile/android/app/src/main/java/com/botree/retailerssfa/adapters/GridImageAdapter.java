package com.botree.retailerssfa.adapters;

import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.NewOutletImageList;

import java.util.List;

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.GalleryViewHolder> {

    private final List<NewOutletImageList> ansList;

    private OnRecyclerItemClick onRecyclerItemClick;

    public GridImageAdapter(List<NewOutletImageList> list) {
        this.ansList = list;

    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    @NonNull
    @Override
    public GridImageAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_gallery_list_item, parent, false);
        return new GridImageAdapter.GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridImageAdapter.GalleryViewHolder holder, int position) {

        byte[] imageAsBytes = Base64.decode(ansList.get(position).getImage().getBytes(), Base64.DEFAULT);
        holder.captureImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,
                0, imageAsBytes.length));

        holder.removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onRecyclerItemClick != null) {
                    onRecyclerItemClick.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ansList.size();
    }

    public void removeAt(int position) {
        ansList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ansList.size());

    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView removeImg;
        private ImageView captureImage;

        private GalleryViewHolder(View itemView) {
            super(itemView);
            captureImage = itemView.findViewById(R.id.scheme_gallery_img);
            removeImg = itemView.findViewById(R.id.scheme_remove_image_img);
        }
    }
}