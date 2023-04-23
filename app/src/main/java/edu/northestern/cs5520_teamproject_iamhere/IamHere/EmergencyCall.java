package edu.northestern.cs5520_teamproject_iamhere.IamHere;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northestern.cs5520_teamproject_iamhere.R;
import java.util.ArrayList;
import java.util.Random;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class EmergencyCall extends AppCompatActivity {
    private DatabaseReference secondDatabase;
    private String firstPhoneNumber;
    private ShakeDetector shakeDetector;

    private String usernameStr;

    /**
     * The SensorManager
     */
    private SensorManager sensorManager;

    /**
     * The Accelerometer Sensor
     */
    private Sensor accelerometer;

    private final String TAG = "ShakeDetectActivity";



    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panic);

        usernameStr = getIntent().getStringExtra("userInformation");

        FirebaseApp secondary = FirebaseApp.getInstance("secondary");
        secondDatabase = FirebaseDatabase.getInstance(secondary).getReference(usernameStr);
        System.out.println("hihi2: " + secondDatabase);
        secondDatabase.orderByKey().limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                String phoneNumber = firstChild.getValue().toString();
                //String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + firstChild.getValue());
                firstPhoneNumber = "tel:" + phoneNumber;
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        // If the shaker detector is fast, while not yet retrieving data from the database,
        // it will throw null pointer exception.
        // Todo: add a new thread to run in the background.
        // Initialize the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize the accelerometer object
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize the shake detector
        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(firstPhoneNumber));
                startActivity(intent);
            }
        });
    }

    public void callDistress(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(firstPhoneNumber));

        // Initialize the shake detector when click button
//        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
//            @Override
//            public void onShake() {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse(firstPhoneNumber));
//                startActivity(intent); //
//            }
//        });
        startActivity(intent); // button onclick to start the call activity
    }
}