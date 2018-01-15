package com.start.laundryapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.i("Refreshed token", refreshedToken);

        sendRegistrationToServer(refreshedToken);

    }


    public void sendRegistrationToServer(String token){
        // TODO: add method for sending token to database
    }
}
