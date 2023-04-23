package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import edu.northestern.cs5520_teamproject_iamhere.R;


/**
 * This class represents the recyclerview adapter of ChatRecord.
 */
public class StickerHistoryAdapter extends
        RecyclerView.Adapter<StickerHistoryAdapter.RecyclerChatRecordViewHolder>
{

    private final String sender_username;
    private final String receiver_username;
    private final HashMap<String, Integer> stickers_hashmap;
    private final ArrayList<MessageRecord> chat_history;

    public StickerHistoryAdapter(ArrayList<MessageRecord> chat_card_list,
                                 String sender_username, String receiver_username)
        {
            this.chat_history = chat_card_list;
            this.sender_username = sender_username;
            this.receiver_username = receiver_username;
            this.stickers_hashmap = new HashMap<>();
            map_stickers();
        }

    private void map_stickers()
        {
            stickers_hashmap.put("sticker1", R.drawable.sticker_1);
            stickers_hashmap.put("sticker2", R.drawable.sticker_2);
            stickers_hashmap.put("sticker3", R.drawable.sticker_3);
        }

    @NonNull
    @Override
    public RecyclerChatRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.chat_history, parent, false);
            return new RecyclerChatRecordViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChatRecordViewHolder viewHolder, int position)
    {
        MessageRecord messageRecord = chat_history.get(position);
        String sticker_tag = messageRecord.getSticker();
        String time = messageRecord.getTime();
        long value = Long.parseLong(time);
        Date date = new Date(value);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formatted = format.format(date);
        viewHolder.getSenderSticker().setImageResource(stickers_hashmap.get(sticker_tag));
        viewHolder.getSenderStickerTime().setText(formatted);
        viewHolder.getReceiverSticker().setImageResource(android.R.color.transparent);
        viewHolder.getReceiverStickerTime().setText("");
    }

    @Override
    public int getItemCount()
    {
        return chat_history.size();
    }

    public static class RecyclerChatRecordViewHolder extends RecyclerView.ViewHolder {
        private final ImageView senderStickerImageView;
        private final ImageView receiverStickerImageView;
        private final TextView senderStickerTimeTextView;
        private final TextView receiverStickerTimeTextView;

        public ImageView getSenderSticker()
        {
            return senderStickerImageView;
        }

        public ImageView getReceiverSticker()
        {
            return receiverStickerImageView;
        }

        public TextView getSenderStickerTime()
        {
            return senderStickerTimeTextView;
        }

        public TextView getReceiverStickerTime()
        {
            return receiverStickerTimeTextView;
        }

        public RecyclerChatRecordViewHolder(@NonNull View itemView)
            {
                super(itemView);
                senderStickerImageView = itemView.findViewById(R.id.imageViewSender);
                receiverStickerImageView = itemView.findViewById(R.id.imageViewReceiver);
                senderStickerTimeTextView = itemView.findViewById(R.id.textViewSenderTime);
                receiverStickerTimeTextView = itemView.findViewById(R.id.textViewReceiverTime);
            }
    }
}