package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import volunteer.upay.com.upay.Models.ChatUser;
import volunteer.upay.com.upay.R;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private List<ChatUser> mChats;
    private Context context;
    SharedPreferences sharedPreferences;


    public ChatRecyclerAdapter(Context context, List<ChatUser> chats) {
        mChats = chats;
        this.context = context;

    }

    public void add(ChatUser chat) {
        mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (TextUtils.equals(mChats.get(position).getEmail(), sharedPreferences.getString("login_email",""))) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        ChatUser chat = mChats.get(position);
        myChatViewHolder.txtChatMessage.setText(chat.getMsg());
        myChatViewHolder.txtUserAlphabet.setText("you");
    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        ChatUser chat = mChats.get(position);
        otherChatViewHolder.txtChatMessage.setText(chat.getMsg());
        otherChatViewHolder.txtUserAlphabet.setText(chat.getName());
    }

    @Override
    public int getItemCount() {
        if (mChats != null) {
            return mChats.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (TextUtils.equals(mChats.get(position).getEmail(), sharedPreferences.getString("login_email",""))) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, txtUserAlphabet;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message_mine);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_name_mine);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, txtUserAlphabet;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message_other);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_name_other);
        }
    }
}