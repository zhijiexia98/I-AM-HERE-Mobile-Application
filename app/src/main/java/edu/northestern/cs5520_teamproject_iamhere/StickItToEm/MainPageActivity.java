package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import edu.northestern.cs5520_teamproject_iamhere.R;


public class MainPageActivity extends AppCompatActivity implements UserRecordListener {
    private UserRecordAdapter userRecordAdapter;
    private final ArrayList<UserInformation> userInformationList = new ArrayList<>();
    private String usernameStr;
    private static final String MainPageActivityTAG = "MainPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_em_recycler_view);
        usernameStr = getIntent().getStringExtra("username");
        UserInformation userInformation = new UserInformation(usernameStr);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // Get "users" reference
        DatabaseReference usersDBRef = mDatabase.child("users");
        // Add userInfo object to users database.
        usersDBRef.child(usernameStr).setValue(userInformation);

        RecyclerView recyclerViewForAllUsers = findViewById(R.id.recyclerViewAllChats);
        RecyclerView.LayoutManager recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewForAllUsers.setLayoutManager(recyclerViewLayoutManger);
        userRecordAdapter = new UserRecordAdapter
                (userInformationList, this);
        recyclerViewForAllUsers.setAdapter(userRecordAdapter);

        //get data from firebase
        usersDBRef.addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                userInformationList.clear();
                for(DataSnapshot eachChildSnapshot :snapshot.getChildren())
                {
                    UserInformation userInformation = eachChildSnapshot.getValue(UserInformation.class);
                    assert userInformation != null;
                    userInformationList.add(userInformation);
                }
                userRecordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });
    }

    @Override
    public void onUserSendStickerButtonClick(int position)
    {
        Toast.makeText(MainPageActivity.this, "Clicked send button",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DisplaySticker.class);
        intent.putExtra("receiver_username", userInformationList.get(position).getUsername());
        intent.putExtra("sender_username", usernameStr);
        startActivity(intent);
    }

    @Override
    public void onUserChatRecordClick(int position)
    {
        Toast.makeText(MainPageActivity.this, "User stickers history clicked!",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserMessageRecordActivity.class);
        intent.putExtra("receiver_username", userInformationList.get(position).getUsername());
        intent.putExtra("sender_username", usernameStr);
        startActivity(intent);
    }
}
