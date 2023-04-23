package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.northestern.cs5520_teamproject_iamhere.R;

public class StickItToEmLoginActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_login);

        editText =findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StickItToEmLoginActivity.this, MainPageActivity.class);
                intent.putExtra("username", editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
