package com.botree.retailerssfa.bot;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.retailerssfa.R;

import java.util.List;

/**
 * Created by shantarao on 26/12/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChatAdapter.class.getSimpleName();
    private List<ListObject> listObjects;
    private int loggedInUserId;
    private Context context;

    ChatAdapter(Context context, List<ListObject> listObjects) {
        this.listObjects = listObjects;
        this.context = context;
    }

    public void setUser(int userId) {
        this.loggedInUserId = userId;
    }

    public void setDataChange(List<ListObject> asList) {
        this.listObjects = asList;
        //now, tell the adapter about the update
        try {
            notifyItemChanged(asList.size() + 1);
        } catch (Exception e) {
            Log.e(TAG, "setDataChange: "+e.getMessage(),e);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ListObject.TYPE_GENERAL_RIGHT) {
            View currentUserView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_list_row_left, parent, false);
            viewHolder = new ChatRightViewHolder(currentUserView); // view holder for normal items

        } else if (viewType == ListObject.TYPE_GENERAL_LEFT) {
            View otherUserView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_list_row_right, parent, false);
            viewHolder = new ChatLeftViewHolder(otherUserView); // view holder for normal items

        } else  {
            View v2 = inflater.inflate(R.layout.date_row, parent, false);
            viewHolder = new DateViewHolder(v2);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == ListObject.TYPE_GENERAL_RIGHT) {
            ChatModelObject generalItem = (ChatModelObject) listObjects.get(position);
            ChatRightViewHolder chatViewHolder = (ChatRightViewHolder) viewHolder;
            chatViewHolder.bind(generalItem.getChatModel());

        } else if (viewHolder.getItemViewType() == ListObject.TYPE_GENERAL_LEFT) {
            ChatModelObject generalItemLeft = (ChatModelObject) listObjects.get(position);
            ChatLeftViewHolder chatLeftViewHolder = (ChatLeftViewHolder) viewHolder;
            chatLeftViewHolder.bind(context, generalItemLeft.getChatModel());

        } else if (viewHolder.getItemViewType() == ListObject.TYPE_DATE) {
            DateObject dateItem = (DateObject) listObjects.get(position);
            DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
            dateViewHolder.bind(dateItem.getDate());

        }
    }

    @Override
    public int getItemCount() {
        if (listObjects != null) {
            return listObjects.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return listObjects.get(position).getType(loggedInUserId);
    }

    public ListObject getItem(int position) {
        return listObjects.get(position);
    }
}