package com.example.marwaadel.shopownertablet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.marwaadel.shopownertablet.model.OfferDataModel;

public class ShowOffer extends AppCompatActivity {
    Toolbar toolbar;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ImageView mTitleView;
    private String mInput;
    private Toolbar mToolbar;
    OfferDataModel offer;
    offerAdapter OfferAdapter;
//    FragmentManager fragmentManager;

    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offer);



    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_offer);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        Intent myIntent = getIntent();
//        handleIntent(myIntent);
//        lv = (ListView) findViewById(R.id.mListView);
//        // DatabaseReference refListName = new DatabaseReference(Constants.FIREBASE_URL_ACTIVE_LIST);
//
//
//        DatabaseReference refListName = FirebaseDatabase.getInstance().getReference();.child("OfferList").child(Constants.My_UID);
//        Query query2 = refListName.orderByChild("Title");
//        OfferAdapter = new offerAdapter(this, OfferDataModel.class, R.layout.offeritem, query2);
//        lv.setAdapter(OfferAdapter);
//        //  Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();//here imp afetr click search
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (OfferAdapter != null) {
//            OfferAdapter.cleanup();
////           finish();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
//
//        //MenuInflater inflater = getMenuInflater();
//        // inflater.inflate(R.menu.activity_main_actions, menu);
//
//
//        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        SearchManager searchManger = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManger.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                doMySearch(newText);
//                return false;
//            }
//        });
//
//        return true;
//    }
//
//    void doMySearch(String query) {
//        lv = (ListView) findViewById(R.id.mListView);
//        // DatabaseReference refListName = new DatabaseReference(Constants.FIREBASE_URL_ACTIVE_LIST);
//        DatabaseReference refListName = FirebaseDatabase.getInstance().getReference();.child("OfferList").child(Constants.My_UID);
//
//        Query query2 = refListName.orderByChild("Title").startAt(query).endAt(query + "\uf8ff");
//
//        OfferAdapter = new offerAdapter(ShowOffer.this, OfferDataModel.class, R.layout.offeritem, query2);
//        lv.setAdapter(OfferAdapter);
//        Toast.makeText(ShowOffer.this, query, Toast.LENGTH_SHORT).show();//here imp afetr click search
//
//
//    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//
//        handleIntent(intent);
//    }
//
//    private void handleIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        if (id == R.id.action_search) {
//            Toast.makeText(ShowOffer.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
//        }
        return super.onOptionsItemSelected(item);
    }
}