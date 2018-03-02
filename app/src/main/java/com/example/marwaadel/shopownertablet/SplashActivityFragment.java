package com.example.marwaadel.shopownertablet;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class SplashActivityFragment extends Fragment {
    private static int SPLASH_TIME_OUT = 3000;
    public SplashActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getActivity(), FireBaseLoginActivity.class);
                startActivity(i);

                // close this activity
                getActivity().finish();
            }
        }, SPLASH_TIME_OUT);
        return  rootView;
    }
}
