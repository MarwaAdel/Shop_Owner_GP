package com.example.marwaadel.shopownertablet;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {
    private boolean mTwoPane;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private SimpleAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    String[] mArray;
    int[] mIcons;
    private List<HashMap<String,String>> mList ;
    FragmentManager fragmentManager;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            fragmentManager = getSupportFragmentManager();
//
//
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.replace(R.id.content_frame, new ShowOfferFragment()).commit();
//
//
//
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.activity_main_actions, menu);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mActivityTitle = (String) title;
        getSupportActionBar().setTitle(mActivityTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.offer_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.offer_detail_container, new DetailActivityFragment(),
                                DetailActivityFragment.TAG).commit();
            }
        } else {
            mTwoPane = false;
        }


        mIcons = new int[]{
                R.drawable.offers_sidemenu,
                R.drawable.reports_sidemenu,
                R.drawable.logout_sidemenu
        };


        mArray = getResources().getStringArray(R.array.nav_items);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new MainActivityFragment()).commit();

        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.sidemenu);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void addDrawerItems() {

        mList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<3;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("title",mArray[i]);
            hm.put("icon", Integer.toString(mIcons[i]) );
            mList.add(hm);
        }
        // Keys used in Hashmap
        String[] from = { "icon" , "title" };
        // Ids of views in listview_layout
        int[] to = { R.id.drawer_item_icon , R.id.drawer_item_text };

        mAdapter = new SimpleAdapter(MainActivity.this, mList, R.layout.drawer_item, from, to);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //     Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                selectItem(position);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItem(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                ft.replace(R.id.content_frame, new MainActivityFragment()).commit();
                break;
            case 1:
                ft.replace(R.id.content_frame, new ReportActivityFragment()).commit();
                break;
            case 2:
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(this,FireBaseLoginActivity.class);
                startActivity(in);
               finish();
            default:
                break;
        }

        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        setTitle(mArray[position]);
        mDrawerLayout.closeDrawer(mDrawerList);


    }

}
