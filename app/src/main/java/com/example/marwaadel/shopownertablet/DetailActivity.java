package com.example.marwaadel.shopownertablet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();

            arguments.putSerializable(DetailActivityFragment.DETAIL_OFFER,
                    getIntent().getSerializableExtra(DetailActivityFragment.DETAIL_OFFER));

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.offer_detail_container, fragment)
                    .commit();


    }

    }




}

