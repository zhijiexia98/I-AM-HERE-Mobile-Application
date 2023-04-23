package edu.northestern.cs5520_teamproject_iamhere.IamHere;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.northestern.cs5520_teamproject_iamhere.MainActivity;
import edu.northestern.cs5520_teamproject_iamhere.R;


public class WomenSafetyMain extends AppCompatActivity {
    private String usernameStr;
    private DatabaseReference contactsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_safety_main);
        usernameStr = getIntent().getStringExtra("username");

        TextView usernameTextView = findViewById(R.id.text);
        usernameTextView.setText(usernameStr + ", welcome to Women Safety App!");

        // Configure the database:
        // Since we have two databases, so we have to use its api key to create the db instance
        // only need to initialize once!!
        FirebaseApp secondary = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(getApplicationContext());
        for (FirebaseApp app : firebaseApps) {
            if (app.getName().equals("secondary")) {
                secondary = app;
                break;
            }
        }
        if (secondary == null) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId("i-am-here-137f9")
                    .setApiKey("AIzaSyCN0c-AKAAiUCbzPYM7bYK6pumWCjcw7i0")
                    .setApplicationId("1:234376966350:android:73343deec755bf0950609f")
                    .setDatabaseUrl("https://i-am-here-137f9-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(getApplicationContext(), options, "secondary");
            secondary = FirebaseApp.getInstance("secondary");
        }
        contactsDatabase = FirebaseDatabase.getInstance(secondary).getReference(usernameStr);
    }

    public void onClick(View view) {
        int theId = view.getId();
        if(theId == R.id.addContacts) {
            Intent intent = new Intent(this, AddContacts.class);
            intent.putExtra("userInformation", usernameStr);
            startActivity(intent);
        } else if (theId == R.id.sendLocation) {
            Intent intent = new Intent(this, SendLocation.class);
            intent.putExtra("userInformation", usernameStr);
            startActivity(intent);
        } else if (theId == R.id.emergencyCall) {
            Intent intent = new Intent(this, EmergencyCall.class);
            intent.putExtra("userInformation", usernameStr);
            startActivity(intent);
        } else if (theId == R.id.deleteUser) {
            // Show a confirmation dialog before deleting the user
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete User");
            builder.setMessage("Are you sure you want to delete the user and all contact information?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Delete the user and all contact information from the database
                    contactsDatabase.removeValue();
                    // Go back to MainActivity
                    Intent intent = new Intent(WomenSafetyMain.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        } else if (theId == R.id.logOut) {
            // Go back to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

