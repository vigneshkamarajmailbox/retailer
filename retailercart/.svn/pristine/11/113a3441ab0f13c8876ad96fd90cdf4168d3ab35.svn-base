/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;

import java.util.ArrayList;
import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;

    public FAQAdapter(List<Item> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        if (type == HEADER) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(R.layout.faq_list_header, parent, false);
            return new ListHeaderViewHolder(view);
        } else {
            TextView itemTextView = new TextView(context);
            itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
            itemTextView.setTextColor(0x88000000);
            itemTextView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            return new RecyclerView.ViewHolder(itemTextView) {
            };
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        if (item.type == HEADER) {
            final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
            itemController.refferalItem = item;
            itemController.headerTitle.setText(item.text);
            if (item.invisibleChildren == null) {
                itemController.btnExpandToggle.setImageResource(R.drawable.circle_minus);
            } else {
                itemController.btnExpandToggle.setImageResource(R.drawable.circle_plus);
            }
            itemController.btnExpandToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExpandButtonClick(item, itemController);
                }
            });

        } else if (item.type == CHILD) {
            TextView itemTextView = (TextView) holder.itemView;
            itemTextView.setText(data.get(position).text);

        }
    }

    private void onExpandButtonClick(Item item, ListHeaderViewHolder itemController) {
        if (item.invisibleChildren == null) {
            item.invisibleChildren = new ArrayList<>();
            int count = 0;
            int pos = data.indexOf(itemController.refferalItem);
            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                item.invisibleChildren.add(data.remove(pos + 1));
                count++;
            }
            notifyItemRangeRemoved(pos + 1, count);
            itemController.btnExpandToggle.setImageResource(R.drawable.circle_plus);
        } else {
            int pos = data.indexOf(itemController.refferalItem);
            int index = pos + 1;
            for (Item i : item.invisibleChildren) {
                data.add(index, i);
                index++;
            }
            notifyItemRangeInserted(pos + 1, index - pos - 1);
            itemController.btnExpandToggle.setImageResource(R.drawable.circle_minus);
            item.invisibleChildren = null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class Item {
        public List<Item> getInvisibleChildren() {
            return invisibleChildren;
        }

        public void setInvisibleChildren(List<Item> invisibleChildren) {
            this.invisibleChildren = invisibleChildren;
        }

        private List<Item> invisibleChildren;
        private int type;
        private String text;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }

    private class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;
        ImageView btnExpandToggle;
        Item refferalItem;

        ListHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.header_title);
            btnExpandToggle = itemView.findViewById(R.id.btn_expand_toggle);
        }
    }
}

