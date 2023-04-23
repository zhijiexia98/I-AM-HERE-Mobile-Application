package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northestern.cs5520_teamproject_iamhere.R;


public class UserRecordAdapter extends
        RecyclerView.Adapter<UserRecordAdapter.RecyclerUserRecordContainerViewHolder> {
    private final ArrayList<UserInformation> userInformationArrayList;
    private final UserRecordListener userRecordListener;

    public UserRecordAdapter(ArrayList<UserInformation> user_card_list,
                             UserRecordListener userRecordListener)
        {
            this.userInformationArrayList = user_card_list;
            this.userRecordListener = userRecordListener;
        }

    @NonNull
    @Override
    public RecyclerUserRecordContainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_activity, parent,
                false);
        return new RecyclerUserRecordContainerViewHolder(view, userRecordListener);
    }


    public static class RecyclerUserRecordContainerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        private final TextView username;
        private final Button sendStickerButton;
        private final UserRecordListener userRecordListener;

        public RecyclerUserRecordContainerViewHolder(@NonNull View itemView, UserRecordListener
                userRecordListener)
        {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTextView);
            sendStickerButton = itemView.findViewById(R.id.sendStickerBtn);
            this.userRecordListener = userRecordListener;

            itemView.setOnClickListener(this);
            sendStickerButton.setOnClickListener(v ->
                    userRecordListener.onUserSendStickerButtonClick(getAdapterPosition()));
        }

        public Button getSendStickerButton()
        {
            return sendStickerButton;
        }

        public TextView getUsername()
        {
            return username;
        }

        @Override
        public void onClick(View v)
        {
            userRecordListener.onUserChatRecordClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemCount()
    {
        return userInformationArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerUserRecordContainerViewHolder viewHolder,
                                 int position)
    {
        String username = userInformationArrayList.get(position).getUsername();
        viewHolder.getUsername().setText(username);
        viewHolder.getSendStickerButton().setTag(username);
    }
}
