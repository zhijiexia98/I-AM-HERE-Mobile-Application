package edu.northestern.cs5520_teamproject_iamhere.IamHere;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northestern.cs5520_teamproject_iamhere.AtYourService.AtYourServiceActivity;
import edu.northestern.cs5520_teamproject_iamhere.IamHere.ContactsRecycleView.ContactsAdapter;
import edu.northestern.cs5520_teamproject_iamhere.IamHere.ContactsRecycleView.ContactsListener;
import edu.northestern.cs5520_teamproject_iamhere.R;


public class AddContacts extends AppCompatActivity implements ContactsListener {
    private EditText contactName;
    private EditText contactPhone;
    private Button confirmAddContactBtn;
    private RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private ContactsListener contactsListener;
    private List<Contacts> contactsList = new ArrayList<>();
    private DatabaseReference secondDatabase;


    private String usernameStr;
    private Boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        confirmAddContactBtn = findViewById(R.id.confirmAddContactBtn);

        usernameStr = getIntent().getStringExtra("userInformation");

        // Create recycler view
        createRecyclerView();

//        // Configure the database:
//        // Since we have two databases, so we have to use its api key to create the db instance
//        // only need to initialize once!!
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setProjectId("i-am-here-137f9")
//                .setApiKey("AIzaSyCN0c-AKAAiUCbzPYM7bYK6pumWCjcw7i0")
//                .setApplicationId("1:234376966350:android:73343deec755bf0950609f")
//                .setDatabaseUrl("https://i-am-here-137f9-default-rtdb.firebaseio.com")
//                .build();
//        FirebaseApp.initializeApp(getApplicationContext(), options, "secondary");
        FirebaseApp secondary = FirebaseApp.getInstance("secondary");

        if (!check) {
            DatabaseReference contactsDatabase = FirebaseDatabase.getInstance(secondary).getReference(usernameStr);
            contactsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                        contactsAdapter.addContact(new Contacts(contactSnapshot.getKey(), contactSnapshot.getValue().toString()));

                    }
                    // contactsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Failed to read value.", error.toException());
                }
            });
            check = true;
        }



        confirmAddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contactName.getText().toString();
                String phone = contactPhone.getText().toString();

                // Add contact to the recycler view
                contactsAdapter.addContact(new Contacts(name, phone));
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Contact has been added successfully", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                // save data to database
                secondDatabase = FirebaseDatabase.getInstance(secondary).getReference(usernameStr);
                secondDatabase.child(name).setValue(phone);
            }
        });


        // delete contact through swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                Contacts deletedContact = contactsList.get(position);
                FirebaseDatabase.getInstance(secondary).getReference(usernameStr)
                        .child(deletedContact.getName()).removeValue();
                Toast.makeText(AddContacts.this, "Contact deleted successfully!", Toast.LENGTH_SHORT).show();
                contactsList.remove(position);
                contactsAdapter.notifyItemRemoved(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(contactsRecyclerView);

    }

    private void createRecyclerView() {
        contactsRecyclerView = findViewById(R.id.contacts_recycler_view);
        contactsRecyclerView.setHasFixedSize(true);
        contactsListener = this;
        contactsAdapter = new ContactsAdapter(contactsList, contactsListener);
        contactsRecyclerView.setAdapter(contactsAdapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    // handle the emergency call
    @Override
    public void onUserCallClick(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            startActivity(intent);
        }

    }


    // handle the contacts info click - to edit
    @Override
    public void onContactsInfoClick(int position) {

    }

    @Override
    public void onUserDeleteClick(int position) {

    }

}
