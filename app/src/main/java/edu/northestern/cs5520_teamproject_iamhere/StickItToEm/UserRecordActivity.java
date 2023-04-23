package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.northestern.cs5520_teamproject_iamhere.R;


public class UserRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.user_activity);
        }


    public void onClickSendStickerButton(View view) {
        Toast.makeText(UserRecordActivity.this, "Clicked send button "
                , Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DisplaySticker.class));
    }
}
