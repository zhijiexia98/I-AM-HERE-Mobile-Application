package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;

import edu.northestern.cs5520_teamproject_iamhere.R;


public class UserMessageRecordActivity extends AppCompatActivity {
    private StickerHistoryAdapter recyclerViewAdapterChatHistory;
    private final ArrayList<MessageRecord> chatLists = new ArrayList<>();
    private TextView sticker_count_info_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_history_recycleview);

        Intent intent = getIntent();
        String receiver_username = intent.getStringExtra("receiver_username");
        String sender_username = intent.getStringExtra("sender_username");
        TextView chat_record_info_textview = findViewById(R.id.chatRecordInfoTextView);
        chat_record_info_textview.append("Message with user:  " + receiver_username);

        RecyclerView recyclerViewChatRecord = findViewById(R.id.chatRecordRecycleView);
        RecyclerView.LayoutManager recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewChatRecord.setLayoutManager(recyclerViewLayoutManger);
        recyclerViewAdapterChatHistory = new StickerHistoryAdapter(chatLists,
                sender_username, receiver_username);
        recyclerViewChatRecord.setAdapter(recyclerViewAdapterChatHistory);

        // Get reference to root firebase database.
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference chatsDatabaseRef = databaseRef.child("chats");
        chatsDatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int stickerSendCnt = 0;
                chatLists.clear();
                for(DataSnapshot eachChildSnapshot : snapshot.getChildren())
                {
                    MessageRecord chat = eachChildSnapshot.getValue(MessageRecord.class);
                    assert chat != null;
                    chatLists.add(chat);
                    stickerSendCnt++;
                }
                chatLists.sort(Comparator.comparing(MessageRecord::getTime));
                recyclerViewAdapterChatHistory.notifyDataSetChanged();
                sticker_count_info_textview = findViewById(R.id.userStickerCountTextView);
                sticker_count_info_textview.setText(MessageFormat.format("You have Sent(<-) {0}" +
                        " Stickers", stickerSendCnt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}