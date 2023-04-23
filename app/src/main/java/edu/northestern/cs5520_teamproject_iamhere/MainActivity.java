package edu.northestern.cs5520_teamproject_iamhere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import edu.northestern.cs5520_teamproject_iamhere.AtYourService.AtYourServiceActivity;
import edu.northestern.cs5520_teamproject_iamhere.IamHere.IAmHereLoginActivity;
import edu.northestern.cs5520_teamproject_iamhere.IamHere.WomenSafetyMain;
import edu.northestern.cs5520_teamproject_iamhere.StickItToEm.StickItToEmLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        int theId = view.getId();
        if(theId == R.id.at_your_service_button) {
            Intent intent = new Intent(this, AtYourServiceActivity.class);
            startActivity(intent);
        } else if (theId == R.id.stick_it_to_em_button) {
            Intent intent = new Intent(this, StickItToEmLoginActivity.class);
            startActivity(intent);
        } else if (theId == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        } else if (theId == R.id.iamhere) {
            Intent intent = new Intent(this, IAmHereLoginActivity.class);
            startActivity(intent);
        }
    }
}