package com.botree.retailerssfa.bot;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;


public class ChatRightViewHolder extends RecyclerView.ViewHolder {
    private TextView userTxt;

    ChatRightViewHolder(View itemView) {
        super(itemView);
        userTxt = itemView.findViewById(R.id.user_chat_txt);
    }

    public void bind(final ChatModel chatModel) {

        userTxt.setText(chatModel.getMessage());
    }
}