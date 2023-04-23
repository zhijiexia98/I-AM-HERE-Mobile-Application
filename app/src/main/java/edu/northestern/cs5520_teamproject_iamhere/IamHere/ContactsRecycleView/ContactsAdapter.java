package edu.northestern.cs5520_teamproject_iamhere.IamHere.ContactsRecycleView;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import edu.northestern.cs5520_teamproject_iamhere.IamHere.Contacts;
import edu.northestern.cs5520_teamproject_iamhere.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private final List<Contacts> contactsList;
    private final ContactsListener contactsListener;
    private Context context;

    public ContactsAdapter(List<Contacts> contactsList, ContactsListener contactsListener) {
        this.contactsList = contactsList;
        this.contactsListener = contactsListener;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_item_card, parent, false);
        return new ContactsViewHolder(view, this.contactsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        Contacts contact = contactsList.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void addContact(Contacts contact) {
        contactsList.add(0, contact);
        notifyItemInserted(0);
    }

    public void clearData() {
        contactsList.clear();
        notifyDataSetChanged();
    }


    public static class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView contactName;
        private final TextView contactPhone;
        private final Button callBtn;
        private final ContactsListener contactsListener;

        public ContactsViewHolder(@NonNull View itemView, ContactsListener contactsListener) {
            super(itemView);
            this.contactsListener = contactsListener;

            contactName = itemView.findViewById(R.id.contactNameTextView);
            contactPhone = itemView.findViewById(R.id.contactPhoneTextView);
            callBtn = itemView.findViewById(R.id.callButton);
            callBtn.setOnClickListener(this);
        }

        public void bind(Contacts contact) {
            contactName.setText(contact.getName());
            contactPhone.setText(contact.getPhoneNumber());
            callBtn.setTag(contact.getPhoneNumber());
        }

        @Override
        public void onClick(View v) {
            String phoneNumber = (String) callBtn.getTag();
            contactsListener.onUserCallClick(phoneNumber);
        }
    }
}
