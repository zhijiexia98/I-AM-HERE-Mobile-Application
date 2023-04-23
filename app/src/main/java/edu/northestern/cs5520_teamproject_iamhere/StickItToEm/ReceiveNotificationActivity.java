package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import edu.northestern.cs5520_teamproject_iamhere.R;


public class ReceiveNotificationActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);

        long date = getIntent().getLongExtra("date", 0);
        long sticker_id = getIntent().getLongExtra("sticker_id", 0);
        String from_username = getIntent().getStringExtra("from_username");
        Log.d(TAG, "!!!!!!!!!!" + date + sticker_id + from_username);

        textView = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView2);

        try {
            imageView.setImageResource((int) sticker_id);
        } catch (Exception e) {
            // Handle image not found exception
            Log.e(TAG, "error", e);
        }
        String s = from_username + " sent you a sticker at " + new Date(date);
        textView.setText(s);



    }
}