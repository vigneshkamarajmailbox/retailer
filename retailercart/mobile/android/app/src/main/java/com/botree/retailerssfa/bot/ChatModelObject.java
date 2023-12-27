package com.botree.retailerssfa.bot;

/**
 * Created by shantarao on 26/12/17.
 */

public class ChatModelObject extends ListObject {

    private ChatModel chatModel;

    public ChatModel getChatModel() {
        return chatModel;
    }

    public void setChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public int getType(int userId) {
        if (this.chatModel.getUserId() == userId) {
            return TYPE_GENERAL_RIGHT;
        } else
            return TYPE_GENERAL_LEFT;
    }
}