package edu.northestern.cs5520_teamproject_iamhere.IamHere.ContactsRecycleView;

public interface ContactsListener {
    void onContactsInfoClick(int position);
    void onUserCallClick(String phoneNumber);
    void onUserDeleteClick(int position);

}
