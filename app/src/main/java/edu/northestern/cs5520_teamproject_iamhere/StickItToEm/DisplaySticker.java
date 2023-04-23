package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northestern.cs5520_teamproject_iamhere.R;


public class DisplaySticker extends AppCompatActivity {
    private String sender_username;
    private String receiver_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stickers_options);
        sender_username = getIntent().getStringExtra("sender_username");
        receiver_username = getIntent().getStringExtra("receiver_username");
        createNotification();
    }

    public void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onClickStickerButton(View view) {
        String sticker_tag = (String) view.getTag();
        Toast.makeText(DisplaySticker.this, "Click sticker successfully"
                + sticker_tag, Toast.LENGTH_SHORT).show();
        long currTime = System.currentTimeMillis();
        saveRecordToDatabase(sender_username, receiver_username,
                sticker_tag, String.valueOf(currTime));
    }

    public void sendNotification(String date, String sticker_id, String from_username) {
        Intent intent = new Intent(this, ReceiveNotificationActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("sticker_id", sticker_id);
        intent.putExtra("from_username", from_username);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        String channelId = getString(R.string.channel_id);
        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, channelId)
                //"Notification icons must be entirely white."
                .setSmallIcon(R.drawable.sticker_2)
                .setContentText(from_username + " sent you a sticker, take a look?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                // hide the notification after its selected
//                .setAutoCancel(true)
                .setContentIntent(pIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // // notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, notifyBuild.build());
    }

    private void saveRecordToDatabase(String sender, String receiver, String sticker_tag,
                                      String epochTime) {
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference chatsDBRef = DBRef.child("chats");
        sendNotification(epochTime, sticker_tag,sender);
        MessageRecord chat = new MessageRecord(sender, receiver, sticker_tag, epochTime);
        String uniqueId = sender.concat(epochTime);
        Context context = getApplicationContext();
        chatsDBRef.child(uniqueId).setValue(chat, (databaseError, ref) ->
        {
            if (databaseError != null)
            {
                Toast myToast1= Toast.makeText(context, "Unable send stickers",
                        Toast.LENGTH_LONG);
                myToast1.show();
            } else
            {
                Toast myToast = Toast.makeText(context, chat.getSticker().concat(" Send successfully"),
                        Toast.LENGTH_LONG);
                myToast.show();
            }
        });
    }
}
